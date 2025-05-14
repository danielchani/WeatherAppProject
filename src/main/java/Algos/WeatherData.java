package Algos;

public class WeatherData {
    private final String city;
    private final double temperature;
    private final double humidity;
    private final String description;
    private final long timestamp;

    public WeatherData(String city, double temperature, double humidity, String description) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.description = description;
        this.timestamp = System.currentTimeMillis();
    }

    public String getCity() { return city; }
    public double getTemperature() { return temperature; }
    public double getHumidity() { return humidity; }
    public String getDescription() { return description; }
    public long getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("%s: %.1f°C, Humidity: %.1f%%, %s", 
            city, temperature, humidity, description);
    }
}
