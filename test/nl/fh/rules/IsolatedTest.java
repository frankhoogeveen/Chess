/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fh.rules;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.fh.chess.Field;
import nl.fh.chess.PieceType;
import nl.fh.gamereport.GameReport;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
import nl.fh.move.Promotion;
import nl.fh.parser.PGN_Reader;
import nl.fh.parser.TolerantReader;
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
    public void testEnPassantCapture1(){
        String pgn = "1. e3 Na6 2. e4 Nb8 3. e5 d5 4. exd6 *";
        String target = "rnbqkbnr/ppp1pppp/3P4/8/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 4";
        
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn);
        assertEquals(1, reports.size());
        
        GameReport report = reports.get(0);
        List<GameState> list = report.getStateList();
        GameState end = list.get(list.size()-1);
        String endFEN = end.toFEN();
        
        assertEquals(target, endFEN);
    }   
}
