/*
 * License: GPL v3
 * 
 */
package nl.fh.integration_tests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import nl.fh.gamereport.GameReport;
import nl.fh.parser.PGN_Reader;
import nl.fh.parser.TolerantReader;
import nl.fh.rules.FIDEchess;
import nl.fh.rules.Rules;
import nl.fh.rules.ChessMoveGenerator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class PhilidorTest {
      
    @Test
    public void testPhilidor(){
        // read a pgn file
        String filePath = "./pgn/Philidor.pgn";
        StringBuilder sb = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
                stream.forEach(s -> sb.append(s).append("\n"));
        }
        catch (IOException e){}
        String pgn = sb.toString();
        assertTrue(pgn.length() > 0);
                
        // process the pgn file
        PGN_Reader parser = new TolerantReader();
        List<GameReport> reports = parser.getGames(pgn, FIDEchess.getGameDriver());
        assertEquals(6, reports.size());
                
    }   
}
