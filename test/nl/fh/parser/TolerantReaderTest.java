/*
 * License: GPL v3
 * 
 */
package nl.fh.parser;

import java.util.List;
import nl.fh.chess.Field;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.ChessGameResult;
import nl.fh.move.ChessMove;
import nl.fh.move.PieceMove;
import nl.fh.rules.FIDEchess;
import nl.fh.rules.GameDriver;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author frank
 */
public class TolerantReaderTest { 

    private GameDriver gameDriver = FIDEchess.getGameDriver();

    @Test
    public void testEmptyPGN() {
        String pgn = "";
        TolerantReader instance = new TolerantReader();
        int expResult = 0;
        List<GameReport> result = instance.getGames(pgn, gameDriver);
        assertEquals(expResult, result.size());
    }
   
    @Test
    public void testEmptyLinesOnly(){
        String pgn = "    \n   \n\n  \n";
        TolerantReader instance = new TolerantReader();
        int expResult = 0;
        List<GameReport> result = instance.getGames(pgn, gameDriver);
        assertEquals(expResult, result.size());        
    }    
    
    @Test
    public void testEscapedLinesOnly(){
        String pgn = "% first line \n% 2nd line";
        TolerantReader instance = new TolerantReader();
        int expResult = 0;
        List<GameReport> result = instance.getGames(pgn, gameDriver);
        assertEquals(expResult, result.size());        
    }
    
    @Test
    public void testResultOnly1(){
        String pgn = "*";
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);
        
        // there has to be one game
        assertEquals(1, result.size());            
        
