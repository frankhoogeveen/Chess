/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameReportFormatter;
import nl.fh.gamereport.PGNformatter;
import nl.fh.match.MatchReportFormatter;
import nl.fh.player.Player;
import nl.fh.player.random.RandomPlayer;
import nl.fh.rules.FIDEchess;

/**
 * 
 * 
 */
public class Job_001_game_between_two_random_players {
    
    public static void main(String[] args){
      Player player1 = new RandomPlayer();
      Player player2 = new RandomPlayer();
      
      GameReport report = FIDEchess.getGameDriver().playGame(player1, player2);

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
      GameReportFormatter formatter = new PGNformatter(FIDEchess.getGameDriver());
      String pgn = formatter.formatGame(report);
      System.out.println(pgn);
    }
}