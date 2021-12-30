/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rule.MoveGenerator;

/**
 *  This wraps around a metric to create the negamax metric 
 *     // https://en.wikipedia.org/wiki/Negamax
 * 
 * This implementation does NOT make use of alpha/beta pruning
 * 
 */
public class NegaMax<T extends GameState> implements Metric<T> {

    private Metric<T> baseMetric;
    private int depth;
    private final MoveGenerator moveGenerator;
    
    /**
     * 
     * @param baseMetric
     * @param depth
     * The mode is the default maximin. 
     * 
     */    
    public NegaMax (Metric<T> baseMetric, MoveGenerator moveGenerator, int depth){
        this.baseMetric = baseMetric;
        this.moveGenerator = moveGenerator;
        this.depth = depth;
    }    
    
    @Override
    public double eval(T state) {
        int sign = state.getMover().getSign();
        
        return  sign * iteration(state, this.depth, sign);  
    }  

    private double iteration(T state, int depth, int sign) {
        if(depth == 0){
            return sign * baseMetric.eval((T) state);
        } 
        
        Set<T> daughters = moveGenerator.calculateChildren(state);
        if(daughters.isEmpty()){
            return sign * baseMetric.eval((T) state);
        }
        
        double currentValue = - Double.MAX_VALUE;
        for(T daughter : daughters){
            double nextValue = - iteration(daughter, depth-1,-sign);
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

    @Override
    public String getDescription() {
        return "Negamax: depth "+ depth + " " + this.baseMetric.getDescription();
    }
}