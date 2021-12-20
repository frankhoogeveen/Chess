/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.utilities;

import nl.fh.metric.minimax.Parent;
import nl.fh.player.evalplayer.Metric;

/**
 * Wrapping this around a given baseMetric, it evaluates states by
 * adding an iid normally distributed random number;
 * 
 */
public class Negative<T extends Parent<T>> implements Metric<T>{

    private final Metric<T> baseMetric;
    
    public Negative(Metric<T> baseMetric){
     this.baseMetric = baseMetric;
    }

    @Override
    public double eval(T t) {
        return - baseMetric.eval(t);
    }

    @Override
    public String getDescription() {
        return "Negative of " + baseMetric.getDescription();
    }

}