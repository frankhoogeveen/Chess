/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import nl.fh.player.evalplayer.Metric;

/**
 * 
 * 
 */
public class TestMetric implements Metric<Double> {

    public TestMetric() {
    }

    @Override
    public double eval(Double t) {
        return t;
    }


}