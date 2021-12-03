/*
 * License: GPL v3
 * 
 */
package nl.fh.gamestate;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author frank
 * 
 * 
 */

public class GameStateTest {
    
    static ArrayList<String> fenList = new ArrayList<String>();


    
    public GameStateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        // the starting position
        fenList.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        
        // the starting position with black to play
        fenList.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 1");        
        
        // an empty board
        fenList.add("8/8/8/8/8/8/8/8 w - - 0 1");     
        
        // not all castling allowed
        fenList.add("rnbqkbnr/1ppppppp/8/p7/7P/8/PPPPPPP1/RNBQKBNR w Qk - 4 4");
        
        //example with en passant
        fenList.add("r1bqkbnr/pppp1ppp/n7/3Pp3/8/8/PPP1PPPP/RNBQKBNR w KQkq e6 0 3");
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void testToFEN() {
        GameState instance = new GameState();
        String expResult = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        String result = instance.toFEN();
        assertEquals(expResult, result);
    }

    @Test
    public void testFromFEN() {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        GameState expResult = new GameState();
        GameState result = GameState.fromFEN(fen);
        assertEquals(expResult, result);
    }
    @Test
    public void testList(){
        for(String fen : fenList){
            GameState state = GameState.fromFEN(fen);
            String result = state.toFEN();
            assertEquals(fen, result);
        }
    }
    
}
