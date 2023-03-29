/**
 * Used to make the scoresheet's sections
 * CPSC 224
 * Final project
 * No sources to cite.
 * 
 * @author Team Full House
 * @version 1.4.0 12/7/2022
 */
package Yahtzee.YahtzeeCore;
public class Section{
    public int score = -1;
    public String name;

    public Section(String name){
        this.name = name;
    }

    /**
     * Check if the score has changed i.e. not empty
     * @return true if it is empty
     */
    public Boolean isEmpty(){
        return this.score == -1;
    }
}