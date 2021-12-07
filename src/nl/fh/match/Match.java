/*
 * License: GPL v3
 * 
 */

package nl.fh.match;

import java.util.List;
import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;

/**
 * 
 * 
 */
public interface Match {

    /**
     * 
     * @param filter 
     * 
     * Sets the filter that specifies which games of the match are stored
     */
    public void setFilter(GameFilter filter);
    
    /**
     * 
     * @return the outcome a match 
     */
    public MatchResult play();
    
    /**
     * @return a list of game reports. Which reports are included in this list
     * is controlled by setFilter();
     */
    public List<GameReport> getReports();
    

}