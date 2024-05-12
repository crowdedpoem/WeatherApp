import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.net.URI;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;
import java.awt.*;

public class Map extends JFrame {

    public Map() {

        super("Weather Demo");

        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 30));
        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 30));
        UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 30));
        UIManager.put("Table.font", new Font("Arial", Font.PLAIN, 25));
        UIManager.put("TableHeader.font", new Font("Arial", Font.PLAIN, 18));

        JLabel labelCity = new JLabel("Enter City:");
        JTextField textUsername = new JTextField(10);
        JButton buttonLogin = new JButton("Add City");
        setLayout(new GridLayout(1, 3));

        Middleware mw = new Middleware(
                "resources/cities.txt");

        // DEVELOPMENT
        JPanel dataPanel = new JPanel(new BorderLayout());

        List<City> savedCities = mw.getCities();
        // TODO call api to get actual weather
        WeatherAPI wapi = new WeatherAPI();
        String data[][] = new String[savedCities.size()][2];
        for (int i = 0; i < savedCities.size(); i++) {
            data[i][0] = savedCities.get(i).getName();
            data[i][1] = wapi.test(data[i][0]);
        }

        String column[] = { "City", "Temperature" };

        // Create a DefaultTableModel with the data and column names
        DefaultTableModel model = new DefaultTableModel(data, column) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        JTable weatherTable = new JTable(model);
        weatherTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(weatherTable);

        dataPanel.add(scrollPane, BorderLayout.CENTER);
        add(dataPanel);

        // DEVELOPMENT

        this.setPreferredSize(new Dimension(1000, 400));
        // create a new panel with GridBagLayout manager
        JPanel newPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        newPanel.add(labelCity, constraints);

        constraints.gridx = 1;
        newPanel.add(textUsername, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        newPanel.add(buttonLogin, constraints);

        // TODO on button click, update the "server"
        // TODO update the table
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve text from JTextField
                String city = textUsername.getText();
                // Print the text to console (you can modify this to print to any output)
                System.out.println("City entered: " + city);

                // save entered city
                // TODO get lat lon for city
                mw.saveCity(city);

                List<City> savedCities = mw.getCities();
                WeatherAPI wapi = new WeatherAPI();
                // TODO call api to get actual weather
                String data[][] = new String[savedCities.size()][2];
                for (int i = 0; i < savedCities.size(); i++) {
                    data[i][0] = savedCities.get(i).getName();

                    data[i][1] = wapi.test(data[i][0]);
                }

                String column[] = { "City", "Temperature" };

                // Create a DefaultTableModel with the data and column names
                DefaultTableModel newModel = new DefaultTableModel(data, column) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false; // Make all cells non-editable
                    }
                };
                weatherTable.setModel(newModel); // Set the updated model
                newPanel.revalidate(); // Revalidate the panel containing the table
                newPanel.repaint(); // Repaint the panel
            }
        });

        // set border for the panel
        newPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Type in city"));

        // add the panel to this frame
        add(newPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        // set look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Map().setVisible(true);
            }
        });
    }
}