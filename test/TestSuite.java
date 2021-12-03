/*
 * License: GPL v3
 * 
 */
import nl.fh.chess.FieldTest;
import nl.fh.gamereport.GameReportTest;
import nl.fh.gamestate.GameStateTest;
import nl.fh.integration_tests.PhilidorTest;
import nl.fh.parser.TolerantReaderTest;
import nl.fh.rules.CastlingRulesTest;
import nl.fh.rules.EnPassantRulesTest;
import nl.fh.rules.PromotionRulesTest;
import nl.fh.rules.RulesTest;
import nl.fh.rules.ShortGamesTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
    GameStateTest.class,
    GameReportTest.class,
    FieldTest.class,
    TolerantReaderTest.class,
    RulesTest.class,
    CastlingRulesTest.class,
    PromotionRulesTest.class,
    EnPassantRulesTest.class,
    ShortGamesTest.class,
    
    //
    // below this line are tests that read files and are therefore not really
    // unit tests
    //
    PhilidorTest.class
    
})

public class TestSuite {
    
}