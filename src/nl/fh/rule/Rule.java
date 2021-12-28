/*
 * License: GPL v3
 * 
 */

package nl.fh.rule;

import nl.fh.gamestate.GameState;

/**
 * Interface that is implemented by objects that represent a specific set of rules
 */
public interface Rule {
   
    
    public GameState getInitialState();
    
    public GameDriver getGameDriver();
    
}
