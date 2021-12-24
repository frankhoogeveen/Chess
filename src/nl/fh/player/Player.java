/*
 * License: GPL v3
 * 
 */
package nl.fh.player;

import java.util.Set;
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
     * @param legalMoves the set of legal moves
     * @return a move  without time control either from the set of legal moves,
     * or a resignation.
     * 
     * The move returned should be one of the legal moves
     */
    public Move getMove(GameState currentState, Set<Move> legalMoves);
    
    /**
     * 
     * @return a one-line description of the player 
     */
    public String getDescription();

}
