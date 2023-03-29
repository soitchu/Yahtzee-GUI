/**
 * Used to make the game area
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
import Yahtzee.YahtzeeCore.*;
import Yahtzee.YahtzeeGUIComponents.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

public class GameGUI extends WindowScene{
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<DiceGUI> dice = new ArrayList<DiceGUI>();
    public ScoresheetGUI scoreGUI;
    private JPanel buttonCon, retriesLeft, diceCon;
    RoundedButton roll, rollAll;
    private RoundedPanel buttonConMain;
    private JLabel retriesText, headingMain;
    private Container gameCon;
    private YahtzeeWindow self;
    protected boolean CPU = true; 

    GameGUI(YahtzeeWindow self){
        // Setting up the UI

        this.self = self;
        this.iniPlayer(self.playerNum, self);
        parent = new BackgroundPanel(self);
        parent.setLayout(new BorderLayout());
        this.gameCon = parent;

        diceCon = new JPanel();
        diceCon.setLayout(new GridBagLayout());
        diceCon.setOpaque(false);
        
        this.iniDice(diceCon);

        parent.add(diceCon, BorderLayout.CENTER);

        RoundedPanel heading = new RoundedPanel(new FlowLayout(), 30, Color.decode("#ffffff"));
        heading.setOpaque(false);
        headingMain = new JLabel();
        headingMain.setBorder(new EmptyBorder(10, 0, 10, 0));
        headingMain.setForeground(Color.black);
        headingMain.setText(players.get(self.index).playerName + " : Choose the dice you want to roll again!");
        heading.add(headingMain);
        parent.add(heading, BorderLayout.PAGE_START);

        buttonConMain = new RoundedPanel(30 , Color.decode("#ffffff"));
        buttonConMain.setLayout(new GridLayout(2, 1));
        buttonCon = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonCon.setOpaque(false);
        buttonConMain.setOpaque(false);

        retriesLeft = new JPanel(new FlowLayout());
        retriesLeft.setOpaque(false);

        buttonConMain.setBorder(new EmptyBorder(20,0,0,0));
        retriesText = new JLabel();
        retriesText.setText("Retries left: " + (self.retries - self.retriesCount));
        retriesLeft.add(retriesText);

        JButton submit = new RoundedButton("Submit", buttonCon, "#1D00FF", 15);
        roll = new RoundedButton("Roll", buttonCon, "#1D00FF", 15);
        rollAll = new RoundedButton("Roll All", buttonCon, "#1D00FF", 15);
        JButton scoresheetButton = new RoundedButton("Scoresheet", buttonCon, "#1D00FF", 15);

        submit.setForeground(Color.white);
        roll.setForeground(Color.white);
        rollAll.setForeground(Color.white);
        scoresheetButton.setForeground(Color.white);

        buttonConMain.add(buttonCon);
        buttonConMain.add(retriesLeft);

        parent.add(buttonConMain, BorderLayout.PAGE_END);

        // Initializing ScoresheetGUI
        scoreGUI = new ScoresheetGUI(buttonConMain, headingMain);

        GameGUI selfGame = this;

        // Setting up all the event listeners
        roll.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                selfGame.rollAction(false,false);                               
            }
        });

        rollAll.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selfGame.rollAction(true,false);               
            }
        });

        scoresheetButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Leaderboard l = new Leaderboard(players);
                Player curPlayer = players.get(self.index);
                ScoresheetWindow windowScoreGUI = new ScoresheetWindow(self, curPlayer, l);
                self.scoresheetWindows.add(windowScoreGUI);
            }
        });

        submit.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selfGame.submitAction(false);
            }
        });

        if(this.players.get(0).isCPU()){
            PlayerCPU p =  (PlayerCPU) this.players.get(0);
            p.toRollOrNotToRoll(dice, this);
        }
    }
    
    /**
     * Called when the "submit" button is clicked
     * or called manually by a CPU player
     */
    public boolean submitAction(boolean CPU){
        Player curPlayer = players.get(self.index);
        if(!curPlayer.isCPU() || CPU){
            return this.openScoresheet(diceCon, CPU);
        }

        return false;
    }

    /**
     * Called when the "roll" button is clicked
     * or called manually by a CPU player
     */
    public void rollAction(boolean all, boolean CPU){
        Player curPlayer = players.get(self.index);
        if(!curPlayer.isCPU() || CPU){
            this.roll(all, CPU);
        }
    }

    /**
     * Transitions to the next scene so players
     * and put their hand on the scoresheet
     * @param diceCon
     */
    public boolean openScoresheet(Container diceCon, boolean CPU){
        // Not opening the scoresheet if some of the dice
        // are still rolling

        if(!CPU){
            for (DiceGUI die : dice) {
                if(die.isRolling()){
                    JOptionPane.showMessageDialog(null, "Some dice are still rolling");
                    return false;
                }
            }
        }
        

        GameGUI gameSelf = this;

        // Making room for the scoresheet
        this.gameCon.remove(diceCon);

        // Setting up the handArray
        ArrayList<Integer> handArray = new ArrayList<Integer>();
        for (DiceGUI die : dice) {
            handArray.add(die.value);
            die.rollWithoutAnimation();
            die.unselect();
        }

        // Setting up the scoresheetGUI
        scoreGUI.init(players.get(self.index), handArray, self.UIScale, this);
        
        Callback c = () -> {
            gameSelf.turnEnd();
        };

        Callback update = () -> {
            for (int i = 0; i < self.scoresheetWindows.size(); i++) {
                self.scoresheetWindows.get(i).update();
            }
        };

        scoreGUI.setCallbackPossibleScore(c);
        scoreGUI.setCallbackShowScore(update);

        // Updating the UI
        buttonConMain.setBorder(new EmptyBorder(20,0,20,0));
        buttonConMain.setLayout(new GridLayout(1, 1));        
        scoreGUI.showPossibleScores();
        gameCon.add(scoreGUI.getComp());
        gameCon.revalidate();
        gameCon.repaint();

        return true;
    }

    /**
     * Initializing the ArrayList of players 
     * @param numberPlayers number of players
     * @param self YahtzeeWindow class
     */
    private void iniPlayer(int numberPlayers, YahtzeeWindow self){

        for (int i = 0; i < numberPlayers; i++) {
            String playerName = "Player #" + (i + 1);
            players.add(new Player(self.numberDice, self.dieSides, self.retries, playerName));
            
            if (numberPlayers > 5){
                numberPlayers = 5;
                JOptionPane.showMessageDialog(null, "Maximum Number of Players is 5! Setting Number of Players to 5...", "Problem!", JOptionPane.INFORMATION_MESSAGE);

            }
        }

        for (int i = 0; i < self.CPUPlayerNum; i++) {
            String playerName = "CPU #" + (i + 1);
            players.add(new PlayerCPU(self.numberDice, self.dieSides, self.retries, playerName, self.timeoutCPU));
        }

        this.CPU = players.get(0).isCPU();

    }

    /**
     * Called after a turn ends
     */
    private void turnEnd(){

        // Giving the next player the turn
        self.index = (self.index + 1) % (self.playerNum + self.CPUPlayerNum);

        this.CPU = players.get(self.index).isCPU();


        // If the player's scoresheet is full
        // we can delcare the winner
        if(players.get(self.index).isScoresheetFull()){
            this.decideWinner(players);
            return;
        }else{

            

            // Updating the scoresheet windows
            for (int i = 0; i < self.scoresheetWindows.size(); i++) {
                self.scoresheetWindows.get(i).changePlayer(players.get(self.index));
            }

            // Resetting the reroll count
            self.retriesCount = 0;

            // Updating the UI
            retriesText.setText("Retries left: " + (self.retries - self.retriesCount));
            headingMain.setText(players.get(self.index).playerName + " : Choose the dice you want to roll again!");
            gameCon.add(diceCon);
            gameCon.remove(scoreGUI.getComp());
            buttonConMain.setLayout(new GridLayout(2, 1));
            buttonConMain.setBorder(new EmptyBorder(20,0,0,0));
            buttonConMain.removeAll();
            buttonConMain.add(buttonCon);
            buttonConMain.add(retriesLeft);                
            gameCon.revalidate();
            gameCon.repaint();

            if(players.get(self.index).isCPU()){
                PlayerCPU cpu = ((PlayerCPU) players.get(self.index));
                cpu.toRollOrNotToRoll(dice, this);
            }
        } 
        
    }

    /**
     * Makes the die Components
     * @param diceCon the parent element
     */
    private void iniDice(Container diceCon){
        Font font = self.customFont.deriveFont(30 * self.UIScale);

        if (self.numberDice > 10){
            JOptionPane.showMessageDialog(null, "Setting number of dice to 10 since it can't be more than 10.");
        }
        // Making numberDice number of dice
        for (int i = 0; i < self.numberDice; i++) {
            JLabel padding = new JLabel();
            padding.setPreferredSize(new Dimension(20, 0));
            DiceGUI die = (new DiceGUI(Math.round(60), Math.round(60), font, self.dieSides, this));
            diceCon.add(die.getLabel());

            // Adding padding between each die
            if(i != self.numberDice - 1){
                diceCon.add(padding);
            }

            dice.add(die);

            // Rolling the die to get an initial value
            die.roll();
        }
    }

    /**
     * Gets the winner
     * @param players the ArrayList of players
     */
    public void decideWinner(ArrayList<Player> players){
        int maxScore = players.get(0).getTotal();
        String playerName = players.get(0).playerName;
        for(Player curPlayer: players){
            int total = curPlayer.getTotal();
            if(total > maxScore){
                maxScore = total;
                playerName = curPlayer.playerName;
            }
        }

        JPanel textCon = new JPanel(new FlowLayout());
        JLabel winnerText = new JLabel(playerName + " Won!");
        Leaderboard l = new Leaderboard(players);
        JScrollPane scrollCon = new JScrollPane(l.getParent());
        
        winnerText.setForeground(Color.white);
        scrollCon.setOpaque(false);
        textCon.setOpaque(false);
        l.getParent().setOpaque(false);
        scrollCon.getViewport().setOpaque(false);

        Font font = self.customFont.deriveFont(30 * self.UIScale);
        winnerText.setFont(font);
        textCon.add(winnerText);
        l.getParent().add(textCon, 0);
        gameCon.removeAll();
        gameCon.add(scrollCon);
        gameCon.revalidate();
        gameCon.repaint();
    }
    
    /**
     * Rolls the dice
     * @param all if all the dice should be rolled. If it is false,
     * only the selected dice will be rolled.
     */
    public void roll(boolean all, boolean CPU){
        
        if(!CPU){
            // Checking if the dice can all rolled again
            if (self.retriesCount >= self.retries) {
                JOptionPane.showMessageDialog(null, "You don't have any retries left.");
                return;
            }
            

            // Making sure the dice are not rolling
            for (DiceGUI die : dice) {
                if(die.isRolling()){
                    JOptionPane.showMessageDialog(null, "Some dice are still rolling");
                    return;
                }
            }
        }
        
        boolean check = false;
        for (DiceGUI die : dice) {
            if (die.selected || all) {
                die.select();
                die.roll();
                check = true;
            }
        }

        if (check) {
            // Updating the reroll count
            self.retriesCount++;
            this.retriesText.setText("Retries left: " + (self.retries - self.retriesCount));
        }
    }

    public boolean hasRunOut(){
        return self.retriesCount >= self.retries;
    }

    public Player getCurPlayer(){
        return this.players.get(self.index);
    }
}
