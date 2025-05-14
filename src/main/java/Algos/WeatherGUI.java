package Algos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherGUI extends JFrame {
    private final WeatherService weatherService;
    private final CacheContext context;
    private JTextField cityField;
    private JTextArea weatherDisplay;
    private JComboBox<String> strategyChoice;
    private JLabel statusLabel;
    private JTextArea cacheDisplay;

    public WeatherGUI() {
        super("Weather Cache Application");
        this.weatherService = new WeatherService();
        this.context = new CacheContext(new LRUCache(10));
        initializeGUI();
    }

    private void initializeGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // North Panel - Input and Controls
        JPanel northPanel = createNorthPanel();
        add(northPanel, BorderLayout.NORTH);
        
        // Center Panel - Weather Display
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);
        
        // South Panel - Cache Display
        JPanel southPanel = createSouthPanel();
        add(southPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400));
    }

    private JPanel createNorthPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // City input
        JLabel cityLabel = new JLabel("City:");
        cityField = new JTextField(20);
        JButton getWeatherBtn = new JButton("Get Weather");
        getWeatherBtn.addActionListener(e -> getWeather());

        // Strategy selection
        JLabel strategyLabel = new JLabel("Cache Strategy:");
        String[] strategies = {"LRU Cache", "Random Cache", "Second Chance"};
        strategyChoice = new JComboBox<>(strategies);
        strategyChoice.addActionListener(e -> changeStrategy());

        // Status label
        statusLabel = new JLabel("Ready");
        statusLabel.setForeground(Color.GRAY);

        // Layout components
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(0, 5, 0, 5);
        panel.add(cityLabel, gbc);

        gbc.gridx = 1;
        panel.add(cityField, gbc);

        gbc.gridx = 2;
        panel.add(getWeatherBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.insets = new Insets(10, 5, 0, 5);
        panel.add(strategyLabel, gbc);

        gbc.gridx = 1;
        panel.add(strategyChoice, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3;
        panel.add(statusLabel, gbc);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Weather Information"));

        weatherDisplay = new JTextArea(10, 40);
        weatherDisplay.setEditable(false);
        weatherDisplay.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        panel.add(new JScrollPane(weatherDisplay), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSouthPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Cache Contents"));

        cacheDisplay = new JTextArea(5, 40);
        cacheDisplay.setEditable(false);
        cacheDisplay.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        panel.add(new JScrollPane(cacheDisplay), BorderLayout.CENTER);
        return panel;
    }

    private void getWeather() {
        String city = cityField.getText().trim();
        if (city.isEmpty()) {
            updateStatus("Please enter a city name", Color.RED);
            return;
        }

        try {
            // Try to get from cache first
            String cached = context.get(city);
            boolean cacheHit = cached != null;

            WeatherData weather;
            if (cacheHit) {
                updateStatus("Cache hit for " + city, new Color(0, 150, 0));
                appendToWeatherDisplay("CACHE HIT: " + cached);
            } else {
                updateStatus("Cache miss - fetching fresh data for " + city, Color.BLUE);
                weather = weatherService.getWeatherData(city);
                context.put(city, weather.toString());
                appendToWeatherDisplay("FRESH DATA: " + weather.toString());
            }

            updateCacheDisplay();

        } catch (Exception e) {
            updateStatus("Error: " + e.getMessage(), Color.RED);
        }
    }

    private void changeStrategy() {
        String strategy = (String) strategyChoice.getSelectedItem();
        if (strategy == null) return;

        AlgoCache newStrategy;
        switch (strategy) {
            case "LRU Cache":
                newStrategy = new LRUCache(10);
                break;
            case "Random Cache":
                newStrategy = new RandomCache(10);
                break;
            case "Second Chance":
                newStrategy = new SecondChanceCache(10);
                break;
            default:
                return;
        }

        context.setStrategy(newStrategy);
        updateStatus("Changed to " + strategy, Color.BLUE);
        updateCacheDisplay();
    }

    private void appendToWeatherDisplay(String text) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        weatherDisplay.append("[" + sdf.format(new Date()) + "] " + text + "\n");
        weatherDisplay.setCaretPosition(weatherDisplay.getDocument().getLength());
    }

    private void updateStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    private void updateCacheDisplay() {
        cacheDisplay.setText(context.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WeatherGUI().setVisible(true);
        });
    }
}
