package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;
     
public class App {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Double[]> geoLocations = new ArrayList<>();
        try {
            conn = DriverManager.getConnection("jdbc:mysql://db/beerchopper?user=root&password=123");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM geo_location");
            while(rs.next()) {
                geoLocations.add(new Double[] { Double.parseDouble(rs.getString("latitude")),
                                                Double.parseDouble(rs.getString("longitude")) });
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        Double[][] trans = App.transitionMatrix(geoLocations, App.getDistFunction());
        System.out.println(trans.length);
    }

    public static Function<Double[], Function<Double[], Double>> getDistFunction() {
        return pointA -> pointB -> {
            Function<Double, Double> toRad = a -> a * Math.PI / 180;
            final int R = 6371; // Radious of the earth
            Double latDistance = toRad.apply(pointB[0]-pointA[0]);
            Double lonDistance = toRad.apply(pointB[1]-pointA[1]);
            Double a = Math.sin(latDistance / 2)
                * Math.sin(latDistance / 2)
                + Math.cos(toRad.apply(pointA[0]))
                * Math.cos(toRad.apply(pointB[0]))
                * Math.sin(lonDistance / 2)
                * Math.sin(lonDistance / 2);
            Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            return R * c;
        };
    }

    public static Double[][] transitionMatrix(List<Double[]> geoLocations,
                                              Function<Double[], Function<Double[], Double>> distFn) {
        int n = geoLocations.size();
        Double[][] mat;
        mat = new Double[n][n];
        for (int i=0; i < n; i++) {
            for (int j=0; j < n; j++) {
                mat[i][j] = distFn.apply(geoLocations.get(i)).apply(geoLocations.get(j));
            }
        }
        return mat;
    }
}
