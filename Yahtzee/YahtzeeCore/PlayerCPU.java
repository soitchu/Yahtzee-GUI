package Yahtzee.YahtzeeCore;

import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;
import javax.swing.*;

import Yahtzee.YahtzeeWindows.DiceGUI;
import Yahtzee.YahtzeeWindows.GameGUI;

public class PlayerCPU extends Player{
    Timer timer;
    boolean dirty = false;
    int wait = 1500;
    public PlayerCPU(int numberDice, int dieSides, int retries, String playerName, int timeoutCPU){
        super(numberDice, dieSides, dieSides, playerName);
        super.CPU = true;
        this.wait = timeoutCPU;
    }

    public void toRollOrNotToRoll(ArrayList<DiceGUI> dice, GameGUI game){
        // action 1 : submit
        // action 2 : roll some
        // action 3 : roll all
        
        
        Random rand = new Random();
        boolean didSubmit = false;
        int action = (rand.nextInt(3) + 1);       

        if(game.hasRunOut()){
            action = 1;
        }

        if(action == 1){
            didSubmit = game.submitAction(true);  
        }
        else if(action == 2){
            for(DiceGUI die : dice){
                int shouldSelect = (rand.nextInt(100) + 1);
                if(shouldSelect % 2 == 0){
                    die.select();   
                }
            }

            game.rollAction(false, true);
        }
        else if(action == 3){
            game.rollAction(true, true);
        }

        PlayerCPU self = this;
        if(!didSubmit){
            timer = new Timer(this.wait, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    self.timer.stop();
                    self.toRollOrNotToRoll(dice, game);
                }


            });
            self.timer.start();
        }else{
            timer = new Timer(this.wait, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    self.timer.stop();
                    self.selectSpot(game);
                }

            });
            self.timer.start();
        }
    }

    public void selectSpot(GameGUI game){
        PlayerCPU self = this;

        game.scoreGUI.selectedSpot = game.scoreGUI.spotWithMaxScore;
        game.scoreGUI.next(true);

        timer = new Timer(this.wait, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.timer.stop();
                game.scoreGUI.endTurn();
            }

        });
        self.timer.start();
    }
}
