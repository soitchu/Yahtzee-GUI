package Yahtzee.UnitTests;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import Yahtzee.YahtzeeCore.Scoresheet;

public class ScoresheetTest {

    @Test
    public void isTheScoreCorrect1(){
        Scoresheet scores = new Scoresheet(5, 6);
        int input[] = {3,10,0};
        scores.setScore(input);
        assertTrue(scores.upperSection.get(3).score == 10);
    }


    @Test
    public void isTheScoreCorrect2(){
        Scoresheet scores = new Scoresheet(5, 6);
        int input[] = {3,100,1};
        scores.setScore(input);
        assertTrue(scores.lowerSection.get(3).score == 100);
    }

    @Test
    public void getTotalScore1(){
        Scoresheet scores = new Scoresheet(5, 6);
        int input1[] = {3,100,1};
        scores.setScore(input1);

        int input2[] = {5,10,1};
        scores.setScore(input2);

        int input3[] = {1,4,1};
        scores.setScore(input3);
        
        assertTrue(scores.getTotalScore() == 114);
    }

    @Test
    public void getTotalScore2(){
        Scoresheet scores = new Scoresheet(5, 6);
        int input1[] = {3,3,0};
        scores.setScore(input1);

        int input2[] = {5,10,1};
        scores.setScore(input2);

        int input3[] = {1,4,0};
        scores.setScore(input3);
        
        assertTrue(scores.getTotalScore() == 17);
    }


    @Test
    public void bonus1(){
        Scoresheet scores = new Scoresheet(5, 6);
        int bonusThres = 63;

        int input1[] = {0,bonusThres - 1,0};
        scores.setScore(input1);
        assertTrue(scores.getTotalScore() == (bonusThres - 1));
    }

    @Test
    public void bonus2(){
        Scoresheet scores = new Scoresheet(5, 6);
        int bonusThres = 63;

        int input1[] = {0,bonusThres,0};        
        scores.setScore(input1);
        assertTrue(scores.getTotalScore() == (bonusThres + 35));
    }

}