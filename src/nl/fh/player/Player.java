/*
 * License: GPL v3
 * 
 */
package nl.fh.player;

import nl.fh.gamestate.GameState;
import nl.fh.move.Move;

/**
 * Objects displaying this interface can play games by interacting with a 
 * proper game driver.
 */
public interface Player {

   
    /**
     * 
     * @param currentState
     * @param legalMoves
     * @return a move  without time control
     */
    public Move getMove(GameState currentState);
    
    /**
     * 
     * @return a one-line description of the player 
     */
    public String getDescription();

}
