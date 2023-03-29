package Yahtzee.UnitTests;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

import Yahtzee.YahtzeeCore.HandAnalyzer;
import Yahtzee.YahtzeeCore.Scoresheet;

public class HandAnalyzerTest {
    
    @Test
    public void isFullHouseTest1(){
        Scoresheet scores = new Scoresheet(5,6);
        ArrayList<Integer> handArray = new ArrayList<>(Arrays.asList(1,1,1,2,2));
        HandAnalyzer analyzer = new HandAnalyzer(handArray, scores.numberDice, scores.dieSides, scores.upperSection.size(), 0);
        assertTrue(analyzer.isFullHouse());
    }

    @Test
    public void isFullHouseTest2(){
        Scoresheet scores = new Scoresheet(5,6);
        ArrayList<Integer> handArray = new ArrayList<>(Arrays.asList(1,2,2,1,1));
        HandAnalyzer analyzer = new HandAnalyzer(handArray, scores.numberDice, scores.dieSides, scores.upperSection.size(), 0);
        assertTrue(analyzer.isFullHouse());
    }

    @Test
    public void isFullHouseTest3(){
        Scoresheet scores = new Scoresheet(5,6);
        ArrayList<Integer> handArray = new ArrayList<>(Arrays.asList(1,2,4,1,1));
        HandAnalyzer analyzer = new HandAnalyzer(handArray, scores.numberDice, scores.dieSides, scores.upperSection.size(), 0);
        assertTrue(analyzer.isFullHouse() == false);
    }

    @Test
    public void isFullHouseTest4(){
        Scoresheet scores = new Scoresheet(7,6);
        ArrayList<Integer> handArray = new ArrayList<>(Arrays.asList(1,1,1,1,2,2,2));
        HandAnalyzer analyzer = new HandAnalyzer(handArray, scores.numberDice, scores.dieSides, scores.upperSection.size(), 0);
        assertTrue(analyzer.isFullHouse());
    }

    @Test
    public void isFullHouseTest5(){
        Scoresheet scores = new Scoresheet(8,6);
        ArrayList<Integer> handArray = new ArrayList<>(Arrays.asList(1,1,1,1,2,2,2,2));
        HandAnalyzer analyzer = new HandAnalyzer(handArray, scores.numberDice, scores.dieSides, scores.upperSection.size(), 0);
        assertTrue(analyzer.isFullHouse());
    }

    @Test
    public void maxStraightFoundTest1(){
        Scoresheet scores = new Scoresheet(9,6);
        ArrayList<Integer> handArray = new ArrayList<>(Arrays.asList(2,2,2,3,4,5,6,6,6));
        HandAnalyzer analyzer = new HandAnalyzer(handArray, scores.numberDice, scores.dieSides, scores.upperSection.size(), 0);

        assertTrue(analyzer.maxStraightFound() == 5);
    }

    @Test
    public void maxStraightFoundTest2(){
        Scoresheet scores = new Scoresheet(3,6);
        ArrayList<Integer> handArray = new ArrayList<>(Arrays.asList(1,2,3));
        HandAnalyzer analyzer = new HandAnalyzer(handArray, scores.numberDice, scores.dieSides, scores.upperSection.size(), 0);

        assertTrue(analyzer.maxStraightFound() == 3);
    }

    @Test
    public void scoreToAddTest1(){
        Scoresheet scores = new Scoresheet(5,6);
        ArrayList<Integer> handArray = new ArrayList<>(Arrays.asList(1,2,3,4,5));
        HandAnalyzer analyzer = new HandAnalyzer(handArray, scores.numberDice, scores.dieSides, scores.upperSection.size(), scores.upperSection.size() + 3 + 1);


        int[] output = analyzer.scoreToAdd();
        assertTrue(output[0] == 3 && output[1] == 40);
    }

    @Test
    public void scoreToAddTest2(){
        Scoresheet scores = new Scoresheet(7,6);
        ArrayList<Integer> handArray = new ArrayList<>(Arrays.asList(1,1,2,3,4,5,5));
        HandAnalyzer analyzer = new HandAnalyzer(handArray, scores.numberDice, scores.dieSides, scores.upperSection.size(), scores.upperSection.size() + 4 + 1);


        int[] output = analyzer.scoreToAdd();
        assertTrue(output[0] == 4 && output[1] == 30);
    }

    @Test
    public void scoreToAddTest3(){
        Scoresheet scores = new Scoresheet(7,6);
        ArrayList<Integer> handArray = new ArrayList<>(Arrays.asList(1,1,1,3,4,5,5));
        HandAnalyzer analyzer = new HandAnalyzer(handArray, scores.numberDice, scores.dieSides, scores.upperSection.size(), 0);


        int output = analyzer.maxStraightFound();
        assertTrue(output == 3);
    }

    @Test
    public void maxOfAKindFoundTest1(){
        Scoresheet scores = new Scoresheet(7,6);
        ArrayList<Integer> handArray = new ArrayList<>(Arrays.asList(1,1,1,1,1,1,1));
        HandAnalyzer analyzer = new HandAnalyzer(handArray, scores.numberDice, scores.dieSides, scores.upperSection.size(), 0);


        int output = analyzer.maxOfAKindFound();
        assertTrue(output == 7);
    }

    @Test
    public void maxOfAKindFoundTest2(){
        Scoresheet scores = new Scoresheet(6,6);
        ArrayList<Integer> handArray = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        HandAnalyzer analyzer = new HandAnalyzer(handArray, scores.numberDice, scores.dieSides, scores.upperSection.size(), 0);


        int output = analyzer.maxOfAKindFound();
        assertTrue(output == 1);
    }



}