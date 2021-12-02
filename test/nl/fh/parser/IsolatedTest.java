/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fh.parser;

import java.util.List;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author frank
 */
public class IsolatedTest {
    
    public IsolatedTest() {
    }
      
    @Test
    public void testPromotion(){
        String pgn =    "[Variant \"From Position\"]\n" +
                        "[FEN \"rnbqk3/ppppp1P1/8/8/8/8/PPPPPP1P/RNBQKBNR w KQq - 0 1\"]\n" +
                        "\n" +
                        "1. g8=Q# 1-0";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn);
        
        assertEquals(1, reports.size());        
        
        GameReport report = reports.get(0);
        assertEquals(GameResult.WIN_WHITE, report.getGameResult());
        
        String fen2 = "rnbqk1Q1/ppppp3/8/8/8/8/PPPPPP1P/RNBQKBNR b KQq - 0 1";
        assertEquals(fen2, report.getFinalState().toFEN());
    }
      
}
