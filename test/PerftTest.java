
import nl.fh.gamestate.GameState;
import nl.fh.rules.Perft;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/*
 * License: GPL v3
 * 
 */

/**
 * 
 * https://www.chessprogramming.org/Perft_Results
 */
public class PerftTest {
    
    private final Rules rules = new SimpleRules();
    @Test
    public void testZero(){
        GameState state = rules.getInitialState();
        
        assertEquals(1, Perft.value(state, 0));
        assertEquals(20, Perft.value(state, 1));  
        assertEquals(400, Perft.value(state, 2));  
        assertEquals(8902, Perft.value(state, 3));  
        assertEquals(197281, Perft.value(state, 4));   
        assertEquals(4865609, Perft.value(state, 5));      
        
    }

}