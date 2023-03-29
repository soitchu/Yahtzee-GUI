package Yahtzee.UnitTests;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import Yahtzee.YahtzeeCore.*;
public class PlayerTest {
    @Test
    public void isNameCorrect(){
        Player p = new Player(5, 6, 3, "Suyash");
        assertTrue(p.playerName.equals("Suyash"));
    } 

    @Test
    public void hasTheScoreBeenSet1(){
        int dieSides = 6;
        Player p = new Player(5, dieSides, 3, "Suyash");

        Scoresheet s = p.getScoresheet();
        for(int i = 0; i < dieSides; i++){
            int input[] = {i,10,0};
            s.setScore(input);

        }

        for(int i = 0; i < 7; i++){
            int input[] = {i,10,1};
            s.setScore(input);
        }

        assertTrue(p.isScoresheetFull());

    } 


    @Test
    public void hasTheScoreBeenSet2(){
        int dieSides = 6;
        Player p = new Player(5, dieSides, 3, "Suyash");

        Scoresheet s = p.getScoresheet();
        for(int i = 0; i < dieSides; i++){
            int input[] = {i,10,0};
            s.setScore(input);

        }

        for(int i = 0; i < 6; i++){
            int input[] = {i,10,1};
            s.setScore(input);
        }

        assertTrue(!p.isScoresheetFull());

    } 
    
    

}