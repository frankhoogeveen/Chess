/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fh.rules;

import java.util.Set;
import nl.fh.chess.BoardSide;
import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.move.Castling;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author frank
 */
public class RulesTest {
    
    public RulesTest() {
    }
      
    @Test
    public void testLegalMove1(){
        Rules rules = new SimpleRules();
        GameState state = rules.getInitialState();
        
        Field from = Field.getInstance("b1");
        Field to   = Field.getInstance("a3");
        Move move = PieceMove.getInstance(from, to);
        
        assertTrue(rules.isLegalMove(move, state));      
    }   

    @Test
    public void testLegalMove2(){
        Rules rules = new SimpleRules();
        GameState state = rules.getInitialState();
        state.increment();
        
        Field from = Field.getInstance("g8");
        Field to   = Field.getInstance("h6");
        Move move = PieceMove.getInstance(from, to);
        
        assertTrue(rules.isLegalMove(move, state));      
    }  
    
    @Test
    public void testLegalMoveGenerator(){
        Rules rules = new SimpleRules();
        GameState state = rules.getInitialState();
        
        Field from = Field.getInstance("e2");
        Field to   = Field.getInstance("e4");
        Move move = PieceMove.getInstance(from, to);
        
        state = state.apply(move);
        
        Set<Move> moves = rules.getAllLegalMoves(state);        
        assertEquals(20, moves.size());
    }
    
    @Test
    public void testIsCovered(){
        String fen = "4k3/6b1/8/8/2R3r1/3n4/8/4K3 w - - 0 1";
        
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen);
        
        assertTrue(!rules.isCovered(Field.getInstance("a4"), state, Color.BLACK));
        assertTrue( rules.isCovered(Field.getInstance("b4"), state, Color.BLACK));
        assertTrue( rules.isCovered(Field.getInstance("c4"), state, Color.BLACK));
        assertTrue( rules.isCovered(Field.getInstance("d4"), state, Color.BLACK));
        assertTrue( rules.isCovered(Field.getInstance("e4"), state, Color.BLACK));
        assertTrue( rules.isCovered(Field.getInstance("f4"), state, Color.BLACK));
        assertTrue(!rules.isCovered(Field.getInstance("g4"), state, Color.BLACK));
        assertTrue( rules.isCovered(Field.getInstance("h4"), state, Color.BLACK));
        assertTrue( rules.isCovered(Field.getInstance("g5"), state, Color.BLACK));
        assertTrue( rules.isCovered(Field.getInstance("g6"), state, Color.BLACK));
        assertTrue( rules.isCovered(Field.getInstance("g7"), state, Color.BLACK));
        assertTrue(!rules.isCovered(Field.getInstance("g8"), state, Color.BLACK));        
    }
    
    @Test
    public void testCannotLeaveKingInCheck(){
        String fen = "1r5k/8/8/8/8/8/8/K7 w - - 0 1";
        GameState state = GameState.fromFEN(fen);
        Rules rules = new SimpleRules();
        
        Set<Move> moves = rules.getAllLegalMoves(state);
        assertEquals(1, moves.size());
    }
}
