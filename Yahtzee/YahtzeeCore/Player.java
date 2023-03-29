/**
 * Used to make a new player class
 * CPSC 224
 * Final project
 * No sources to cite.
 * 
 * @author Team Full House
 * @version 1.4.0 12/7/2022
 */

package Yahtzee.YahtzeeCore;

public class Player {

    private Scoresheet scores;
    protected boolean CPU = false;
    public String playerName;
    public int numberDice = 5;
    public int dieSides = 5;

    public Player(int numberDice, int dieSides, int retries, String playerName) {
        this.numberDice = numberDice;
        this.dieSides = dieSides;
        this.scores = new Scoresheet(numberDice, dieSides);
        this.playerName = playerName;
    }

    /**
     * Return the scoresheet of this player. Used for testing purposes
     * @return the scoresheet
     */
    public Scoresheet getScoresheet(){
        return this.scores;
    }


    /**
     * Checks if the scoresheet is full
     * 
     * @return returns true if the scoresheet is full
     */
    public boolean isScoresheetFull() {
        // return true;
        return this.scores.isFull();
    }

    /**
     * Calculates the total score
     * 
     * @return the total score
     */
    public int getTotal() {
        return this.scores.getTotalScore();
    }

    public boolean isCPU(){
        return this.CPU;
    }

}