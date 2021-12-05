/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameReport;
import nl.fh.player.Player;
import nl.fh.player.random.RandomPlayer;
import nl.fh.player.terminal.TerminalPlayer;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;

/**
 * 
 * 
 */
public class Job_002_game_between_terminal_and_random_player {
    
    public static void main(String[] args){
      Player player1 = new RandomPlayer();
      Player player2 = new TerminalPlayer();
      Rules rules = new SimpleRules();
      
      GameReport report = rules.playGame(player1, player2);

      /*
      System.out.println(report.getStateList().get(0));
      for(int i = 0; i < report.getMoveList().size(); i++ ){
          System.out.print(report.getMoveList().get(i).toString());
          System.out.print("  " );
          System.out.println(report.getMoveList().get(i).moveString(report.getStateList().get(i), rules));
          System.out.println(report.getStateList().get(i+1).toFEN());
          System.out.println();
      }
      */
      
      System.out.println(report.toPGN(rules));
    }
}