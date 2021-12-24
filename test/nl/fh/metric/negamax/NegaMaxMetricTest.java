/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.negamax;

import nl.fh.metric.*;
import nl.fh.gamestate.GameState;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rules.Chess;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class NegaMaxMetricTest {
    private final double delta = 1.e-9;
    Metric<GameState> baseMetric = new MaterialCountMetric(Chess.gameDriver);   
    NegaMax<GameState> nega = new NegaMax<GameState>(baseMetric, Chess.moveGenerator, 0);
    
    @Test
    public void BackRankCaseTest(){
        String fen = "k7/pp6/8/8/8/8/7R/7K w - - 0 1";
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(0);
        assertEquals(+3.0, nega.eval(state), delta);
        
        nega.setDepth(1);
        assertEquals( MaterialCountMetric.MATE_VALUE + 3.0, nega.eval(state), delta);
        
        nega.setDepth(2);
        assertEquals( MaterialCountMetric.MATE_VALUE + 3.0, nega.eval(state), delta);
        
        nega.setDepth(3);
        assertEquals( MaterialCountMetric.MATE_VALUE + 3.0, nega.eval(state), delta);       
        
    }  
    @Test
    public void BackRankCaseTest2(){
        String fen = "k6R/pp6/8/8/8/8/8/7K b - - 1 1";
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(0);
        assertEquals( MaterialCountMetric.MATE_VALUE + 3.0, nega.eval(state), delta);
        
        nega.setDepth(1);
        assertEquals( MaterialCountMetric.MATE_VALUE + 3.0, nega.eval(state), delta);
        
        nega.setDepth(2);
        assertEquals( MaterialCountMetric.MATE_VALUE + 3.0, nega.eval(state), delta);      
        
    }  

    @Test
    public void BackRankCaseTest3(){
        String fen = "k6R/pp6/8/8/8/8/8/7K w - - 1 1";
        GameState state  = GameState.fromFEN(fen);

        nega.setDepth(0);
        assertEquals( MaterialCountMetric.MATE_VALUE + 3.0, nega.eval(state), delta);
        
        nega.setDepth(1);
        assertEquals( MaterialCountMetric.MATE_VALUE + 3.0, nega.eval(state), delta);
        
        nega.setDepth(2);
        assertEquals( MaterialCountMetric.MATE_VALUE + 3.0, nega.eval(state), delta);         
    }    
    
    @Test
    public void BackRankCaseTest4(){
        String fen = "k7/ppr5/8/8/8/8/7R/7K w - - 0 1";
        GameState state  = GameState.fromFEN(fen);

        nega.setDepth(0);
        assertEquals( -2.0, nega.eval(state), delta);  

        nega.setDepth(1);
        assertEquals( -2.0, nega.eval(state), delta);
        
        nega.setDepth(2);
        assertEquals( -2.0, nega.eval(state), delta);  

        nega.setDepth(3);
        assertEquals( MaterialCountMetric.MATE_VALUE + 3.0, nega.eval(state), delta);          
    } 

    @Test
    public void BackRankCaseTest5(){
        String fen = "k7/pprr4/8/8/8/8/7R/7K w - - 0 1";
        
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(0);
        assertEquals( -7.0, nega.eval(state), delta);   

        nega.setDepth(1);
        assertEquals( -7.0, nega.eval(state), delta);
        
        nega.setDepth(2);
        assertEquals( -7.0, nega.eval(state), delta);      

        nega.setDepth(3);
        assertEquals( -2.0, nega.eval(state), delta);             
    } 
 
    @Test
    public void BackRankCaseTest6(){
        String fen = "k7/pprr1b2/8/8/8/8/7R/7K w - - 0 1";
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(0);
        assertEquals( -10.0, nega.eval(state), delta);

        nega.setDepth(1);
        assertEquals( -10.0, nega.eval(state), delta);  
        
        nega.setDepth(2);
        assertEquals( -10.0, nega.eval(state), delta);          

        nega.setDepth(3);
        assertEquals( -7.0, nega.eval(state), delta);   
    } 
    
    @Test
    public void BackRankCaseTest7(){
        String fen = "k6R/pprr1b2/8/8/8/8/8/7K b - - 2 1";
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(1);
        
        nega.setDepth(0);
        assertEquals( -10.0, nega.eval(state), delta);   

        nega.setDepth(1);
        assertEquals( -10.0, nega.eval(state), delta);
        
        nega.setDepth(2);
        assertEquals( -7.0, nega.eval(state), delta);         

        nega.setDepth(3);
        assertEquals( -7.0, nega.eval(state), delta);     
    }  
}









