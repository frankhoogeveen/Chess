/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.gamestate.GameState;
import nl.fh.metric.PawnLocationMetric;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;

/**
 * 
 * 
 */
public class Job_005_create_testdata_for_negamax_class {
    public static void main(String[] args){
        Rules rules = new SimpleRules();
        
        // position from the 6th round of the 2021 World Chess Championship
        String fen = "r1b2rk1/1pp1qppp/2n1p3/2N5/p1PP2n1/5NP1/PP3PBP/R2Q1RK1 b - - 1 14";
        
        GameState state = GameState.fromFEN(fen, rules);
        Metric<GameState> metric = new PawnLocationMetric();
        
        list(state, metric);     
        System.out.println();
    }

    private static void list(GameState state, Metric<GameState> metric) {
        double vmin = Double.MAX_VALUE;
        double vmax = -Double.MAX_VALUE;
        
        System.out.println("-------------------------------");
        System.out.println(state.toFEN());
        for(GameState s : state.getChildren()){
            double val = metric.eval(s);
            if(val > vmax){
                vmax = val;
                
            }
            if( val < vmin){
                vmin = val;
            }
            System.out.print(String.format("%,.6f", metric.eval(s)));
            System.out.print(" ");
            System.out.println(s.toFEN());
        }
        System.out.println();
        System.out.println("vmax: " + String.format("%,.6f", vmax));
        System.out.println("vmin: " + String.format("%,.6f", vmin));    
        System.out.println();
    }
}