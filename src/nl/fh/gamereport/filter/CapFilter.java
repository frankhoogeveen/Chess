/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport.filter;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;

/**
 * Caps the number of games that is retained
 * 
 */
public class CapFilter implements GameFilter{

    private final int cap;
    private int count;
    private final GameFilter filter;


    public CapFilter(int cap, GameFilter filter){
        this.cap = cap;
        this.count = 0;
        this.filter = filter;
    }
    
    @Override
    public boolean retain(GameReport report) {
        count += 1;
        if(count > cap){
            return false;
        }
        return true;
    } 
}

