/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport.filter;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;

/**
 * Makes combination of filters using boolean AND
 * 
 */
public class AndFilter implements GameFilter{

    private final GameFilter[] filters;

    public AndFilter(GameFilter... filters){
        this.filters = filters;
    }
    
    @Override
    public boolean retain(GameReport report) {
        for(GameFilter f : this.filters){
            if(!f.retain(report)){
                return false;
            }
        }
        return true;
    } 
}

