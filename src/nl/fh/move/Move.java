/*
 * License: GPL v3
 * 
 */
package nl.fh.move;

import nl.fh.rule.ChessResultArbiter;
import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;


/**
 *
 * @author frank
 */
public interface Move {

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
    public GameState applyTo(GameState state);
}
