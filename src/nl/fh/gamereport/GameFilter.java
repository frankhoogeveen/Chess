/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport;

/**
 * This interface makes a selection of which game reports should be 
 * maintained for reporting purposes.
 */
public interface GameFilter {
    
    /**
     * 
     * @param state
     * @return true if the game state should be retained
     */
    public boolean retain(GameReport report);

}
