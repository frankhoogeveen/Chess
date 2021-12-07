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
public class OrFilter implements GameFilter{

    private final GameFilter[] filters;

    public OrFilter(GameFilter... filters){
        this.filters = filters;
    }
    
    @Override
    public boolean retain(GameReport report) {
        for(GameFilter f : this.filters){
            if(f.retain(report)){
                return true;
            }
        }
        return false;
    } 
}

