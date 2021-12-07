/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport.filter;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;

/**
 * Makes combination of filters using boolean OR
 * 
 */
public class NotFilter implements GameFilter{

    private final GameFilter filter;

    public NotFilter(GameFilter filter){
        this.filter = filter;
    }
    
    @Override
    public boolean retain(GameReport report) {
        return !this.filter.retain(report);
    } 
}

