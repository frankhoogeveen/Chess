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
import nl.fh.gamereport.ChessGameResult;
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
public class ThreeFoldRepetitionTest {
    
    private GameDriver gameDriver = FIDEchess.getGameDriver();
    private MoveGenerator moveGenerator = gameDriver.getMoveGenerator();   
    private ChessResultArbiter arbiter = (ChessResultArbiter) gameDriver.getResultArbiter();
    
    @Test
    public void testRepetition(){
        String fen1 = "rnbq1bnr/pppkpppp/8/3p4/3P4/8/PPPBPPPP/RN1QKBNR w KQ - 2 3";
        String fen2 = "rnbq1bnr/pppkpppp/8/3p4/3P4/8/PPPBPPPP/RN1QKBNR w KQ - 6 5";
        GameState state1 = GameState.fromFEN(fen1);
        GameState state2 = GameState.fromFEN(fen2);
        
        assertTrue(arbiter.repeats(state1, state2));
    }  
    
    @Test
    public void testRepetition2(){
        // This case shows equal states. At first sight, there
        // is no repetition since the en passant flag is raised 
        // in one case and not in the other. However, e.p. is 
        // illegal due to a pin. Therefore both the locations
        // of the pieces on the board and the allowed moves are the same.
        // According to rule 9.2 FIDE Laws of Chess, these are repeating positions
        String fen1 = "7k/8/8/K1pP3r/8/8/8/8 w - - 0 2";
        String fen2 = "7k/8/8/K1pP3r/8/8/8/8 w - c6 0 2";
        GameState state1 = GameState.fromFEN(fen1);
        GameState state2 = GameState.fromFEN(fen2);
        
        assertTrue(arbiter.repeats(state1, state2));
    }     
    
    @Test
    public void testThreefoldRepetitionManually(){
        GameReport report = new GameReport();
        GameState state = FIDEchess.getInitialState();
        
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
        
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());
        
