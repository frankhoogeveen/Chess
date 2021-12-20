/*
 * License: GPL v3
 * 
 */

package nl.fh.integration_tests;

import nl.fh.gamestate.GameState;
import nl.fh.metric.MaterialCountMetric;
import nl.fh.metric.ShannonMetric;
import nl.fh.metric.minimax.NegaMax;
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
        
        int depth = 1;
        Player player = MetricPlayer.getInstance(new NegaMax(shannon, depth));
        
        Move move = player.getMove(state);
        assertEquals("Qb2#", move.moveString(state));
    }
    
   //@Test  //test switched off because of time consumption
    public void testMateInOneNegaMax2(){
        String fen = "rnb1k1nr/pppp1ppp/4p3/4P3/3b4/8/4qPPP/1K5R b kq - 0 15";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);
        ShannonMetric shannon = new ShannonMetric();
        
        int depth = 2;
        Player player = MetricPlayer.getInstance(new NegaMax(shannon, depth));
        
        Move move = player.getMove(state);
        assertEquals("Qb2#", move.moveString(state));
    }   
    
    @Test
    public void testMateInOneBlackToMove(){
        String fen = "3K4/7r/3k4/8/8/8/8/8 b - - 0 1";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);
        MaterialCountMetric metric = new MaterialCountMetric();
        
        Player player = MetricPlayer.getInstance(metric);
        
        Move move = player.getMove(state);
        assertEquals("Rh8#", move.moveString(state));
       
    }     

    
    @Test
    public void testMateInOneWhiteToMove(){
        String fen = "8/8/8/qn6/kn6/1n6/1KP5/8 w - - 0 1";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);
        MaterialCountMetric metric = new MaterialCountMetric();
        
        Player player = MetricPlayer.getInstance(metric);
        
        Move move = player.getMove(state);
        assertEquals("cxb3#", move.moveString(state));
       
    }     
}