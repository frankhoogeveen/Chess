/*
 * License: GPL v3
 * 
 */

package nl.fh.rules;

import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;

/**
 * License GPL v3
 */
public interface MoveGenerator {
    
    /**
     * 
     * @return the initial state of the game
     * The initial state does not have to be deterministic. It might be
     * random. Repeated calls to this method may return different values.
     */
    public GameState getInitialState();

    /**
     * 
     * @param move
     * @param state
     * @return true if the move is legal in the given game state 
     */
    public boolean isLegalMove(Move move, GameState state);
    
    /**
     * @param state
     * @return the set of all legal moves in the game state
     * 
     * This is an expensive method to call. It is encouraged to 
     * retrieve the legal moves from the GameState which is buffering
     * previously calculated move sets.
     */
    public Set<Move> calculateAllLegalMoves(GameState state);    

}
