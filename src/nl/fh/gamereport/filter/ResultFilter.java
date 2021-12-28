/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport.filter;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;

/**
 * Filters a (set of) game results
 * 
 */
public class ResultFilter implements GameFilter{

    private final GameResult[] results;

    public ResultFilter(GameResult... results){
        this.results = results;
    }
    
    @Override
    public boolean retain(GameReport report) {
        for(GameResult f : this.results){
            if(f == report.getGameResult()){
                return true;
            }
        }
        return false;
    } 
}

