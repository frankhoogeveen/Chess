/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.negamax;

import nl.fh.metric.*;
import nl.fh.gamestate.GameState;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.metric.utilities.MaxOfChildren;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rules.Chess;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class IsolatedCase {
    private final double delta = 1.e-9;
    Metric<GameState> baseMetric = new MaterialCountMetric(Chess.gameDriver);   
    NegaMax<GameState> nega = new NegaMax<GameState>(baseMetric, Chess.moveGenerator, 0);
    
    String fenW = "k7/8/8/8/8/3K4/3p4/8 w - - 0 1";
    String fenB = "k7/8/8/8/8/3K4/3p4/8 b - - 0 1";
 

    @Test
    public void BackRankCaseTestDepthOneWhiteToMove(){
        String fen = fenW;
        GameState state  = GameState.fromFEN(fen);
        
        nega.setDepth(1);

        Metric<GameState> metric = new MaxOfChildren(baseMetric, Chess.gameDriver);
        
        assertEquals( 0.0, metric.eval(state), delta);    
        assertEquals(metric.eval(state),nega.eval(state), delta);          
    }   
   
}









