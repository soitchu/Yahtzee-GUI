/**
 * Analyzes the handArray
 * CPSC 224
 * Final project
 * No sources to cite.
 * 
 * @author Team Full House
 * @version 1.4.0 12/7/2022
 */

package Yahtzee.YahtzeeCore;
import java.util.ArrayList;

public class HandAnalyzer {

    private ArrayList<Integer> handArray;
    private int[] counts;
    private int totalCount = 0;
    private int numberDice;
    private int dieSides;
    private int upperSize;
    private int selectedSpot;

    public HandAnalyzer(ArrayList<Integer> handArray, int numberDice, int dieSides, int upperSize, int selectedSpot){
        this.handArray = handArray;
        this.numberDice = numberDice;
        this.upperSize = upperSize;
        this.dieSides = dieSides;
        this.selectedSpot = selectedSpot;
        counts = new int[this.dieSides];
        this.countNums();

    }

    /**
     * Analyzes the handArray  
     * @return An array of length 3. The first element stores the category-id where the score
     * will be added. The second element stores what score will
     * be added. The third element stores if the category-id refers to the upper/lower section of the 
     * scoresheet. 0 refers to the upper-section and 1 refers to the lower-section.
     */
    public int[] scoreToAdd(){
        
        int[] output = {0,0,0};
        int kind = -1;
        // Stores the scores corresponding to the category-id of the lower-section
        int[] allScores = {50, this.totalCount, this.totalCount, 40, 30, 25, this.totalCount};
        int whatKind = this.maxOfAKindFound();
        int straight = this.maxStraightFound();
        int maxStraightPossible = Math.min(this.dieSides, this.numberDice);
        boolean lowerSection = false;

        if(this.selectedSpot <= this.upperSize){
            kind = this.selectedSpot - 1;
            output[1] = this.counts[kind]*(kind + 1);
        }else{
            lowerSection = true;
            int spot = this.selectedSpot - this.upperSize - 1;
            switch (spot) {
                case 0:
                    output[1] = (whatKind == this.numberDice) ? allScores[0] : 0;
                    break;
                case 1:
                    output[1] = (whatKind >= this.numberDice - 1) ? allScores[1] : 0;
                    break;
                case 2:
                    output[1] = (whatKind >= this.numberDice - 2) ? allScores[2] : 0;
                    break;
                case 3:
                    output[1] = (straight >= maxStraightPossible) ? allScores[3] : 0;
                    break;
                case 4:
                    output[1] = (straight >= maxStraightPossible - 1) ? allScores[4] : 0;
                    break;
                case 5:
                    output[1] = (this.isFullHouse()) ? allScores[5] : 0;
                    break;
                case 6:
                    output[1] = allScores[6];
                    break;                
            }

            kind = spot;
        }

        output[0] = kind;
        output[2] = lowerSection ? 1 : 0;
        return output;


    }
    
    /**
     * Stores the frequency of numbers in the handArray. 0th element corresponds to the number
     * of ones and so on.
     */
    private void countNums(){
        for(int i = 0; i < this.handArray.size(); i++){
            int handNum = this.handArray.get(i);
            this.counts[handNum - 1]++;
            totalCount += handNum;
        }
    }

    /**
     * Checks if the handArray is a full house
     * @return returns true if the handArray is a full house
     */
    public Boolean isFullHouse(){
        Boolean hasCeil = false;
        Boolean hasFloor = false;
        double half = (double)this.numberDice/2;
        int lowerBound = (int) Math.floor(half);
        int upperBound = (int) Math.ceil(half);
        
        for(int i = 0; i < this.counts.length; i++){
            if(this.counts[i] == lowerBound && hasFloor == false){
                hasFloor = true;
            } else if(this.counts[i] == upperBound && hasCeil == false){
                hasCeil = true;
            } else if(this.counts[i]!=0){
                return false;
            }
        }
        return hasCeil && hasFloor;
    }

    /**
     * Find the highest frequency of a number in the "counts" array 
     * @return the hightest frequency of a number
     */
    public int maxOfAKindFound(){

        int maxKind = 0;

        for(int i = 0; i < this.counts.length; i++){
            if(maxKind < this.counts[i]){
                maxKind = this.counts[i];
            }
        }

        return maxKind;

    }

    /**
     * Used to find small-straights and large-straights
     * @return the largest straight found
     */
    public int maxStraightFound(){

        int maxLength = 0;
        int currentLength = 0;
        for(int i = 0; i < this.counts.length; i++){
            if(this.counts[i] > 0){
                currentLength++;
            } else{

                maxLength = Math.max(maxLength, currentLength);
                currentLength = 0;

            }
        }

        maxLength = Math.max(maxLength, currentLength);
        return maxLength;

    }
}
