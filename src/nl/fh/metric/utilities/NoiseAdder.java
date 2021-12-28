/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.utilities;

import java.util.Random;
import nl.fh.gamestate.GameState;
import nl.fh.player.evalplayer.Metric;

/**
 * Wrapping this around a given baseMetric, it evaluates states by
 * adding an iid normally distributed random number;
 * 
 */
public class NoiseAdder<S extends GameState> implements Metric<S>{

    private final Metric<S> baseMetric;
    private final double sigma;
    private final Random random;
    
    public NoiseAdder(double sigma, Metric<S> baseMetric){
     this.baseMetric = baseMetric;
     this.sigma = sigma;
     this.random = new Random();
    }

    @Override
    public double eval(S t) {
        return baseMetric.eval(t) + sigma * random.nextGaussian();
    }

    @Override
    public String getDescription() {
        return "Noise " + sigma + " + " + baseMetric.getDescription();
    }

}