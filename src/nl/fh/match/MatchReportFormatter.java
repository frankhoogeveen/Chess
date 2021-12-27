/*
 * License: GPL v3
 * 
 */

package nl.fh.match;

/**
 * 
 * 
 */
public interface MatchReportFormatter {
    
    /**
     * 
     * @param report
     * @return a formatted report of the match 
     */
    public String formatMatch(MatchReport report);

}