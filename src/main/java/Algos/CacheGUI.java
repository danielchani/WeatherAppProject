package Algos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CacheGUI extends JFrame {
    private CacheContext context;
    private JTextArea displayArea;
    private JTextField keyField;
    private JTextField valueField;
    private JComboBox<String> algoChoice;

    public CacheGUI() {
        super("Cache Algorithm Demo");
        initializeGUI();
    }

    private void initializeGUI() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create top panel with algorithm choice
        JPanel topPanel = new JPanel();
        String[] algorithms = {"LRU Cache", "Random Cache", "Second Chance Cache"};
        algoChoice = new JComboBox<>(algorithms);
        algoChoice.addActionListener(e -> switchAlgorithm());
        topPanel.add(new JLabel("Algorithm: "));
        topPanel.add(algoChoice);
        add(topPanel, BorderLayout.NORTH);

        // Create input panel
        JPanel inputPanel = new JPanel();
        keyField = new JTextField(10);
        valueField = new JTextField(10);
        JButton putButton = new JButton("Put");
        JButton getButton = new JButton("Get");

        inputPanel.add(new JLabel("Key:"));
        inputPanel.add(keyField);
        inputPanel.add(new JLabel("Value:"));
        inputPanel.add(valueField);
        inputPanel.add(putButton);
        inputPanel.add(getButton);

        add(inputPanel, BorderLayout.CENTER);

        // Create display area
        displayArea = new JTextArea(10, 40);
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        // Initialize with LRU Cache
        context = new CacheContext(new LRUCache(3));

        // Add button listeners
        putButton.addActionListener(e -> {
            String key = keyField.getText();
            String value = valueField.getText();
            if (!key.isEmpty() && !value.isEmpty()) {
                context.put(key, value);
                displayArea.append("Put: " + key + " -> " + value + "\n");
                displayArea.append("Cache: " + context.toString() + "\n");
                keyField.setText("");
                valueField.setText("");
            }
        });

        getButton.addActionListener(e -> {
            String key = keyField.getText();
            if (!key.isEmpty()) {
                String value = context.get(key);
                displayArea.append("Get: " + key + " -> " + value + "\n");
                displayArea.append("Cache: " + context.toString() + "\n");
                keyField.setText("");
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void switchAlgorithm() {
        int capacity = 3;
        switch (algoChoice.getSelectedIndex()) {
            case 0:
                context.setStrategy(new LRUCache(capacity));
                break;
            case 1:
                context.setStrategy(new RandomCache(capacity));
                break;
            case 2:
                context.setStrategy(new SecondChanceCache(capacity));
                break;
        }
        displayArea.append("\nSwitched to " + algoChoice.getSelectedItem() + "\n");
    }
}
