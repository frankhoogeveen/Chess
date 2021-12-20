/*
 * License: GPL v3
 * 
 */

package jobs;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fh.chess.Color;
import nl.fh.gamestate.GameState;
import nl.fh.metric.MaterialCountMetric;
import nl.fh.metric.minimax.NegaMax;
import nl.fh.metric.minimax.NegaMaxAlphaBeta;
import nl.fh.metric.utilities.Counter;
import nl.fh.move.Move;
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
        
        nega.setDepth(3);
        negaAB.setDepth(3);
        
        String fen = "r2qkb1r/pppbpppp/2n5/1B1pN3/3PnB2/4P3/PPP2PPP/RN1QK2R b KQkq - 7 7";
        GameState state  = GameState.fromFEN(fen, rules);
        
        counter.setCount(0);
        double value = nega.eval(state);
        System.out.println(value);
        System.out.println(counter.getCount());
        System.out.println();
        
        counterAB.setCount(0);
        double valueAB = negaAB.eval(state);
        System.out.println(valueAB);
        System.out.println(counterAB.getCount());
        System.out.println();        
        
        
        
        
    }

}