        state = state.apply(Ng8);
        report.addPly(Ng8, state);
        assertTrue(arbiter.isThreeFoldRepetition(report));    
        
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());               
        Set<Move> legalMoves = moveGenerator.calculateAllLegalMoves(state);
        ChessGameResult result = arbiter.determineResult(report, legalMoves);
        
        // the arbiter determines the result, but does not change the report
        assertEquals(ChessGameResult.UNDECIDED, report.getGameResult());          
        assertEquals(ChessGameResult.DRAW_BY_THREEFOLD_REPETITION, result);       
        report.setResult(result);
        
        assertEquals(ChessGameResult.DRAW_BY_THREEFOLD_REPETITION, report.getGameResult());        
        
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
        assertEquals(ChessGameResult.DRAW_BY_THREEFOLD_REPETITION.toString(),
                     report.getTag("Result"));
        
        assertTrue(arbiter.isThreeFoldRepetition(report));        
        
    }

    @Test
    public void testThreeFoldRepetitionCastling(){
        String pgn = "[FEN \"r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1\"]"
                   + "\n"
                   + "1. Rg1 Rg8 2. Rh1 Rh8 3. Rg1 Rg8 4. Rh1 Rh8 "
                   + "5. Rg1 Rg8 6. Rh1 Rh8 7. Rg1 Rg8 8. Rh1 Rh8 "
                   + "9. Rg1 Rg8 10. Rh1 Rh8 11. Rg1 Rg8 12. Rh1 Rh8 *";
        
        GameState initial = GameState.fromFEN("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1");
        
        Player playerW = PgnReplayer.getInstance(pgn, Color.WHITE);
        Player playerB = PgnReplayer.getInstance(pgn, Color.BLACK);     
        
        GameReport report = gameDriver.playGame(playerW, playerB, initial);
//        
//        
//        System.out.println(pgn);
//        System.out.println();
//        System.out.println(report.toPGN(rules));
//        System.out.println();
//        for(int i = 0; i <  report.getMoveList().size(); i++){
//            System.out.println("\n" + Integer.toString(i));
//            System.out.println(report.getStateList().get(i).toFEN());            
//            System.out.println(report.getMoveList().get(i).toString());
//
//        }
//        
//        System.out.println(report.getStateList().get(report.getStateList().size()-1).toFEN());
        
        assertEquals(10, report.getMoveList().size());
        assertEquals(ChessGameResult.DRAW_BY_THREEFOLD_REPETITION.toString(),
                     report.getTag("Result"));
    } 
    
    @Test
    public void testThreeFoldRepetitionEnPassant(){
        String pgn = "[FEN \"k7/8/1r6/2R5/8/8/4P3/K7 w - - 0 1\"]"
                   + "\n"
                   + "1. e4 Rb4 2. Rc7 Rb6 3. Rc5 Rb4 4. Rc7 Rb6 "
                   + "5. Rc5 Rb4 6. Rc7 Rb6 7. Rc5 Rb4 8. Rc7 Rb6 "
                   + "10. Rc7 Rb6 11. Rc5 Rb4 12. Rc7 Rb6 13. Rc5 Rb4 "
                   + "14. Rc7 *";
        
        GameState initial = GameState.fromFEN("k7/8/1r6/2R5/8/8/4P3/K7 w - - 0 1");
        
        Player playerW = PgnReplayer.getInstance(pgn, Color.WHITE);
        Player playerB = PgnReplayer.getInstance(pgn, Color.BLACK);     
        
        GameReport report = gameDriver.playGame(playerW, playerB, initial);
        
//        System.out.println(pgn);
//        System.out.println();
//        System.out.println(report.toPGN(rules));
//        System.out.println();
//        for(int i = 0; i <  report.getMoveList().size(); i++){
//            System.out.println("\n" + Integer.toString(i));
//            System.out.println(report.getStateList().get(i).toFEN());            
//            System.out.println(report.getMoveList().get(i).toString());
//
//        }
//        
//        System.out.println(report.getStateList().get(report.getStateList().size()-1).toFEN());
        
        assertEquals(9, report.getMoveList().size());
        assertEquals(ChessGameResult.DRAW_BY_THREEFOLD_REPETITION.toString(),
                     report.getTag("Result"));
    } 
    
    @Test
    /**
     * test for three fold repetition where en passant plays a role.
     * Since there is now a pawn that can capture en passant, we have
     * to play one more ply to have a three fold repetition.
     */
    public void testThreeFoldRepetitionEnPassant2(){
        String pgn = "[FEN \"k7/8/1r6/2R5/5p2/8/4P3/K7 w - - 0 1\"]"
                   + "\n"
                   + "1. e4 Rb4 2. Rc7 Rb6 3. Rc5 Rb4 4. Rc7 Rb6 "
                   + "5. Rc5 Rb4 6. Rc7 Rb6 7. Rc5 Rb4 8. Rc7 Rb6 "
                   + "10. Rc7 Rb6 11. Rc5 Rb4 12. Rc7 Rb6 13. Rc5 Rb4 "
                   + "14. Rc7 *";
        
        GameState initial = GameState.fromFEN("k7/8/1r6/2R5/5p2/8/4P3/K7 w - - 0 1");
        
        Player playerW = PgnReplayer.getInstance(pgn, Color.WHITE);
        Player playerB = PgnReplayer.getInstance(pgn, Color.BLACK);     
        
        GameReport report = gameDriver.playGame(playerW, playerB, initial);
//        
//        System.out.println(pgn);
//        System.out.println();
//        System.out.println(report.toPGN(rules));
//        System.out.println();
//        for(int i = 0; i <  report.getMoveList().size(); i++){
//            System.out.println("\n" + Integer.toString(i));
//            System.out.println(report.getStateList().get(i).toFEN());            
//            System.out.println(report.getMoveList().get(i).toString());
//
//        }
        
//        System.out.println(report.getStateList().get(report.getStateList().size()-1).toFEN());
        
        assertEquals(10, report.getMoveList().size());
        assertEquals(ChessGameResult.DRAW_BY_THREEFOLD_REPETITION.toString(),
                     report.getTag("Result"));
    }       
    
    /**
     * Yet again there is a pawn to could in principle capture en passant,
     * but not in practice since it is pinned to the king.
     * 
     */
    @Test
    public void testThreeFoldRepetitionEnPassant3(){
        String pgn = "[FEN \"5R2/8/1r6/2R5/5p2/8/4P3/K4k2 w - - 0 1\"]"
                   + "\n\n"
                   + "1. e4 Rb4 2. Rc7 Rb6 3. Rc5 Rb4 4. Rc7 Rb6 "
                   + "5. Rc5 Rb4 6. Rc7 Rb6 7. Rc5 Rb4 8. Rc7 Rb6 "
                   + "9. Rc4 Rb4 10. Rc7 Rb6 11. Rc5 Rb4 12. Rc7 Rb6 13. Rc5 Rb4 "
                   + "14. Rc7 "
                   + "*";
        
        GameState initial = GameState.fromFEN("5R2/8/1r6/2R5/5p2/8/4P3/K4k2 w - - 0 1");
        
        Player playerW = PgnReplayer.getInstance(pgn, nl.fh.chess.Color.WHITE);
        Player playerB = PgnReplayer.getInstance(pgn, nl.fh.chess.Color.BLACK);     
        
        GameReport report = gameDriver.playGame(playerW, playerB, initial);
        
//        System.out.println(pgn);
//        System.out.println();
//        System.out.println(report.toPGN(rules));
//        System.out.println();
//        for(int i = 0; i <  report.getMoveList().size(); i++){
//            System.out.println("\n" + Integer.toString(i));
//            System.out.println(report.getStateList().get(i).toFEN());            
//            System.out.println(report.getMoveList().get(i).toString());
//        }
//        
//        System.out.println(report.getStateList().get(report.getStateList().size()-1).toFEN());
        
        assertEquals(9, report.getMoveList().size());
        assertEquals(ChessGameResult.DRAW_BY_THREEFOLD_REPETITION.toString(),
                     report.getTag("Result"));
    }   

}