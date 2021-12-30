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
public class RandomFilter implements GameFilter{

    private final double p;


    /**
     * 
     * @param p fraction retained (0 <= p <= 1) 
     */
    
    public RandomFilter(double p){
        this.p = p;
    }
    
    @Override
    public boolean retain(GameReport report) {
        if(Math.random() < p){
            return true;
        }
        return false;
    } 
}

