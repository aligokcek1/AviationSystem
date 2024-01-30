import java.util.*;


public class AviationSystem {
    static HashMap<String, Airport> airports; // airportCode --> Airport
    static HashMap<String, Airfield> airfields;  // airfieldName --> Airfield
    HashMap<String, ArrayList<Flight>> flights;  // fromAirportCode adj list--> Flight

    public AviationSystem() {
        this.airports = new HashMap<>();
        this.airfields = new HashMap<>();
        this.flights = new HashMap<>();
    }

    //METHODS THAT I USE FOR INITIAL BUILD

    public void addAirfield(Airfield airfield) {
        airfields.put(airfield.airfieldName, airfield);
    }

    public void addAirport(String airfieldName, Airport airport) {
        airports.put(airport.airportCode, airport);
        flights.put(airport.airportCode, new ArrayList<Flight>());
    }

    public void addFlight(String fromCommaTo, String planeName) {
        String[] strArray = fromCommaTo.split(",");
        Airport fromAirport = airports.get(strArray[0]);
        Airport toAirport = airports.get(strArray[1]);
        Flight flight = new Flight(fromAirport, toAirport, planeName);
        ArrayList<Flight> l = flights.get(strArray[0]);
        l.add(flight);
        flights.put(strArray[0], l);
    }

    //DIJKSTRA'S ALGORITHM FOR THE SHORTEST PATH
    private static void dijkstra(HashMap<String, ArrayList<Flight>> flights, String source, String destination, HashMap<String, Double> shortestCosts, HashMap<String, String> previousAirports, long time) {
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(shortestCosts::get));

        for (String airportCode : flights.keySet()) {
            shortestCosts.put(airportCode, Double.MAX_VALUE);
        }

        shortestCosts.put(source, 0.0);
        priorityQueue.add(source);

        while (!priorityQueue.isEmpty()) {
            String currentAirport = priorityQueue.poll();
            if (currentAirport.equals(destination)) {
                return;
            }
            for (Flight flight : flights.get(currentAirport)) {
                double newCost = shortestCosts.get(currentAirport) + costCalculator(flight, time, time);

                if (newCost < shortestCosts.get(flight.airportTo.airportCode)) {
                    shortestCosts.put(flight.airportTo.airportCode, newCost);
                    previousAirports.put(flight.airportTo.airportCode, currentAirport);
                    priorityQueue.add(flight.airportTo.airportCode);
                }
            }
        }
    }

    private static double costCalculator(Flight flight, long timeOrigin, long timeDest) {
        double wmFrom = airfields.get(flight.airportFrom.airfieldIn).weatherMultipliers.get(timeOrigin);
        double wmTo = airfields.get(flight.airportTo.airfieldIn).weatherMultipliers.get(timeDest);
        return flight.costCalculator(wmFrom, wmTo);
    }

    //PATH PRINTERS
    private void printShortestPath(String source, String destination, Map<String, String> previousAirports, Map<String, Double> shortestCosts) {
        List<String> path = new ArrayList<>();
        String currentAirport = destination;

        while (currentAirport != null) {
            path.add(currentAirport);
            currentAirport = previousAirports.get(currentAirport);
        }

        Collections.reverse(path);
        Main.task1_out.println(String.join(" ", path) + " " + String.format("%.5f", shortestCosts.get(destination)));
    }

    public void task1_out(String airportFrom, String airportTo, long timeOrigin) {
        HashMap<String, Double> shortestCosts = new HashMap<>();
        HashMap<String, String> previousAirports = new HashMap<>();
        dijkstra(flights, airportFrom, airportTo, shortestCosts, previousAirports, timeOrigin);
        printShortestPath(airportFrom, airportTo, previousAirports, shortestCosts);

    }


}

