/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate;

/**
 * Object that represent the state of a game board should implement this interface
 */
public interface GameState {
    
    /**
     * 
     * @return the player to move next
     */
    public Mover getMover(); 
    
    /**
     * 
     * @return a completely independent (deep) copy of this game state 
     */
    public GameState copy();

}
