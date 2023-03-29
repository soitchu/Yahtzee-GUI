/**
 * Used to make the leaderboard
 * CPSC 224
 * Final project
 * No sources to cite.
 * 
 * @author Team Full House
 * @version 1.4.0 12/7/2022
 */

package Yahtzee.YahtzeeWindows;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import Yahtzee.YahtzeeCore.Player;
import Yahtzee.YahtzeeGUIComponents.RoundedPanel;

public class Leaderboard {
    JPanel parent = new JPanel();
    ArrayList<JLabel[]> scorePanels = new ArrayList<JLabel[]>();
    ArrayList<Player> scores;

    Leaderboard(ArrayList<Player> players){
        this.scores = players;
        parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
        ArrayList<Player> scores = new ArrayList<Player>();
        for(Player p : players){
            scores.add(p);
        }

        scores.sort(new Comparator<Player>() {
            public int compare(Player player1, Player player2) {
                return  player2.getTotal() - player1.getTotal();
            }
        });

        for(Player p : scores){
            JPanel row = new RoundedPanel(10);
            JLabel name = new JLabel(p.playerName);
            JLabel score = new JLabel(String.valueOf(p.getTotal()));

            row.setBackground(Color.decode("#DC0000"));
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            row.setPreferredSize(new Dimension(300, 50));
            
            score.setForeground(Color.white);
            name.setForeground(Color.white);

            row.add(Box.createRigidArea(new Dimension(20,50)));
            row.add(name);
            row.add(Box.createHorizontalGlue());
            row.add(score);
            row.add(Box.createRigidArea(new Dimension(20,50)));

            JLabel rowInfo[] = {name , score};
            parent.add(row);
            scorePanels.add(rowInfo);
        }
    }

    /**
     * Updates the leaderboard
     */
    public void update(){

        // Can be optimised
        this.scores.sort(new Comparator<Player>() {
            public int compare(Player player1, Player player2) {
                return  player2.getTotal() - player1.getTotal();
            }
        });

        int count  = 0;
        for(JLabel row[] : scorePanels){
            Player curPlayer = this.scores.get(count);
            row[0].setText(curPlayer.playerName);
            row[1].setText(String.valueOf(curPlayer.getTotal()));

            count++;
        }
    }

    /**
     * Returns the leaderboard's main component
     */
    public JPanel getParent() {
        return parent;
    }
}