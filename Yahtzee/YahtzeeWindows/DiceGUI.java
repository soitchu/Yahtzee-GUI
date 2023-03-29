/**
 * Used to make the die elements
 * CPSC 224
 * Final project
 * No sources to cite.
 * 
 * @author Team Full House
 * @version 1.4.0 12/7/2022
 */
package Yahtzee.YahtzeeWindows;

import javax.swing.*;
import Yahtzee.YahtzeeGUIComponents.RoundedButton;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class DiceGUI {
    private int dieSides = 6, rollAnimationCount = 0, rollAnimationMax = 2;
    private Color selectedColor = Color.decode("#DE1738"),
    unselectedColor = new Color(0,0,0,0);
    private RoundedButton dice = new RoundedButton(1, true, selectedColor, 10);
    private Timer timer;
    public int value;
    public boolean selected = false;
    private GameGUI config;

    public DiceGUI(int height, int width, Font font, int dieSides, GameGUI config) {
        this.dieSides = dieSides;
        this.dice.setFont(font);
        this.dice.setSize(width, height);
        this.dice.setPreferredSize(new Dimension(width, height));
        this.dice.setVerticalAlignment(SwingConstants.CENTER);
        this.dice.setHorizontalAlignment(SwingConstants.CENTER);
        this.dice.setBackground(selectedColor);
        this.dice.setForeground(Color.black);

        this.config = config;
        this.rollWithoutAnimation();
        this.rollWithAnimation();
        this.iniListener();
    }

    /**
     * Initialise all the event listeners
     */
    private void iniListener() {
        
        DiceGUI self = this;
        this.dice.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (!config.CPU) {
                    self.selected = !self.selected;

                    if (self.selected) {
                        self.select();
                    } else {
                        self.unselect();
                    }
                }
            }
        });
    }

    /**
     * Returns the die
     * 
     * @return the die JPanel
     */
    public Component getLabel() {
        return this.dice.getCon();
    }

    /**
     * Checks if the rolling animation is in progress
     * 
     * @return true if the rolling animation is in progress
     */
    public boolean isRolling() {
        return this.rollAnimationCount != 0;
    }

    /**
     * Rolls the die
     */
    public void roll() {

        // Not rolling the die if it already is
        if (this.rollAnimationCount > 0) {
            return;
        }

        this.rollAnimationCount = 1;
        DiceGUI self = this;
        self.rollWithAnimation();
        // Changing the number on the die every 100ms
        self.timer = new Timer(1200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.rollAnimationCount++;
                // Stopping the animation
                if (self.rollAnimationCount >= self.rollAnimationMax) {
                    self.rollWithoutAnimation();
                    self.unselect();
                    self.rollAnimationCount = 0;
                    self.timer.stop();
                } 
            }
        });
        timer.start();
    }

    /**
     * Rolls the die without the animation
     */
    public void rollWithoutAnimation() {
        int num = this.rollDie();
        if (num == 1) {
            this.dice.setIcon(new ImageIcon("Yahtzee/assets/die-1.png"));
        }
        else if (num == 2) {
            this.dice.setIcon(new ImageIcon("Yahtzee/assets/die-2.png"));
        }
        else if (num == 3) {
            this.dice.setIcon(new ImageIcon("Yahtzee/assets/die-3.png"));
        }
        else if (num == 4) {
            this.dice.setIcon(new ImageIcon("Yahtzee/assets/die-4.png"));
        }
        else if (num == 5) {
            this.dice.setIcon(new ImageIcon("Yahtzee/assets/die-5.png"));
        }
        else if (num == 6) {
            this.dice.setIcon(new ImageIcon("Yahtzee/assets/die-6.png"));
        }
        this.value = num;
    }

    /**
     * Plays the rolling animation
     */
    public void rollWithAnimation() {
        this.dice.setIcon(new ImageIcon("Yahtzee/assets/diceroll.gif"));
    }

    /**
     * Selects this die
     */
    public void select() {
        this.selected = true;
        this.dice.getCon().setBackground(this.selectedColor);
        this.dice.setForeground(Color.WHITE);

    }

    /**
     * Unselects this die
     */
    public void unselect() {
        this.selected = false;
        this.dice.getCon().setBackground(this.unselectedColor);
        this.dice.setForeground(Color.black);

    }

    /**
     * Generated a random number between 1 and dieSides
     * 
     * @return a random number between 1 and dieSides
     */
    private int rollDie() {
        Random rand = new Random();
        return (rand.nextInt(this.dieSides) + 1);
    }
}
