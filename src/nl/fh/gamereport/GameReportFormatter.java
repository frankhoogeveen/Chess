/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport;

/**
 * Takes a game report and produces a string representing the game report
 */
public interface GameReportFormatter {
    
    /**
     * 
     * @param report
     * @return the formatted report
     */
    public String formatGame(GameReport report);

}
