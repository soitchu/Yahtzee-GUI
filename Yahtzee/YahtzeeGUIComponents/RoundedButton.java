/**
 * Used to make a rounded JButton
 * CPSC 224
 * Final project
 * No sources to cite.
 * 
 * @author Team Full House
 * @version 1.4.0 12/7/2022
 */

package Yahtzee.YahtzeeGUIComponents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RoundedButton extends JButton {
    JPanel con;

    public RoundedButton(String text, JPanel parent, String color, int radius){
        // The JButton is stored in a container. Specifically, it is
        // a RoundedPanel
        con = new RoundedPanel(radius, Color.decode(color));
        con.setOpaque(false);
        this.setText(text);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setBorder(new EmptyBorder(10, 20, 10, 20) );
        con.add(this);
        parent.add(con);
    }

    public RoundedButton(String text, String color, int radius){
        con = new RoundedPanel(radius, Color.decode(color));
        con.setOpaque(false);
        this.setText(text);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setBorder(new EmptyBorder(10, 20, 10, 20) );
        con.add(this);
    }

    public RoundedButton(int dieSideShowing, Boolean gifOrNo, Color color, int radius){
        // The JButton is stored in a container. Specifically, it is
        // a RoundedPanel
        con = new RoundedPanel(radius, color);
        con.setOpaque(false);
        if (dieSideShowing == 1) {
            if (gifOrNo == true) {
                this.setIcon(new ImageIcon("Yahtzee/assets/diceroll.gif"));
            }
            else {
                this.setIcon(new ImageIcon("Yahtzee/assets/die-1.png"));
            }
        }
        else if (dieSideShowing == 2) {
            if (gifOrNo == true) {
                this.setIcon(new ImageIcon("Yahtzee/assets/diceroll.gif"));
            }
            else {
                this.setIcon(new ImageIcon("Yahtzee/assets/die-2.png"));
            }
        }
        else if (dieSideShowing == 3) {
            if (gifOrNo == true) {
                this.setIcon(new ImageIcon("Yahtzee/assets/diceroll.gif"));
            }
            else {
                this.setIcon(new ImageIcon("Yahtzee/assets/die-3.png"));
            }
        }
        else if (dieSideShowing == 4) {
            if (gifOrNo == true) {
                this.setIcon(new ImageIcon("Yahtzee/assets/diceroll.gif"));
            }
            else {
                this.setIcon(new ImageIcon("Yahtzee/assets/die-4.png"));
            }
        }
        else if (dieSideShowing == 5) {
            if (gifOrNo == true) {
                this.setIcon(new ImageIcon("Yahtzee/assets/diceroll.gif"));
            }
            else {
                this.setIcon(new ImageIcon("Yahtzee/assets/die-5.png"));
            }
        }
        else if (dieSideShowing == 6) {
            if (gifOrNo == true) {
                this.setIcon(new ImageIcon("Yahtzee/assets/diceroll.gif"));
            }
            else {
                this.setIcon(new ImageIcon("Yahtzee/assets/die-6.png"));
            }
        }
        this.setIcon(new ImageIcon("Yahtzee/assets/die-1.png"));
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setBorder(new EmptyBorder(10, 20, 10, 20) );
        con.add(this);
    }

    /**
     * Returns the container panel
     * @return the container panel
     */
    public JPanel getCon(){
        return con;
    }
}
