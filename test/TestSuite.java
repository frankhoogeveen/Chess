/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * copyright F. Hoogeveen
 * @author frank
 */

import nl.fh.chess.FieldTest;
import nl.fh.gamereport.GameReportTest;
import nl.fh.gamestate.GameStateTest;
import nl.fh.integration_tests.PhilidorTest;
import nl.fh.parser.TolerantReaderTest;
import nl.fh.rules.CastlingRulesTest;
import nl.fh.rules.RulesTest;
import nl.fh.rules.ShortGamesTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author frank
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
    GameStateTest.class,
    GameReportTest.class,
    FieldTest.class,
    TolerantReaderTest.class,
    RulesTest.class,
    CastlingRulesTest.class,
    ShortGamesTest.class,
    
    //
    // below this line are tests that read files and are therefore not really
    // unit tests
    //
    PhilidorTest.class
    
})

public class TestSuite {
    
}


//TODO 
// the special types of moves e.p. castling, promotion
// make the PGN reader deal with illegal moves
// make the PGN reader deal with syntacticly incorrect pgn files
//