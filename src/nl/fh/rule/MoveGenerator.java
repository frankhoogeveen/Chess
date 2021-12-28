/*
 * License: GPL v3
 * 
 */

package nl.fh.rule;

import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;

/**
 * License GPL v3
 */
public interface MoveGenerator {
    
    /**
     * @param state
     * @return the set of all legal moves in the game state
     * 
     * This is an expensive method to call. 
     */
    public Set<Move> calculateAllLegalMoves(GameState state);
    
    /**
     * 
     * @param state
     * @return all states that are the result of applying legal moves to
     * the given state. 
     */
    public Set<GameState> calculateChildren(GameState state);

    
}
