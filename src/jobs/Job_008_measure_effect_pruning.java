/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamestate.GameState;
import nl.fh.metric.MaterialCountMetric;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.metric.minimax.NegaMaxAlphaBeta;
import nl.fh.metric.minimax.NegaMaxGen3;
import nl.fh.metric.utilities.Counter;
import nl.fh.metric.utilities.TableBuffer;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;

/**
 * 
 */
public class Job_008_measure_effect_pruning {
    
        
    public static void main(String[] args){
        Rules rules = new SimpleRules(); 

        Metric<GameState> baseMetric = new MaterialCountMetric();
        
        Counter<GameState> counter = new Counter<GameState>(baseMetric);
        NegaMax<GameState> nega = new NegaMax<GameState>(counter, 0);
        
        Counter<GameState> counterAB = new Counter<GameState>(baseMetric);        
        NegaMaxAlphaBeta<GameState> negaAB = new NegaMaxAlphaBeta<GameState>(counterAB,0);
        
        int size2 = 10000;
        Counter<GameState> counterAB2 = new Counter<GameState>(baseMetric);  
        TableBuffer<GameState> buffered = new TableBuffer<GameState>(counterAB2, size2);        
        NegaMaxAlphaBeta<GameState> negaAB2= new NegaMaxAlphaBeta<GameState>(buffered,0);
        
        int size3 = 20000;
        Counter<GameState> counterAB3 = new Counter<GameState>(baseMetric);  
        TableBuffer<GameState> buffered3 = new TableBuffer<GameState>(counterAB3, size3);        
        NegaMaxGen3<GameState> negaAB3= new NegaMaxGen3<GameState>(buffered3,0);        

        nega.setDepth(3);
        negaAB.setDepth(3);
        negaAB2.setDepth(3); 
        negaAB3.setDepth(5);            
        
        String fen = "r2qkb1r/pppbpppp/2n5/1B1pN3/3PnB2/4P3/PPP2PPP/RN1QK2R b KQkq - 7 7";
//        String fen = "k7/3q4/8/8/8/4Q3/7K/8 w - - 0 1";
        GameState state  = GameState.fromFEN(fen, rules);
        
        System.out.println(fen);
        System.out.println();
        
        counter.setCount(0);
        double value = nega.eval(state);
        System.out.println(nega.getDescription());
        System.out.println(value);
        System.out.println(counter.getCount());
        System.out.println();
        
        counterAB.setCount(0);
        double valueAB = negaAB.eval(state);
        System.out.println(negaAB.getDescription());           
        System.out.println(valueAB);
        System.out.println(counterAB.getCount());
        System.out.println();    
               
//        counterAB2.setCount(0);
//        double valueAB2= negaAB2.eval(state);
//        System.out.println(negaAB2.getDescription());            
//        System.out.println(valueAB2);
//        System.out.println(counterAB2.getCount());
//        System.out.println(); 
        
        counterAB3.setCount(0);
        double valueAB3= negaAB3.eval(state);
        System.out.println(negaAB3.getDescription());            
        System.out.println(valueAB3);
        System.out.println(counterAB3.getCount());
        System.out.println();         
        
//         OutputToFile.write("buffer", buffered.toCSV());
        
    }

}