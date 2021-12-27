/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameReportFormatter;
import nl.fh.gamereport.PGNformatter;
import nl.fh.player.Player;
import nl.fh.player.random.RandomPlayer;
import nl.fh.player.terminal.TerminalPlayer;
import nl.fh.rules.FIDEchess;

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
        report = FIDEchess.getGameDriver().playGame(player1, player2);
      } else {
        report = FIDEchess.getGameDriver().playGame(player2, player1);          
      }
      
      GameReportFormatter formatter = new PGNformatter(FIDEchess.getGameDriver());
      System.out.println(formatter.formatGame(report));
    }
}