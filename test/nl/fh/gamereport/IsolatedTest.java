/*
 * License: GPL v3
 * 
 */
package nl.fh.gamereport;

import nl.fh.chess.Field;
import nl.fh.chess.PieceType;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
import nl.fh.move.Promotion;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author frank
 */
public class IsolatedTest {

    private Rules rules = new SimpleRules();
    
    public IsolatedTest() {
    }
    
    
    @Test
    public void testPromotion3(){
        GameState state = GameState.fromFEN("4r3/2kP1P2/8/8/8/8/8/3K4 w - - 0 1", rules);
        
        Field from = Field.getInstance("f7");
        Field to   = Field.getInstance("e8");
        Move m = Promotion.getInstance(from, to, PieceType.WHITE_KNIGHT);
        
        assertEquals("fxe8=N+", m.moveString(state));         
    } 
}
