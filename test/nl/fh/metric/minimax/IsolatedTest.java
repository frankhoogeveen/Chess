/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.metric.ShannonMetric;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.Metric;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class IsolatedTest {
    @Test
    public void testNegamaxCase(){
        // position from https://lichess.org/igoizkyc#23        
        // set up the position after 2...Qh4
        String fen = "rnb1kbnr/pppp1ppp/8/4p3/4P2q/3P4/PPP2PPP/RNBQKBNR w KQkq - 1 3";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);

        Metric<GameState> shannon = new ShannonMetric();
        int depth = 1;
        NegaMax nega = new NegaMax(shannon, depth, SearchMode.MINIMAX);
        Player player = MetricPlayer.getInstance(nega);
        
        Move calculatedMove = player.getMove(state);
        Move moveQh5 = PieceMove.getInstance( Field.getInstance("d1"), Field.getInstance("h5"));
        
        //assertEquals(moveQh5, calculatedMove);      // why   
        System.out.println(calculatedMove.moveString(state));
        GameState state1 = calculatedMove.applyTo(state);
        
        System.out.println(nega.eval(state1));
        
        for(Move m : state1.getLegalMoves()){
            GameState s = m.applyTo(state1);
            System.out.print(m.moveString(state1));
            System.out.print(" ");
            System.out.print(shannon.eval(s));
            System.out.print("\n");
        }
        
        
        
    }

}