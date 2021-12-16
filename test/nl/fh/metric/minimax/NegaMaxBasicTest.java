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
public class NegaMaxBasicTest {

    private double delta = 1.e-8;
    
    @Test
    public void testDepthOne(){
        TestNode root  = new TestNode(0);
        TestNode v1    = new TestNode(1);
        TestNode v2    = new TestNode(2);
        
        root.addDaughter(v1);
        root.addDaughter(v2);
        
        SearchMode mode = SearchMode.MAXIMIN;
        NegaMax nega = new NegaMax(new TestMetric(), 1, mode);
        assertEquals(2., nega.eval(root), delta);
        
        nega.setMode(SearchMode.MINIMAX);
        assertEquals(1., nega.eval(root), delta);
    }    
    
    @Test
    public void testDepthTwo(){
        TestNode root = new TestNode(0);
        TestNode v1    = new TestNode(1);
        TestNode v2    = new TestNode(2);
        TestNode v3    = new TestNode(3);
        TestNode v4    = new TestNode(4);
        TestNode v5    = new TestNode(5);
        TestNode v6    = new TestNode(6); 
        
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
        TestNode root = new TestNode(0);
        TestNode v1    = new TestNode(1);
        TestNode v2    = new TestNode(2);
        TestNode v3    = new TestNode(3);
        TestNode v4    = new TestNode(4);
        TestNode v5    = new TestNode(5);
        TestNode v6    = new TestNode(6);
        TestNode v7    = new TestNode(7);
        TestNode v8    = new TestNode(8);
        TestNode v9    = new TestNode(9);
        TestNode v10    = new TestNode(10);
        TestNode v11    = new TestNode(11);
        TestNode v12   = new TestNode(12);
        TestNode v13    = new TestNode(13);
        TestNode v14   = new TestNode(14);      
        
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
        TestNode root = new TestNode(0);
        TestNode v1    = new TestNode(1);
        TestNode v2    = new TestNode(2);
        TestNode v3    = new TestNode(3);
        TestNode v4    = new TestNode(4);
        TestNode v5    = new TestNode(5);
        TestNode v6    = new TestNode(6);
        TestNode v7    = new TestNode(7);
        TestNode v8    = new TestNode(8);
        TestNode v9    = new TestNode(9);
        TestNode v10    = new TestNode(10);
        TestNode v11    = new TestNode(11);
        TestNode v12   = new TestNode(12);
        TestNode v13    = new TestNode(13);
        TestNode v14   = new TestNode(14);      
        
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
        
        SearchMode mode = SearchMode.MAXIMIN;
        NegaMax nega = new NegaMax(new TestMetric(), 3, mode);
        assertEquals(6., nega.eval(root), delta);
        
        nega.setMode(SearchMode.MINIMAX);
        assertEquals(7., nega.eval(root), delta);
    }     
    
    @Test
    public void testDeerThanNeeded(){
        TestNode root = new TestNode(0);
        TestNode v1    = new TestNode(1);
        TestNode v2    = new TestNode(2);
        TestNode v3    = new TestNode(3);
        TestNode v4    = new TestNode(4);
        TestNode v5    = new TestNode(5);
        TestNode v6    = new TestNode(6);
        TestNode v7    = new TestNode(7);
        TestNode v8    = new TestNode(8);
        TestNode v9    = new TestNode(9);
        TestNode v10    = new TestNode(10);
        TestNode v11    = new TestNode(11);
        TestNode v12   = new TestNode(12);
        TestNode v13    = new TestNode(13);
        TestNode v14   = new TestNode(14);   
        TestNode v20   = new TestNode(20);   
        TestNode v30   = new TestNode(30);           
        
        
        
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
        
        v7.addDaughter(v20);
        v7.addDaughter(v30);
        
        SearchMode mode = SearchMode.MAXIMIN;
        NegaMax nega = new NegaMax(new TestMetric(), 4, mode);
        assertEquals(6., nega.eval(root), delta);
        
        nega.setMode(SearchMode.MINIMAX);
        assertEquals(8., nega.eval(root), delta);
        
        
        nega.setDepth(3);
        nega.setMode(SearchMode.MAXIMIN);
        assertEquals(6., nega.eval(root), delta); 
        
        nega.setMode(SearchMode.MINIMAX);
        assertEquals(7., nega.eval(root), delta);        
        
        
        
    }      

}