/*
 * License: GPL v3
 * 
 */
package nl.fh.gamestate;


/**
 *
 * @author frank
 */
public interface Move<S extends GameState> {

    /**
     * 
     * @param state
     * @return the game state after the move is applied
     * 
     *  Throws an IllegalArgumentException is the move cannot be executed,
     *  but does not necessarily check for legality of the move. E.g. a move 
     *  that leaves the king in check does not have to throw an exception. 
     * 
     * By calling this method, the parent field in the resulting GameState is 
     * set to state.
     * 
     */
    public S applyTo(S state);
}