        GameReport report = result.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());
        
        assertEquals(0, report.getTags().size());

        assertEquals(0, report.getMoveList().size());
        
    }
    
    @Test
    public void testResultOnly2(){
        String pgn = "0-1";
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);
        
        // there has to be one game
        assertEquals(1, result.size());            
        
        GameReport report = result.get(0);
        assertEquals(ChessGameResult.WIN_BLACK, report.getGameResult());
        
        assertEquals(0, report.getTags().size());

        assertEquals(0, report.getMoveList().size());
        
    }
    @Test
    public void testResultOnly3(){
        String pgn = "1-0";
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);
        
        // there has to be one game
        assertEquals(1, result.size());            
        
        GameReport report = result.get(0);
        assertEquals(ChessGameResult.WIN_WHITE, report.getGameResult());
        
        assertEquals(0, report.getTags().size());

        assertEquals(0, report.getMoveList().size());
        
    }
    @Test
    public void testResultOnly4(){
        String pgn = "1/2-1/2";
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);
        
        // there has to be one game
        assertEquals(1, result.size());            
        
        GameReport report = result.get(0);
        assertEquals(ChessGameResult.DRAW, report.getGameResult());
        
        assertEquals(0, report.getTags().size());

        assertEquals(0, report.getMoveList().size());
        
    }    
    @Test
    public void testSingleTag1(){
        String pgn = "[key \"value\"]\n\n*\n";
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);
        
        // there has to be one game
        assertEquals(1, result.size());            
        
        GameReport report = result.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());

        // with zero moves
        assertEquals(0, report.getMoveList().size());

        
        assertEquals(1, report.getTags().size());        
        
        assertTrue(report.getTags().contains("key"));
        assertEquals("value", report.getTag("key"));
        
    }
    
    @Test
    // test the value:   value\"  which should be stored in a pgn as value\\\"
    // which in turn is written as the java string value\\\\\\\"
    public void testSingleTag2(){
        String pgn = "[key \"value\\\\\\\"\"]\n\n*\n";
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);
        
        // there has to be one game
        assertEquals(1, result.size());            
        
        GameReport report = result.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());

        // with zero moves
        assertEquals(0, report.getMoveList().size());

        assertEquals(1, report.getTags().size());        
        
        assertTrue(report.getTags().contains("key"));
        assertEquals("value\\\"", report.getTag("key"));
        
    }    
    
    @Test
    public void testMultipleTag(){
        String pgn = "[key1 \"value1\"]\n[key2 \"value2\"]\n\n*\n";
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);
        
        // there has to be one game
        assertEquals(1, result.size());            
        
        GameReport report = result.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());

        // with zero moves
        assertEquals(0, report.getMoveList().size());   

        
        assertEquals(2, report.getTags().size());        
        
        assertTrue(report.getTags().contains("key1"));
        assertEquals("value1", report.getTag("key1"));
        
        assertTrue(report.getTags().contains("key2"));
        assertEquals("value2", report.getTag("key2"));        
        
    }    
    
    @Test
    public void testMoveString1(){
        String pgn = "1. Na3 Nh6 *";
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);
        
        // there has to be one game
        assertEquals(1, result.size());            
        
        GameReport report = result.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());

        // with two moves
        List<ChessMove> moveList = report.getMoveList();
        assertEquals(2, moveList.size());
        assertEquals(Field.getInstance("b1"), ((PieceMove)moveList.get(0)).getFrom());
        assertEquals(Field.getInstance("a3"), ((PieceMove)moveList.get(0)).getTo());

        assertEquals(Field.getInstance("g8"), ((PieceMove)moveList.get(1)).getFrom());
        assertEquals(Field.getInstance("h6"), ((PieceMove)moveList.get(1)).getTo());  

        // no tags
        assertEquals(0, report.getTags().size());        
    }   
    
    @Test
    public void testMoveString2(){
        String pgn = "1. b3 b6 *";
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);

        // there has to be one game
        assertEquals(1, result.size());            

        GameReport report = result.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());

        // with two moves
        List<ChessMove> moveList = report.getMoveList();
        assertEquals(2, moveList.size());
        assertEquals(Field.getInstance("b2"), ((PieceMove)moveList.get(0)).getFrom());
        assertEquals(Field.getInstance("b3"), ((PieceMove)moveList.get(0)).getTo());

        assertEquals(Field.getInstance("b7"), ((PieceMove)moveList.get(1)).getFrom());
        assertEquals(Field.getInstance("b6"), ((PieceMove)moveList.get(1)).getTo());  

        // no tags
        assertEquals(0, report.getTags().size());       
    }    
    
    @Test
    public void testMoveString3(){
        String pgn = "1. b3+ b6# *";
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);

        // there has to be one game
        assertEquals(1, result.size());            

        GameReport report = result.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());

        // with two moves
        
        List<ChessMove> moveList = report.getMoveList();
        assertEquals(2, moveList.size());
        assertEquals(Field.getInstance("b2"), ((PieceMove)moveList.get(0)).getFrom());
        assertEquals(Field.getInstance("b3"), ((PieceMove)moveList.get(0)).getTo());

        assertEquals(Field.getInstance("b7"), ((PieceMove)moveList.get(1)).getFrom());
        assertEquals(Field.getInstance("b6"), ((PieceMove)moveList.get(1)).getTo());     

        // no tags
        assertEquals(0, report.getTags().size());        
    }    
    
    @Test
    public void testMoveString4(){
        String pgn = "1. b3+ b6#  2. Na3 Nh6 *";
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);

        // there has to be one game
        assertEquals(1, result.size());            

        GameReport report = result.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());

        // with four moves      
        
        List<ChessMove> moveList = report.getMoveList();
        assertEquals(4, moveList.size());
        assertEquals(Field.getInstance("b2"), ((PieceMove)moveList.get(0)).getFrom());
        assertEquals(Field.getInstance("b3"), ((PieceMove)moveList.get(0)).getTo());

        assertEquals(Field.getInstance("b7"), ((PieceMove)moveList.get(1)).getFrom());
        assertEquals(Field.getInstance("b6"), ((PieceMove)moveList.get(1)).getTo());    
        
        assertEquals(Field.getInstance("b1"), ((PieceMove)moveList.get(2)).getFrom());
        assertEquals(Field.getInstance("a3"), ((PieceMove)moveList.get(2)).getTo());

        assertEquals(Field.getInstance("g8"), ((PieceMove)moveList.get(3)).getFrom());
        assertEquals(Field.getInstance("h6"), ((PieceMove)moveList.get(3)).getTo());            

        // no tags
        assertEquals(0, report.getTags().size());        
    }       
    
    @Test
    public void testMoveString5(){
        String pgn = "1. f3 e6 2. g4 Qh4# 0-1";
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);

        // there has to be one game
        assertEquals(1, result.size());            

        GameReport report = result.get(0);
        assertEquals(ChessGameResult.WIN_BLACK, report.getGameResult());

        // with four moves 
        
        List<ChessMove> moveList = report.getMoveList();
        assertEquals(4,moveList.size());
        assertEquals(Field.getInstance("f2"), ((PieceMove)moveList.get(0)).getFrom());
        assertEquals(Field.getInstance("f3"), ((PieceMove)moveList.get(0)).getTo());

        assertEquals(Field.getInstance("e7"), ((PieceMove)moveList.get(1)).getFrom());
        assertEquals(Field.getInstance("e6"), ((PieceMove)moveList.get(1)).getTo());    
        
        assertEquals(Field.getInstance("g2"), ((PieceMove)moveList.get(2)).getFrom());
        assertEquals(Field.getInstance("g4"), ((PieceMove)moveList.get(2)).getTo());

        assertEquals(Field.getInstance("d8"), ((PieceMove)moveList.get(3)).getFrom());
        assertEquals(Field.getInstance("h4"), ((PieceMove)moveList.get(3)).getTo());            

        // no tags
        assertEquals(0, report.getTags().size());        
    }  
    
    /**
     * case to test what happens if there is no whitespace between the 
     * move number and the white move
     */
    @Test
    public void testMoveString6(){
        String pgn = "1.Na3 Nh6 *";
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);
        
        // there has to be one game
        assertEquals(1, result.size());            
        
        GameReport report = result.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());

        // with two moves
        List<ChessMove> moveList = report.getMoveList();
        assertEquals(2, moveList.size());
        assertEquals(Field.getInstance("b1"), ((PieceMove)moveList.get(0)).getFrom());
        assertEquals(Field.getInstance("a3"), ((PieceMove)moveList.get(0)).getTo());

        assertEquals(Field.getInstance("g8"), ((PieceMove)moveList.get(1)).getFrom());
        assertEquals(Field.getInstance("h6"), ((PieceMove)moveList.get(1)).getTo());  

        // no tags
        assertEquals(0, report.getTags().size());        
    }  
    
    @Test
    public void testMoveString7(){
        String pgn = "1.Na3 Nh6 2.e4 *";
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);
        
        // there has to be one game
        assertEquals(1, result.size());            
        
        GameReport report = result.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());

        // with three moves
        List<ChessMove> moveList = report.getMoveList();
        assertEquals(3, moveList.size());
        assertEquals(Field.getInstance("b1"), ((PieceMove)moveList.get(0)).getFrom());
        assertEquals(Field.getInstance("a3"), ((PieceMove)moveList.get(0)).getTo());

        assertEquals(Field.getInstance("g8"), ((PieceMove)moveList.get(1)).getFrom());
        assertEquals(Field.getInstance("h6"), ((PieceMove)moveList.get(1)).getTo());  

        assertEquals(Field.getInstance("e2"), ((PieceMove)moveList.get(2)).getFrom());
        assertEquals(Field.getInstance("e4"), ((PieceMove)moveList.get(2)).getTo());          

        // no tags
        assertEquals(0, report.getTags().size());        
    }       
    
    @Test
    public void testTagAndMoveString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[key1 \"value1\"]\n[key2 \"value2\"]\n\n");
        sb.append("1. f3 e6 2. g4 Qh4# 0-1");
        
        String pgn = sb.toString();
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);

        // there has to be one game
        assertEquals(1, result.size());            

        GameReport report = result.get(0);
        assertEquals(ChessGameResult.WIN_BLACK, report.getGameResult());

        // with four moves    
        
        List<ChessMove> moveList = report.getMoveList();
        assertEquals(4, moveList.size());
        assertEquals(Field.getInstance("f2"), ((PieceMove)moveList.get(0)).getFrom());
        assertEquals(Field.getInstance("f3"), ((PieceMove)moveList.get(0)).getTo());

        assertEquals(Field.getInstance("e7"), ((PieceMove)moveList.get(1)).getFrom());
        assertEquals(Field.getInstance("e6"), ((PieceMove)moveList.get(1)).getTo());    
        
        assertEquals(Field.getInstance("g2"), ((PieceMove)moveList.get(2)).getFrom());
        assertEquals(Field.getInstance("g4"), ((PieceMove)moveList.get(2)).getTo());

        assertEquals(Field.getInstance("d8"), ((PieceMove)moveList.get(3)).getFrom());
        assertEquals(Field.getInstance("h4"), ((PieceMove)moveList.get(3)).getTo());            

        // two tags
        assertEquals(2, report.getTags().size());           
    }
    
    @Test
    public void testTwoGames(){
        StringBuilder sb = new StringBuilder();
        sb.append("[key1 \"value1\"]\n[key2 \"value2\"]\n\n");
        sb.append("1. f3 e6 2. g4 Qh4# 0-1\n\n");
        sb.append("[key3 \"value3\"]\n[key4 \"value4\"]\n\n");
        sb.append("1. b3+ b6#  2. Na3 Nh6 *\n\n");        
        
        
        String pgn = sb.toString();
        TolerantReader instance = new TolerantReader();
        List<GameReport> result = instance.getGames(pgn, gameDriver);

        // there has to be two games
        assertEquals(2, result.size());            

        GameReport report = result.get(0);
        assertEquals(ChessGameResult.WIN_BLACK, report.getGameResult());

        // with four moves  
        List<ChessMove> moveList = report.getMoveList();
        assertEquals(4, moveList.size());
        assertEquals(Field.getInstance("f2"), ((PieceMove)moveList.get(0)).getFrom());
        assertEquals(Field.getInstance("f3"), ((PieceMove)moveList.get(0)).getTo());

        assertEquals(Field.getInstance("e7"), ((PieceMove)moveList.get(1)).getFrom());
        assertEquals(Field.getInstance("e6"), ((PieceMove)moveList.get(1)).getTo());    
        
        assertEquals(Field.getInstance("g2"), ((PieceMove)moveList.get(2)).getFrom());
        assertEquals(Field.getInstance("g4"), ((PieceMove)moveList.get(2)).getTo());

        assertEquals(Field.getInstance("d8"), ((PieceMove)moveList.get(3)).getFrom());
        assertEquals(Field.getInstance("h4"), ((PieceMove)moveList.get(3)).getTo());            

        // two tags
        assertEquals(2, report.getTags().size());           
    }     
    
    @Test
    public void testMultipleGames(){
        String pgn = "1.e4 e5 * \n\n1.e4 e5 \n2.Bc4 *";
                
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        assertEquals(2, reports.size());
    }
    
    @Test
    public void testIncorrectPGN(){
        String pgn =  "1.e4 e5 * \n\n "
                    + "1.e4 e5 *  \n\n"
                    + "1.e4 e5 2. xxx \n\n *"   // incorrect game
                    + "1.e4 e5 *  \n\n";
                
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        assertEquals(3, reports.size());
    }
    
    @Test
    public void testPromotion(){
        String pgn =    "[Variant \"From Position\"]\n" +
                        "[FEN \"rnbqk3/ppppp1P1/8/8/8/8/PPPPPP1P/RNBQKBNR w KQq - 0 1\"]\n" +
                        "\n" +
                        "1. g8=Q# 1-0";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        
        assertEquals(1, reports.size());        
        
        GameReport report = reports.get(0);
        assertEquals(ChessGameResult.WIN_WHITE, report.getGameResult());
        
        String fen2 = "rnbqk1Q1/ppppp3/8/8/8/8/PPPPPP1P/RNBQKBNR b KQq - 0 1";
        assertEquals(fen2, report.getFinalState().toFEN(1));
    }
    
    @Test
    public void testPromotion2(){
        String pgn =    "[Variant \"From Position\"]\n" +
                        "[FEN \"rnbqk3/ppppp1P1/8/8/8/8/PPPPPP1P/RNBQKBNR w KQq - 0 1\"]\n" +
                        "\n" +
                        "1. g8=N# *";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        
        assertEquals(1, reports.size());        
        
        GameReport report = reports.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());
        
        String fen2 = "rnbqk1N1/ppppp3/8/8/8/8/PPPPPP1P/RNBQKBNR b KQq - 0 1";
        assertEquals(fen2, report.getFinalState().toFEN(1));
    }    
    
    @Test
    public void testCastlingWhiteKingside(){
        String pgn =    "[Variant \"From Position\"]\n" +
                        "[FEN \"4k3/8/8/8/8/8/8/R3K2R w KQ - 0 1\"]\n" +
                        "\n" +
                        "1. O-O *";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        
        assertEquals(1, reports.size());        
        
        GameReport report = reports.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());
        assertEquals(1, report.getMoveList().size());
        
        
        String fen2 = "4k3/8/8/8/8/8/8/R4RK1 b - - 1 1";
        assertEquals(fen2, report.getFinalState().toFEN(1));        
    }
    @Test
    public void testCastlingWhiteQueenside(){
        String pgn =    "[Variant \"From Position\"]\n" +
                        "[FEN \"4k3/8/8/8/8/8/8/R3K2R w KQ - 0 1\"]\n" +
                        "\n" +
                        "1. O-O-O *";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        
        assertEquals(1, reports.size());        
        
        GameReport report = reports.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());
        assertEquals(1, report.getMoveList().size());        
        
        String fen2 = "4k3/8/8/8/8/8/8/2KR3R b - - 1 1";
        assertEquals(fen2, report.getFinalState().toFEN(1));        
    }    
    
    @Test
    public void testCastlingBlackKingside(){
        String pgn =    "[Variant \"From Position\"]\n" +
                        "[FEN \"r3k2r/8/8/8/8/8/8/4K3 b kq - 0 1\"]\n" +
                        "\n" +
                        "1. O-O *";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        
        assertEquals(1, reports.size());        
        
        GameReport report = reports.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());
        assertEquals(1, report.getMoveList().size());        
        
        String fen2 = "r4rk1/8/8/8/8/8/8/4K3 w - - 1 2";
        assertEquals(fen2, report.getFinalState().toFEN(2));        
    }
    @Test
    public void testCastlingBlackQueenside(){
        String pgn =    "[Variant \"From Position\"]\n" +
                        "[FEN \"r3k2r/8/8/8/8/8/8/4K3 b kq - 0 1\"]\n" +
                        "\n" +
                        "1. O-O-O *";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        
        assertEquals(1, reports.size());        
        
        GameReport report = reports.get(0);
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());
        assertEquals(1, report.getMoveList().size());        
        
        String fen2 = "2kr3r/8/8/8/8/8/8/4K3 w - - 1 2";
        assertEquals(fen2, report.getFinalState().toFEN(2));        
    }       
}
