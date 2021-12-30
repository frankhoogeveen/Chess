/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.tictactoe;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class TicTacToeMoveTest {

    @Test
    public void applyToTest(){
        TicTacToeState state = new TicTacToeState();
        
        TicTacToeMove move = new TicTacToeMove(1,1);
        TicTacToeState newState = move.applyTo(state);
        
        TicTacToeState expected = new TicTacToeState();
        expected.setField(1, 1, TicTacToeEnum.FIRST);
        
        assertEquals(expected, newState);
    }
    
    @Test
    public void applyToTest2(){
        TicTacToeState state = new TicTacToeState();
        
        TicTacToeMove move1 = new TicTacToeMove(1,2);
        TicTacToeState newState = move1.applyTo(state);
        
        TicTacToeState expected = new TicTacToeState();
        expected.setField(1, 2, TicTacToeEnum.FIRST);
        assertEquals(expected, newState);        
        
        TicTacToeMove move2 = new TicTacToeMove(2,0);
        newState = move2.applyTo(newState);        

        expected.setField(2, 0, TicTacToeEnum.SECOND);
        assertEquals(expected, newState);        
    }  
    
    @Test
    public void applyToTest3(){
        TicTacToeState state = new TicTacToeState();
        
        TicTacToeMove move1 = new TicTacToeMove(2,1);
        TicTacToeState newState = move1.applyTo(state);     
        
        TicTacToeMove move2 = new TicTacToeMove(0,2);
        newState = move2.applyTo(newState);        
        
        
        TicTacToeState expected = new TicTacToeState();
        expected.setField(2, 1, TicTacToeEnum.FIRST);        
        expected.setField(0, 2, TicTacToeEnum.SECOND);

        assertEquals(expected, newState);     
        
        assertEquals(TicTacToeEnum.EMPTY, newState.getFieldContent(0, 0));
        assertEquals(TicTacToeEnum.EMPTY, newState.getFieldContent(0, 1));
        assertEquals(TicTacToeEnum.SECOND, newState.getFieldContent(0, 2));  
        
        assertEquals(TicTacToeEnum.EMPTY, newState.getFieldContent(1, 0));
        assertEquals(TicTacToeEnum.EMPTY, newState.getFieldContent(1, 1));
        assertEquals(TicTacToeEnum.EMPTY, newState.getFieldContent(1, 2));   

        assertEquals(TicTacToeEnum.EMPTY, newState.getFieldContent(2, 0));
        assertEquals(TicTacToeEnum.FIRST, newState.getFieldContent(2, 1));
        assertEquals(TicTacToeEnum.EMPTY, newState.getFieldContent(2, 2));           
    }      
}