package nl.fh.rules;

import java.util.List;
import nl.fh.gamereport.GameReport;
import nl.fh.gamestate.GameState;
import nl.fh.parser.PGN_Reader;
import nl.fh.parser.TolerantReader;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * copyright F. Hoogeveen
 * @author frank
 */
public class ShortGamesTest {
    @Test
    public void testPawnCapture(){
        String pgn = "1. Nf3 Nc6 2. Ne5 Nb8 3. Ng6 fxg6 *";
        String target = "rnbqkbnr/ppppp1pp/6p1/8/8/8/PPPPPPPP/RNBQKB1R w KQkq - 0 4";
        
        PGN_Reader parser = new TolerantReader();
        GameReport report = parser.getGames(pgn).get(0);
        
        List<GameState> list = report.getStateList();
        GameState end = list.get(list.size()-1);
        String endFEN = end.toFEN();
        
        assertEquals(target, endFEN);
    }
    
    @Test
    public void testPawnCapture2(){
        String pgn = "1. e4 d5 2. exd5 *";
        String target = "rnbqkbnr/ppp1pppp/8/3P4/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2";
        
        PGN_Reader parser = new TolerantReader();
        GameReport report = parser.getGames(pgn).get(0);
        
        List<GameState> list = report.getStateList();
        GameState end = list.get(list.size()-1);
        String endFEN = end.toFEN();
        
//        System.out.println(target);
//        System.out.println(endFEN);
        
        assertEquals(target, endFEN);
    }    
}
