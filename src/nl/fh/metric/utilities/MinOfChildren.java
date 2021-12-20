/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.utilities;

import nl.fh.chess.Color;
import nl.fh.metric.minimax.Parent;
import nl.fh.player.evalplayer.Metric;

/**
 * Wrapping this around a given baseMetric, it goes one level deep into the 
 * rabbit hole and evaluates the min of the base metric over the children.
 * 
 */
public class MinOfChildren<T extends Parent<T>> implements Metric<T>{

    private final Metric<T> baseMetric;
    
    public MinOfChildren(Metric<T> baseMetric){
     this.baseMetric = baseMetric;
    }

    @Override
    public double eval(T t) {
        double currentValue = +Double.MAX_VALUE;
        for(T child : t.getChildren()){
            double value = baseMetric.eval(child);
            if(value < currentValue){
                currentValue = value;
            }
        }
        
        return currentValue;
    }

    @Override
    public String getDescription() {
        return "Min over children of " + baseMetric.getDescription();
    }

}