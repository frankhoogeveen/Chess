package nl.fh.gamereport;

import nl.fh.chess.BoardSide;
import nl.fh.chess.Field;
import nl.fh.chess.PieceType;
import nl.fh.gamestate.GameState;
import nl.fh.move.Castling;
import nl.fh.move.DrawOfferAccepted;
import nl.fh.move.EnPassantCapture;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
import nl.fh.move.Promotion;
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
        assertEquals("O-O-O", m.moveString(state));
    }
    
    @Test
    public void testCastlingShort(){
        GameState state = null;
        Move m = Castling.getInstance(BoardSide.KINGSIDE);
        assertEquals("O-O", m.moveString(state));       
    }
    
    @Test
    public void testDrawOfferAccepted(){
        GameState state = null;
        Move m = DrawOfferAccepted.getInstance();
        assertEquals("", m.moveString(state));            
    }
    
    @Test
    public void testEnPassantCapture(){
        GameState state = GameState.fromFEN("4k3/8/8/2pP4/8/8/8/4K3 w - c6 0 2", rules);
        
        Move m = EnPassantCapture.getInstance(Field.getInstance("d5"), state.getEnPassantField());
        assertEquals("dxc6", m.moveString(state));             
    }
    @Test
    public void testEnPassantCapture2(){
        GameState state = GameState.fromFEN("1Q6/3k4/6N1/1N2pP2/8/8/8/K3R2B w - e6 0 2", rules);
        
        Move m = EnPassantCapture.getInstance(Field.getInstance("f5"), state.getEnPassantField());
        assertEquals("fxe6#", m.moveString(state));             
    }    
    
    @Test
    public void testPieceMove1(){
        GameState state = rules.getInitialState();
        
        Field from = Field.getInstance("b1");
        Field to   = Field.getInstance("c3");
        Move m = PieceMove.getInstance(from, to);
        
        assertEquals("Nc3", m.moveString(state)); 
    }
    
    @Test
    public void testPieceMove2(){
        GameState state = rules.getInitialState();
        
        Field from = Field.getInstance("a2");
        Field to   = Field.getInstance("a4");
        Move m = PieceMove.getInstance(from, to);
        
        assertEquals("a4", m.moveString(state)); 
    }    
    
    @Test
    public void testPieceMove3(){
        GameState state = GameState.fromFEN("k7/4r3/1rr1B1r1/8/2r1r3/4r3/8/K7 b - - 0 1", rules);
        
        Field from = Field.getInstance("c6");
        Field to   = Field.getInstance("e6");
        Move m = PieceMove.getInstance(from, to);
        
        assertEquals("Rcxe6", m.moveString(state)); 
    }   

    @Test
    public void testPieceMove4(){
        GameState state = GameState.fromFEN("k7/4r3/1rr1B1r1/8/2r1r3/4r3/8/K7 b - - 0 1", rules);
        
        Field from = Field.getInstance("e4");
        Field to   = Field.getInstance("e6");
        Move m = PieceMove.getInstance(from, to);
        
        assertEquals("R4xe6", m.moveString(state)); 
    }
    
    @Test
    public void testPieceMove5(){
        GameState state = GameState.fromFEN("k7/4r3/1rr3r1/8/2r1r3/4r3/8/K7 b - - 0 1", rules);
        
        Field from = Field.getInstance("c6");
        Field to   = Field.getInstance("e6");
        Move m = PieceMove.getInstance(from, to);
        
        assertEquals("Rce6", m.moveString(state)); 
    }   

    @Test
    public void testPieceMove6(){
        GameState state = GameState.fromFEN("k7/4r3/1rr3r1/8/2r1r3/4r3/8/K7 b - - 0 1", rules);
        
        Field from = Field.getInstance("e4");
        Field to   = Field.getInstance("e6");
        Move m = PieceMove.getInstance(from, to);
        
        assertEquals("R4e6", m.moveString(state)); 
    }  
    
    @Test
    public void testPieceMove7(){
        GameState state = GameState.fromFEN("4k3/8/8/8/R6R/8/8/4K3 w - - 0 1", rules);
        
        Field from = Field.getInstance("a4");
        Field to   = Field.getInstance("e4");
        Move m = PieceMove.getInstance(from, to);
        
        assertEquals("Rae4+", m.moveString(state)); 
    }    
    
    @Test
    public void testPieceMove8(){
        GameState state = GameState.fromFEN("3rkr2/8/Q7/8/8/8/Q7/4K3 w - - 0 1",rules);
        
        Field from = Field.getInstance("a6");
        Field to   = Field.getInstance("e6");
        Move m = PieceMove.getInstance(from, to);
        
        assertEquals("Q6e6#", m.moveString(state)); 
    }    
    
    @Test
    public void testPromotion(){
        GameState state = GameState.fromFEN("8/1k2P3/8/8/8/8/8/4K3 w - - 0 1", rules);
        
        Field from = Field.getInstance("e7");
        Field to   = Field.getInstance("e8");
        Move m = Promotion.getInstance(from, to, PieceType.WHITE_KNIGHT);
        
        assertEquals("e8=N", m.moveString(state));         
    }    
    
    @Test
    public void testPromotion2(){
        GameState state = GameState.fromFEN("1k6/6PR/8/8/8/8/8/4K3 w - - 0 1", rules);
        
        Field from = Field.getInstance("g7");
        Field to   = Field.getInstance("g8");
        Move m = Promotion.getInstance(from, to, PieceType.WHITE_ROOK);
        
        assertEquals("g8=R#", m.moveString(state));         
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