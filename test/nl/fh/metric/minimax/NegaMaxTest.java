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
public class NegaMaxTest {
    
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
    
    @Test
    public void testDepthTwo(){
        TreeNode<Double> root = new TreeNode<Double>(0.);
        TreeNode<Double> v1    = new TreeNode<Double>(1.);
        TreeNode<Double> v2    = new TreeNode<Double>(2.);
        TreeNode<Double> v3    = new TreeNode<Double>(3.);
        TreeNode<Double> v4    = new TreeNode<Double>(4.);
        TreeNode<Double> v5    = new TreeNode<Double>(5.);
        TreeNode<Double> v6    = new TreeNode<Double>(6.); 
        
        root.addDaughter(v1);
        root.addDaughter(v2);
        
        v1.addDaughter(v3);
        v1.addDaughter(v4);
        
        v2.addDaughter(v5);
        v2.addDaughter(v6);
        
        double delta = 1.e-6;
        
        SearchMode mode = SearchMode.MAXIMIN;
        NegaMax nega = new NegaMax(new TestMetric(), 2, mode);
        assertEquals(5., nega.eval(root), delta);
        
        nega.setMode(SearchMode.MINIMAX);
        assertEquals(4., nega.eval(root), delta);
    }
    
    @Test
    public void testDepthThree(){
        TreeNode<Double> root = new TreeNode<Double>(0.);
        TreeNode<Double> v1    = new TreeNode<Double>(1.);
        TreeNode<Double> v2    = new TreeNode<Double>(2.);
        TreeNode<Double> v3    = new TreeNode<Double>(3.);
        TreeNode<Double> v4    = new TreeNode<Double>(4.);
        TreeNode<Double> v5    = new TreeNode<Double>(5.);
        TreeNode<Double> v6    = new TreeNode<Double>(6.);
        TreeNode<Double> v7    = new TreeNode<Double>(7.);
        TreeNode<Double> v8    = new TreeNode<Double>(8.);
        TreeNode<Double> v9    = new TreeNode<Double>(9.);
        TreeNode<Double> v10    = new TreeNode<Double>(10.);
        TreeNode<Double> v11    = new TreeNode<Double>(11.);
        TreeNode<Double> v12   = new TreeNode<Double>(12.);
        TreeNode<Double> v13    = new TreeNode<Double>(13.);
        TreeNode<Double> v14   = new TreeNode<Double>(14.);      
        
        root.addDaughter(v1);
        root.addDaughter(v2);
        
        v1.addDaughter(v3);
        v1.addDaughter(v4);
        
        v2.addDaughter(v5);
        v2.addDaughter(v6);

        v3.addDaughter(v7);
        v3.addDaughter(v8);

        v4.addDaughter(v9);
        v4.addDaughter(v10);

        v5.addDaughter(v11);
        v5.addDaughter(v12);
        
        v6.addDaughter(v13);
        v6.addDaughter(v14);        
        
        
        double delta = 1.e-6;
        
        SearchMode mode = SearchMode.MAXIMIN;
        NegaMax nega = new NegaMax(new TestMetric(), 3, mode);
        assertEquals(12., nega.eval(root), delta);
        
        nega.setMode(SearchMode.MINIMAX);
        assertEquals(9., nega.eval(root), delta);
    }    
  
    
    @Test
    public void testDepthUneven(){
        TreeNode<Double> root = new TreeNode<Double>(0.);
        TreeNode<Double> v1    = new TreeNode<Double>(1.);
        TreeNode<Double> v2    = new TreeNode<Double>(2.);
        TreeNode<Double> v3    = new TreeNode<Double>(3.);
        TreeNode<Double> v4    = new TreeNode<Double>(4.);
        TreeNode<Double> v5    = new TreeNode<Double>(5.);
        TreeNode<Double> v6    = new TreeNode<Double>(6.);
        TreeNode<Double> v7    = new TreeNode<Double>(7.);
        TreeNode<Double> v8    = new TreeNode<Double>(8.);
        TreeNode<Double> v9    = new TreeNode<Double>(9.);
        TreeNode<Double> v10    = new TreeNode<Double>(10.);
        TreeNode<Double> v11    = new TreeNode<Double>(11.);
        TreeNode<Double> v12   = new TreeNode<Double>(12.);
        TreeNode<Double> v13    = new TreeNode<Double>(13.);
        TreeNode<Double> v14   = new TreeNode<Double>(14.);      
        
        root.addDaughter(v1);
        root.addDaughter(v2);
        
        v1.addDaughter(v3);
        v1.addDaughter(v4);
        
        v2.addDaughter(v5);
        v2.addDaughter(v6);

        v3.addDaughter(v7);
        v3.addDaughter(v8);

        v5.addDaughter(v11);
        v5.addDaughter(v12);   
        
        double delta = 1.e-6;
        
        SearchMode mode = SearchMode.MAXIMIN;
        NegaMax nega = new NegaMax(new TestMetric(), 3, mode);
        assertEquals(6., nega.eval(root), delta);
        
        nega.setMode(SearchMode.MINIMAX);
        assertEquals(7., nega.eval(root), delta);
    }       

}