/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.negamax;

import nl.fh.metric.*;
import nl.fh.gamestate.chess.ChessState;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.metric.minimax.NegaMaxAlphaBeta;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rule.FIDEchess;
import nl.fh.rule.GameDriver;
import nl.fh.rule.MoveGenerator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class NegaMaxAlphaBetaTest {
    private final double delta = 1.e-9;
    
    private GameDriver gameDriver = FIDEchess.getGameDriver();
    private MoveGenerator moveGenerator = gameDriver.getMoveGenerator();    
    
    Metric<ChessState> baseMetric = MaterialCountMetric.getWrappedInstance();   
    NegaMax<ChessState> nega = new NegaMax<ChessState>(baseMetric,moveGenerator, 0);
    NegaMaxAlphaBeta<ChessState> negaAB = new NegaMaxAlphaBeta<ChessState>(baseMetric, moveGenerator,  0);

    @Test
    public void testPruningDepthZero(){
        String fen = "r2qkb1r/pppbpppp/2n5/1B1pN3/3PnB2/4P3/PPP2PPP/RN1QK2R w KQkq - 7 7";
        ChessState state  = ChessState.fromFEN(fen);
        
        nega.setDepth(0);
        negaAB.setDepth(0);
        assertEquals(nega.eval(state), negaAB.eval(state), delta);
    }   
    
    @Test
    public void testPruningDepthOne(){
        String fen = "r2qkb1r/pppbpppp/2n5/1B1pN3/3PnB2/4P3/PPP2PPP/RN1QK2R w KQkq - 7 7";
        ChessState state  = ChessState.fromFEN(fen);
        
        nega.setDepth(1);
        negaAB.setDepth(1);
        assertEquals(nega.eval(state), negaAB.eval(state), delta);
    }   

    @Test
    public void testPruningDepthTwo(){
        String fen = "r2qkb1r/pppbpppp/2n5/1B1pN3/3PnB2/4P3/PPP2PPP/RN1QK2R w KQkq - 7 7";
        ChessState state  = ChessState.fromFEN(fen);
        
        nega.setDepth(2);
        negaAB.setDepth(2);
        assertEquals(nega.eval(state), negaAB.eval(state), delta);
    }  
    
    @Test
    public void testPruningDepthTwoBlackToMove(){
        String fen = "r2qkb1r/pppbpppp/2n5/1B1pN3/3PnB2/4P3/PPP2PPP/RN1QK2R b KQkq - 7 7";
        ChessState state  = ChessState.fromFEN(fen);
  
        nega.setDepth(2);
        negaAB.setDepth(2);
        assertEquals(nega.eval(state), negaAB.eval(state), delta);
    }       
    
    @Test
    public void testPruningDepthThree(){
        String fen = "r2qkb1r/pppbpppp/2n5/1B1pN3/3PnB2/4P3/PPP2PPP/RN1QK2R w KQkq - 7 7";
        ChessState state  = ChessState.fromFEN(fen);
        
        nega.setDepth(3);
        negaAB.setDepth(3);
        assertEquals(nega.eval(state), negaAB.eval(state), delta);
    }       
   
}









