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


class DB {
    private Connection conn = null;

    private static final Logger logger = Logger.getLogger(DB.class.getName());
    
    public DB(String url) throws SQLException {
        this.conn = DriverManager.getConnection(url);
    }

    public <T> List<T> query(String q, Function<ResultSet, T> unpackFn) {
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
