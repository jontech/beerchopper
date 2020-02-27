package project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;
import java.sql.SQLException;

 @SpringBootTest(classes = Config.class)
 public class DBTest {
     @Autowired private DB db;

     @Test public void testGetGeoLocations() throws SQLException {
         List<GeoLocation> geoLocations =
             this.db.getGeoLocations(32.891998291015625, -117.14399719238281);

         assertEquals(73, geoLocations.size());

         GeoLocation depot = geoLocations.get(0);
         assertEquals(32.891998291015625, depot.latitude, 0);
         assertEquals(-117.14399719238281, depot.longitude, 0);
     }

     @Test public void testGetBreweriesByIds() throws SQLException {
         List<Integer> ids = Arrays.asList(1, 2, 3);
         List<Brewery> breweries = this.db.getBreweries(ids);

         assertEquals(3, breweries.size());
     }

     @Test public void testGetBeerKinds() throws SQLException {
         List<Integer> breweryIds = Arrays.asList(1,2,3);
         List<BeerKind> beerKinds = this.db.getBreweriesBeerKinds(breweryIds);

         assertEquals(21, beerKinds.size());
     }
}
