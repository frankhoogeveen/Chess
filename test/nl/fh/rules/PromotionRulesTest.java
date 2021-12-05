/*
 * License: GPL v3
 * 
 */
package nl.fh.rules;

import nl.fh.gamestate.GameState;
import java.util.Set;
import nl.fh.move.Move;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 */
public class PromotionRulesTest {

    @Test
    public void testPromotionWhite(){
    String fen = "8/5P2/2k5/8/8/8/8/2K5 w - - 0 1";
    Rules rules = new SimpleRules();
    
    GameState state = GameState.fromFEN(fen);
    Set<Move> moves = rules.calculateAllLegalMoves(state);
    
    assertEquals(5+4, moves.size());
    }
    
    @Test
    public void testPromotionWhite2(){
    String fen = "4n1n1/5P2/2k5/8/8/8/8/2K5 w - - 0 1";
    Rules rules = new SimpleRules();
    
    GameState state = GameState.fromFEN(fen);
    Set<Move> moves = rules.calculateAllLegalMoves(state);
    
    assertEquals(5+3*4, moves.size());
    }    
    
    @Test
    public void testPromotionBlack(){
    String fen = "8/8/5k2/8/8/2K5/5p2/8 b - - 0 1";
    Rules rules = new SimpleRules();
    
    GameState state = GameState.fromFEN(fen);
    Set<Move> moves = rules.calculateAllLegalMoves(state);
    
    assertEquals(8+4, moves.size());         
    } 
    
    @Test
    public void testPromotionBlack2(){
    String fen = "8/8/5k2/8/8/2K5/5p2/4Nnn1 b - - 0 1";
    Rules rules = new SimpleRules();
    
    GameState state = GameState.fromFEN(fen);
    Set<Move> moves = rules.calculateAllLegalMoves(state);
    
    assertEquals(8+7+4, moves.size());         
    }       
}