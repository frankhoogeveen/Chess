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
import nl.fh.rules.Chess;
import nl.fh.rules.GameDriver;
import nl.fh.rules.MoveGenerator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class NegaMaxMetricTest3 {
    private final double delta = 1.e-9;
    private double MATE_VALUE = 1.e6;       
    
    private GameDriver gameDriver = Chess.getGameDriver();
    private MoveGenerator moveGenerator = gameDriver.getMoveGenerator();       
    Metric<GameState> baseMetric = MaterialCountMetric.getWrappedInstance();   
    NegaMax<GameState> nega = new NegaMax<GameState>(baseMetric, moveGenerator, 0);
    
    String fenW = "k7/pp6/8/8/8/8/7R/7K w - - 0 1";
    String fenB = "k7/pp6/8/8/8/8/7R/7K b - - 0 1";
 
    @Test
    public void DirectBackRankCaseTestZeroDepthWhiteToMove(){
        String fen = fenW;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(0);
        assertEquals( +3.0, nega.eval(state), delta);
        
        assertEquals(baseMetric.eval(state),nega.eval(state), delta);      
    }     

    @Test
    public void DirectBackRankCaseTestZeroDepthBlackToMove(){
        String fen = fenB;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(0);
        assertEquals( +3.0, nega.eval(state), delta);
        
        assertEquals(baseMetric.eval(state),nega.eval(state), delta);      
    }  

    @Test
    public void DirectBackRankCaseTestDepthOneWhiteToMove(){
        String fen = fenW;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(1);

        Metric<GameState> metric = new MaxOfChildren(baseMetric, gameDriver);
        
        assertEquals( MATE_VALUE, metric.eval(state), delta);    
        assertEquals(metric.eval(state),nega.eval(state), delta);          
    } 
    
    @Test
    public void DirectBackRankCaseTestDepthOneBlackToMove(){
        String fen = fenB;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(1);
        
        Metric<GameState> metric = new MinOfChildren(baseMetric, gameDriver);
        
        assertEquals( +3.0, metric.eval(state), delta);    
        assertEquals(metric.eval(state),nega.eval(state), delta);    
    } 
}









