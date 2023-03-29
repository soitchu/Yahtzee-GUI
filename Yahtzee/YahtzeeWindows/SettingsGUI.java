/**
 * Used to make the settings window
 * CPSC 224
 * Final project
 * No sources to cite.
 * 
 * @author Team Full House
 * @version 1.4.0 12/7/2022
 */

package Yahtzee.YahtzeeWindows;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import Yahtzee.YahtzeeGUIComponents.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class SettingsGUI extends WindowScene{
    SettingsGUI(YahtzeeWindow self, boolean hideButtons){
        // Setting up the UI
        
        ArrayList<JTextField> fields = new ArrayList<JTextField>();
        parent.add(Box.createVerticalGlue());       
        fields.add(this.createSettingComponents("Number of dice: ", parent, self.numberDice));
        fields.add(this.createSettingComponents("Retries: ", parent, self.retries));
        fields.add(this.createSettingComponents("Number of Players: ", parent, self.playerNum));
        fields.add(this.createSettingComponents("Number of CPU players: ", parent, self.CPUPlayerNum));

        JPanel buttonCon = new JPanel();

        parent.setLayout(new BoxLayout(parent, BoxLayout.PAGE_AXIS));
        parent.setOpaque(false);

        buttonCon.setLayout(new FlowLayout());
        buttonCon.setOpaque(false);
        SettingsGUI settingSelf = this;

        if(!hideButtons){

            Font font = new Font("Times New Roman", Font.PLAIN, 15);

            JButton exit = new RoundedButton("Exit", buttonCon, "#ffffff", 20);
            exit.setFont(font);
            JButton saveAndExit = new RoundedButton("Save and Exit", buttonCon, "#ffffff", 20);
            saveAndExit.setFont(font);
            saveAndExit.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    settingSelf.applyConfig(self, fields);
                    self.openHomeScreen();
                }
            });


            exit.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    self.openHomeScreen();
                }
            });

        }else{
            JButton play = new RoundedButton("Play", buttonCon, "#ffffff", 20);
            //play.setForeground(Color.white);
            Font font = new Font("Times New Roman", Font.PLAIN, 15);
            play.setFont(font);

            play.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    settingSelf.applyConfig(self, fields);
                    self.openGameWindow();
                }
            });
        }
        parent.add(buttonCon);
        parent.add(Box.createVerticalGlue());      
            
    }
    
    /**
     * Making a text box Component
     * @param name The string next to the text box
     * @param parent The parent element where the text box
     * will be added
     * @param defaultValue The default value of the text box
     * @return The textbox Component
     */
    public JTextField createSettingComponents(String name, JPanel parent, int defaultValue) {
        JLabel label = new JLabel(name);
        label.setForeground(Color.white);
        Font font = new Font("Times New Roman", Font.BOLD, 15);
        label.setFont(font);
        JTextField textField = new JTextField(10);
        textField.setOpaque(false);
        textField.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel textFieldCon = new RoundedPanel(15, Color.white);
        textFieldCon.setOpaque(false);
        textFieldCon.setLayout(new FlowLayout());
        textFieldCon.add(textField);

        textField.setText(String.valueOf(defaultValue));
        JPanel settingsConText = new JPanel();
        settingsConText.setOpaque(false);
        JLabel padding = new JLabel();
        padding.setPreferredSize(new Dimension(1000, 0));

        settingsConText.add(label);
        settingsConText.add(padding);
        settingsConText.add(textFieldCon);

        parent.add(settingsConText);
        return textField;
    }

    /**
     * Gets the values from the text boxes saves it
     * to the YahtzeeWindow object and yahtzeeConfig.txt
     * @param self YahtzeeWindow object that stores the config
     * @param fields the ArrayList of textbox Components
     */
    void applyConfig(YahtzeeWindow self, ArrayList<JTextField> fields) {
        int config[] = new int[4];
        for (int i = 0; i < 4; i++) {
            int num;
            try {
                num = Integer.parseInt(fields.get(i).getText());
            } catch (Exception ex) {
                num = self.defaultConfig[i];
            }
            config[i] = num;
        }

        self.setConfig(config);
        self.saveConfig();

        if ((self.playerNum + self.CPUPlayerNum) == 0){
            self.playerNum = 1;
            JOptionPane.showMessageDialog(null, "Minimum Number of Players is 1! Setting Number of Players to 1...", "Problem!", JOptionPane.INFORMATION_MESSAGE);

        }
    }
}
