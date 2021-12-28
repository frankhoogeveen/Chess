/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.utilities;

import nl.fh.gamestate.GameState;
import nl.fh.player.evalplayer.Metric;

/**
 * Wrapping this around a given baseMetric it does not change
 * the base metric, but keeps track of the number of times it is called
 * 
 */
public class Counter<S extends GameState> implements Metric<S>{

    private final Metric<S> baseMetric;
    private int count;
    
    public Counter(Metric<S> baseMetric){
     this.baseMetric = baseMetric;
     this.count = 0;
    }

    @Override
    public double eval(S t) {
        count += 1;
        return baseMetric.eval(t);
    }

    @Override
    public String getDescription() {
        return "/counter/"+ baseMetric.getDescription();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}