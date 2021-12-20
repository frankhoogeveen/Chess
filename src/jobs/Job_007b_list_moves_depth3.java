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
import nl.fh.move.Move;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;

/**
 * 
 */
public class Job_007b_list_moves_depth3 {
    
        
    // list moves at depth two
    public static void main(String[] args){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime date = LocalDateTime.now();
        String dateString = date.format(formatter);
        
        String filePath = "../out/job_007a_"+ dateString + ".csv";
     
        String fen = "k7/ppr5/8/8/8/8/7R/7K w - - 0 1";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);

        Metric<GameState> metric = new MaterialCountMetric();
        NegaMax<GameState> nega = new NegaMax(metric, 0);

        StringBuilder sb = new StringBuilder();
        
        sb.append(fen);
        sb.append("\n");
        
        nega.setDepth(3);
        double value3 = nega.eval(state);  
        sb.append(value3);
        
        sb.append("\n\n");
        sb.append("move1;move2;move3;depth0;depth1;depth2;\n");
        
        for(Move move1 : state.getLegalMoves()){
            GameState state1 = move1.applyTo(state);
            nega.setDepth(2);
            double value2 = nega.eval(state1);              

            for(Move move2 : state1.getLegalMoves()){
                GameState state2 = move2.applyTo(state1);
                
                nega.setDepth(1);
                double value1 = nega.eval(state2);             
                
                for(Move move3 : state2.getLegalMoves()){
                    
                    GameState state3 = move3.applyTo(state2);
                    
                    nega.setDepth(0);
                    double value0 = nega.eval(state3);  

                    sb.append(move1.moveString(state));
                    sb.append(";");
                    sb.append(move2.moveString(state1));
                    sb.append(";");
                    sb.append(move3.moveString(state2));
                    sb.append(";");                    
                    sb.append(value0);
                    sb.append(";"); 
                    sb.append(value1);
                    sb.append(";"); 
                    sb.append(value2);
                    sb.append(";");                     
                    sb.append("\n");
                }

              
            }
            
        }
        
        
        try {
            try (FileWriter file = new FileWriter(filePath)) {
                file.write(sb.toString());
                System.out.println("Written to " + filePath);
            }
        } catch (IOException ex) {
            Logger.getLogger(Job_006_reproduce_and_analyze_intermittent_bug.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }
}