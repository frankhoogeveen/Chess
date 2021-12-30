/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.utilities;

import nl.fh.gamestate.GameState;
import nl.fh.player.evalplayer.Metric;

/**
 * mapping that always evaluates to zero
 */
public class ZeroMetric<S extends GameState> implements Metric<S>{

    
    public ZeroMetric(){
    }

    @Override
    public double eval(S t) {
        return 0.0;
    }

    @Override
    public String getDescription() {
        return "Zero";
    }

}