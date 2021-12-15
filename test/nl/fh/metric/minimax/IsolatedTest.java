/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class IsolatedTest {
    
    @Test
    public void testDepthOne(){
        TreeNode<Double> root  = new TreeNode<Double>(0.);
        TreeNode<Double> v1    = new TreeNode<Double>(1.);
        TreeNode<Double> v2    = new TreeNode<Double>(2.);
        
        root.addDaughter(v1);
        root.addDaughter(v2);
        
        double delta = 1.e-6;
        
        SearchMode mode = SearchMode.MAXIMIN;
        NegaMax nega = new NegaMax(new TestMetric(), 1, mode);
        assertEquals(2., nega.eval(root), delta);
        
        nega.setMode(SearchMode.MINIMAX);
        assertEquals(1., nega.eval(root), delta);
    }    
 

}