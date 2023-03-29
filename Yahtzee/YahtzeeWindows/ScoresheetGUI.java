/**
 * Used to make the scoresheet GUI
 * CPSC 224
 * Final project
 * No sources to cite.
 * 
 * @author Team Full House
 * @version 1.4.0 12/7/2022
 */


package Yahtzee.YahtzeeWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.table.*;
import Yahtzee.YahtzeeCore.*;
import Yahtzee.YahtzeeGUIComponents.*;

public class ScoresheetGUI {
    private JScrollPane scrollCon = new JScrollPane();
    public int selectedSpot = -1;
    private ArrayList<Integer> handArray;
    private JTable table;
    private Callback end;
    private Callback updateScoresheetWindow;
    private JPanel buttonCon;
    public RoundedButton submit;
    private JLabel headingMain;
    private Player player;
    public Scoresheet scores;
    public TableModel model;
    public int spotWithMaxScore = -1;
    public MouseAdapter showScoresheet;
    public GameGUI game;
    public ScoresheetGUI(JPanel buttonCon, JLabel headingMain){
        this.buttonCon = buttonCon;
        this.headingMain = headingMain;
    }

    /**
     * Resets the key members so 
     * it can be reused
     */
    public void resetState(){
        this.selectedSpot = -1;
        this.spotWithMaxScore = -1;
        this.handArray = null;
        this.end = null;
        this.scores = null;
    }

