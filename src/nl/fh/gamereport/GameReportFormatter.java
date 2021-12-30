/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport;

import nl.fh.gamestate.GameState;

/**
 * Takes a game report and produces a string representing the game report
 */
public interface GameReportFormatter<S extends GameState> {
    
    /**
     * 
     * @param report
     * @return the formatted report
     */
    public String formatGame(GameReport<S> report);

}
