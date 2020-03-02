package project;

import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.ArrayList;
import java.util.List;

class DataModel {
    public final int vehicleNumber = 1;
    public final int depot = 0;
    public final int[] starts = {0};
    public final int[] ends = {0};
    public final int[][] transMat;
    public final int distPenalty;

    public DataModel(List<GeoLocation> geoLocations) {
        this.transMat = this.makeTransitionMatrix(geoLocations, DataModel.getDistFn());
        this.distPenalty = DataModel.makeDistPenalty(this.transMat);
    }

    public DataModel(List<GeoLocation> geoLocations, BiFunction<Double[], Double[], Integer> distFn) {
        this.transMat = this.makeTransitionMatrix(geoLocations, distFn);
        this.distPenalty = DataModel.makeDistPenalty(this.transMat);
    }

    public DataModel(int[][] transMat) {
        this.transMat = transMat;
        this.distPenalty = DataModel.makeDistPenalty(this.transMat);
    }

    public static int[][] makeTransitionMatrix(List<GeoLocation> geoLocations,
                                               BiFunction<Double[], Double[], Integer> distFn) {
        int n = geoLocations.size();
        int[][] mat = new int[n][n];
        for (int i=0; i < n; i++) {
            for (int j=0; j < n; j++) {
                mat[i][j] = distFn
                    .apply(geoLocations.get(i).getCoord(), geoLocations.get(j).getCoord());
            }
        }
        return mat;
    }

    public static BiFunction<Double[], Double[], Integer> getDistFn() {
        return (pointA, pointB) -> {
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
            return (int)(R * c);
        };
    }

    public static int makeDistPenalty(int[][] transMat) {
        // Sum distances after diagonal in transition matrix
        int n = transMat.length;
        int penalty = 0;
        for (int i=1; i < n; i++) {
            for (int j=i+1; j < n; j++) {
                penalty += transMat[i][j];
            }
        }
        return penalty;
    }
}
