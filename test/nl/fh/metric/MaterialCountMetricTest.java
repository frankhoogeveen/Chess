/*
 * License: GPL v3
 * 
 */

package nl.fh.metric;

import nl.fh.gamestate.GameState;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rule.FIDEchess;
import nl.fh.rule.GameDriver;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class MaterialCountMetricTest {
    private final double delta = 1.e-9;
    private GameDriver gameDriver = FIDEchess.getGameDriver();
    private double MATE_VALUE = 1.e6;
    
    @Test
    public void BackRankCaseTest(){
        String fen = "k7/pp6/8/8/8/8/7R/7K w - - 0 1";
        
        GameState state  = GameState.fromFEN(fen);
        
        Metric<GameState> metric =MaterialCountMetric.getWrappedInstance();
        
        assertEquals(+3.0, metric.eval(state), delta);     
        
    }
    
    @Test
    public void BackRankCaseTest2(){
        String fen = "k6R/pp6/8/8/8/8/8/7K b - - 1 1";
        
        GameState state  = GameState.fromFEN(fen);
        
        Metric<GameState> metric =MaterialCountMetric.getWrappedInstance();
        
        assertEquals( MATE_VALUE, metric.eval(state), delta);     
        
    }  
//    The state tested here cannot be reached legally, therefor the
//    test is commented out.
//    @Test
//    public void BackRankCaseTest3(){
//        String fen = "k6R/pp6/8/8/8/8/8/7K w - - 1 1";
//        
//        GameState state  = GameState.fromFEN(fen);
//        
//        Metric<GameState> metric =MaterialCountMetric.getWrappedInstance();
//        
//        assertEquals( MATE_VALUE, metric.eval(state), delta);     
//    }    
    
    @Test
    public void BackRankCaseTest4(){
        String fen = "k7/ppr5/8/8/8/8/7R/7K w - - 0 1";
        
        GameState state  = GameState.fromFEN(fen);
        
        Metric<GameState> metric =MaterialCountMetric.getWrappedInstance();
        
        assertEquals(-2.0, metric.eval(state), delta);        
    } 
    
    @Test
    public void BackRankCaseTest5(){
        String fen = "k7/pprr4/8/8/8/8/7R/7K w - - 0 1";
        
        GameState state  = GameState.fromFEN(fen);
        
        Metric<GameState> metric =MaterialCountMetric.getWrappedInstance();
        
        assertEquals(-7.0, metric.eval(state), delta);      
    }    
    @Test
    public void BackRankCaseTest6(){
        String fen = "k7/pprr1b2/8/8/8/8/7R/7K w - - 0 1";
        
        GameState state  = GameState.fromFEN(fen);
        
        Metric<GameState> metric =MaterialCountMetric.getWrappedInstance();
        
        assertEquals(-10.0, metric.eval(state), delta);      
    }    
    @Test
    public void BackRankCaseTest7(){
        String fen = "k6R/pprr1b2/8/8/8/8/8/7K b - - 2 1";
        
        GameState state  = GameState.fromFEN(fen);
        
        Metric<GameState> metric =MaterialCountMetric.getWrappedInstance();
        
        assertEquals(-10.0, metric.eval(state), delta);    
    }  
    @Test
    public void BackRankCaseTest8(){
        String fen = "k3b2R/pprr4/8/8/8/8/8/7K w - - 3 2";
        
        GameState state  = GameState.fromFEN(fen);
        
        Metric<GameState> metric =MaterialCountMetric.getWrappedInstance();
        
        assertEquals(-10.0, metric.eval(state), delta);       
    }  
    @Test
    public void BackRankCaseTest9(){
        String fen = "k3R3/pprr4/8/8/8/8/8/7K b - - 0 2";
        
        GameState state  = GameState.fromFEN(fen);
        
        Metric<GameState> metric =MaterialCountMetric.getWrappedInstance();
        
        assertEquals(-7.0, metric.eval(state), delta);      
    }  
    @Test
    public void BackRankCaseTest10(){
        String fen = "k2rR3/ppr5/8/8/8/8/8/7K w - - 1 3";
        
        GameState state  = GameState.fromFEN(fen);
        
        Metric<GameState> metric =MaterialCountMetric.getWrappedInstance();
        
        assertEquals(-7.0, metric.eval(state), delta);    
    }  
    @Test
    public void BackRankCaseTest11(){
        String fen = "k2R4/ppr5/8/8/8/8/8/7K b - - 0 3";
        
        GameState state  = GameState.fromFEN(fen);
        
        Metric<GameState> metric =MaterialCountMetric.getWrappedInstance();
        
        assertEquals(-2.0, metric.eval(state), delta);     
    }  
    @Test
    public void BackRankCaseTest12(){
        String fen = "k1rR4/pp6/8/8/8/8/8/7K w - - 1 4";
        
        GameState state  = GameState.fromFEN(fen);
        
        Metric<GameState> metric =MaterialCountMetric.getWrappedInstance();
        
        assertEquals(-2.0, metric.eval(state), delta);       
    }  
    @Test
    public void BackRankCaseTest13(){
        String fen = "k1R5/pp6/8/8/8/8/8/7K b - - 0 4";
        
        GameState state  = GameState.fromFEN(fen);
        
        Metric<GameState> metric =MaterialCountMetric.getWrappedInstance();
        
        assertEquals( MATE_VALUE, metric.eval(state), delta);     
    }      
}









