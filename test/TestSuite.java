/*
 * License: GPL v3
 * 
 */
import nl.fh.chess.FieldTest;
import nl.fh.gamereport.GameReportTest;
import nl.fh.gamereport.MoveCodesTest;
import nl.fh.gamestate.GameStateTest;
import nl.fh.gamestate.tictactoe.TicTacToeArbiterTest;
import nl.fh.gamestate.tictactoe.TicTacToeMoveGeneratorTest;
import nl.fh.gamestate.tictactoe.TicTacToeMoveTest;
import nl.fh.gamestate.tictactoe.TicTacToeReportTest;
import nl.fh.gamestate.tictactoe.TicTacToeStateTest;
import nl.fh.integration_tests.MatchTest;
import nl.fh.integration_tests.MateInOneTest;
import nl.fh.integration_tests.PhilidorTest;
import nl.fh.metric.MaterialCountMetricTest;
import nl.fh.metric.negamax.NegaMaxAlphaBetaTest;
import nl.fh.metric.negamax.NegaMaxGen3Test;
import nl.fh.metric.negamax.NegaMaxMetricTest1;
import nl.fh.metric.negamax.NegaMaxMetricTest2;
import nl.fh.metric.negamax.NegaMaxMetricTest3;
import nl.fh.metric.negamax.NegaMaxMetricTest4;
import nl.fh.parser.TolerantReaderTest;
import nl.fh.rules.CastlingRulesTest;
import nl.fh.rules.EnPassantRulesTest;
import nl.fh.rules.PromotionRulesTest;
import nl.fh.rules.SimpleRulesTest;
import nl.fh.rules.ShortGamesTest;
import nl.fh.rules.ThreeFoldRepetitionTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
    GameStateTest.class,
    GameReportTest.class,
    MoveCodesTest.class,
    FieldTest.class,
    TolerantReaderTest.class,
    SimpleRulesTest.class,
    ThreeFoldRepetitionTest.class,
    CastlingRulesTest.class,
    PromotionRulesTest.class,
    EnPassantRulesTest.class,
    ShortGamesTest.class,
    MaterialCountMetricTest.class,
    NegaMaxMetricTest1.class,
    NegaMaxMetricTest2.class,
    NegaMaxMetricTest3.class,
    NegaMaxMetricTest4.class,    
    NegaMaxAlphaBetaTest.class,   
    NegaMaxAlphaBetaTest.class,
    
    // the tic tac toe tests
    TicTacToeStateTest.class,
    TicTacToeMoveTest.class,
    TicTacToeMoveGeneratorTest.class,
    TicTacToeArbiterTest.class,
    TicTacToeReportTest.class,
    
    //
    // below this line are tests that read files or are otherwise expensive
    // and are therefore not really unit tests
    //
    
    PerftTest.class,         // several tests take hours and are commented out
    NegaMaxGen3Test.class,  
    MateInOneTest.class,
    PhilidorTest.class, 
    MatchTest.class
})

public class TestSuite {
    
}