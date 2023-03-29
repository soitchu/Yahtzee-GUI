/**
 * Extends a JPanel such that it can have
 * a background image
 * CPSC 224
 * Final project
 * No sources to cite.
 * 
 * @author Team Full House
 * @version 1.4.0 12/7/2022
 */

package Yahtzee.YahtzeeGUIComponents;

import javax.swing.*;
import Yahtzee.YahtzeeWindows.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private YahtzeeWindow self;
    public BackgroundPanel(YahtzeeWindow self){
        this.self = self;
    }
    @Override
    public void paintComponent(Graphics page)
    {   
        // the YahtzeeWindow class stores where the image
        // should be painted and what images should be 
        // painted. This is done to make the panel responsive
        super.paintComponent(page);
        page.drawImage(self.bgResized, self.bgX, self.bgY, null);
    }
}
