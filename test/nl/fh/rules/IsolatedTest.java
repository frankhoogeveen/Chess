/*
 * License: GPL v3
 * 
 */

package nl.fh.rules;

import nl.fh.chess.Color;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.GameState;
import nl.fh.player.Player;
import nl.fh.player.pgn_replayer.PgnReplayer;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class IsolatedTest {
    
    private static final Rules rules = new SimpleRules();   
    
    @Test
    public void testThreeFoldRepetitionCastling(){
        String pgn = "[FEN \"r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1\"]"
                   + "\n"
                   + "1. Rg1 Rg8 2. Rh1 Rh8 3. Rg1 Rg8 4. Rh1 Rh8 "
                   + "5. Rg1 Rg8 6. Rh1 Rh8 7. Rg1 Rg8 8. Rh1 Rh8 "
                   + "9. Rg1 Rg8 10. Rh1 Rh8 11. Rg1 Rg8 12. Rh1 Rh8 *";
        
        GameState initial = GameState.fromFEN("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1", rules);
        
        Player playerW = PgnReplayer.getInstance(pgn, Color.WHITE);
        Player playerB = PgnReplayer.getInstance(pgn, Color.BLACK);     
        
        GameReport report = rules.playGame(playerW, playerB, initial);
        
        
        System.out.println(pgn);
        System.out.println();
        System.out.println(report.toPGN(rules));
        System.out.println();
        for(int i = 0; i <  report.getMoveList().size(); i++){
            System.out.println("\n" + Integer.toString(i));
            System.out.println(report.getStateList().get(i).toFEN());            
            System.out.println(report.getMoveList().get(i).toString());

        }
        
        System.out.println(report.getStateList().get(report.getStateList().size()-1).toFEN());
        
        assertEquals(10, report.getMoveList().size());
        assertEquals(GameResult.DRAW_BY_THREEFOLD_REPETITION.toString(),
                     report.getTag("Result"));
    } 

}