/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.tictactoe.TicTacToeGameReportFormatter;
import nl.fh.rule.tictactoe.TicTacToe;
import nl.fh.gamereport.GameReportFormatter;
import nl.fh.gamereport.filter.BlockFilter;
import nl.fh.gamereport.filter.TransparentFilter;
import nl.fh.gamereport.filter.WinnerFilter;
import nl.fh.gamestate.tictactoe.TicTacToeState;
import nl.fh.match.AlternatingMatch;
import nl.fh.match.GenericMatchReportFormatter;
import nl.fh.match.MatchReport;
import nl.fh.match.MatchReportFormatter;
import nl.fh.metric.minimax.NegaMaxAlphaBeta;
import nl.fh.metric.minimax.NegaMaxGen3;
import nl.fh.metric.utilities.OutcomeMetric;
import nl.fh.metric.utilities.ZeroMetric;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.Metric;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.player.random.RandomPlayer;
import nl.fh.rule.GameDriver;
import nl.fh.rule.MoveGenerator;

/**
 * 
 * 
 */
public class Job_012_tictactoe_between_random_player_and_negamax {

    private static double WIN_VALUE = 1.e+6;
    
    public static void main(String[] args){

      GameDriver<TicTacToeState> driver = TicTacToe.getGameDriver();
      MoveGenerator<TicTacToeState> moveGenerator = driver.getMoveGenerator();
        
      Player player1 = new RandomPlayer();  
      
      int depth = 20;
      Metric<TicTacToeState> metric = new ZeroMetric();
      metric = new OutcomeMetric(metric, WIN_VALUE, driver);
//      metric = new NegaMax(metric, moveGenerator, depth);
//      metric = new NegaMaxAlphaBeta(metric, moveGenerator, depth);      
      metric = new NegaMaxGen3(metric, moveGenerator, depth);
      Player player2 = MetricPlayer.getInstance(metric);      
      
      int nGames = 300;  
//      GameFilter filter = new TransparentFilter();
//      GameFilter filter = new BlockFilter();    
      GameFilter filter = new WinnerFilter(player1);
      AlternatingMatch match = new AlternatingMatch(nGames, driver);
      
      MatchReport report = match.play(player1, player2, filter);
      
      GameReportFormatter<TicTacToeState> gFormatter = new TicTacToeGameReportFormatter();
      MatchReportFormatter<TicTacToeState> mFormatter = new GenericMatchReportFormatter<TicTacToeState>(gFormatter);
      
      System.out.println(mFormatter.formatMatch(report));
    }
}