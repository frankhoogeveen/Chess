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
public class NegaMax<T> implements Metric<T> {

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
        TreeNode<T> node = new TreeNode(state) ;   
        return sign * iteration(node, this.depth, sign);  
    }
    
    /**
     * 
     * @param node
     * @return the evaluation of this node. This method can be used when 
     * the tree has already been explicitely set up. Useful in testing.
     */
    public double eval(TreeNode<T> node) {
        int sign = this.mode.getSign();
        return sign * iteration(node, this.depth, sign);  
    }    

    private double iteration(TreeNode<T> node, int depth, int sign) {
        if(depth == 0){
            return sign * node.eval(baseMetric);
        } 
        
        Set<TreeNode<T>> daughters = node.getDaughters();
        
        if(daughters.isEmpty()){
            return sign * node.eval(baseMetric);            
        }
        
        double currentValue = - Double.MAX_VALUE;
        for(TreeNode<T> daughter : daughters){
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