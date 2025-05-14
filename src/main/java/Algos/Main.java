package Algos;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Weather Cache Application...");
        String mode = args.length > 0 ? args[0] : "gui";
        
        switch (mode.toLowerCase()) {
            case "console":
                System.out.println("Running console demo...");
                runConsoleDemo();
                break;
            case "gui":
                System.out.println("Starting Weather GUI...");
                runGUI();
                break;
            case "server":
                System.out.println("Starting Weather Cache Server...");
                runServer();
                break;
            case "client":
                System.out.println("Starting Weather Cache Client...");
                runClient();
                break;
            default:
                System.out.println("Usage: java -cp target/classes Algos.Main [console|gui|server|client]");
        }
    }

    private static void runConsoleDemo() {
        try {
            System.out.println("Weather Cache Demo");
            System.out.println("-----------------");

            WeatherService service = new WeatherService();
            CacheContext context = new CacheContext(new LRUCache(3));

            // Demo with different cities
            String[] cities = {"Tel Aviv", "Jerusalem", "Haifa", "Eilat"};
            
            System.out.println("\nTesting LRU Cache:");
            for (String city : cities) {
                WeatherData weather = service.getWeatherData(city);
                System.out.println("Adding " + city);
                context.put(city, weather.toString());
                System.out.println("Cache state: " + context.toString());
            }

            // Test cache hit
            System.out.println("\nTrying to get Haifa (should be in cache):");
            String cached = context.get("Haifa");
            System.out.println(cached != null ? "Found: " + cached : "Not found");

            // Test cache miss
            System.out.println("\nTrying to get Tel Aviv (should be evicted):");
            cached = context.get("Tel Aviv");
            System.out.println(cached != null ? "Found: " + cached : "Not found");

            // Demo Random Cache
            System.out.println("\nSwitching to Random Cache:");
            context.setStrategy(new RandomCache(3));
            for (String city : cities) {
                WeatherData weather = service.getWeatherData(city);
                System.out.println("Adding " + city);
                context.put(city, weather.toString());
                System.out.println("Cache state: " + context.toString());
            }

            // Demo Second Chance
            System.out.println("\nSwitching to Second Chance Cache:");
            context.setStrategy(new SecondChanceCache(3));
            for (String city : cities) {
                WeatherData weather = service.getWeatherData(city);
                System.out.println("Adding " + city);
                context.put(city, weather.toString());
                if (city.equals("Haifa")) {
                    System.out.println("Accessing Haifa again to give it a second chance");
                    context.get("Haifa");
                }
                System.out.println("Cache state: " + context.toString());
            }
        } catch (Exception e) {
            System.err.println("Error in demo: " + e.getMessage());
        }
    }

    private static void runGUI() {
        javax.swing.SwingUtilities.invokeLater(() -> new WeatherGUI().setVisible(true));
    }

    private static void runServer() {
        new CacheServer().start();
    }

    private static void runClient() {
        new CacheClient().start();
    }
}