/*
 * License: GPL v3
 * 
 */
package nl.fh.rules;

import nl.fh.rule.chess.FIDEchess;
import nl.fh.rule.MoveGenerator;
import nl.fh.rule.GameDriver;
import nl.fh.gamestate.chess.ChessState;
import java.util.List;
import nl.fh.gamereport.GameReport;
import nl.fh.gamestate.StateFormatter;
import nl.fh.gamestate.chess.format.FENformatter;
import nl.fh.gamestate.chess.parser.PGN_Reader;
import nl.fh.gamestate.chess.parser.TolerantReader;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 */
public class ShortGamesTest {
    
    private GameDriver gameDriver = FIDEchess.getGameDriver();
    private MoveGenerator moveGenerator = gameDriver.getMoveGenerator();   
    private StateFormatter formatter = new FENformatter();      
      
    @Test
    public void testPawnCapture(){
        String pgn = "1. Nf3 Nc6 2. Ne5 Nb8 3. Ng6 fxg6 *";
        String target = "rnbqkbnr/ppppp1pp/6p1/8/8/8/PPPPPPPP/RNBQKB1R w KQkq - 0 4";
        
        PGN_Reader parser = new TolerantReader();
        
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        assertEquals(1, reports.size());
        GameReport report = reports.get(0);
        
        List<ChessState> list = report.getStateList();
        ChessState end = list.get(list.size()-1);
        String endFEN = formatter.format(end, 4);
        
        assertEquals(target, endFEN);
    }
    
    @Test
    public void testPawnCapture2(){
        String pgn = "1. e4 d5 2. exd5 *";
        String target = "rnbqkbnr/ppp1pppp/8/3P4/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        assertEquals(1, reports.size());
        GameReport report = reports.get(0);
        
        List<ChessState> list = report.getStateList();
        ChessState end = list.get(list.size()-1);
        String endFEN = formatter.format(end,2);
        
        assertEquals(target, endFEN);
    }  
    
    @Test
    public void testEnPassantCapture1(){
        String pgn = "1. e3 Na6 2. e4 Nb8 3. e5 d5 4. exd6 *";
        String target = "rnbqkbnr/ppp1pppp/3P4/8/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 4";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, gameDriver);
        assertEquals(1, reports.size());
        
        GameReport report = reports.get(0);
        List<ChessState> list = report.getStateList();
        ChessState end = list.get(list.size()-1);
        String endFEN = formatter.format(end,4);
        
        assertEquals(target, endFEN);
    }        
}
