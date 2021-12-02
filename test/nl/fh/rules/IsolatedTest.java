/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fh.rules;

import java.util.HashSet;
import java.util.Set;
import nl.fh.chess.Field;
import nl.fh.chess.PieceType;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
import nl.fh.move.Promotion;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author frank
 */
public class IsolatedTest {
    
    public IsolatedTest() {
    }
    
    
    @Test
    public void testFindLegalMoves2(){
        Rules rules = new SimpleRules();
        
        String fen = "rnbqk3/ppppp1P1/8/8/8/8/PPPPPP1P/RNBQKBNR w KQq - 0 1";
        GameState state = GameState.fromFEN(fen);
        
        Set<Move> set = rules.getAllLegalMoves(state);
        
        Field from = Field.getInstance("g7");        
        Field to = Field.getInstance("g8");
        
        Move movePawn = PieceMove.getInstance(from, to);
        Move movePromotion = Promotion.getInstance(from, to, PieceType.WHITE_KNIGHT);
        assertTrue(!rules.isLegalMove(movePawn, state));
        assertTrue(rules.isLegalMove(movePromotion, state));        
    }    
}
