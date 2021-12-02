/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fh.parser;

import java.util.List;
import nl.fh.gamereport.GameReport;
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
    public void testMultipleGames(){
        String pgn = "1.e4 e5 * \n\n1.e4 e5 \n2.Bc4 *";
                
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn);
        assertEquals(2, reports.size());
    } 
      
}
