/**
 * Used to make the scoresheet window
 * CPSC 224
 * Final project
 * No sources to cite.
 * 
 * @author Team Full House
 * @version 1.4.0 12/7/2022
 */

package Yahtzee.YahtzeeWindows;

import javax.swing.*;
import Yahtzee.YahtzeeCore.*;
import java.awt.*;
import java.awt.event.*;

public class ScoresheetWindow {
    private JFrame frame; 
    private ScoresheetGUI scoreGUI; 
    private Leaderboard leaderboard; 
    ScoresheetWindow(YahtzeeWindow self, Player curPlayer, Leaderboard l){
        // Setting up the UI
        ScoresheetWindow selfWin = this;
        float UIScale = self.UIScale;
        JScrollPane scrollCon = new JScrollPane(l.getParent());
        scrollCon.setPreferredSize(new Dimension(0,150));

        frame = new JFrame("Scoresheet");
        frame.setSize(Math.round(500*UIScale), Math.round(500*UIScale));
        frame.setPreferredSize(new Dimension(Math.round(500*UIScale), Math.round(500*UIScale)));

        JPanel heading = new JPanel();
        JLabel headingMain = new JLabel("Choose the dice you want to roll again!");
        JPanel buttonCon = new JPanel();

        this.scoreGUI = new ScoresheetGUI(buttonCon, headingMain);
        this.leaderboard = l;

        scoreGUI.init(curPlayer, UIScale);
        scoreGUI.showScoresheet();        
    
        heading.setLayout(new FlowLayout());
        headingMain.setText("");
        
        heading.add(headingMain);
        frame.add(heading, BorderLayout.PAGE_START);
        frame.add(buttonCon,BorderLayout.PAGE_END);
        frame.add(scoreGUI.getComp(), BorderLayout.CENTER);
        frame.add(scrollCon, BorderLayout.PAGE_END);
        frame.setVisible(true);
        frame.setLocationRelativeTo(self.frame);
        Rectangle bounds = self.frame.getBounds();
        frame.setLocation((bounds.x + bounds.width), bounds.y);
        frame.pack();
        
        // Called when the window is closed
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                self.scoresheetWindows.remove(selfWin);
            }
        });

    }

    /**
     * Getting the scoresheetGUI object of this window
     * Used to update the scoresheet
     * @return the scoresheetGUI object
     */
    public ScoresheetGUI getScoreGUI() {
        return this.scoreGUI;
    }

    /**
     * Updates the scoresheet
     */
    public void update(){
        this.scoreGUI.showScoresheet();
        this.leaderboard.update();
    }

    /**
     * Changes the player whose scoresheet is being
     * displayed
     */
    public void changePlayer(Player p){
        this.scoreGUI.changePlayer(p);
        this.update();
    }
}
