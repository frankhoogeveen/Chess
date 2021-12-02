/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fh.rules;

import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
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
    public void testCannotLeaveKingInCheck(){
        String fen = "1r5k/8/8/8/8/8/8/K7 w - - 0 1";
        GameState state = GameState.fromFEN(fen);
        Rules rules = new SimpleRules();
        
        Set<Move> moves = rules.getAllLegalMoves(state);
        assertEquals(1, moves.size());
    }  
}
