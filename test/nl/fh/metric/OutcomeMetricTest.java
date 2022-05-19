package nl.fh.metric;

import java.util.HashSet;
import java.util.Set;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.Move;
import nl.fh.gamestate.tictactoe.TicTacToeMove;
import nl.fh.gamestate.tictactoe.TicTacToeState;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.metric.utilities.OutcomeMetric;
import nl.fh.metric.utilities.ZeroMetric;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rule.GameDriver;
import nl.fh.rule.MoveGenerator;
import nl.fh.rule.ResultArbiter;
import nl.fh.rule.tictactoe.TicTacToe;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/*
 * License: GPL v3
 * 
 */

/**
 * 
 * 
 */
public class OutcomeMetricTest {
    private static final double WIN_VALUE = 1.e+6;    
    private static final double EPSILON = 1.e-8;
    
    GameDriver<TicTacToeState> driver = TicTacToe.getGameDriver();
    MoveGenerator<TicTacToeState> moveGenerator = driver.getMoveGenerator();
    ResultArbiter arbiter = driver.getResultArbiter();
    
    @Test
    public void ZeroMetricTest(){
        Metric<TicTacToeState> metric = new ZeroMetric();    
        TicTacToeState state = driver.getInitialState();

        assertEquals(0., metric.eval(state), EPSILON);
        
        TicTacToeMove move = new TicTacToeMove(2,0);
        state = move.applyTo(state);    
        
        assertEquals(0., metric.eval(state), EPSILON);        
    }
    @Test 
    public void OutcomeMetricTest1(){
        Metric<TicTacToeState> metric = new ZeroMetric();  
        metric = new OutcomeMetric(metric, WIN_VALUE, driver);        
        
        TicTacToeState state = driver.getInitialState();
        assertEquals(0., metric.eval(state), EPSILON);
        
        TicTacToeMove move = new TicTacToeMove(0,0);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);   

