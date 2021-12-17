/*
 * License: GPL v3
 * 
 */

package nl.fh;

import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.chess.PieceKind;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.move.Promotion;
import nl.fh.player.Player;
import nl.fh.player.pgn_replayer.PgnReplayer;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * 
 * 
 */
public class IsolatedTest {
    
    @Test
    public void testThreeFoldRepetition(){
        String pgn = "1. d4 d5 2. Bd2 Kd7 3. Bc1 Kc6 4. Bd2 Kd7 "
                + "5. Bc3 Ke6 6. Bd2 Kd7 7. Bc3 Ke8 8. Bd2 Kd7 "
                + "9. Nc3 Nf6 10. Nb1 Ng8 *";
        
        Rules rules = new SimpleRules();
        
        Player playerW = PgnReplayer.getInstance(pgn, Color.WHITE);
        Player playerB = PgnReplayer.getInstance(pgn, Color.BLACK);     
        
        GameReport report = rules.playGame(playerW, playerB);
        
        assertEquals(12, report.getMoveList().size());
        assertEquals(GameResult.DRAW_BY_THREEFOLD_REPETITION.toString(),
                     report.getTag("Result"));
        
        for(int i = 0; i < report.getStateList().size()-1; i++){
            assertFalse(rules.isThreeFoldRepetition(report.getStateList().get(i)));
        }
        
        for(int i = 0; i < report.getStateList().size(); i++){
            System.out.println(i);
            System.out.println(report.getStateList().get(i).toFEN());
            System.out.println(report.getStateList().get(i).countRepetitions());
            System.out.println();
        }
        
        System.out.println(pgn);
        
        assertTrue(rules.isThreeFoldRepetition(report.getStateList().get(12)));        
        
    }  

}