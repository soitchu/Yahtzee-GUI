/**
 * Starts the game
 * CPSC 224
 * Final project
 * No sources to cite.
 * 
 * @author Team Full House
 * @version 1.4.0 12/7/2022
 */
package Yahtzee;

import Yahtzee.YahtzeeWindows.*;

public class Main {
    public static void main(String[] args){
        YahtzeeWindow yahtzee = new YahtzeeWindow();
        int timeoutCPU = 1500;
        if(args.length > 0){
            try{
                timeoutCPU = Integer.parseInt(args[0]);
            }catch(Exception e){
                System.out.println("Not a valid number");
            }
        }
        if(args.length == 2 && args[0].equals("test")){
            timeoutCPU = Integer.parseInt(args[1]);
            yahtzee.testing = true;
            yahtzee.start(timeoutCPU);
        }else{
            yahtzee.start(timeoutCPU);
        }
    }
}
