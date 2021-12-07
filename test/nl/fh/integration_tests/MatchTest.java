/*
 * License: GPL v3
 * 
 */

package nl.fh.integration_tests;

import java.util.List;
import static java.util.Locale.filter;
import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.filter.CapFilter;
import nl.fh.gamereport.filter.NotFilter;
import nl.fh.gamereport.filter.OrFilter;
import nl.fh.gamereport.filter.TransparentFilter;
import nl.fh.gamereport.filter.WinnerFilter;
import nl.fh.match.AlternatingMatch;
import nl.fh.match.Match;
import nl.fh.match.MatchResult;
import nl.fh.metric.Shannon;
import nl.fh.parser.PGN_Reader;
import nl.fh.parser.TolerantReader;
import nl.fh.player.Player;
import nl.fh.player.evalplayer.MetricPlayer;
import nl.fh.player.random.RandomPlayer;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class MatchTest {
    @Test 
    /**
     * Tests the playing of a match, the conversion to pgn and the legibility
     * of the pgn
     */
    public void testMatch(){
        Rules rules = new SimpleRules();
        
        Player playerR = new RandomPlayer();
        Player playerM = MetricPlayer.getInstance(new Shannon());
        
        int nGames = 3;     
        GameFilter filter = new TransparentFilter();
        Match match = new AlternatingMatch(nGames, rules);
        
        MatchResult result = match.play(playerR, playerM, filter);
        String pgn = result.toPGN();
        
        PGN_Reader reader = new TolerantReader();
        List<GameReport> result2 = reader.getGames(pgn, rules);
        
        assertEquals(nGames, result2.size());
    }

}