/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate;

/**
 * 
 * 
 */
public interface StateFormatter<S extends GameState> {
    
    /**
     * 
     * @param state
     * @return a string that describes the state
     */
    public String format(S state);    
    
    /**
     * 
     * @param state
     * @param nMove the move number
     * @return a string that describes the state
     */
    public String format(S state, int nMove);        

}