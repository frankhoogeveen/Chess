/*
 * License: GPL v3
 * 
 */

package nl.fh.rules;

import java.util.Set;
import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.move.EnPassantCapture;
import nl.fh.move.Move;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * 
 * 
 */
public class IsolatedTest {

    private Rules rules = new SimpleRules();
    
    @Test
    public void testEnPassentCase2(){
        
        GameState state = GameState.fromFEN("8/ppp2k2/8/5p1K/PPp2P1p/8/2P3PN/RNQ4R b - b3 0 28", rules);
        Set<Move> legalMoves = state.getLegalMoves();
        
        for(Move m : legalMoves){
            System.out.println(m.moveString(state));
        }
        
        Move m = EnPassantCapture.getInstance(Field.getInstance("c4"), Field.getInstance("b3"));
        assertTrue(legalMoves.contains(m));        
        
    }
}