/*
 * License: GPL v3
 * 
 */

package nl.fh.integration_tests;

import java.util.Set;
import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.move.EnPassantCapture;
import nl.fh.move.Move;
import nl.fh.rules.SimpleRules;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * 
 * 
 */
public class IsolatedTest {
    @Test
    public void testEnPassantCase(){
        SimpleRules rules = new SimpleRules();
        GameState state = GameState.fromFEN("2kr1n1K/ppp2p2/8/4p1pn/PPp1b3/8/8/8 b - b3 0 33", rules);
        Set<Move> legalMoves = state.getLegalMoves();
        
        for(Move lm : legalMoves){
            System.out.println(lm.moveString(state));
        }
        
        Move m = EnPassantCapture.getInstance(Field.getInstance("c4"), Field.getInstance("b3"));
        Move m2 = EnPassantCapture.getInstance(Field.getInstance("c4"), Field.getInstance("b3"));   
        assertTrue(m.equals(m2));
        
        System.out.println(m.moveString(state));
        assertTrue(legalMoves.contains(m));
        
    } 

}