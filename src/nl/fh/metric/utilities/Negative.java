/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.utilities;

import nl.fh.gamestate.GameState;
import nl.fh.player.evalplayer.Metric;

/**
 * Wrapping this around a given baseMetric, it evaluates states by
 * adding an iid normally distributed random number;
 * 
 */
public class Negative<S extends GameState> implements Metric<S>{

    private final Metric<S> baseMetric;
    
    public Negative(Metric<S> baseMetric){
     this.baseMetric = baseMetric;
    }

    @Override
    public double eval(S t) {
        return - baseMetric.eval(t);
    }

    @Override
    public String getDescription() {
        return "Negative of " + baseMetric.getDescription();
    }

}