/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.utilities;

import java.util.Random;
import nl.fh.player.evalplayer.Metric;

/**
 * Wrapping this around a given baseMetric, it evaluates states by
 * adding an iid normally distributed random number;
 * 
 */
public class NoiseAdder<T> implements Metric<T>{

    private final Metric<T> baseMetric;
    private final double sigma;
    private final Random random;
    
    public NoiseAdder(double sigma, Metric<T> baseMetric){
     this.baseMetric = baseMetric;
     this.sigma = sigma;
     this.random = new Random();
    }

    @Override
    public double eval(T t) {
        return baseMetric.eval(t) + sigma * random.nextGaussian();
    }

    @Override
    public String getDescription() {
        return "Noise " + sigma + " + " + baseMetric.getDescription();
    }

}