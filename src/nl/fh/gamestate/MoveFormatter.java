/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate;

import nl.fh.gamestate.GameState;
import nl.fh.gamestate.Move;
import nl.fh.rule.GameDriver;

/**
 * 
 * 
 */
public interface MoveFormatter<S extends GameState> {
    
    /**
     * 
     * @param move
     * @param state
     * @param driver
     * @return a string that describes move in the context of state and driver
     */
    public String format(Move<S> move, S state, GameDriver<S> driver);

}