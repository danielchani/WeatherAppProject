package Algos;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CacheClient {
    private static final String HOST = "localhost";
    private static final int PORT = 5000;
    private final WeatherService weatherService;

    public CacheClient() {
        this.weatherService = new WeatherService();
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Weather Cache Client");
            System.out.println("Commands: ");
            System.out.println("  weather <city> - Get weather for a city");
            System.out.println("  cache <city> - Check if city data is in cache");
            System.out.println("  strategy <type> - Change cache strategy (lru/random/secondchance)");
            System.out.println("  exit - Exit the application");

            while (true) {
                System.out.print("> ");
                String line = scanner.nextLine().trim();
                
                if (line.equals("exit")) {
                    break;
                }

                String[] parts = line.split(" ", 2);
                if (parts.length < 2 && !line.startsWith("list")) {
                    System.out.println("Invalid command format");
                    continue;
                }

                switch (parts[0].toLowerCase()) {
                    case "weather":
                        getWeather(parts[1]);
                        break;
                    case "cache":
                        checkCache(parts[1]);
                        break;
                    case "strategy":
                        changeStrategy(parts[1]);
                        break;
                    case "list":
                        sendRequest("LIST");
                        break;
                    default:
                        System.out.println("Unknown command");
                }
            }
        }
    }

    private void getWeather(String city) {
        try {
            // First try to get from cache
            String cached = sendRequest("GET " + city);
            
            if (cached != null && !cached.equals("NOT_FOUND")) {
                System.out.println("Cache hit! " + cached);
                return;
            }

            // If not in cache, get fresh data
            System.out.println("Cache miss - fetching fresh data...");
            WeatherData weather = weatherService.getWeatherData(city);
            
            // Store in cache
            sendRequest("PUT " + city + " " + weather.toString());
            System.out.println("Fresh data: " + weather);
            
        } catch (Exception e) {
            System.err.println("Error getting weather: " + e.getMessage());
        }
    }

    private void checkCache(String city) {
        String response = sendRequest("GET " + city);
        if (response != null && !response.equals("NOT_FOUND")) {
            System.out.println("Found in cache: " + response);
        } else {
            System.out.println("Not found in cache");
        }
    }

    private void changeStrategy(String strategy) {
        String response = sendRequest("STRATEGY " + strategy);
        System.out.println("Strategy change response: " + response);
    }

    private String sendRequest(String request) {
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(request);
            return in.readLine();

        } catch (IOException e) {
            System.err.println("Error communicating with server: " + e.getMessage());
            return null;
        }
    }
}
