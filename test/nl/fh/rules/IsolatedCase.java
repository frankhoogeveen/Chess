/*
 * License: GPL v3
 * 
 */

package nl.fh.rules;

import java.util.Set;
import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.player.pgn_replayer.PgnReplayer;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.GameState;
import nl.fh.move.ChessMove;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
import nl.fh.player.Player;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * 
 * 
 */
public class IsolatedCase {
    
    private GameDriver gameDriver = Chess.getGameDriver();
    private MoveGenerator moveGenerator = gameDriver.getMoveGenerator();   
    private ChessResultArbiter arbiter = (ChessResultArbiter) gameDriver.getResultArbiter(); 
    
    @Test
    public void testThreefoldRepetitionManually(){
        GameReport report = new GameReport();
        GameState state = Chess.getInitialState();
        
        Field f3 = Field.getInstance("f3");
        Field f6 = Field.getInstance("f6");
        Field g1 = Field.getInstance("g1");
        Field g8 = Field.getInstance("g8");        
        
        ChessMove Nf3 = PieceMove.getInstance(g1, f3);
        ChessMove Nf6 = PieceMove.getInstance(g8, f6);
        ChessMove Ng1 = PieceMove.getInstance(f3, g1);
        ChessMove Ng8 = PieceMove.getInstance(f6, g8);        
                
        report.addGameState(state);
        assertFalse(arbiter.isThreeFoldRepetition(report));
        
        state = state.apply(Nf3);
        report.addPly(Nf3, state);
        assertFalse(arbiter.isThreeFoldRepetition(report));
        
        state = state.apply(Nf6);
        report.addPly(Nf6, state);
        assertFalse(arbiter.isThreeFoldRepetition(report));
        
        state = state.apply(Ng1);
        report.addPly(Ng1, state);
        assertFalse(arbiter.isThreeFoldRepetition(report));
        
        state = state.apply(Ng8);
        report.addPly(Ng8, state);
        assertFalse(arbiter.isThreeFoldRepetition(report));
        
        state = state.apply(Nf3);
        report.addPly(Nf3, state);
        assertFalse(arbiter.isThreeFoldRepetition(report));
        
        state = state.apply(Nf6);
        report.addPly(Nf6, state);
        assertFalse(arbiter.isThreeFoldRepetition(report));
        
        state = state.apply(Ng1);
        report.addPly(Ng1, state);
        assertFalse(arbiter.isThreeFoldRepetition(report));
        
        assertEquals(GameResult.UNDECIDED, report.getGameResult());
        
        state = state.apply(Ng8);
        report.addPly(Ng8, state);
        assertTrue(arbiter.isThreeFoldRepetition(report));    
        
        assertEquals(GameResult.UNDECIDED, report.getGameResult());               
        Set<Move> legalMoves = moveGenerator.calculateAllLegalMoves(state);
        GameResult result = arbiter.determineResult(report, legalMoves);
        
        // the arbiter determines the result, but does not change the report
        assertEquals(GameResult.UNDECIDED, report.getGameResult());          
        assertEquals(GameResult.DRAW_BY_THREEFOLD_REPETITION, result);       
        report.setResult(result);
        
        assertEquals(GameResult.DRAW_BY_THREEFOLD_REPETITION, report.getGameResult());        
        
    }
    
    
    @Test
    public void testThreeFoldRepetition(){
        String pgn = "1. d4 d5 2. Bd2 Kd7 3. Bc1 Kc6 4. Bd2 Kd7 "
                + "5. Bc3 Ke6 6. Bd2 Kd7 7. Bc3 Ke8 8. Bd2 Kd7 "
                + "9. Nc3 Nf6 10. Nb1 Ng8 *";
        
        Player playerW = PgnReplayer.getInstance(pgn, Color.WHITE);
        Player playerB = PgnReplayer.getInstance(pgn, Color.BLACK);     
        
        GameReport report = gameDriver.playGame(playerW, playerB);
        
        assertEquals(12, report.getMoveList().size());
        assertEquals(GameResult.DRAW_BY_THREEFOLD_REPETITION.toString(),
                     report.getTag("Result"));
        
        assertTrue(arbiter.isThreeFoldRepetition(report));        
        
    }

 
}