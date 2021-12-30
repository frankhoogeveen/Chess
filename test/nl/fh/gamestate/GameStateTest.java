/*
 * License: GPL v3
 * 
 */
package nl.fh.gamestate;

import nl.fh.gamestate.chess.ChessState;
import java.util.ArrayList;
import nl.fh.gamestate.StateFormatter;
import nl.fh.gamestate.chess.ChessState;
import nl.fh.gamestate.chess.format.FENformatter;
import nl.fh.rule.chess.FIDEchess;
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
    StateFormatter formatter = new FENformatter();
    
    public GameStateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        // note that all fen strings in this list should have move number 1,
        // since move number is not part of the game state.
        
        // the starting position
        fenList.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        
        // the starting position with black to play
        fenList.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 1");        
        
        // an empty board
        fenList.add("8/8/8/8/8/8/8/8 w - - 0 1");     
        
        // not all castling allowed
        fenList.add("rnbqkbnr/1ppppppp/8/p7/7P/8/PPPPPPP1/RNBQKBNR w Qk - 4 1");
        
        //example with en passant
        fenList.add("r1bqkbnr/pppp1ppp/n7/3Pp3/8/8/PPP1PPPP/RNBQKBNR w KQkq e6 0 1");
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void testToFEN() {
        ChessState instance = new ChessState();
        String expResult = "8/8/8/8/8/8/8/8 w KQkq - 0 1";
        String result = formatter.format(instance);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testToFEN2() {

        ChessState instance = FIDEchess.getInitialState();
        String expResult = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        String result = formatter.format(instance);
        assertEquals(expResult, result);
    }  
    
    @Test
    public void testToFEN3() {

        ChessState instance = FIDEchess.getInitialState();
        String expResult = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 7";
        String result = formatter.format(instance, 7);
        assertEquals(expResult, result);
    }     

    @Test
    public void testFromFEN() {
        String fen = "8/8/8/8/8/8/8/8 w KQkq - 0 1";
        ChessState expResult = new ChessState();
        ChessState result = ChessState.fromFEN(fen);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testFromFEN2() {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        ChessState expResult = FIDEchess.getInitialState();
        ChessState result = ChessState.fromFEN(fen);
        assertEquals(expResult, result);
    }

    @Test
    public void testTrim(){
        String fen = "r1bqkbnr/pppp1ppp/n7/3Pp3/8/8/PPP1PPPP/RNBQKBNR w KQkq e6 0 3";
        String fen2 = "  " + fen + " ";
        
        ChessState result1 = ChessState.fromFEN(fen);
        ChessState result2 = ChessState.fromFEN(fen2);
        assertEquals(result1, result2);        
    }
    
    
    
    
    @Test
    public void testList(){
        for(String fen : fenList){
            ChessState state = ChessState.fromFEN(fen);
            String result = formatter.format(state, 1);
            assertEquals(fen, result);
        }
    }
    
}
