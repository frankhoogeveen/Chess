/*
 * License: GPL v3
 * 
 */

package nl.fh.rule;

import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.gamestate.Move;

/**
 * License GPL v3
 */
public interface MoveGenerator<S extends GameState> {
    
    /**
     * @param state
     * @return the set of all legal moves in the game state
     * 
     * This is an expensive method to call. 
     */
    public Set<Move<S>> calculateAllLegalMoves(S state);
    
    /**
     * 
     * @param state
     * @return all states that are the result of applying legal moves to
     * the given state. 
     * This is an expensive method to call.
     */
    public Set<S> calculateChildren(S state);

    
}
