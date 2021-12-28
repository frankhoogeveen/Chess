package nl.fh.gamereport;

import nl.fh.gamestate.chess.BoardSide;
import nl.fh.gamestate.chess.Field;
import nl.fh.gamestate.chess.PieceKind;
import nl.fh.gamestate.chess.ChessState;
import nl.fh.gamestate.chess.move.Castling;
import nl.fh.gamestate.chess.move.ChessMove;
import nl.fh.gamestate.chess.move.DrawOfferAccepted;
import nl.fh.gamestate.chess.move.EnPassantCapture;
import nl.fh.gamestate.chess.move.PieceMove;
import nl.fh.gamestate.chess.move.Promotion;
import nl.fh.rule.FIDEchess;
import nl.fh.rule.ChessResultArbiter;
import nl.fh.rule.GameDriver;
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
    
    GameDriver driver = FIDEchess.getGameDriver();

    
    @Test
    public void testCastlingLong(){
        String fen = "8/6k1/8/8/8/8/8/R3K2R w KQ - 0 1";
        ChessState state = ChessState.fromFEN(fen);
        ChessMove m = Castling.getInstance(BoardSide.QUEENSIDE);
        assertEquals("O-O-O", m.formatPGN(state, driver));
    }
    
    @Test
    public void testCastlingShort(){
        String fen = "8/3k4/8/8/8/8/8/R3K2R w KQ - 0 1";
        ChessState state = ChessState.fromFEN(fen);
        ChessMove m = Castling.getInstance(BoardSide.KINGSIDE);
        assertEquals("O-O", m.formatPGN(state, driver));       
    }
    
    @Test
    public void testCastlingCheck(){
        String fen = "8/8/8/8/8/8/8/k3K2R w K - 0 1";
        ChessState state = ChessState.fromFEN(fen);
        ChessMove m = Castling.getInstance(BoardSide.KINGSIDE);
        assertEquals("O-O+", m.formatPGN(state, driver));       
    }  
    
    @Test
    public void testCastlingMate(){
        String fen = "8/8/8/8/8/8/R7/R3K2k w Q - 0 1";
        ChessState state = ChessState.fromFEN(fen);
        ChessMove m = Castling.getInstance(BoardSide.QUEENSIDE);
        assertEquals("O-O-O#", m.formatPGN(state, driver));       
    }       
    
    @Test
    public void testDrawOfferAccepted(){
        ChessState state = null;
        ChessMove m = DrawOfferAccepted.getInstance();
        assertEquals("", m.formatPGN(state, driver));            
    }
    
    @Test
    public void testEnPassantCapture(){
        ChessState state = ChessState.fromFEN("4k3/8/8/2pP4/8/8/8/4K3 w - c6 0 2");
        
        ChessMove m = EnPassantCapture.getInstance(Field.getInstance("d5"), state.getEnPassantField());
        assertEquals("dxc6", m.formatPGN(state, driver));             
    }
    @Test
    public void testEnPassantCapture2(){
        ChessState state = ChessState.fromFEN("1Q6/3k4/6N1/1N2pP2/8/8/8/K3R2B w - e6 0 2");
        
        ChessMove m = EnPassantCapture.getInstance(Field.getInstance("f5"), state.getEnPassantField());
        assertEquals("fxe6#", m.formatPGN(state, driver));             
    }   
    
    @Test
    public void testEnPassantCapture3(){
        ChessState state = ChessState.fromFEN("rn2k1nr/ppp2ppp/8/3p4/2Pp3P/7b/P3PP1P/RNB1K1NR b KQkq c3 0 8");
        
        ChessMove m = EnPassantCapture.getInstance(Field.getInstance("d4"), Field.getInstance("c3"));
        assertEquals("dxc3", m.formatPGN(state, driver));            
        
    }
    @Test
    public void testEnPassantCapture4(){
        ChessState state = ChessState.fromFEN("rnb1k1nr/ppp2ppp/3p4/8/1P1pP3/P7/2P2PP1/R4K2 b kq e3 0 12");
        
        ChessMove m = EnPassantCapture.getInstance(Field.getInstance("d4"), Field.getInstance("e3"));
        assertEquals("dxe3", m.formatPGN(state, driver));            
        
    }           
    
    @Test
    public void testPieceMove1(){
        ChessState state = FIDEchess.getInitialState();
        
        Field from = Field.getInstance("b1");
        Field to   = Field.getInstance("c3");
        ChessMove m = PieceMove.getInstance(from, to);
        
        assertEquals("Nc3", m.formatPGN(state, driver)); 
    }
    
    @Test
    public void testPieceMove2(){
        ChessState state = FIDEchess.getInitialState();
        
        Field from = Field.getInstance("a2");
        Field to   = Field.getInstance("a4");
        ChessMove m = PieceMove.getInstance(from, to);
        
        assertEquals("a4", m.formatPGN(state, driver)); 
    }    
    
    @Test
    public void testPieceMove3(){
        ChessState state = ChessState.fromFEN("k7/4r3/1rr1B1r1/8/2r1r3/4r3/8/K7 b - - 0 1");
        
        Field from = Field.getInstance("c6");
        Field to   = Field.getInstance("e6");
        ChessMove m = PieceMove.getInstance(from, to);
        
        assertEquals("Rcxe6", m.formatPGN(state, driver)); 
    }   

    @Test
    public void testPieceMove4(){
        ChessState state = ChessState.fromFEN("k7/4r3/1rr1B1r1/8/2r1r3/4r3/8/K7 b - - 0 1");
        
        Field from = Field.getInstance("e4");
        Field to   = Field.getInstance("e6");
        ChessMove m = PieceMove.getInstance(from, to);
        
        assertEquals("R4xe6", m.formatPGN(state, driver)); 
    }
    
    @Test
    public void testPieceMove5(){
        ChessState state = ChessState.fromFEN("k7/4r3/1rr3r1/8/2r1r3/4r3/8/K7 b - - 0 1");
        
        Field from = Field.getInstance("c6");
        Field to   = Field.getInstance("e6");
        ChessMove m = PieceMove.getInstance(from, to);
        
        assertEquals("Rce6", m.formatPGN(state, driver)); 
    }   

    @Test
    public void testPieceMove6(){
        ChessState state = ChessState.fromFEN("k7/4r3/1rr3r1/8/2r1r3/4r3/8/K7 b - - 0 1");
        
        Field from = Field.getInstance("e4");
        Field to   = Field.getInstance("e6");
        ChessMove m = PieceMove.getInstance(from, to);
        
        assertEquals("R4e6", m.formatPGN(state, driver)); 
    }  
    
    @Test
    public void testPieceMove7(){
        ChessState state = ChessState.fromFEN("4k3/8/8/8/R6R/8/8/4K3 w - - 0 1");
        
        Field from = Field.getInstance("a4");
        Field to   = Field.getInstance("e4");
        ChessMove m = PieceMove.getInstance(from, to);
        
        assertEquals("Rae4+", m.formatPGN(state, driver)); 
    }    
    
    @Test
    public void testPieceMove8(){
        ChessState state = ChessState.fromFEN("3rkr2/8/Q7/8/8/8/Q7/4K3 w - - 0 1");
        
        Field from = Field.getInstance("a6");
        Field to   = Field.getInstance("e6");
        ChessMove m = PieceMove.getInstance(from, to);
        
        assertEquals("Q6e6#", m.formatPGN(state, driver)); 
    }    
    
    @Test
    public void testPromotion(){
        ChessState state = ChessState.fromFEN("8/1k2P3/8/8/8/8/8/4K3 w - - 0 1");
        
        Field from = Field.getInstance("e7");
        Field to   = Field.getInstance("e8");
        ChessMove m = Promotion.getInstance(from, to, PieceKind.KNIGHT);
        
        assertEquals("e8=N", m.formatPGN(state, driver));         
    }    
    
    @Test
    public void testPromotion2(){
        ChessState state = ChessState.fromFEN("1k6/6PR/8/8/8/8/8/4K3 w - - 0 1");
        
        Field from = Field.getInstance("g7");
        Field to   = Field.getInstance("g8");
        ChessMove m = Promotion.getInstance(from, to, PieceKind.ROOK);
        
        assertEquals("g8=R#", m.formatPGN(state, driver));         
    }  
    
    @Test
    public void testPromotion3(){
        ChessState state = ChessState.fromFEN("4r3/2kP1P2/8/8/8/8/8/3K4 w - - 0 1");
        
        Field from = Field.getInstance("f7");
        Field to   = Field.getInstance("e8");
        ChessMove m = Promotion.getInstance(from, to, PieceKind.KNIGHT);
        
        assertEquals("fxe8=N+", m.formatPGN(state, driver));         
    } 

    @Test
    public void testPawnPromotionByCapture(){
        ChessState state = ChessState.fromFEN("8/1k6/8/8/8/8/1K4p1/7R b - - 0 1");
        
        Field from = Field.getInstance("g2");
        Field to   = Field.getInstance("h1");
        ChessMove m = Promotion.getInstance(from, to, PieceKind.BISHOP);
        
        assertEquals("gxh1=B", m.formatPGN(state, driver));          
        
    }
        
    
}