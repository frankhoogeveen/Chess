/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.negamax;

import nl.fh.metric.*;
import nl.fh.gamestate.GameState;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.metric.utilities.MaxOfChildren;
import nl.fh.metric.utilities.MinOfChildren;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rules.FIDEchess;
import nl.fh.rules.GameDriver;
import nl.fh.rules.MoveGenerator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class NegaMaxMetricTest4 {
    private final double delta = 1.e-9;
    private double MATE_VALUE = 1.e6;       
    
    private GameDriver gameDriver = FIDEchess.getGameDriver();
    private MoveGenerator moveGenerator = gameDriver.getMoveGenerator();       
    Metric<GameState> baseMetric = MaterialCountMetric.getWrappedInstance();   
    NegaMax<GameState> nega = new NegaMax<GameState>(baseMetric, moveGenerator, 0);
    
    String fenW = "k7/pp1b4/8/8/8/8/7R/7K w - - 0 1";
    String fenB = "k7/pp1b4/8/8/8/8/7R/7K b - - 0 1";
 
    @Test
    public void IndirectBackRankCaseTestZeroDepthWhiteToMove(){
        String fen = fenW;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(0);
        assertEquals( 0.0, nega.eval(state), delta);
        
        assertEquals(baseMetric.eval(state),nega.eval(state), delta);      
    }     

    @Test
    public void IndirectBackRankCaseTestZeroDepthBlackToMove(){
        String fen = fenB;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(0);
        assertEquals( 0.0, nega.eval(state), delta);
        
        assertEquals(baseMetric.eval(state),nega.eval(state), delta);      
    }  

    @Test
    public void IndirectBackRankCaseTestDepthOneWhiteToMove(){
        String fen = fenW;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(1);

        Metric<GameState> metric = new MaxOfChildren(baseMetric, gameDriver);
        
        assertEquals( 0.0 , metric.eval(state), delta);    
        assertEquals(metric.eval(state),nega.eval(state), delta);          
    } 
    
    @Test
    public void IndirectBackRankCaseTestDepthOneBlackToMove(){
        String fen = fenB;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(1);
        
        Metric<GameState> metric = new MinOfChildren(baseMetric, gameDriver);
        
        assertEquals( 0.0, metric.eval(state), delta);    
        assertEquals(metric.eval(state),nega.eval(state), delta);    
    } 
    
    @Test
    public void IndirectBackRankCaseTestDepthTwoWhiteToMove(){
        String fen = fenW;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(2);

        Metric<GameState> metric = baseMetric;
        metric = new MinOfChildren(metric, gameDriver);        
        metric = new MaxOfChildren(metric, gameDriver);
        
        assertEquals( 0.0 , metric.eval(state), delta);    
        assertEquals(metric.eval(state),nega.eval(state), delta);          
    } 
    
    @Test
    public void IndirectBackRankCaseTestDepthTwoBlackToMove(){
        String fen = fenB;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(2);
        
        Metric<GameState> metric = baseMetric;
        metric = new MaxOfChildren(metric, gameDriver);        
        metric = new MinOfChildren(metric, gameDriver);        

        assertEquals( 0.0, metric.eval(state), delta);    
        assertEquals(metric.eval(state),nega.eval(state), delta);    
    }     
    
    @Test
    public void IndirectBackRankCaseTestDepthThreeWhiteToMove(){
        String fen = fenW;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(3);

        Metric<GameState> metric = baseMetric;
        metric = new MaxOfChildren(metric, gameDriver);        
        metric = new MinOfChildren(metric, gameDriver);        
        metric = new MaxOfChildren(metric, gameDriver);
        
        assertEquals( MATE_VALUE, metric.eval(state), delta);    
        assertEquals(metric.eval(state),nega.eval(state), delta);          
    } 
    
    @Test
    public void IndirectBackRankCaseTestDepthThreeBlackToMove(){
        String fen = fenB;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(3);
        
        Metric<GameState> metric = baseMetric;
        metric = new MinOfChildren(metric, gameDriver);           
        metric = new MaxOfChildren(metric, gameDriver);  
        metric = new MinOfChildren(metric, gameDriver);       

        assertEquals( 0.0, metric.eval(state), delta);    
        assertEquals(metric.eval(state),nega.eval(state), delta);    
    }      
}









