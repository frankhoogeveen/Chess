/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.tictactoe.TicTacToeGameReportFormatter;
import nl.fh.rule.tictactoe.TicTacToe;
import nl.fh.gamereport.GameReportFormatter;
import nl.fh.gamereport.filter.RandomFilter;
import nl.fh.gamereport.filter.TransparentFilter;
import nl.fh.gamestate.tictactoe.TicTacToeState;
import nl.fh.match.AlternatingMatch;
import nl.fh.match.GenericMatchReportFormatter;
import nl.fh.match.MatchReport;
import nl.fh.match.MatchReportFormatter;
import nl.fh.player.Player;
import nl.fh.player.random.RandomPlayer;
import nl.fh.rule.GameDriver;

/**
 * 
 * 
 */
public class Job_010_tictactoe_between_random_players {
    
    public static void main(String[] args){

      Player player1 = new RandomPlayer();  
      Player player2 = new RandomPlayer();      
      
      GameDriver<TicTacToeState> driver = TicTacToe.getGameDriver();
      int nGames = 1000;
      
//    GameFilter filter = new TransparentFilter();
      GameFilter filter = new RandomFilter(4./nGames);
      AlternatingMatch match = new AlternatingMatch(nGames, driver);
      
      MatchReport report = match.play(player1, player2, filter);
      
      GameReportFormatter<TicTacToeState> gFormatter = new TicTacToeGameReportFormatter();
      MatchReportFormatter<TicTacToeState> mFormatter = new GenericMatchReportFormatter<TicTacToeState>(gFormatter);
      
      System.out.println(mFormatter.formatMatch(report));
    }
}