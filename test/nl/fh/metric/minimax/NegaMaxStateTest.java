/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.metric.ShannonMetric;
import nl.fh.move.Move;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * 
 */
public class NegaMaxStateTest {

    private double delta = 1.e-8;
    
    @Test
    public void testZeroDepth(){
        // position from https://lichess.org/igoizkyc#23
        String fen = "r1bq1rk1/ppp2pb1/2np1n1p/4P1p1/8/2N1P1B1/PPPNBPPP/R2Q1RK1 b - - 0 12";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);
        
        Metric<GameState> shannon = new ShannonMetric();
        
        Metric<GameState> nega = new NegaMax(shannon, 0, SearchMode.MAXIMIN);
        
        double shannonValue = shannon.eval(state);
        double negaValue = nega.eval(state);
        
        assertEquals(shannonValue, negaValue, delta);
    }
    
    @Test
    public void testOneDeep(){
        // position from https://lichess.org/igoizkyc#23
        String fen = "r1bq1rk1/ppp2pb1/2np1n1p/4P1p1/8/2N1P1B1/PPPNBPPP/R2Q1RK1 b - - 0 12";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);
        
        Metric<GameState> shannon = new ShannonMetric();
        
        Metric<GameState> nega = new NegaMax(shannon, 1, SearchMode.MAXIMIN);
        
        double negaValue = nega.eval(state);
        
        double currentMax = - Double.MAX_VALUE;
        for(Move m : state.getLegalMoves()){
            GameState next = m.applyTo(state);
            double value = shannon.eval(next);
            if(value > currentMax){
                currentMax = value;
            }
            
        }
        
        assertEquals(currentMax, negaValue, delta);
    }  
    
    @Test
    public void testTwoDeep(){
        // position from https://lichess.org/igoizkyc#23
        String fen = "r1bq1rk1/ppp2pb1/2np1n1p/4P1p1/8/2N1P1B1/PPPNBPPP/R2Q1RK1 b - - 0 12";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);
        
        Metric<GameState> shannon = new ShannonMetric();
        
        Metric<GameState> nega2  = new NegaMax(shannon, 2, SearchMode.MAXIMIN);
        Metric<GameState> nega1 = new NegaMax(shannon, 1, SearchMode.MINIMAX);        
        
        double negaValue = nega2.eval(state);
        
        double currentMax = - Double.MAX_VALUE;
        for(Move m : state.getLegalMoves()){
            GameState next = m.applyTo(state);
            double value = nega1.eval(next);
            if(value > currentMax){
                currentMax = value;
            }
            
        }
        
        assertEquals(currentMax, negaValue, delta);
    }   
    
    //@Test    // switched off since it takes about 30 seconds
    public void testThreeDeep(){
        // position from https://lichess.org/igoizkyc#23
        String fen = "r1bq1rk1/ppp2pb1/2np1n1p/4P1p1/8/2N1P1B1/PPPNBPPP/R2Q1RK1 b - - 0 12";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);
        
        Metric<GameState> shannon = new ShannonMetric();
        
        Metric<GameState> nega3  = new NegaMax(shannon, 3, SearchMode.MAXIMIN);
        Metric<GameState> nega2 = new NegaMax(shannon, 2, SearchMode.MINIMAX);        
        
        double negaValue = nega3.eval(state);
        
        double currentMax = - Double.MAX_VALUE;
        for(Move m : state.getLegalMoves()){
            GameState next = m.applyTo(state);
            double value = nega2.eval(next);
            if(value > currentMax){
                currentMax = value;
            }
            
        }
        
        assertEquals(currentMax, negaValue, delta);
    }      

}