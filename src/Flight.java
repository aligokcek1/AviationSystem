public class Flight {  // (MISSION)
    Airport airportFrom;
    Airport airportTo;
    double distance;
    String planeName;
    long duration;

    Flight(Airport airportFrom, Airport airportTo, String planeName){
        this.airportFrom = airportFrom;
        this.airportTo = airportTo;
        this.distance = distanceCalculator();
        this.planeName = planeName;
        this.duration = durationCalculator();
    }


    private double distanceCalculator(){
        double lat1 = airportFrom.latitude;
        double lat2 = airportTo.latitude;
        double lon1 = airportFrom.longitude;
        double lon2 = airportTo.longitude;
        double dum1 = Math.toRadians((lat2 - lat1) / 2);
        double dum2 = Math.toRadians((lon2 - lon1) / 2);
        double dum3 = Math.pow(Math.sin(dum1), 2);
        double dum4 = Math.cos(Math.toRadians(lat1));
        double dum5 = Math.cos(Math.toRadians(lat2));
        double dum6 = Math.pow(Math.sin(dum2), 2);
        double sqrt = Math.sqrt(dum3 + dum4  * dum5 * dum6);
        return 2 * 6371 * Math.asin(sqrt);
    }

    private long durationCalculator(){
        switch (planeName){
            case "Carreidas 160":
                if (distance<=175){
                    return 21600;
                }
                else if (distance<=350){
                    return 2*21600;
                }
                else if (distance>350){
                    return 3*21600;
                }
            case "T-16 Skyhopper":
                if (distance <= 2500){
                    return 21600;
                }
                else if (distance <= 5000){
                    return 2*21600;
                }
                else if (distance >5000){
                    return 3*21600;
                }
            case "Skyfleet S570":
                if (distance <= 500){
                    return 21600;
                }
                else if (distance <= 1000){
                    return 2*21600;
                }
                else if (distance >1000){
                    return 3*21600;
                }
            case "Orion III":
                if (distance <= 1500){
                    return 21600;
                }
                else if (distance <= 3000){
                    return 2*21600;
                }
                else if (distance >3000){
                    return 3*21600;
                }
        }
        return 0;
    }

    public double costCalculator(double wmDeparture, double wmLanding){
        return 300*wmDeparture*wmLanding + distance;
    }

}
