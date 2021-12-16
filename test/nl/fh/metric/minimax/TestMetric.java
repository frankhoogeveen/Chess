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
public class TestMetric implements Metric<TestNode> {

    public TestMetric() {
    }

    @Override
    public double eval(TestNode t) {
        return t.getContent();
    }

}