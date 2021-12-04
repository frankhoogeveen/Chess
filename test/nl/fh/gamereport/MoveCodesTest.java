package nl.fh.gamereport;

import nl.fh.chess.BoardSide;
import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.move.Castling;
import nl.fh.move.DrawOfferAccepted;
import nl.fh.move.EnPassantCapture;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/*
 * License: GPL v3
 * 
 */



/**
 * This class collects all the tests on MoveCodes for all Move types
 * 
 */
public class MoveCodesTest {

    private static Rules rules = new SimpleRules();
    
    @Test
    public void testCastlingLong(){
        GameState state = null;
        Move m = Castling.getInstance(BoardSide.QUEENSIDE);
        assertEquals("O-O-O", m.moveString(state, rules));
    }
    
    @Test
    public void testCastlingShort(){
        GameState state = null;
        Move m = Castling.getInstance(BoardSide.KINGSIDE);
        assertEquals("O-O", m.moveString(state, rules));       
    }
    
    @Test
    public void testDrawOfferAccepted(){
        GameState state = null;
        Move m = DrawOfferAccepted.getInstance();
        assertEquals("", m.moveString(state, rules));            
    }
    
    @Test
    public void testEnPassantCapture(){
        GameState state = GameState.fromFEN("4k3/8/8/2pP4/8/8/8/4K3 w - c6 0 2");
        
        Move m = EnPassantCapture.getInstance(Field.getInstance("d5"), state.getEnPassantField());
        assertEquals("dxc6", m.moveString(state, rules));             
    }
    
    @Test
    public void testPieceMove1(){
        GameState state = rules.getInitialState();
        
        Field from = Field.getInstance("b1");
        Field to   = Field.getInstance("c3");
        Move m = PieceMove.getInstance(from, to);
        
        assertEquals("Nc3", m.moveString(state, rules)); 
    }
    
    @Test
    public void testPieceMove2(){
        GameState state = rules.getInitialState();
        
        Field from = Field.getInstance("a2");
        Field to   = Field.getInstance("a4");
        Move m = PieceMove.getInstance(from, to);
        
        assertEquals("a4", m.moveString(state, rules)); 
    }    
    
    @Test
    public void testPieceMove3(){
        GameState state = GameState.fromFEN("k7/4r3/1rr1B1r1/8/2r1r3/4r3/8/K7 b - - 0 1");
        
        Field from = Field.getInstance("c6");
        Field to   = Field.getInstance("e6");
        Move m = PieceMove.getInstance(from, to);
        
        assertEquals("Rcxe6", m.moveString(state, rules)); 
    }   

    @Test
    public void testPieceMove4(){
        GameState state = GameState.fromFEN("k7/4r3/1rr1B1r1/8/2r1r3/4r3/8/K7 b - - 0 1");
        
        Field from = Field.getInstance("e4");
        Field to   = Field.getInstance("e6");
        Move m = PieceMove.getInstance(from, to);
        
        assertEquals("R4xe6", m.moveString(state, rules)); 
    }    
    
}