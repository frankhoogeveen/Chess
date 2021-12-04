/*
 * License: GPL v3
 * 
 */
package nl.fh.gamereport;

import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
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
    public void testPieceMove1(){
        GameState state = rules.getInitialState();
        
        Field from = Field.getInstance("b1");
        Field to   = Field.getInstance("c3");
        Move m = PieceMove.getInstance(from, to);
        
        assertEquals("Nc3", m.moveString(state, rules)); 
    }
}
