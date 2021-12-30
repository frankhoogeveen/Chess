package nl.fh.gamestate.tictactoe;

import java.util.List;
import nl.fh.gamereport.GameReport;
import nl.fh.gamestate.GameState;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/*
 * License: GPL v3
 * 
 */

/**
 * 
 * 
 */
public class TicTacToeReportTest {
    
    @Test
    public void firstReportTest(){
        GameReport<TicTacToeState> report = new GameReport<TicTacToeState>();
        
        TicTacToeState state0 = new TicTacToeState();
        report.addGameState(state0);
        
        List<TicTacToeState> list = report.getStateList();
        assertEquals(state0, list.get(0));
        
        TicTacToeMove m = new TicTacToeMove("a1");
        TicTacToeState state1 = m.applyTo(state0);
        assertFalse(state1.equals(state0));
        
        report.addGameState(state1);        
        
        list = report.getStateList();
        assertEquals(state0, list.get(0));        
        assertEquals(state1, list.get(1));
    }

}