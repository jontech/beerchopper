package project;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class AppTest {
    App app = new App();
    
    @Test public void testBuildTransitionMatrix() {
        List<Double[]> geoLocations = new ArrayList<>();
        geoLocations.add(new Double[] {30.223400115966797, -97.76969909667969});
        geoLocations.add(new Double[] {37.782501220703125, -122.39299774169922});
        geoLocations.add(new Double[] {50.76679992675781, 4.30810022354126});

        Double[][] trans = this.app.transitionMatrix(geoLocations, pairA -> pairB -> 1.1);

        assertEquals(3, trans.length);
        assertEquals(3, trans[0].length);
        assertEquals(1.1, trans[0][0], 0.001);
    }
}
