/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate;

//TODO change to FIRST_MOVER SECOND_MOVER
import nl.fh.gamestate.chess.Color;

/**
 * Object that represent the state of a game board should implement this interface
 */
public interface GameState {
    
    //TODO make this work for Go y separating color and first/second mover
    /**
     * 
     * @return the player to move next ) 
     */
    public Color getColor(); 
    
    /**
     * 
     * @return a completely independent (deep) copy of this game state 
     */
    public GameState copy();

}
