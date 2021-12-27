/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport.filter;

import nl.fh.gamereport.GameFilter;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.ChessGameResult;

/**
 * Filters a (set of) game results
 * 
 */
public class ResultFilter implements GameFilter{

    private final ChessGameResult[] results;

    public ResultFilter(ChessGameResult... results){
        this.results = results;
    }
    
    @Override
    public boolean retain(GameReport report) {
        for(ChessGameResult f : this.results){
            if(f == report.getGameResult()){
                return true;
            }
        }
        return false;
    } 
}

