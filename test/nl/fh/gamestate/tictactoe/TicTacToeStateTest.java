/*
 * License: GPL v3
 * 
 */
package nl.fh.gamestate.tictactoe;

import nl.fh.gamestate.GameState;
import nl.fh.gamestate.Mover;
import nl.fh.gamestate.tictactoe.TicTacToeEnum;
import nl.fh.gamestate.tictactoe.TicTacToeState;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author frank
 */
public class TicTacToeStateTest {
    
    public TicTacToeStateTest() {
    }

    @Test
    public void testGetMover() {
        TicTacToeState instance = new TicTacToeState();
        Mover expResult = Mover.FIRST_MOVER;
        Mover result = instance.getMover();
        assertEquals(expResult, result);
    }


    @Test
    public void testCountEmtpy() {
        TicTacToeState instance = new TicTacToeState();
        int expResult = 9;
        int result = instance.countEmtpy();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetFieldContent() {
        int x = 0;
        int y = 0;
        TicTacToeState instance = new TicTacToeState();
        TicTacToeEnum expResult = TicTacToeEnum.EMPTY;
        TicTacToeEnum result = instance.getFieldContent(x, y);
        assertEquals(expResult, result);
    }

    @Test
    public void testSetField() {
        int x = 2;
        int y = 1;
        TicTacToeEnum next = TicTacToeEnum.FIRST;
        TicTacToeState instance = new TicTacToeState();
        instance.setField(x, y, next);
        assertEquals(TicTacToeEnum.FIRST, instance.getFieldContent(x, y));
    }
    
}
