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
public class NegaMaxVerbose<T extends GameState> implements Metric<T> {

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
    public NegaMaxVerbose (Metric<T> baseMetric, MoveGenerator moveGenerator, int depth){
        this.baseMetric = baseMetric;
        this.moveGenerator = moveGenerator;
        this.depth = depth;
    }   
    
    private void separator(){
        System.out.println("========================");
    }
    
    private void separator2(){
        System.out.println("------------------------");        
    }
    
    
    @Override
    public double eval(T state) {
        separator();
        int sign = state.getMover().getSign();
        
        double result = sign * iteration(state, this.depth, sign);  
        separator();
        return result;
    }  

    private double iteration(T state, int depth, int sign) {
        System.out.println("iteration");
        System.out.println(state);
        System.out.println("depth: " + depth);
        System.out.println("sign : " + sign);
        
        if(depth == 0){
            double result = sign * baseMetric.eval((T) state);
            System.out.println("result: " + result);
            separator2();
            return result;
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
        
        System.out.println("result: " + currentValue);
        separator2();        
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