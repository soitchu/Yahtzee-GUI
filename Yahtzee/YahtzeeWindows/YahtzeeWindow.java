/**
 * The main class the handles everything
 * CPSC 224
 * Final project
 * No sources to cite.
 * 
 * @author Team Full House
 * @version 1.4.0 12/7/2022
 */

package Yahtzee.YahtzeeWindows;

import javax.imageio.ImageIO;
import javax.swing.*;
import Yahtzee.YahtzeeGUIComponents.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;

public class YahtzeeWindow {
    public boolean testing = false;
    int[] defaultConfig = { 5, 3, 2, 0};
    int playerNum = 2, numberDice = 5, retries = 3,  retriesCount = 0, dieSides = 6, CPUPlayerNum = 0, timeoutCPU = 1500;
    public Container framePane;
    private WindowScene currentScene;
    public Image bgResized;
    JFrame frame;
    private BufferedImage bg;
    public int bgX = 0, 
               bgY = 0, 
               index = 0;
    float UIScale = 1;
    private double[] windowSize = { 500*UIScale, 500*UIScale };
    Font customFont = new Font("Times New Roman", Font.BOLD, Math.round(15 * UIScale));
    ArrayList<ScoresheetWindow> scoresheetWindows = new ArrayList<ScoresheetWindow>();

    /**
     * Creates the JFrame
     */
    private void createAndShowGUI() {

        this.frame = new JFrame("Yahtzee");
        frame.setPreferredSize(new Dimension((int)windowSize[0], (int)windowSize[1]));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        YahtzeeWindow self = this;

        // Called when the JFrame is resized
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                Dimension d = componentEvent.getComponent().getSize();
                self.windowSize[0] = d.getWidth();
                self.windowSize[1] = d.getHeight();
                try {
                    // Resizing the background image and redrawing it
                    self.resizeBackground();
                    self.framePane.paintComponents(framePane.getGraphics());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        frame.pack();
        frame.setContentPane(new BackgroundPanel(this));
        this.framePane = frame.getContentPane();
        this.framePane.setLayout(new BorderLayout());
        if(this.testing){
            this.playerNum = 0;
            this.CPUPlayerNum = 2;
            this.openGameWindow();
        }else{
            this.openHomeScreen();
        }
        frame.setVisible(true);
    }

    /**
     * Opening the home screen
     */
    void openHomeScreen(){
        this.currentScene = new HomeGUI(this);
        this.displayCurrentScene();
    }

    /**
     * Open the settings
     */
    void openSettings(boolean hideButtons){
        this.currentScene = new SettingsGUI(this, hideButtons); 
        this.displayCurrentScene();
    }

    /**
     * Opening the game area
     */
    public void openGameWindow(){
        this.currentScene = new GameGUI(this); 
        this.displayCurrentScene();
    }

    /**
     * Deleting the Components that are not needed
     * and diplaying the current scene
     */
    void displayCurrentScene(){
        Container parent = this.frame.getContentPane();
        parent.removeAll();
        parent.add(this.currentScene.getParent());
        this.frame.revalidate();
        this.frame.repaint();
    }

    /**
     * Resizing the background image
     * (It is not very optimised)
     * @param frame
     */
    void resizeBackground() {
        float aspectRatio = (float) this.bg.getWidth() / (float) this.bg.getHeight();
        int width = frame.getWidth();
        int height = frame.getHeight();

        if (width < height) {
            width = Math.round(height * aspectRatio);
            this.bgX = (frame.getWidth() - width) / 2;
            this.bgY = 0;
            if (width < frame.getWidth()) {
                width = frame.getWidth();
                height = Math.round(width / aspectRatio);
                this.bgX = 0;
                this.bgY = (frame.getHeight() - height) / 2;

            }
        } else {
            height = Math.round(width / aspectRatio);
            this.bgX = 0;
            this.bgY = (frame.getHeight() - height) / 2;
            if (height < frame.getHeight()) {
                height = frame.getHeight();
                width = Math.round(height * aspectRatio);
                this.bgX = (frame.getWidth() - width) / 2;
                this.bgY = 0;
            }
        }
        this.bgResized = (new ImageIcon(bg)).getImage().getScaledInstance(width, height, Image.SCALE_FAST);
    }

    /**
     * Setting the config variables
     * @param config an array of 3 elements
     * 0th element stores the number of sides the die has
     * 1st element stores the number of dice
     * 2nd element stores how many times the user can reroll
     */
    public void setConfig(int[] config) {
        this.numberDice = config[0];
        this.retries = config[1];
        this.playerNum = config[2];
        this.CPUPlayerNum = config[3];
    }

    /**
     * Storing the config to the file
     */
    public void saveConfig() {
        try {
            PrintWriter output = new PrintWriter("Yahtzee/yahtzeeConfig.txt");
            output.println(this.numberDice);
            output.println(this.retries);
            output.println(this.playerNum);
            output.println(this.CPUPlayerNum);
            output.close();
        } catch (Exception ex) {

        }
    }

    /**
     * Getting the config from the text file
     */
    public void getConfig() {
        try {
            File config = new File("Yahtzee/yahtzeeConfig.txt");
            Scanner reader = new Scanner(config);

            for (int i = 0; i < 4; i++) {
                String data = reader.nextLine();
                int value = Integer.parseInt(data);
                switch (i) {
                    case 0:
                        this.numberDice = value;
                        break;

                    case 1:
                        this.retries = value;
                        break;

                    case 2:
                        this.playerNum = value;
                        break;

                    case 3:
                        this.CPUPlayerNum = value;
                        break;
                }
                
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Setting up everything and making the JFrame
     */
    public void start(int timeoutCPU) {
        try {
            this.bg = ImageIO.read(new File("Yahtzee/assets/Hello.png"));
            this.timeoutCPU = timeoutCPU;
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            File myStream = new File("Yahtzee/assets/Montserrat.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, myStream);
            font = font.deriveFont((float)(15 * UIScale));
            this.customFont = font;

            UIManager.getLookAndFeelDefaults().put("defaultFont", font);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}