    /**
     * Getting the player whose scoresheet is
     * being displayed
     * @return the Player
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * Initializes the scoresheet. Used by ScoresheetWindow
     * @param player the player whose scoresheet will be displayed
     * @param UIScale UI scaling
     */
    public void init(Player player, float UIScale){
        this.player = player;
        this.scores = player.getScoresheet();
        String[] columnNames = {"Category", "Score"};
        String[][] data = {};
        
        
        this.table = new JTable(data, columnNames);
        this.scrollCon = new JScrollPane(table);
        table.setFillsViewportHeight(true); 
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        

        table.setRowHeight(Math.round(40*UIScale));
        
        
        JButton reload = new JButton("Reload");  

        ScoresheetGUI self = this;

        MouseAdapter reloadMouseAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                self.showScoresheet();
            }
        };

        reload.addMouseListener(reloadMouseAdapter);
    }

    /**
     * Changes the player
     * @param player the new player
     */
    public void changePlayer(Player player){
        this.player = player;
        this.scores = player.getScoresheet();
    }

    /**
     * Initializes the scoresheet. Used by GameGUI
     * @param player the player whose scoresheet will be displayed
     * @param handArray the handArray of the player
     * @param UIScale UI scaling factor
     */
    public void init(Player player, ArrayList<Integer> handArray, float UIScale, GameGUI game){

        this.player = player;
        this.game = game;
        this.handArray = handArray;
        this.scores = player.getScoresheet();
        String[] columnNames = {"Category", "Score"};
        String[][] data = {};
        
        
        this.table = new JTable(data, columnNames);
        this.scrollCon = new JScrollPane(table);
        table.setFillsViewportHeight(true); 
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        

        table.setRowHeight(Math.round(40*UIScale));
        

        ScoresheetGUI self = this;


        // Setting up an event listener that gets which
        // spot has been selected
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(!self.game.CPU){
                    int row = table.rowAtPoint(evt.getPoint());
                    selectedSpot = row + 1;
                }
            }
        });


        this.buttonCon.removeAll();
        JPanel submitCon = new JPanel();
        submit = new RoundedButton("Next", submitCon, "#DC0000", 15);  
        submit.setForeground(Color.white);
        
        submitCon.setLayout(new FlowLayout());
        submitCon.setOpaque(false);
        this.buttonCon.add(submitCon);


        
        this.showScoresheet = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                self.next(false);

            }
        };

        submit.addMouseListener(showScoresheet);

    }

    /**
     * Called when the "next" button is clicked
     * @param CPU where it's being called by CPU or not
     */
    public void next(boolean CPU){
        if((this.game.CPU && !CPU)){
            return;
        }
        // Setting up other even listeners
        ScoresheetGUI self = this;
        MouseAdapter endMouseAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(!self.game.CPU){
                    self.endTurn();
                }                
            }
        };

        if(this.selectedSpot != -1 && this.scores.isEmpty(this.selectedSpot)){
            HandAnalyzer tempAnalyzer = new HandAnalyzer(this.handArray, this.scores.numberDice, this.scores.dieSides, this.scores.upperSection.size(), this.selectedSpot);
            int[] score = tempAnalyzer.scoreToAdd();
            this.scores.setScore(score);
            this.submit.removeMouseListener(this.showScoresheet);
            this.submit.addMouseListener(endMouseAdapter);
            this.showScoresheet();
            this.updateScoresheetWindow.call();
        }else{
            JOptionPane.showMessageDialog(null, "That spot is not empty");
        }
    }

    /**
     * Called when a player's turn ends
     */
    public void endTurn(){
        if(this.end != null || this.game.CPU){
            Callback tempCallback = this.end;
            this.resetState();
            tempCallback.call();
        }
    }

    /**
     * Setting a callback that will be called at the 
     * end of the turn
     * @param end the function that will be called
     */
    public void setCallbackPossibleScore(Callback end){
        this.end = end;
    }

    /**
     * Setting a callback that will be called when
     * the user has chosen a spot on the scoresheet
     * and has clicked "next"
     * @param updateScoresheetWindow the function that will be called
     */
    public void setCallbackShowScore(Callback updateScoresheetWindow){
        this.updateScoresheetWindow = updateScoresheetWindow;
    }


    /**
     * Shows the possible scores and asks the user 
     * to select a spot
     */
    public void showPossibleScores(){
        int maxScore = -1;
        int maxSpot = -1;
        String[][] data = new String[scores.totalSpots][2];       
        String[] columnNames = {"Category", "Score"};

        // Making it so the user can't edit the table
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        this.model = tableModel;
        table.setModel(tableModel);
        
        this.headingMain.setText(this.player.playerName + " : Choose the spot by clicking on it and clicking next");

        // Stores which row should be highlighted 
        // Being highlighted signifies the spot is not empty
        ArrayList<Boolean> highlight = new ArrayList<Boolean>();
        int index = 0;
        for(int i = 1; i <= this.scores.totalSpots; i++){
            highlight.add(!this.scores.isEmpty(i));
            if(this.scores.isEmpty(i)){
                HandAnalyzer tempAnalyzer = new HandAnalyzer(this.handArray, this.scores.numberDice, this.scores.dieSides, this.scores.upperSection.size(), i);
                int score = tempAnalyzer.scoreToAdd()[1];

                if(maxScore < score){
                    maxScore = score;
                    maxSpot = i;
                }
                this.model.setValueAt(String.valueOf(score), index, 1);
            }else{
                this.model.setValueAt(this.scores.getSectionScore(i), index, 1);
            }

            this.model.setValueAt(this.scores.getSectionName(i), index, 0);
            index++;
        }

        this.spotWithMaxScore = maxSpot;

        // Using the custom renderer so the rows will
        // highlighted
        this.table.getColumnModel().getColumn(0).setCellRenderer(new CustomColumnCellRenderer(highlight, true));
        this.table.getColumnModel().getColumn(1).setCellRenderer(new CustomColumnCellRenderer(highlight,true));
    }

    /**
     * Showing the scoresheet after a spot
     * has been selected
     */
    public void showScoresheet(){
        ArrayList<Boolean> highlight = new ArrayList<Boolean>();
        String[][] data = new String[scores.totalSpots + 4][2];       
        String[] columnNames = {"Category", "Score"};
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        this.model = tableModel;
        this.table.setModel(tableModel);
        this.headingMain.setText(this.player.playerName + " : Your scoresheet");

        String[][] scoreArray = this.scores.toArray();
        for(int i = 0; i < (this.scores.totalSpots + 4); i++){
            this.model.setValueAt(scoreArray[i][1], i, 1);
            this.model.setValueAt(scoreArray[i][0], i, 0);
            highlight.add(scoreArray[i][2].equals("1"));
        }
        
        this.table.getColumnModel().getColumn(0).setCellRenderer(new CustomColumnCellRenderer(highlight, false));
        this.table.getColumnModel().getColumn(1).setCellRenderer(new CustomColumnCellRenderer(highlight, false));
    }

    /**
     * Getting the table Component
     * @return the table Component
     */
    public Component getComp(){        
        return this.scrollCon;
    }
}