        move = new TicTacToeMove(0,1);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);    
        
        move = new TicTacToeMove(1,0);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);    

        move = new TicTacToeMove(1,1);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON); 
        
        move = new TicTacToeMove(2,0);
        state = move.applyTo(state);     
        assertEquals(+WIN_VALUE, metric.eval(state), EPSILON);    
                 
    } 
    @Test 
    public void OutcomeMetricTest2(){
        Metric<TicTacToeState> metric = new ZeroMetric();  
        metric = new OutcomeMetric(metric, WIN_VALUE, driver);        
        
        TicTacToeState state = driver.getInitialState();
        assertEquals(0., metric.eval(state), EPSILON);
        
        TicTacToeMove move = new TicTacToeMove(2,0);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);   

        move = new TicTacToeMove(0,1);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);    
        
        move = new TicTacToeMove(1,0);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);    

        move = new TicTacToeMove(0,2);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON); 
        
        move = new TicTacToeMove(1,1);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);    
        
        move = new TicTacToeMove(1,2);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);    

        move = new TicTacToeMove(2,1);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);    
        
        move = new TicTacToeMove(2,2);
        state = move.applyTo(state);    
        assertEquals( -WIN_VALUE, metric.eval(state),EPSILON);    
                 
    }
    
    @Test 
    public void OutcomeMetricTest3(){
        Metric<TicTacToeState> metric = new ZeroMetric();  
        metric = new OutcomeMetric(metric, WIN_VALUE, driver);        
        
        TicTacToeState state = driver.getInitialState();
        assertEquals(0., metric.eval(state), EPSILON);
        
        TicTacToeMove move = new TicTacToeMove(0,0);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);   

        move = new TicTacToeMove(0,1);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);    
        
        move = new TicTacToeMove(1,0);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);    

        move = new TicTacToeMove(2,0);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON); 
        
        move = new TicTacToeMove(1,1);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);    
        
        move = new TicTacToeMove(1,2);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);    

        move = new TicTacToeMove(2,1);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);    
        
        move = new TicTacToeMove(2,2);
        state = move.applyTo(state);    
        assertEquals( 0., metric.eval(state),EPSILON);    
        
        move = new TicTacToeMove(0,2);
        state = move.applyTo(state);  
        
        System.out.println(state);
        assertEquals( 0., metric.eval(state),EPSILON);            
                 
    }    
    
    @Test 
    public void OutcomeMetricTest100(){
        Metric<TicTacToeState> metric = new ZeroMetric();  
        metric = new OutcomeMetric(metric, WIN_VALUE, driver);       
        
        int depth = 0;
        Metric<TicTacToeState> metric2 = new NegaMax(metric, moveGenerator, depth);
        
        TicTacToeState state = driver.getInitialState();
        assertEquals(0., metric.eval(state), EPSILON);
        assertEquals(0., metric2.eval(state), EPSILON);        
        
        TicTacToeMove move = new TicTacToeMove(2,0);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);  
        assertEquals(0., metric2.eval(state), EPSILON);            

        move = new TicTacToeMove(0,1);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);   
        assertEquals(0., metric2.eval(state), EPSILON);            
        
        move = new TicTacToeMove(1,0);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);  
        assertEquals(0., metric2.eval(state), EPSILON);            

        move = new TicTacToeMove(0,2);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON); 
        assertEquals(0., metric2.eval(state), EPSILON);            
        
        move = new TicTacToeMove(1,1);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);  
        assertEquals(0., metric2.eval(state), EPSILON);            
        
        move = new TicTacToeMove(1,2);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON); 
        assertEquals(0., metric2.eval(state), EPSILON);            

        move = new TicTacToeMove(2,1);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);  
        assertEquals(0., metric2.eval(state), EPSILON);            
        
        move = new TicTacToeMove(2,2);
        state = move.applyTo(state);    
        assertEquals( -WIN_VALUE, metric.eval(state),EPSILON);    
        assertEquals( -WIN_VALUE, metric2.eval(state), EPSILON);            
                 
    }    
    
    @Test 
    public void OutcomeMetricTest101(){
        Metric<TicTacToeState> metric = new ZeroMetric();  
        metric = new OutcomeMetric(metric, WIN_VALUE, driver);       
        
        int depth = 1;
        Metric<TicTacToeState> metric2 = new NegaMax(metric, moveGenerator, depth);
        
        TicTacToeState state = driver.getInitialState();
//        assertEquals(0., metric.eval(state), EPSILON);
//        assertEquals(0., metric2.eval(state), EPSILON);        
        
        TicTacToeMove move = new TicTacToeMove(2,0);
        state = move.applyTo(state);    
//        assertEquals(0., metric.eval(state), EPSILON);  
//        assertEquals(0., metric2.eval(state), EPSILON);            

        move = new TicTacToeMove(0,1);
        state = move.applyTo(state);    
//        assertEquals(0., metric.eval(state), EPSILON);   
//        assertEquals(0., metric2.eval(state), EPSILON);            
        
        move = new TicTacToeMove(1,0);
        state = move.applyTo(state);    
//        assertEquals(0., metric.eval(state), EPSILON);  
//        assertEquals(0., metric2.eval(state), EPSILON);            

        move = new TicTacToeMove(0,2);
//      Note that after this move the board is 0..|0..|.xx so x could be winning 
//      in a single move
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON); 
        assertEquals(WIN_VALUE, metric2.eval(state), EPSILON);            
        
        move = new TicTacToeMove(1,1);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);  
        assertEquals(-WIN_VALUE, metric2.eval(state), EPSILON);            
        
        move = new TicTacToeMove(1,2);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON); 
        assertEquals(WIN_VALUE, metric2.eval(state), EPSILON);            

        move = new TicTacToeMove(2,1);
        state = move.applyTo(state);    
        assertEquals(0., metric.eval(state), EPSILON);  
        assertEquals(-WIN_VALUE, metric2.eval(state), EPSILON);            
        
        move = new TicTacToeMove(2,2);
        state = move.applyTo(state);  
        assertEquals( -WIN_VALUE, metric.eval(state),EPSILON);    
        assertEquals( -WIN_VALUE, metric2.eval(state), EPSILON);           
                 
    }        

}