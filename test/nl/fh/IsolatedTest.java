/*
 * License: GPL v3
 * 
 */

package nl.fh;

import nl.fh.chess.Field;
import nl.fh.chess.PieceKind;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.move.Promotion;
import nl.fh.rules.SimpleRules;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class IsolatedTest {
    
    
    @Test
    public void testPromotion3(){
        SimpleRules rules = new SimpleRules();
        GameState state = GameState.fromFEN("4r3/2kP1P2/8/8/8/8/8/3K4 w - - 0 1", rules);
        
        Field from = Field.getInstance("f7");
        Field to   = Field.getInstance("e8");
        Move m = Promotion.getInstance(from, to, PieceKind.KNIGHT);
        
        assertEquals("fxe8=N+", m.moveString(state));         
    }     

}