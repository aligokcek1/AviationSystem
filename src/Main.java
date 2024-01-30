//Ali GOKCEK    29.12.2023

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static PrintWriter task1_out;
    public static void main(String[] args) throws FileNotFoundException {
        Locale.setDefault(Locale.US);

        // TAKE INPUT FILE LOCATION
        String airportFileLoc = args[0];
        String directionsFileLoc = args[1];
        String weatherFileLoc = args[2];
        String missionsFileLoc = args[3];
        String outputFileLoc = args[4];


        AviationSystem aviationSystem = new AviationSystem();

        // READ AIRPORT FILE
        File airportFile = new File(airportFileLoc);
        Scanner airportSc = new Scanner(airportFile);
        airportSc.nextLine();
        while(airportSc.hasNextLine()){
            String[] lineArray = airportSc.nextLine().split(",");
            String airportCode = lineArray[0];
            String airfieldName = lineArray[1];
            double latitude = Double.parseDouble(lineArray[2]);
            double longitude = Double.parseDouble(lineArray[3]);
            double parkingCost = Double.parseDouble(lineArray[4]);
            airfieldCreator(aviationSystem, airfieldName);
            airportCreator(aviationSystem, airportCode, airfieldName, latitude, longitude, parkingCost);
        }
        airportSc.close();

        // READ WEATHER FILE
        File weatherFile = new File(weatherFileLoc);
        Scanner weatherSc = new Scanner(weatherFile);
        weatherSc.nextLine();
        while(weatherSc.hasNextLine()){
            String[] lineArray = weatherSc.nextLine().split(",");
            String airfieldName = lineArray[0];
            long time = Long.parseLong(lineArray[1]);
            int weatherCode = Integer.parseInt(lineArray[2]);
            weatherCreator(aviationSystem, airfieldName, time, weatherCode);
        }
        weatherSc.close();

        // READ PLANE MODEL
        File missionsFile = new File(missionsFileLoc);
        Scanner missionSc = new Scanner(missionsFile);
        String planeName = missionSc.nextLine().strip();

        // READ DIRECTIONS FILE
        File directionsFile = new File(directionsFileLoc);
        Scanner directionSc = new Scanner(directionsFile);
        directionSc.nextLine();
        while (directionSc.hasNextLine()){
            String line = directionSc.nextLine();
            directionCreator(aviationSystem, line, planeName);
        }
        directionSc.close();

        //PRINTWRITER
        task1_out = new PrintWriter(outputFileLoc);

        // READ MISSIONS FILE
        while (missionSc.hasNextLine()){
            String line = missionSc.nextLine();
            String[] dumArray = line.split(" ");
            long timeOrigin = Long.parseLong(dumArray[2]);
            long deadline = Long.parseLong(dumArray[3]);
            String from = dumArray[0];
            String to = dumArray[1];
            task1Printer(aviationSystem,from,to,timeOrigin,deadline);
        }
        task1_out.close();
        missionSc.close();
    }
    private static void airfieldCreator(AviationSystem aviationSystem, String airfieldName){
        if (aviationSystem.airfields.containsKey(airfieldName)){
            return;
        }
        Airfield airfield = new Airfield(airfieldName);
        aviationSystem.addAirfield(airfield);
    }
    private static void airportCreator(AviationSystem aviationSystem, String airportCode, String airfieldName, double latitude, double longitude, double parkingCost){
        Airport airport = new Airport(airportCode, airfieldName, latitude, longitude, parkingCost);
        aviationSystem.addAirport(airfieldName, airport);
    }
    private static void weatherCreator(AviationSystem aviationSystem, String airfieldName, long time, int weatherCode){
        if (!aviationSystem.airfields.containsKey(airfieldName)){
            return;
        }
        Airfield airfield = aviationSystem.airfields.get(airfieldName);
        double weatherMultiplier = airfield.getWeatherMultiplier(weatherCode);
        airfield.weatherMultipliers.put(time, weatherMultiplier);
    }
    private static void directionCreator(AviationSystem aviationSystem, String fromTo, String planeName){
        aviationSystem.addFlight(fromTo, planeName);
    }

    private static void task1Printer(AviationSystem aviationSystem, String from, String to, long timeOrigin, long deadline){
        aviationSystem.task1_out(from, to, timeOrigin);
    }
}
