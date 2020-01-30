package project;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class DataModelTest {
    @Test public void testBuildTransitionMatrix() {
        List<GeoLocation> geoLocations =
            Arrays.asList(new GeoLocation(1, 30.223400115966797, -97.76969909667969),
                          new GeoLocation(2, 37.782501220703125, -122.39299774169922),
                          new GeoLocation(3, 50.76679992675781, 4.30810022354126));

        Long[][] transMat = DataModel.makeTransitionMatrix(geoLocations, pointA -> pointB -> 1L);

        assertEquals(3, transMat.length);
        assertEquals(3, transMat[0].length);
        assertEquals(1L, transMat[0][0], 0);
    }

    @Test public void testGeoLocationDistance() {
        Double[] pointA = {30.223400115966797, -97.76969909667969};
        Double[] pointB = {37.782501220703125, -122.39299774169922};

        long res = DataModel.getDistFunction().apply(pointA).apply(pointB);

        assertEquals(2411, res, 0);
    }
}
