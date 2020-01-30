package project;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.sql.SQLException;

public class DBTest {
    @Test public void testGetGeoLocations() throws SQLException {
        DB db = new DB("jdbc:mysql://db/beerchopper?user=root&password=123");
        List<GeoLocation> geoLocations = db.getGeoLocations();

        assertEquals(1304, geoLocations.size());
    }
}