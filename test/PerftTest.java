
import nl.fh.gamestate.GameState;
import nl.fh.rules.FIDEchess;
import nl.fh.rules.MoveGenerator;
import nl.fh.rules.Perft;
import nl.fh.rules.PerftCase;
import nl.fh.rules.ChessMoveGenerator;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/*
 * License: GPL v3
 * 
 */

/**
 * The source of the current test set is:
 * https://www.chessprogramming.org/Perft_Results
 * 
 * Other higher order test sets can be found in 
 * https://www.talkchess.com/forum3/viewtopic.php?t=46055
 * 
 */
public class PerftTest {
    
    private final MoveGenerator moveGenerator = new ChessMoveGenerator();
    
    @Test
    public void testInitialState(){
        GameState state = FIDEchess.getInitialState();
        
        assertEquals(1, Perft.value(state, 0));
        assertEquals(20, Perft.value(state, 1));  
        assertEquals(400, Perft.value(state, 2));  
        assertEquals(8902, Perft.value(state, 3));  
        assertEquals(197281, Perft.value(state, 4));   
//        assertEquals(4865609, Perft.value(state, 5));  //takes 30 seconds
//        assertEquals(119060324, Perft.value(state, 6));
//        assertEquals(3195901860, Perft.value(state, 7)); // takes about 5 hrs         
    }
    
    @Test 
    public void testCases(){

        for(int caseId = 0; caseId < Perft.getCases().size(); caseId++){
           
            PerftCase perftCase = Perft.getCases().get(caseId);
            
            int depth = perftCase.getDepth();
            long expValue = perftCase.getPerftValue();
            GameState state = GameState.fromFEN(perftCase.getFen());
            
            //System.out.println(caseId + " " + perftCase.getComment());

            assertEquals(expValue, Perft.value(state, depth));
        }
         
    }

}