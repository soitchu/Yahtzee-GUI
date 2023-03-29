/**
 * Used to make a scoresheet
 * CPSC 224
 * Final project
 * No sources to cite.
 * 
 * @author Team Full House
 * @version 1.4.0 12/7/2022
 */
package Yahtzee.YahtzeeCore;
import java.util.*;

public class Scoresheet {
    public ArrayList<Section> upperSection = new ArrayList<Section>();
    public ArrayList<Section> lowerSection = new ArrayList<Section>();    
    public int numberDice;
    public int dieSides;
    public int totalSpots;
    private int upperTotal = 0;
    private boolean upperBonus = false;
    private int lowerTotal = 0;
    private int upperBonusThreshold;
    
    public Scoresheet(int numberDice, int dieSides){
        this.numberDice = numberDice;
        this.dieSides = dieSides;
        for(int i = 1; i <= this.dieSides; i++){
            this.upperSection.add((new Section(String.valueOf(i) + "s")));
        }

        // Stores the category names of the lower section
        String[] lowerSectionNames = {"Yahtzee", (this.numberDice - 1) + " of a kind", (this.numberDice - 2) + " of a kind", "Large Straight", "Small Straight", "Full House", "Chance"};

        for(int i = 0; i < lowerSectionNames.length; i++){
            lowerSection.add((new Section(lowerSectionNames[i])));
        }

        this.upperBonusThreshold = 3*(this.dieSides*(this.dieSides + 1))/2;
        this.totalSpots = this.upperSection.size() + this.lowerSection.size();

    }

    /**
     * Checks if a specific spot is empty
     * @param spot spot number of the section
     * @return true if the spot is empty
     */
    public boolean isEmpty(int spot){
        if(spot <= this.upperSection.size()){
            spot--;
            return this.upperSection.get(spot).isEmpty();
        }else{
            spot = spot - this.upperSection.size() - 1;
            return this.lowerSection.get(spot).isEmpty();
        }
    }

    /**
     * Gets the section's score
     * @param spot spot number of the section
     * @return the section's score
     */
    public int getSectionScore(int spot){
        if(spot <= this.upperSection.size()){
            spot--;
            return this.upperSection.get(spot).score;
        }else{
            spot = spot - this.upperSection.size() - 1;
            return this.lowerSection.get(spot).score;
        }
    }

    /**
     * Gets the section's name
     * @param spot spot number of the section
     * @return the section's name
     */
    public String getSectionName(int spot){
        if(spot <= this.upperSection.size()){
            spot--;
            return this.upperSection.get(spot).name;
        }else{
            spot = spot - this.upperSection.size() - 1;
            return this.lowerSection.get(spot).name;
        }
    }

    /**
     * Checks if there are any empty spots left
     * @return true if there are no empty spots left
     */
    public boolean isFull(){
        for(int i = 1; i <= this.totalSpots; i++){
            if(this.isEmpty(i)){
                return false;
            }
        }

        return true;
    } 

    /**
     * Calculates the upper section's total
     * @return the upper section's total
     */
    public int getUpperTotal(){
        return (this.upperTotal + (this.upperBonus ? 35 : 0));
    }

    /**
     * Calculates the upper section's bonus
     * @return the upper section's bonus
     */
    public int getUpperBonus(){
        return (this.upperBonus ? 35 : 0);
    }

    /**
     * Calculates the total of the whole scoresheet
     * @return the total of the whole scoresheet
     */
    public int getTotalScore(){
        return (this.getUpperTotal() + this.lowerTotal);
    }

    /**
     * Calculates the total of the lower section
     * @return the total of the lower scoresheet
     */
    public int getLowerTotal(){
        return this.lowerTotal;
    }

    /**
     * Coverts the scoresheet to an array.
     * @return  a 2D array. Each element is of size 3
     * the 1st element stores the section's name
     * the 2nd element stores the section's score
     * the 3rd element signifies if the section should be 
     * highlighted when it is displayed
     */
    public String[][] toArray(){
        String[][] score = new String[this.totalSpots + 4][3];
        int index = -1;
        for(int i = 1; i <= this.dieSides; i++){
            int scoreCur = this.getSectionScore(i);
            if(scoreCur == -1){
                scoreCur = 0;
            }

            score[++index][0] = this.getSectionName(i);
            score[index][1] = String.valueOf(scoreCur);
            score[index][2] = "0";
        }

        score[++index][0] = "Bonus";
        score[index][1] = String.valueOf(this.getUpperBonus());
        score[index][2] = "1";

        score[++index][0] = "Upper Total";
        score[index][1] = String.valueOf(this.getUpperTotal());
        score[index][2] = "1";


        for(int i = this.dieSides + 1; i <= this.totalSpots; i++){
            int scoreCur = this.getSectionScore(i);
            if(scoreCur == -1){
                scoreCur = 0;
            }
            score[++index][0] = this.getSectionName(i);
            score[index][1] = String.valueOf(scoreCur);
            score[index][2] = "0";
        }

        score[++index][0] = "Lower Total";
        score[index][1] = String.valueOf(this.getLowerTotal());
        score[index][2] = "1";

        score[++index][0] = "Grand Total";
        score[index][1] = String.valueOf(this.getTotalScore());
        score[index][2] = "1";

        return score;
    }

    /**
     * Sets the score 
     * @param toAdd an array of size 3. The first element stores the category-id where the score
     * will be added. The second element stores what score will
     * be added. The third element stores if the category-id refers to the upper/lower section of the 
     * scoresheet. 0 refers to the upper-section and 1 refers to the lower-section.
     */
    public void setScore(int[] toAdd){
        if(toAdd[2] == 0){
            this.upperSection.get(toAdd[0]).score = toAdd[1];
            this.upperTotal += toAdd[1];
            if(this.upperTotal >= this.upperBonusThreshold){
                this.upperBonus = true;
            }
        }else{
            this.lowerSection.get(toAdd[0]).score = toAdd[1];
            this.lowerTotal += toAdd[1];

        }
    }     
}
