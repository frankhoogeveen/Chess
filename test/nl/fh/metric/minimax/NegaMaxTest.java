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
        TestTree root = new TestTree(0.);
        TestTree v1    = new TestTree(1.);
        TestTree v2    = new TestTree(2.);
        
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
        TestTree root = new TestTree(0.);
        TestTree v1    = new TestTree(1.);
        TestTree v2    = new TestTree(2.);
        TestTree v3    = new TestTree(3.);
        TestTree v4    = new TestTree(4.);
        TestTree v5    = new TestTree(5.);
        TestTree v6    = new TestTree(6.); 
        
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
        TestTree root = new TestTree(0.);
        TestTree v1    = new TestTree(1.);
        TestTree v2    = new TestTree(2.);
        TestTree v3    = new TestTree(3.);
        TestTree v4    = new TestTree(4.);
        TestTree v5    = new TestTree(5.);
        TestTree v6    = new TestTree(6.);
        TestTree v7    = new TestTree(7.);
        TestTree v8    = new TestTree(8.);
        TestTree v9    = new TestTree(9.);
        TestTree v10    = new TestTree(10.);
        TestTree v11    = new TestTree(11.);
        TestTree v12   = new TestTree(12.);
        TestTree v13    = new TestTree(13.);
        TestTree v14   = new TestTree(14.);      
        
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
        TestTree root = new TestTree(0.);
        TestTree v1    = new TestTree(1.);
        TestTree v2    = new TestTree(2.);
        TestTree v3    = new TestTree(3.);
        TestTree v4    = new TestTree(4.);
        TestTree v5    = new TestTree(5.);
        TestTree v6    = new TestTree(6.);
        TestTree v7    = new TestTree(7.);
        TestTree v8    = new TestTree(8.);
        TestTree v9    = new TestTree(9.);
        TestTree v10    = new TestTree(10.);
        TestTree v11    = new TestTree(11.);
        TestTree v12   = new TestTree(12.);
        TestTree v13    = new TestTree(13.);
        TestTree v14   = new TestTree(14.);      
        
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