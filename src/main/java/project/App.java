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

        Double[][] trans = App.transitionMatrix(geoLocations, pairA -> pairB -> 0.0);
        System.out.println(trans.length);
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
