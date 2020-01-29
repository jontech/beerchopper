package project;

import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

class DataModel {
    public final int vehicleNumber = 1;
    public final int depot = 0;
    public final int[] starts = {1};
    public final int[] ends = {1};
    public Long[][] transMat = null;
    
    public DataModel(List<Double[]> geoLocations) {
        this.transMat = this.makeTransitionMatrix(geoLocations, this.getDistFunction());        
    }

    public DataModel(Long[][] transMat) {
        this.transMat = transMat;
    }

    public static Function<Double[], Function<Double[], Long>> getDistFunction() {
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
            return Double.valueOf(R * c).longValue();
        };
    }

    public static Long[][] makeTransitionMatrix(List<Double[]> geoLocations,
                                              Function<Double[], Function<Double[], Long>> distFn) {
        int n = geoLocations.size();
        Long[][] mat = new Long[n][n];
        for (int i=0; i < n; i++) {
            for (int j=0; j < n; j++) {
                mat[i][j] = distFn.apply(geoLocations.get(i)).apply(geoLocations.get(j));
            }
        }
        return mat;
    }
}
