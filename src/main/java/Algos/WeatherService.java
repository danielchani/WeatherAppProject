package Algos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class WeatherService {
    private static final String API_KEY = "your-api-key"; // Replace with actual API key
    private final Random random = new Random(); // For demo purposes

    public WeatherData getWeatherData(String city) throws Exception {
        // In a real application, we would make an API call here
        // For demo purposes, we'll generate random weather data
        return simulateWeatherData(city);
    }

    private WeatherData simulateWeatherData(String city) {
        // Simulate realistic temperature range (10-30°C)
        double temperature = 10 + random.nextDouble() * 20;
        // Simulate realistic humidity range (30-90%)
        double humidity = 30 + random.nextDouble() * 60;
        
        String[] descriptions = {
            "Sunny", "Partly Cloudy", "Cloudy", "Light Rain", "Heavy Rain",
            "Thunderstorm", "Clear Sky", "Overcast"
        };
        String description = descriptions[random.nextInt(descriptions.length)];
        
        return new WeatherData(city, temperature, humidity, description);
    }

    // For future implementation with real API
    private String makeApiCall(String city) throws Exception {
        String urlString = String.format("https://api.weatherapi.com/v1/current.json?key=%s&q=%s", 
            API_KEY, city);
        
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        
        return response.toString();
    }
}
