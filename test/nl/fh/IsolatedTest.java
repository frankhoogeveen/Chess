/*
 * License: GPL v3
 * 
 */

package nl.fh;

import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.chess.PieceKind;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.GameState;
import nl.fh.metric.ShannonMetric;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.metric.minimax.SearchMode;
import nl.fh.move.Move;
import nl.fh.move.Promotion;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.Metric;
import nl.fh.player.pgn_replayer.PgnReplayer;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * 
 * 
 */
public class IsolatedTest {
    
    
    @Test
    public void testTwoDeep(){
        // position from https://lichess.org/igoizkyc#23
        String fen = "r1bq1rk1/ppp2pb1/2np1n1p/4P1p1/8/2N1P1B1/PPPNBPPP/R2Q1RK1 b - - 0 12";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);
        
        Metric<GameState> shannon = new ShannonMetric();
        
        Metric<GameState> nega2  = new NegaMax(shannon, 2, SearchMode.MAXIMIN);
        Metric<GameState> nega1 = new NegaMax(shannon, 1, SearchMode.MAXIMIN);        
        
        double negaValue = nega2.eval(state);
        
        double currentMax = - Double.MAX_VALUE;
        for(Move m : state.getLegalMoves()){
            GameState next = m.applyTo(state);
            
            assertEquals(Color.BLACK, next.getColor());
            
            double value = nega1.eval(next);
            if(value > currentMax){
                currentMax = value;
            }
            
        }
        
        assertEquals(currentMax, negaValue, 1.e-8);
    }   

}