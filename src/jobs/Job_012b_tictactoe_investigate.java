/*
 * License: GPL v3
 * 
 */

package jobs;

import java.util.Set;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.tictactoe.TicTacToeGameReportFormatter;
import nl.fh.rule.tictactoe.TicTacToe;
import nl.fh.gamereport.GameReportFormatter;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.GameState;
import nl.fh.gamestate.Move;
import nl.fh.gamestate.tictactoe.TicTacToeMove;
import nl.fh.gamestate.tictactoe.TicTacToeState;
import nl.fh.metric.minimax.NegaMaxAlphaBeta;
import nl.fh.metric.minimax.NegaMaxVerbose;
import nl.fh.metric.utilities.OutcomeMetric;
import nl.fh.metric.utilities.ZeroMetric;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.Metric;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.player.random.RandomPlayer;
import nl.fh.rule.GameDriver;
import nl.fh.rule.MoveGenerator;
import nl.fh.rule.ResultArbiter;

/**
 * 
 * 
 */
public class Job_012b_tictactoe_investigate {

    private static final double WIN_VALUE = 1.e+6;
    
    public static void main(String[] args){

      GameDriver<TicTacToeState> driver = TicTacToe.getGameDriver();
      MoveGenerator<TicTacToeState> moveGenerator = driver.getMoveGenerator();
      ResultArbiter arbiter = driver.getResultArbiter();
      
      
      int depth = 20;
      Metric<TicTacToeState> metric = new ZeroMetric();
      metric = new OutcomeMetric(metric, WIN_VALUE, driver);
//      metric = new NegaMax(metric, moveGenerator, depth);
      metric = new NegaMaxVerbose(metric, moveGenerator, depth);
//      metric = new NegaMaxAlphaBeta(metric, moveGenerator, depth);      
//      metric = new NegaMaxGen3(metric, moveGenerator, depth);


      
      GameReport report = new GameReport();
      
      TicTacToeState state = driver.getInitialState();
      System.out.println(state);
      report.addGameState(state);
      Set<Move<TicTacToeState>> legalMoves = driver.getMoveGenerator().calculateAllLegalMoves(state);
      System.out.println(arbiter.determineResult(report, legalMoves));
      System.out.println();      
      
      // first move
      TicTacToeMove move = new TicTacToeMove(2,0);
      state = move.applyTo(state);
      report.addPly(move, state);
      System.out.println(state);
      legalMoves = driver.getMoveGenerator().calculateAllLegalMoves(state);
      System.out.println(arbiter.determineResult(report, legalMoves));
      System.out.println();
      
      move = new TicTacToeMove(2,2);
      state = move.applyTo(state);
      report.addPly(move, state);      
      System.out.println(state);
      legalMoves = driver.getMoveGenerator().calculateAllLegalMoves(state);
      System.out.println(arbiter.determineResult(report, legalMoves));
      System.out.println();      
      
      // second move
      move = new TicTacToeMove(1,0);
      state = move.applyTo(state);
      report.addPly(move, state);      
      System.out.println(state);
      legalMoves = driver.getMoveGenerator().calculateAllLegalMoves(state);
      System.out.println(arbiter.determineResult(report, legalMoves));
      System.out.println();
      
      move = new TicTacToeMove(0,1);
      state = move.applyTo(state);
      report.addPly(move, state);
      System.out.println(state);
      legalMoves = driver.getMoveGenerator().calculateAllLegalMoves(state);
      System.out.println(arbiter.determineResult(report, legalMoves));
      System.out.println();
      
      // at his point player1 makes a move that is not winning
      // while move (0,0) should be winning
      
      legalMoves = driver.getMoveGenerator().calculateAllLegalMoves(state);
      for(Move<TicTacToeState> m : legalMoves){
          System.out.println(m);
          System.out.println(metric.eval(m.applyTo(state)));
          System.out.println("***********************************");
      }
      
      
      
      
      
      
      
//      move = new TicTacToeMove(0,0);
//      state = move.applyTo(state);
//      report.addPly(move, state);
//      System.out.println(state);
//      legalMoves = driver.getMoveGenerator().calculateAllLegalMoves(state);
//      System.out.println(arbiter.determineResult(report, legalMoves));
//      System.out.println();      
      
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