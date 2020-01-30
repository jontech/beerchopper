package project;

class GeoLocation {
    int id;
    Double latitude;
    Double longitude;

    public GeoLocation(int id, Double latitude, Double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double[] getCoord() {
        return new Double[] { this.latitude, this.longitude };
    }
}
