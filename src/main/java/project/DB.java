package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Logger;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

class DB {
    private Connection conn = null;

    private static final Logger logger = Logger.getLogger(DB.class.getName());
    
    public DB(String url) throws SQLException {
        this.conn = DriverManager.getConnection(url);
    }

    public List<GeoLocation> getGeoLocations() {
        return this.query("SELECT * FROM geo_location",
                 rs -> {
                     try {
                         return new GeoLocation(rs.getInt("id"),
                                                Double.parseDouble(rs.getString("latitude")), 
                                                Double.parseDouble(rs.getString("longitude")));
                     } catch (SQLException e) {
                         throw new RuntimeException(e);
                     }
                 });
    }

    public List<Brewery> getBreweries(List<Integer> ids) {
        String joined =
            ids.stream().map(Object::toString).collect(Collectors.joining(","));

        return this.query(String.format("SELECT * FROM brewery WHERE id IN (%s)", joined),
                 rs -> {
                     try {
                         return new Brewery(rs.getInt("id"),
                                            rs.getString("name"),
                                            rs.getString("country"));
                     } catch (SQLException e) {
                         throw new RuntimeException(e);
                     }
                 });
    }
    
    private <T> List<T> query(String q, Function<ResultSet, T> unpackFn) {
        Statement stmt = null;
        ResultSet rs = null;
        List<T> result = new ArrayList<>();
        try {
            stmt = this.conn.createStatement();
            rs = stmt.executeQuery(q);
            while (rs.next()) {
                result.add(unpackFn.apply(rs));
            }
        } catch (SQLException e ) {
            logger.info(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } // ignore
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore
            }
        }
        
        return result;
    }
}
