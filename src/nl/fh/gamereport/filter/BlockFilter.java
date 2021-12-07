/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport.filter;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;

/**
 * Filter blocks all games
 * 
 */
public class BlockFilter implements GameFilter{

    public BlockFilter(){
    }
    
    @Override
    public boolean retain(GameReport report) {
        return false;
    } 
}

