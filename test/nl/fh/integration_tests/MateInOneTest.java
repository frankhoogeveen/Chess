/*
 * License: GPL v3
 * 
 */

package nl.fh.integration_tests;

import nl.fh.gamestate.GameState;
import nl.fh.metric.ShannonMetric;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.metric.minimax.SearchMode;
import nl.fh.move.Move;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class MateInOneTest {
    
    @Test
    public void testMateInOneShannon(){
        String fen = "rnb1k1nr/pppp1ppp/4p3/4P3/3b4/8/4qPPP/1K5R b kq - 0 15";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);
        ShannonMetric shannon = new ShannonMetric();
       
//        for(Move m : state.getLegalMoves()){
//            GameState post = m.applyTo(state);
//            System.out.print(m.moveString(state));
//            System.out.print(" ");
//            System.out.println(shannon.eval(post));
//        }
        
        Player player = MetricPlayer.getInstance(shannon);
        
        Move move = player.getMove(state);
        assertEquals("Qb2#", move.moveString(state));
       
    } 
    
    
    @Test
    public void testMateInOneNegaMax(){
        String fen = "rnb1k1nr/pppp1ppp/4p3/4P3/3b4/8/4qPPP/1K5R b kq - 0 15";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);
        ShannonMetric shannon = new ShannonMetric();
       
//        for(Move m : state.getLegalMoves()){
//            GameState post = m.applyTo(state);
//            System.out.print(m.moveString(state));
//            System.out.print(" ");
//            System.out.println(shannon.eval(post));
//        }
        
        int depth = 1;
        Player player = MetricPlayer.getInstance(new NegaMax(shannon, depth, SearchMode.MAXIMIN));
        
        Move move = player.getMove(state);
        assertEquals("Qb2#", move.moveString(state));
       
    }

}