/*
 * License: GPL v3
 * 
 */
package nl.fh.rules;


import nl.fh.gamestate.GameState;
import java.util.List;
import nl.fh.chess.BoardSide;
import nl.fh.gamereport.GameReport;
import nl.fh.move.Castling;
import nl.fh.move.Move;
import nl.fh.parser.PGN_Reader;
import nl.fh.parser.TolerantReader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * 
 */
public class CastlingRulesTest {
    
    private GameDriver gameDriver = Chess.getGameDriver();

    @Test
    /**
     * sensitivity for the K castling flag
     */
    public void testWhiteKingSideCastling1(){
        String fen = "4k3/8/8/8/8/8/8/R3K2R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(isLegalMove(move, state));
    }
    
    @Test
    /**
     * sensitivity for the absence of K castling flag
     */
    public void testWhiteKingSideCastling2(){
        String fen = "4k3/8/8/8/8/8/8/R3K2R w Q - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    } 
    
    @Test
    /**
     *  response to '-' as castling flag
     */
    public void testWhiteKingSideCastling3(){
        String fen = "4k3/8/8/8/8/8/8/R3K2R w - - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }      
    
    @Test
    /**
     * king in check
     */
    public void testWhiteKingSideCastling4(){
        String fen = "4k3/8/8/8/8/6b1/8/R3K2R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }     
    
    @Test
    /**
     * field between king and rook covered
     */
    public void testWhiteKingSideCastling5(){
        String fen = "4k3/8/8/8/8/4n3/8/R3K2R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }    

    @Test
    /**
     * field between king and rook covered
     */
    public void testWhiteKingSideCastling6(){
        String fen = "4k3/8/8/6r1/8/8/8/R3K2R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }    
    
    @Test
    /**
     * rook is attacked
     */
    public void testWhiteKingSideCastling7(){
        String fen = "4k3/8/2q5/8/8/8/8/R3K2R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(isLegalMove(move, state));
    }  
    
    @Test
    /**
     * opposing piece between king and rook
     */
    public void testWhiteKingSideCastling8(){
        String fen = "4k3/8/8/8/8/8/8/R3Kn1R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }   
    
    @Test
    /** 
     * own piece between king and rook
     */
    public void testWhiteKingSideCastling9(){
        String fen = "4k3/8/8/8/8/8/8/R3K1RR w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }  
    
    @Test
    /**
     * moving the rook disallows castling
     */
    public void testWhiteKingSideCastlingMove1(){
        String fen = "k7/8/8/8/8/8/8/4K2R w K - 0 1";
        String moves = "1. Rh2 Ka7 2. Rh1 Ka8";  
     
        String pgn = "[SetUp \"1\"]\n[FEN \""
                + fen 
                +"\"]\n"
                + moves
                + " *\n";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        GameReport report = reports.get(0);
        List<GameState> states = report.getStateList();
        
        GameState initialState = states.get(0);
        GameState finalState = states.get(states.size()-1);  
        
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        assertTrue( isLegalMove(move, initialState));        
        assertTrue(!isLegalMove(move, finalState));
        
        assertEquals("k7/8/8/8/8/8/8/4K2R w - - 4 3", finalState.toFEN());
    }  
    
    @Test
    /**
     * moving the king disallows castling
     */
    public void testWhiteKingSideCastlingMove2(){
        String fen = "k7/8/8/8/8/8/8/4K2R w K - 0 1";
        String moves = "1. Ke2 Ka7 2. Ke1 Ka8";  
     
        String pgn = "[SetUp \"1\"]\n[FEN \""
                + fen 
                +"\"]\n"
                + moves
                + " *\n";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        GameReport report = reports.get(0);
        List<GameState> states = report.getStateList();
        
        GameState initialState = states.get(0);
        GameState finalState = states.get(states.size()-1);  
        
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        assertTrue( isLegalMove(move, initialState));        
        assertTrue(!isLegalMove(move, finalState));
        
        assertEquals("k7/8/8/8/8/8/8/4K2R w - - 4 3", finalState.toFEN());
    }  
    
    @Test
    /**
     * moving the other rook
     */
    public void testWhiteKingSideCastlingMove3(){
        String fen = "1k6/8/8/8/8/8/8/R3K2R w K - 0 1";
        String moves = "1. Ra2 Kb7 2. Ra1 Kb8";  
     
        String pgn = "[SetUp \"1\"]\n[FEN \""
                + fen 
                +"\"]\n"
                + moves
                + " *\n";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        GameReport report = reports.get(0);
        List<GameState> states = report.getStateList();
        
        GameState initialState = states.get(0);
        GameState finalState = states.get(states.size()-1);  
        
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        assertTrue( isLegalMove(move, initialState));        
        assertTrue( isLegalMove(move, finalState));
        
        assertEquals("1k6/8/8/8/8/8/8/R3K2R w K - 4 3", finalState.toFEN());
    }     

////////////////////////////////////////////////////////////////////
    @Test
    /**
     * sensitivity for the Q castling flag
     */

    public void testWhiteQueenSideCastling1(){
        String fen = "4k3/8/8/8/8/8/8/R3K2R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(isLegalMove(move, state));
    }
    
    @Test
    /**
     * sensitivity for the absence of K castling flag
     */
    public void testWhiteQueenSideCastling2(){
        String fen = "4k3/8/8/8/8/8/8/R3K2R w Kkq - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    } 
    
    @Test
    /**
     *  response to '-' as castling flag
     */
    public void testWhiteQueenSideCastling3(){
        String fen = "4k3/8/8/8/8/8/8/R3K2R w - - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }      
    
    @Test
    /**
     * king in check
     */
    public void testWhiteQueenSideCastling4(){
        String fen = "4k3/8/8/8/8/6b1/8/R3K2R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }  
    
    @Test
    /**
     * king in check after castling
     */
    public void testWhiteQueenSideCastling4a(){
        String fen = "4k3/8/8/8/8/4b3/8/R3K2R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }      
    
    @Test
    /**
     * field between king and rook covered
     */
    public void testWhiteQueenSideCastling5(){
        String fen = "4k3/8/8/8/8/4n3/8/R3K2R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }    

    @Test
    /**
     * field between king and rook covered
     */
    public void testWhiteQueenSideCastling6(){
        String fen = "4k3/8/8/3r4/8/8/8/R3K2R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }    
        
    
    
    @Test
    /**
     * field between king and rook covered, but king not moving through
     */
    public void testWhiteQueenSideCastling6a(){
        String fen = "4k3/8/8/1r6/8/8/8/R3K2R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(isLegalMove(move, state));
    }    
    
    @Test
    /**
     * rook is attacked
     */
    public void testWhiteQueenSideCastling7(){
        String fen = "4k3/8/5q2/8/8/8/8/R3K2R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(isLegalMove(move, state));
    }  
    
    @Test
    /**
     * opposing piece between king and rook
     */
    public void testWhiteQueenSideCastling8(){
        String fen = "4k3/8/8/8/8/8/8/R1b1K2R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }   
    
    @Test
    /** 
     * own piece between king and rook
     */
    public void testWhiteQueenSideCastling9(){
        String fen = "4k3/8/8/8/8/8/8/R1N1K2R w KQ - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }  
    
    @Test
    /**
     * moving the rook disallows castling
     */
    public void testWhiteQueenSideCastlingMove1(){
        String fen = "7k/8/8/8/8/8/8/R3K3 w KQ - 0 1";
        String moves = "1. Ra2 Kg7 2. Ra1 Kh8";  
     
        String pgn = "[SetUp \"1\"]\n[FEN \""
                + fen 
                +"\"]\n"
                + moves
                + " *\n";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        GameReport report = reports.get(0);
        List<GameState> states = report.getStateList();
        
        GameState initialState = states.get(0);
        GameState finalState = states.get(states.size()-1);  
        
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        assertTrue( isLegalMove(move, initialState));           
        assertTrue(!isLegalMove(move, finalState));
        
        assertEquals("7k/8/8/8/8/8/8/R3K3 w K - 4 3", finalState.toFEN());
    } 
    
    @Test
    /**
     * moving the king disallows castling
     */
    public void testWhiteQueenSideCastlingMove2(){
        String fen = "7k/8/8/8/8/8/8/R3K3 w KQ - 0 1";
        String moves = "1. Ke2 Kh7 2. Ke1 Kh8";  
     
        String pgn = "[SetUp \"1\"]\n[FEN \""
                + fen 
                +"\"]\n"
                + moves
                + " *\n";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        GameReport report = reports.get(0);
        List<GameState> states = report.getStateList();
        
        GameState initialState = states.get(0);
        GameState finalState = states.get(states.size()-1);  
        
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        assertTrue( isLegalMove(move, initialState));        
        assertTrue(!isLegalMove(move, finalState));
        
        assertEquals("7k/8/8/8/8/8/8/R3K3 w - - 4 3", finalState.toFEN());
    }  
    
    @Test
    /**
     * moving the other rook
     */
    public void testWhiteQueenSideCastlingMove3(){
        String fen = "1k6/8/8/8/8/8/8/R3K2R w KQ - 0 1";
        String moves = "1. Rh2 Kb7 2. Rh1 Kb8";  
     
        String pgn = "[SetUp \"1\"]\n[FEN \""
                + fen 
                +"\"]\n"
                + moves
                + " *\n";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        GameReport report = reports.get(0);
        List<GameState> states = report.getStateList();
        
        GameState initialState = states.get(0);
        GameState finalState = states.get(states.size()-1);  
        
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        assertTrue( isLegalMove(move, initialState));        
        assertTrue( isLegalMove(move, finalState));
        
        assertEquals("1k6/8/8/8/8/8/8/R3K2R w Q - 4 3", finalState.toFEN());
    }
////////////////////////////////////////////////////////////////////////////////
  
    @Test
    /**
     * sensitivity for the K castling flag
     */

    public void testBlackKingSideCastling1(){
        String fen = "r3k2r/8/8/8/8/8/8/4K3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(isLegalMove(move, state));
    }
    
    @Test
    /**
     * sensitivity for the absence of K castling flag
     */
    public void testBlackKingSideCastling2(){
        String fen = "r3k2r/8/8/8/8/8/8/4K3 b q - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    } 
    
    @Test
    /**
     *  response to '-' as castling flag
     */
    public void testBlackKingSideCastling3(){
        String fen = "r3k2r/8/8/8/8/8/8/4K3 b - - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }      
    
    @Test
    /**
     * king in check
     */
    public void testBlackKingSideCastling4(){
        String fen = "r3k2r/8/3N4/8/8/8/8/4K3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }     
    
    @Test
    /**
     * field between king and rook covered
     */
    public void testBlackKingSideCastling5(){
        String fen = "r3k2r/8/4N3/8/8/8/8/4K3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }    

    @Test
    /**
     * field between king and rook covered
     */
    public void testBlackKingSideCastling6(){
        String fen = "r3k2r/8/8/8/8/8/B7/4K3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);

        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }    
    
    @Test
    /**
     * rook is attacked
     */
    public void testBlackKingSideCastling7(){
        String fen = "r3k2r/8/8/8/8/8/1B6/4K3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);

        GameState state = GameState.fromFEN(fen);
        assertTrue(isLegalMove(move, state));
    }  
    
    @Test
    /**
     * opposing piece between king and rook
     */
    public void testBlackKingSideCastling8(){
        String fen = "r3kN1r/8/8/8/8/8/8/4K3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);

        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }   
    
    @Test
    /** 
     * own piece between king and rook
     */
    public void testBlackKingSideCastling9(){
        String fen = "r3kq1r/8/8/8/8/8/8/4K3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.KINGSIDE);

        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }  
    
    @Test
    /**
     * moving the rook disallows castling
     */
    public void testBlackKingSideCastlingMove1(){
        String fen = "r3k2r/8/8/8/8/8/8/4K3 b kq - 0 1";
        String moves = "1. Rh7 Ke2  2. Rh8 Ke1";  
     
        String pgn = "[SetUp \"1\"]\n[FEN \""
                + fen 
                +"\"]\n"
                + moves
                + " *\n";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        GameReport report = reports.get(0);
        List<GameState> states = report.getStateList();
        
        GameState initialState = states.get(0);
        GameState finalState = states.get(states.size()-1);  
        
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        assertTrue( isLegalMove(move, initialState));        
        assertTrue(!isLegalMove(move, finalState));
        
        assertEquals("r3k2r/8/8/8/8/8/8/4K3 b q - 4 3", finalState.toFEN());
    }  
    
    @Test
    /**
     * moving the king disallows castling
     */
    public void testBlackKingSideCastlingMove2(){
        String fen = "r3k2r/8/8/8/8/8/8/4K3 b kq - 0 1";
        String moves = "1. Ke7 Ke2 2. Ke8 Ke1";  
     
        String pgn = "[SetUp \"1\"]\n[FEN \""
                + fen 
                +"\"]\n"
                + moves
                + " *\n";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        GameReport report = reports.get(0);
        List<GameState> states = report.getStateList();
        
        GameState initialState = states.get(0);
        GameState finalState = states.get(states.size()-1);  
        
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        assertTrue( isLegalMove(move, initialState));        
        assertTrue(!isLegalMove(move, finalState));
        
        assertEquals("r3k2r/8/8/8/8/8/8/4K3 b - - 4 3", finalState.toFEN());
    }  
    
    @Test
    /**
     * moving the other rook
     */
    public void testBlackKingSideCastlingMove3(){
        String fen = "r3k2r/8/8/8/8/8/8/4K3 b kq - 0 1";
        String moves = "1. Ra7 Ke2 2. Ra8 Ke1";  
     
        String pgn = "[SetUp \"1\"]\n[FEN \""
                + fen 
                +"\"]\n"
                + moves
                + " *\n";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        GameReport report = reports.get(0);
        List<GameState> states = report.getStateList();
        
        GameState initialState = states.get(0);
        GameState finalState = states.get(states.size()-1);  
        
        Move move = Castling.getInstance(BoardSide.KINGSIDE);
        
        assertTrue( isLegalMove(move, initialState));        
        assertTrue( isLegalMove(move, finalState));
        
        assertEquals("r3k2r/8/8/8/8/8/8/4K3 b k - 4 3", finalState.toFEN());
    }     

////////////////////////////////////////////////////////////////////
    @Test
    /**
     * sensitivity for the Q castling flag
     */
    public void testBlackQueenSideCastling1(){
        String fen = "r3k2r/8/8/8/8/8/8/4K3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(isLegalMove(move, state));
    }
    
    @Test
    /**
     * sensitivity for the absence of q castling flag
     */
    public void testBlackQueenSideCastling2(){
        String fen = "r3k2r/8/8/8/8/8/8/4K3 b k - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    } 
    
    @Test
    /**
     *  response to '-' as castling flag
     */
    public void testBlackQueenSideCastling3(){
        String fen = "r3k2r/8/8/8/8/8/8/4K3 b - - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);

        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }      
    
    @Test
    /**
     * king in check
     */
    public void testBlackQueenSideCastling4(){
        String fen = "r3k2r/8/8/8/8/8/4Q3/4K3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }  
    
    @Test
    /**
     * king in check after castling
     */
    public void testBlackQueenSideCastling4a(){
        String fen = "r3k2r/8/8/8/6Q1/8/8/4K3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }      
    
    @Test
    /**
     * field between king and rook covered
     */
    public void testBlackQueenSideCastling5(){
        String fen = "r3k2r/8/8/8/8/8/8/3QK3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }    

    @Test
    /**
     * field between king and rook covered
     */
    public void testBlackQueenSideCastling6(){
        String fen = "r3k2r/8/8/8/8/8/8/2Q1K3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }    
        
    
    
    @Test
    /**
     * field between king and rook covered, but king not moving through
     */
    public void testBlackQueenSideCastling6a(){
        String fen = "r3k2r/8/8/8/8/8/8/1Q2K3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(isLegalMove(move, state));
    }    
    
    @Test
    /**
     * rook is attacked
     */
    public void testBlackQueenSideCastling7(){
        String fen = "r3k2r/8/8/8/8/8/8/4K2Q b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(isLegalMove(move, state));
    }  
    
    @Test
    /**
     * opposing piece between king and rook
     */
    public void testBlackQueenSideCastling8(){
        String fen = "r2Bk2r/8/8/8/8/8/8/4K3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }   
    
    @Test
    /** 
     * own piece between king and rook
     */
    public void testBlackQueenSideCastling9(){
        String fen = "rn2k2r/8/8/8/8/8/8/4K3 b kq - 0 1";
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        GameState state = GameState.fromFEN(fen);
        assertTrue(!isLegalMove(move, state));
    }  
    
    @Test
    /**
     * moving the rook disallows castling
     */
    public void testBlackQueenSideCastlingMove1(){
        String fen = "r3k2r/8/8/8/8/8/8/4K3 b kq - 0 1";
        String moves = "1. Ra7 Ke2 2. Ra8 Ke1";  
     
        String pgn = "[SetUp \"1\"]\n[FEN \""
                + fen 
                +"\"]\n"
                + moves
                + " *\n";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        GameReport report = reports.get(0);
        List<GameState> states = report.getStateList();
        
        GameState initialState = states.get(0);
        GameState finalState = states.get(states.size()-1);  
        
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        assertTrue( isLegalMove(move, initialState));           
        assertTrue(!isLegalMove(move, finalState));
        
        assertEquals("r3k2r/8/8/8/8/8/8/4K3 b k - 4 3", finalState.toFEN());
    } 
    
    @Test
    /**
     * moving the king disallows castling
     */
    public void testBlackQueenSideCastlingMove2(){
        String fen = "r3k2r/8/8/8/8/8/8/4K3 b kq - 0 1";
        String moves = "1. Ke7 Ke2 2. Ke8 Ke1";  
     
        String pgn = "[SetUp \"1\"]\n[FEN \""
                + fen 
                +"\"]\n"
                + moves
                + " *\n";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        GameReport report = reports.get(0);
        List<GameState> states = report.getStateList();
        
        GameState initialState = states.get(0);
        GameState finalState = states.get(states.size()-1);  
        
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        assertTrue( isLegalMove(move, initialState));        
        assertTrue(!isLegalMove(move, finalState));
        
        assertEquals("r3k2r/8/8/8/8/8/8/4K3 b - - 4 3", finalState.toFEN());
    }  
    
    @Test
    /**
     * moving the other rook
     */
    public void testBlackQueenSideCastlingMove3(){
        String fen = "r3k2r/8/8/8/8/8/8/4K3 b kq - 0 1";
        String moves = "1. Rh7 Ke2 2. Rh8 Ke1";  
     
        String pgn = "[SetUp \"1\"]\n[FEN \""
                + fen 
                +"\"]\n"
                + moves
                + " *\n";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        GameReport report = reports.get(0);
        List<GameState> states = report.getStateList();
        
        GameState initialState = states.get(0);
        GameState finalState = states.get(states.size()-1);  
        
        Move move = Castling.getInstance(BoardSide.QUEENSIDE);
        
        assertTrue( isLegalMove(move, initialState));        
        assertTrue( isLegalMove(move, finalState));
        
        assertEquals("r3k2r/8/8/8/8/8/8/4K3 b q - 4 3", finalState.toFEN());
    }    

    private boolean isLegalMove(Move move, GameState state) {
        return gameDriver.getMoveGenerator().calculateAllLegalMoves(state).contains(move);
    }
}
