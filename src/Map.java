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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
 

public class Map extends JFrame {
    
     
    public Map() {
        
        super("Weather Demo");

        
        UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 30));
        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 30));
        UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 30));
         JLabel labelCity = new JLabel("Enter City:");
         JTextField textUsername = new JTextField(10);
         JButton buttonLogin = new JButton("Login");
        
        this.setPreferredSize(new Dimension(800, 400));
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

                // Add ActionListener to the JButton
                buttonLogin.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Retrieve text from JTextField
                        String city = textUsername.getText();
                        // Print the text to console (you can modify this to print to any output)
                        System.out.println("City entered: " + city);
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