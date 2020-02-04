package project;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;
import java.sql.SQLException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
public class DBTest {
    @Autowired private DB db;

    @Test public void testGetGeoLocations() throws SQLException {
        List<GeoLocation> geoLocations = this.db.getGeoLocations();

        assertEquals(1304, geoLocations.size());
    }

    @Test public void testGetBreweriesByIds() throws SQLException {
        List<Integer> ids = Arrays.asList(1, 2, 3);
        List<Brewery> breweries = this.db.getBreweries(ids);

        assertEquals(3, breweries.size());
    }
}
