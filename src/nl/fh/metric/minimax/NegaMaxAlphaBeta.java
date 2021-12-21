/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import java.util.Set;
import nl.fh.chess.Colored;
import nl.fh.player.evalplayer.Metric;

/**
 *  This wraps around a metric to create the negamax metric with alpha beta pruning.
 *     // https://en.wikipedia.org/wiki/Negamax
 * 
 * 
 */
public class NegaMaxAlphaBeta<T extends Parent<T> & Colored> implements Metric<T> {
    private Metric<T> baseMetric;
    private int depth;
    
    /**
     * 
     * @param baseMetric
     * @param depth
     * The mode is the default maximin. 
     * 
     */    
    public NegaMaxAlphaBeta (Metric<T> baseMetric, int depth){
        this.baseMetric = baseMetric;
        this.depth = depth;
    }    
    
    @Override
    public double eval(T state) {
        int sign = state.getColor().getSign();
        double alpha = - Double.MAX_VALUE;
        double beta = Double.MAX_VALUE;
        return  sign * iteration(state, this.depth, sign, alpha, beta);  
    }  

    private double iteration(T state, int depth, int sign, double alpha, double beta) {
        if(depth == 0){
            return sign * baseMetric.eval(state);
        } 
        
        Set<T> daughters = state.getChildren();
        if(daughters.isEmpty()){
            return sign * baseMetric.eval(state);
        }
        
        double currentValue = - Double.MAX_VALUE;
        for(T daughter : daughters){
            double nextValue = - iteration(daughter, depth-1,-sign, -beta, -alpha);
            
            if(nextValue > currentValue){
                currentValue = nextValue;
            }
            
            if(currentValue > alpha){
                alpha = currentValue;
            }
            
            if(alpha > beta){
                break;
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
        return "NegamaxAlphaBeta: depth "+ depth + " " + this.baseMetric.getDescription();
    }
}