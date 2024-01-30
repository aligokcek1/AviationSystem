public class Airport {
    String airportCode;
    String airfieldIn;
    double latitude;
    double longitude;
    double parkingCost;

    public Airport(String airportCode, String airfieldName, double latitude, double longitude, double parkingCost) {
        this.airportCode = airportCode;
        this.airfieldIn = airfieldName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.parkingCost = parkingCost;
    }
}
