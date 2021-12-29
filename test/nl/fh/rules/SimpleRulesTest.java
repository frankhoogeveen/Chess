/*
 * License: GPL v3
 * 
 */
package nl.fh.rules;

import nl.fh.rule.chess.FIDEchess;
import nl.fh.rule.MoveGenerator;
import nl.fh.rule.GameDriver;
import nl.fh.gamestate.chess.ChessState;
import java.util.Set;
import nl.fh.gamestate.chess.Color;
import nl.fh.gamestate.chess.Field;
import nl.fh.gamestate.chess.PieceKind;
import nl.fh.gamestate.Move;
import nl.fh.gamestate.chess.move.PieceMove;
import nl.fh.gamestate.chess.move.Promotion;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class SimpleRulesTest {
    
    private GameDriver gameDriver = FIDEchess.getGameDriver();
    private MoveGenerator moveGenerator = gameDriver.getMoveGenerator();
      
    @Test
    public void testLegalMove1(){
        ChessState state = FIDEchess.getInitialState();
        
        Field from = Field.getInstance("b1");
        Field to   = Field.getInstance("a3");
        Move move = PieceMove.getInstance(from, to);
        
        assertTrue(moveGenerator.calculateAllLegalMoves(state).contains(move));      
    }   

    @Test
    public void testLegalMove2(){

        ChessState state = FIDEchess.getInitialState();
        state.increment();
        
        Field from = Field.getInstance("g8");
        Field to   = Field.getInstance("h6");
        Move move = PieceMove.getInstance(from, to);
        
        assertTrue(moveGenerator.calculateAllLegalMoves(state).contains(move));    
    }  
    
    @Test
    public void testLegalMoveGenerator(){

        ChessState state = FIDEchess.getInitialState();
        
        Field from = Field.getInstance("e2");
        Field to   = Field.getInstance("e4");
        Move move = PieceMove.getInstance(from, to);
        
        state = state.apply(move);
        
        Set<Move> moves = moveGenerator.calculateAllLegalMoves(state);        
        assertEquals(20, moves.size());
    }
    
    @Test
    public void testIsCovered(){
        String fen = "4k3/6b1/8/8/2R3r1/3n4/8/4K3 w - - 0 1";
        
        ChessState state = ChessState.fromFEN(fen);
        
        assertTrue(!Field.getInstance("a4").isCovered(state, Color.BLACK));
        assertTrue( Field.getInstance("b4").isCovered(state, Color.BLACK));
        assertTrue( Field.getInstance("c4").isCovered(state, Color.BLACK));
        assertTrue( Field.getInstance("d4").isCovered(state, Color.BLACK));
        assertTrue( Field.getInstance("e4").isCovered(state, Color.BLACK));
        assertTrue( Field.getInstance("f4").isCovered(state, Color.BLACK));
        assertTrue(!Field.getInstance("g4").isCovered(state, Color.BLACK));
        assertTrue( Field.getInstance("h4").isCovered(state, Color.BLACK));
        assertTrue( Field.getInstance("g5").isCovered(state, Color.BLACK));
        assertTrue( Field.getInstance("g6").isCovered(state, Color.BLACK));
        assertTrue( Field.getInstance("g7").isCovered(state, Color.BLACK));
        assertTrue(!Field.getInstance("g8").isCovered(state, Color.BLACK));        
    }
    
    @Test
    public void testCannotLeaveKingInCheck(){
        
        String fen = "1r5k/8/8/8/8/8/8/K7 w - - 0 1";
        ChessState state = ChessState.fromFEN(fen);

        
        Set<Move> moves = moveGenerator.calculateAllLegalMoves(state);
        assertEquals(1, moves.size());
    }
    
    @Test
    public void testFindLegalMoves(){
        
        String fen = "rnbqk3/ppppp1P1/8/8/8/8/PPPPPP1P/RNBQKBNR w KQq - 0 1";
        ChessState state = ChessState.fromFEN(fen);
        
        Set<Move> set = moveGenerator.calculateAllLegalMoves(state);
        
        Field from = Field.getInstance("g7");        
        Field to = Field.getInstance("g8");
        
        Move movePawn = PieceMove.getInstance(from, to);
        Move movePromotion = Promotion.getInstance(from, to, PieceKind.KNIGHT);
        
        assertTrue(!moveGenerator.calculateAllLegalMoves(state).contains(movePawn));
        assertTrue(moveGenerator.calculateAllLegalMoves(state).contains(movePromotion));        
    }      
}
