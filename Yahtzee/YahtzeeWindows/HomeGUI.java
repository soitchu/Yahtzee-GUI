/**
 * Used to make the homescreen
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
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class HomeGUI extends WindowScene{
    HomeGUI(YahtzeeWindow self){
        parent = new JPanel(new BorderLayout());
        try{
            // Getting the config
            self.getConfig();

            // Setting up the UI
            BufferedImage img = ImageIO.read(new File("Yahtzee/assets/logo.png"));
            Image imgResized = this.getResizedImage(img, Math.round(300*self.UIScale));
            JLabel wIcon = new JLabel(new ImageIcon(imgResized));

            BackgroundPanel startCon = new BackgroundPanel(self);


            startCon.setLayout(new GridLayout(0,1));
            startCon.setBackground(Color.blue);
            startCon.setAlignmentX(Component.CENTER_ALIGNMENT);
            startCon.setAlignmentY(Component.CENTER_ALIGNMENT);
            startCon.add(wIcon);

            parent.add(startCon);

            JPanel startMainCon = new JPanel();
            startMainCon.setOpaque(false);
            startMainCon.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
            

            JButton start = new RoundedButton("Start", startMainCon, "#ffffff", 20);
            JButton settings = new RoundedButton("Settings", startMainCon, "#ffffff", 20);

           //JButton start = new JButton();



            startCon.add(startMainCon);

            //  Setting up all the event listeners
            start.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    self.openSettings(true);
                }
            });

            settings.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    self.openSettings(false);
                }
            });


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Resizes the image
     * @param image the image  
     * @param width new width
     * @return the resized image
     */
    Image getResizedImage(BufferedImage image, int width){
        float aspectRatio = (float)image.getHeight()/(float)image.getWidth();
        int heightScaled = Math.round(width*aspectRatio);            
        int widthScaled = width;
        Image imgResized = (new ImageIcon(image)).getImage().getScaledInstance(widthScaled, heightScaled, Image.SCALE_FAST);
        return imgResized;
    }

}