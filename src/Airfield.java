import java.util.HashMap;

public class Airfield {
    String airfieldName;
    HashMap<Long, Double> weatherMultipliers;

    public Airfield(String airfieldName) {
        this.airfieldName = airfieldName;
        weatherMultipliers = new HashMap<>();
    }


    public double getWeatherMultiplier(int weatherCode){
        String binaryString = String.format("%5s", Integer.toBinaryString(weatherCode)).replace(" ","0");
        int Bw = Integer.parseInt(String.valueOf(binaryString.charAt(0)));
        int Br = Integer.parseInt(String.valueOf(binaryString.charAt(1)));
        int Bs = Integer.parseInt(String.valueOf(binaryString.charAt(2)));
        int Bh = Integer.parseInt(String.valueOf(binaryString.charAt(3)));
        int Bb = Integer.parseInt(String.valueOf(binaryString.charAt(4)));
        double weatherMultiplier = (Bw*1.05+(1-Bw)) * (Br*1.05+(1-Br)) * (Bs*1.1+(1-Bs)) * (Bh*1.15+(1-Bh)) * (Bb*1.2+(1-Bb));
        return weatherMultiplier;
    }
}
