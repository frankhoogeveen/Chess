/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameReport;
import nl.fh.player.Player;
import nl.fh.player.random.RandomPlayer;
import nl.fh.player.terminal.TerminalPlayer;
import nl.fh.rules.Chess;

/**
 * 
 * 
 */
public class Job_002_game_between_terminal_and_random_player {
    
    public static void main(String[] args){
      Player player1 = new RandomPlayer();
      Player player2 = new TerminalPlayer();
      
      GameReport report;
      if(Math.random() > 0.5){
        report = Chess.gameDriver.playGame(player1, player2);
      } else {
        report = Chess.gameDriver.playGame(player2, player1);          
      }
      
      System.out.println(report.toPGN());
    }
}