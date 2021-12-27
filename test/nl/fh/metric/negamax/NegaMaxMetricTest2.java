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
public class NegaMaxMetricTest2 {
    private final double delta = 1.e-9;
    
    private GameDriver gameDriver = Chess.getGameDriver();
    private MoveGenerator moveGenerator = gameDriver.getMoveGenerator();       
    Metric<GameState> baseMetric = new MaterialCountMetric(gameDriver);   
    NegaMax<GameState> nega = new NegaMax<GameState>(baseMetric, moveGenerator, 0);
    
    String fenW = "k7/8/8/8/8/3K4/3p4/8 w - - 0 1";
    String fenB = "k7/8/8/8/8/3K4/3p4/8 b - - 0 1";
 
    @Test
    public void PromotionCaseTestZeroDepthWhiteToMove(){
        String fen = fenW;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(0);
        assertEquals( -1.0, nega.eval(state), delta);
        
        assertEquals(baseMetric.eval(state),nega.eval(state), delta);      
    }     

    @Test
    public void PromotionCaseTestZeroDepthBlackToMove(){
        String fen = fenB;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(0);
        assertEquals( -1.0, nega.eval(state), delta);
        
        assertEquals(baseMetric.eval(state),nega.eval(state), delta);      
    }  

    @Test
    public void PromotionCaseTestDepthOneWhiteToMove(){
        String fen = fenW;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(1);

        Metric<GameState> metric = new MaxOfChildren(baseMetric, gameDriver);
        
        assertEquals( 0.0, metric.eval(state), delta);    
        assertEquals(metric.eval(state),nega.eval(state), delta);          
    } 
    
    @Test
    public void PromotionCaseTestDepthOneBlackToMove(){
        String fen = fenB;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(1);
        
        Metric<GameState> metric = new MinOfChildren(baseMetric, gameDriver);
        
        assertEquals( -9.0, metric.eval(state), delta);    
        assertEquals(metric.eval(state),nega.eval(state), delta);    
    } 
}









