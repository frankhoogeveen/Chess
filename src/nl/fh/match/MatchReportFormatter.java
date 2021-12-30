/*
 * License: GPL v3
 * 
 */

package nl.fh.match;

import nl.fh.gamestate.GameState;

/**
 * 
 * 
 */
public interface MatchReportFormatter<S extends GameState> {
    
    /**
     * 
     * @param report
     * @return a formatted report of the match 
     */
    public String formatMatch(MatchReport<S> report);

}