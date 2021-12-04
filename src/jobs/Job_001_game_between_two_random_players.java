/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameReport;
import nl.fh.player.Player;
import nl.fh.player.random.RandomPlayer;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;

/**
 * 
 * 
 */
public class Job_001_game_between_two_random_players {
    
    public static void main(String[] args){
      Player player1 = new RandomPlayer();
      Player player2 = new RandomPlayer();
      Rules rules = new SimpleRules();
      
      GameReport report = rules.playGame(player1, player2);
      System.out.println(report.toPGN(rules));
    }
}