/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.tictactoe.TicTacToeGameReportFormatter;
import nl.fh.rule.tictactoe.TicTacToe;
import nl.fh.gamereport.GameReportFormatter;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.tictactoe.TicTacToeState;
import nl.fh.metric.minimax.NegaMaxAlphaBeta;
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
public class Job_012a_tictactoe_between_random_player_and_negamax_investigate {

    private static final double WIN_VALUE = 1.e+6;
    
    public static void main(String[] args){

      GameDriver<TicTacToeState> driver = TicTacToe.getGameDriver();
      MoveGenerator<TicTacToeState> moveGenerator = driver.getMoveGenerator();
      GameReportFormatter<TicTacToeState> gFormatter = new TicTacToeGameReportFormatter();      
        

      // player with essentially unlimited look-ahead evaluating
      // endpositions by win/loss/draw
      int depth = 20;
      Metric<TicTacToeState> metric = new ZeroMetric();
      metric = new OutcomeMetric(metric, WIN_VALUE, driver);
//      metric = new NegaMax(metric, moveGenerator, depth);
      metric = new NegaMaxAlphaBeta(metric, moveGenerator, depth);      
//      metric = new NegaMaxGen3(metric, moveGenerator, depth);

      Player player1 = MetricPlayer.getInstance(metric);      

      // playing against a random player
      Player player2 = new RandomPlayer();  
      
      // find a game where the random player wins, i.e. player1 does 
      // not play optimally, contrary to expectations
      GameResult result = GameResult.UNDECIDED;
      GameReport report  = null;
      
      while(result != GameResult.WIN_SECOND_MOVER){
          report = driver.playGame(player1, player2);
          result = report.getGameResult();
      }
      
      System.out.println(gFormatter.formatGame(report));      
    }
}

// the game that unexpectedly loses
// the move c2 is wrong....
// 
//=== report ===
//c1    |   |  X
//c3   O|   |  X
//b1   O|   | XX
//a2   O|O  | XX
//c2   O|O X| XX
//b3  OO|O X| XX
//b2  OO|OXX| XX
//a3 OOO|OXX| XX
//
//WIN_SECOND_MOVER