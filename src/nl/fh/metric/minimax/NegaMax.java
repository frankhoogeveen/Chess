/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import java.util.Set;
import nl.fh.player.evalplayer.Metric;

/**
 *  This wraps around a metric to create the negamax metric 
 *     // https://en.wikipedia.org/wiki/Negamax
 * 
 * This implementation does NOT make use of alpha/beta pruning
 * 
 */
public class NegaMax<T extends Parent<T>> implements Metric<T> {

    private Metric<T> baseMetric;
    private int depth;
    private SearchMode mode;
    
    /**
     * 
     * @param baseMetric
     * @param depth
     * @param mode is either MINIMAX (the top level minimizes) or MAXIMIN. 
     * 
     */
    public NegaMax (Metric<T> baseMetric, int depth, SearchMode mode){
        this.baseMetric = baseMetric;
        this.depth = depth;
        this.mode = mode;
    }
    
    @Override
    public double eval(T state) {
        int sign = this.mode.getSign();
        return sign * iteration(state, this.depth, sign);  
    }  

    private double iteration(T state, int depth, int sign) {
        if(depth == 0){
            return sign * baseMetric.eval(state);
        } 
        
        Set<T> daughters = state.getChildren();
        
        if(daughters.isEmpty()){
            return sign * baseMetric.eval(state);            
        }
        
        double currentValue = - Double.MAX_VALUE;
        for(T daughter : daughters){
            double nextValue = - iteration(daughter, depth-1, -sign);
            if(nextValue > currentValue){
                currentValue = nextValue;
            }
        }
        
        return currentValue;
    }

    public Metric<T> getBaseMetric() {
        return baseMetric;
    }

    public void setBaseMetric(Metric<T> baseMetric) {
        this.baseMetric = baseMetric;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public SearchMode getMode() {
        return mode;
    }

    public void setMode(SearchMode mode) {
        this.mode = mode;
    }
}