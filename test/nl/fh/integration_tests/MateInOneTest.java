/*
 * License: GPL v3
 * 
 */

package nl.fh.integration_tests;

import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.metric.MaterialCountMetric;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.move.ChessMove;
import nl.fh.move.Move;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.Metric;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.rules.FIDEchess;
import nl.fh.rules.GameDriver;
import nl.fh.rules.MoveGenerator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class MateInOneTest {
    
    GameDriver gameDriver = FIDEchess.getGameDriver();
    MoveGenerator moveGenerator = gameDriver.getMoveGenerator();
    
    @Test
    public void testMateInOneMaterial(){
        String fen = "rnb1k1nr/pppp1ppp/4p3/4P3/3b4/8/4qPPP/1K5R b kq - 0 15";
        GameState state = GameState.fromFEN(fen);
        Metric metric = MaterialCountMetric.getWrappedInstance();
        
        Player player = MetricPlayer.getInstance(metric);
        Set<Move> legalMoves = moveGenerator.calculateAllLegalMoves(state);
        
        ChessMove move = (ChessMove) player.getMove(state, legalMoves);
        assertEquals("Qb2#", move.formatPGN(state, FIDEchess.getGameDriver()));
       
    } 
    
    
    @Test
    public void testMateInOneNegaMax(){
        String fen = "rnb1k1nr/pppp1ppp/4p3/4P3/3b4/8/4qPPP/1K5R b kq - 0 15";
        GameState state = GameState.fromFEN(fen);
        Metric metric = MaterialCountMetric.getWrappedInstance();
        
        Player player = MetricPlayer.getInstance(metric);
        Set<Move> legalMoves = moveGenerator.calculateAllLegalMoves(state);
        
        ChessMove move = (ChessMove) player.getMove(state, legalMoves);
        assertEquals("Qb2#", move.formatPGN(state, gameDriver));
    }
    
    @Test  
    public void testMateInOneNegaMax2(){
        String fen = "rnb1k1nr/pppp1ppp/4p3/4P3/3b4/8/4qPPP/1K5R b kq - 0 15";
        GameState state = GameState.fromFEN(fen);
        Metric metric = MaterialCountMetric.getWrappedInstance();
        
        int depth = 2;
        Player player = MetricPlayer.getInstance(new NegaMax(metric, moveGenerator, depth));
        Set<Move> legalMoves = moveGenerator.calculateAllLegalMoves(state);        
        
        ChessMove move = (ChessMove) player.getMove(state, legalMoves);
        assertEquals("Qb2#", move.formatPGN(state, gameDriver));
    }   
    
    @Test
    public void testMateInOneBlackToMove(){
        String fen = "3K4/7r/3k4/8/8/8/8/8 b - - 0 1";
        GameState state = GameState.fromFEN(fen);
        Metric metric = MaterialCountMetric.getWrappedInstance();
        
        Player player = MetricPlayer.getInstance(metric);
        Set<Move> legalMoves = moveGenerator.calculateAllLegalMoves(state);             
        ChessMove move = (ChessMove) player.getMove(state, legalMoves);
        assertEquals("Rh8#", move.formatPGN(state, gameDriver));
       
    }     

    
    @Test
    public void testMateInOneWhiteToMove(){
        String fen = "8/8/8/qn6/kn6/1n6/1KP5/8 w - - 0 1";
        GameState state = GameState.fromFEN(fen);
        Metric metric = MaterialCountMetric.getWrappedInstance();
        
        Player player = MetricPlayer.getInstance(metric);
        Set<Move> legalMoves = moveGenerator.calculateAllLegalMoves(state);             
        ChessMove move = (ChessMove) player.getMove(state, legalMoves);
        assertEquals("cxb3#", move.formatPGN(state, gameDriver));
    }     
}