/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import nl.fh.chess.Color;
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
        TestNode root  = new TestNode(0, Color.WHITE);
        TestNode v1    = new TestNode(1, Color.WHITE);
        TestNode v2    = new TestNode(2, Color.WHITE);
        
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
        TestNode root = new TestNode(0, Color.WHITE);
        TestNode v1    = new TestNode(1, Color.WHITE);
        TestNode v2    = new TestNode(2, Color.WHITE);
        TestNode v3    = new TestNode(3, Color.WHITE);
        TestNode v4    = new TestNode(4, Color.WHITE);
        TestNode v5    = new TestNode(5, Color.WHITE);
        TestNode v6    = new TestNode(6, Color.WHITE); 
        
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
        TestNode root = new TestNode(0, Color.WHITE);
        TestNode v1    = new TestNode(1, Color.WHITE);
        TestNode v2    = new TestNode(2, Color.WHITE);
        TestNode v3    = new TestNode(3, Color.WHITE);
        TestNode v4    = new TestNode(4, Color.WHITE);
        TestNode v5    = new TestNode(5, Color.WHITE);
        TestNode v6    = new TestNode(6, Color.WHITE);
        TestNode v7    = new TestNode(7, Color.WHITE);
        TestNode v8    = new TestNode(8, Color.WHITE);
        TestNode v9    = new TestNode(9, Color.WHITE);
        TestNode v10    = new TestNode(10, Color.WHITE);
        TestNode v11    = new TestNode(11, Color.WHITE);
        TestNode v12    = new TestNode(12, Color.WHITE);
        TestNode v13    = new TestNode(13, Color.WHITE);
        TestNode v14    = new TestNode(14, Color.WHITE);      
        
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
        
        
        SearchMode mode = SearchMode.MAXIMIN;
        NegaMax nega = new NegaMax(new TestMetric(), 3, mode);
        assertEquals(12., nega.eval(root), delta);
        
        nega.setMode(SearchMode.MINIMAX);
        assertEquals(9., nega.eval(root), delta);
    }    
  
    
    @Test
    public void testDepthUneven(){
        TestNode root = new TestNode(0, Color.WHITE);
        TestNode v1    = new TestNode(1, Color.WHITE);
        TestNode v2    = new TestNode(2, Color.WHITE);
        TestNode v3    = new TestNode(3, Color.WHITE);
        TestNode v4    = new TestNode(4, Color.WHITE);
        TestNode v5    = new TestNode(5, Color.WHITE);
        TestNode v6    = new TestNode(6, Color.WHITE);
        TestNode v7    = new TestNode(7, Color.WHITE);
        TestNode v8    = new TestNode(8, Color.WHITE);
        TestNode v9    = new TestNode(9, Color.WHITE);
        TestNode v10    = new TestNode(10, Color.WHITE);
        TestNode v11    = new TestNode(11, Color.WHITE);
        TestNode v12   = new TestNode(12, Color.WHITE);
        TestNode v13    = new TestNode(13, Color.WHITE);
        TestNode v14   = new TestNode(14, Color.WHITE);      
        
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
    public void testDeeperThanNeeded(){
        TestNode root = new TestNode(0, Color.WHITE);
        TestNode v1    = new TestNode(1, Color.UNDEFINED);
        TestNode v2    = new TestNode(2, Color.UNDEFINED);
        TestNode v3    = new TestNode(3, Color.UNDEFINED);
        TestNode v4    = new TestNode(4, Color.UNDEFINED);
        TestNode v5    = new TestNode(5, Color.UNDEFINED);
        TestNode v6    = new TestNode(6, Color.UNDEFINED);
        TestNode v7    = new TestNode(7, Color.UNDEFINED);
        TestNode v8    = new TestNode(8, Color.UNDEFINED);
        TestNode v9    = new TestNode(9, Color.UNDEFINED);
        TestNode v10    = new TestNode(10, Color.UNDEFINED);
        TestNode v11    = new TestNode(11, Color.UNDEFINED);
        TestNode v12   = new TestNode(12, Color.UNDEFINED);
        TestNode v13    = new TestNode(13, Color.UNDEFINED);
        TestNode v14   = new TestNode(14, Color.UNDEFINED);   
        TestNode v20   = new TestNode(20, Color.UNDEFINED);   
        TestNode v30   = new TestNode(30, Color.UNDEFINED);           
        
        
        
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
    
    @Test
    public void testDeeperThanNeededBlack(){
        TestNode root = new TestNode(0, Color.BLACK);
        TestNode v1    = new TestNode(1, Color.UNDEFINED);
        TestNode v2    = new TestNode(2, Color.UNDEFINED);
        TestNode v3    = new TestNode(3, Color.UNDEFINED);
        TestNode v4    = new TestNode(4, Color.UNDEFINED);
        TestNode v5    = new TestNode(5, Color.UNDEFINED);
        TestNode v6    = new TestNode(6, Color.UNDEFINED);
        TestNode v7    = new TestNode(7, Color.UNDEFINED);
        TestNode v8    = new TestNode(8, Color.UNDEFINED);
        TestNode v9    = new TestNode(9, Color.UNDEFINED);
        TestNode v10    = new TestNode(10, Color.UNDEFINED);
        TestNode v11    = new TestNode(11, Color.UNDEFINED);
        TestNode v12   = new TestNode(12, Color.UNDEFINED);
        TestNode v13    = new TestNode(13, Color.UNDEFINED);
        TestNode v14   = new TestNode(14, Color.UNDEFINED);   
        TestNode v20   = new TestNode(20, Color.UNDEFINED);   
        TestNode v30   = new TestNode(30, Color.UNDEFINED);           
        
        
        
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
        assertEquals(8., nega.eval(root), delta);
        
        nega.setMode(SearchMode.MINIMAX);
        assertEquals(6., nega.eval(root), delta);
        
        
        nega.setDepth(3);
        nega.setMode(SearchMode.MAXIMIN);
        assertEquals(7., nega.eval(root), delta); 
        
        nega.setMode(SearchMode.MINIMAX);
        assertEquals(6., nega.eval(root), delta);        
    }       

}