package Yahtzee.UnitTests;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import Yahtzee.YahtzeeCore.*;

public class SectionTest {
    @Test
    public void isNameCorrect(){
        Section sec = new Section("name2");
        assertTrue(sec.name.equals("name2"));
    } 

    @Test
    public void isEmpty1(){
        Section sec = new Section("name2");
        sec.score = 10;
        assertTrue(!sec.isEmpty());
    } 

    @Test
    public void isEmpty2(){
        Section sec = new Section("name2");
        assertTrue(sec.isEmpty());
    } 
}