import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class Screen extends JFrame {
    DatabaseAPI mw;
    public Screen() {

        super("Weather Demo");
        this.setPreferredSize(new Dimension(1000, 400));

        // font sizing
        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 30));
        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 30));
        UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 30));
        UIManager.put("Table.font", new Font("Arial", Font.PLAIN, 25));
        UIManager.put("TableHeader.font", new Font("Arial", Font.PLAIN, 18));

        mw = new DatabaseAPI("resources/cities.txt");

        // create table data
        JPanel dataPanel = new JPanel(new BorderLayout());
        DefaultTableModel mod = createModelForTable();
        JTable weatherTable = new JTable(mod);
        weatherTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(weatherTable);
        dataPanel.add(scrollPane, BorderLayout.CENTER);
        add(dataPanel);

        
        // create input panel
        // create components for panel
        JLabel labelCity = new JLabel("Enter City:");
        JTextField textUsername = new JTextField(10);
        JButton addCityButton = new JButton("Add City");
        JButton removeCityButton = new JButton("Remove City");
        JButton refreshDataButton = new JButton("Refresh Data");

        setLayout(new GridLayout(1, 3));
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
        constraints.anchor = GridBagConstraints.EAST;
        newPanel.add(addCityButton, constraints);
        constraints.anchor = GridBagConstraints.WEST;
        newPanel.add(removeCityButton, constraints);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        newPanel.add(refreshDataButton, constraints);

        removeCityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = textUsername.getText();
                // save entered city
                System.out.println("delete button hit");
                mw.deleteCity(city);
                // update table
                DefaultTableModel newModel = createModelForTable();
                weatherTable.setModel(newModel);
                newPanel.revalidate();
                newPanel.repaint();
            }
        });

        addCityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = textUsername.getText();
                // save entered city
                mw.saveCity(city);
                // update table
                DefaultTableModel newModel = createModelForTable();
                weatherTable.setModel(newModel);
                newPanel.revalidate();
                newPanel.repaint();
            }
        });

        refreshDataButton.addActionListener(new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent e) {
                    // update table
                    DefaultTableModel newModel = createModelForTable();
                    weatherTable.setModel(newModel);
                    newPanel.revalidate();
                    newPanel.repaint();
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

    public DefaultTableModel createModelForTable(){
                List<String> savedCities = mw.getCities();
                WeatherAPI wapi = new WeatherAPI();
                // TODO call api to get actual weather
                String data[][] = new String[savedCities.size()][2];
                for (int i = 0; i < savedCities.size(); i++) {
                    String cityName = savedCities.get(i);
                    data[i][0] = cityName;
                    data[i][1] = wapi.getWeatherFor(cityName);
                }
                String column[] = { "City", "Temperature" };

                DefaultTableModel newModel = new DefaultTableModel(data, column) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                return newModel;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Screen().setVisible(true);
            }
        });
    }
}