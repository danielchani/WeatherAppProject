package Algos;

import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

public class CacheServer {
    private static final int PORT = 5000;
    private static final int CACHE_SIZE = 10;
    private CacheContext context;
    private final ConcurrentHashMap<String, Long> accessTimes;

    public CacheServer() {
        this.context = new CacheContext(new LRUCache(CACHE_SIZE));
        this.accessTimes = new ConcurrentHashMap<>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Weather Cache Server is listening on port " + PORT);
            System.out.println("Current strategy: LRU");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    String request = in.readLine();
                    if (request == null) continue;

                    String[] parts = request.split(" ", 3);
                    String response;

                    switch (parts[0].toUpperCase()) {
                        case "PUT":
                            if (parts.length == 3) {
                                context.put(parts[1], parts[2]);
                                accessTimes.put(parts[1], System.currentTimeMillis());
                                response = "OK";
                            } else {
                                response = "ERROR: Invalid PUT format";
                            }
                            break;

                        case "GET":
                            if (parts.length == 2) {
                                String value = context.get(parts[1]);
                                if (value != null) {
                                    accessTimes.put(parts[1], System.currentTimeMillis());
                                    response = value;
                                } else {
                                    response = "NOT_FOUND";
                                }
                            } else {
                                response = "ERROR: Invalid GET format";
                            }
                            break;

                        case "STRATEGY":
                            if (parts.length == 2) {
                                response = changeStrategy(parts[1]);
                            } else {
                                response = "ERROR: Invalid STRATEGY format";
                            }
                            break;

                        case "LIST":
                            response = context.toString();
                            break;

                        default:
                            response = "ERROR: Unknown command";
                    }

                    out.println(response);
                    System.out.println("Processed request: " + request + " -> " + response);

                } catch (IOException e) {
                    System.err.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + PORT);
        }
    }

    private String changeStrategy(String strategyName) {
        AlgoCache newStrategy;
        switch (strategyName.toLowerCase()) {
            case "lru":
                newStrategy = new LRUCache(CACHE_SIZE);
                break;
            case "random":
                newStrategy = new RandomCache(CACHE_SIZE);
                break;
            case "secondchance":
                newStrategy = new SecondChanceCache(CACHE_SIZE);
                break;
            default:
                return "ERROR: Unknown strategy";
        }
        
        context.setStrategy(newStrategy);
        System.out.println("Changed to " + strategyName + " strategy");
        return "OK: Strategy changed to " + strategyName;
    }
}
