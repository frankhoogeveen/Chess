/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport.filter;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;

/**
 * Filter allows all games to pass through
 * 
 */
public class TransparentFilter implements GameFilter{

    public TransparentFilter(){
    }
    
    @Override
    public boolean retain(GameReport report) {
        return true;
    } 
}

