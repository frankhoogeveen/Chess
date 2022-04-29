package nl.fh.gamestate.tictactoe;



import java.util.Set;
import nl.fh.gamestate.Move;
import nl.fh.metric.minimax.NegaMaxAlphaBeta;
import nl.fh.metric.utilities.OutcomeMetric;
import nl.fh.metric.utilities.ZeroMetric;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.Metric;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.rule.GameDriver;
import nl.fh.rule.MoveGenerator;
import nl.fh.rule.ResultArbiter;
import nl.fh.rule.tictactoe.TicTacToe;
import static org.junit.Assert.fail;
import org.junit.Test;

/*
 * License: GPL v3
 * 
 */

/**
 * 
 * 
 */
public class TicTacToeGameTest {
      GameDriver<TicTacToeState> driver = TicTacToe.getGameDriver();
      MoveGenerator<TicTacToeState> moveGenerator = driver.getMoveGenerator();
      ResultArbiter<TicTacToeState> arbiter = driver.getResultArbiter();
      private static double WIN_VALUE = 1.e+6;
    
    /**
     * At one point the game NegaMax vs Random went
     * 
     * c1   a3
     * c2   c3
     * b1?? a1
     * b2   b3
     * 
     * and NegaMax lost. This test investigates what happened.
     * 
     */
    @Test
    public void reconstructGameTest(){

        int depth = 20;
        Metric<TicTacToeState> metricZ = new ZeroMetric();
        OutcomeMetric metricO = new OutcomeMetric(metricZ, WIN_VALUE, driver);
        NegaMaxAlphaBeta metricN = new NegaMaxAlphaBeta(metricO, moveGenerator, depth);    
        Player player = MetricPlayer.getInstance(metricN);             

        TicTacToeState state = new TicTacToeState(); 

        Move<TicTacToeState> move1 = new TicTacToeMove("c1");
        state = move1.applyTo(state);
        System.out.println(state);

        Move<TicTacToeState> move2 = new TicTacToeMove("a3");
        state = move2.applyTo(state);
        System.out.println(state);

        Move<TicTacToeState> move3 = new TicTacToeMove("c2");
        state = move3.applyTo(state);
        System.out.println(state);

        Move<TicTacToeState> move4 = new TicTacToeMove("c3");
        state = move4.applyTo(state);
        System.out.println(state);      

        // at this point NegaMax picks the wrong move: b1
        Set<Move<TicTacToeState>> legalMoves = moveGenerator.calculateAllLegalMoves(state);
        Move move = player.getMove(state, legalMoves);
        System.out.println(move);
        
        
        Move<TicTacToeState> moveLosing1 = new TicTacToeMove("b1");
        TicTacToeState stateLosing = moveLosing1.applyTo(state);
        
        Move<TicTacToeState> moveLosing2 = new TicTacToeMove("b3");
        stateLosing = moveLosing2.applyTo(stateLosing);        

        System.out.println(stateLosing);
        System.out.println(metricO.eval(stateLosing)); 
        
        legalMoves = moveGenerator.calculateAllLegalMoves(stateLosing);
        System.out.println(arbiter.isWin(stateLosing, legalMoves));
        System.out.println(arbiter.isLoss(stateLosing, legalMoves));
        System.out.println(arbiter.isDraw(stateLosing, legalMoves, null));        

        
        fail();
    }
    
    

}