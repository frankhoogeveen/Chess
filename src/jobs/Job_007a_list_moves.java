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
import nl.fh.gamestate.GameState;
import nl.fh.metric.ShannonMetric;
import nl.fh.move.Move;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;

/**
 * 
 */
public class Job_007a_list_moves {
    
        
    
    public static void main(String[] args){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime date = LocalDateTime.now();
        String dateString = date.format(formatter);
        
        String filePath = "../out/job_007a_"+ dateString + ".csv";
     
        String fen = "r1bq1rk1/ppp2pb1/2np1n1p/4P1p1/8/2N1P1B1/PPPNBPPP/R2Q1RK1 b - - 0 12";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);

        Metric<GameState> shannon = new ShannonMetric();

        StringBuilder sb = new StringBuilder();
        
        sb.append(fen);
        sb.append("\n\n");
        sb.append("move1;move2;Value;state1;state2\n");
        
        for(Move move1 : state.getLegalMoves()){
            GameState state1 = move1.applyTo(state);

            for(Move move2 : state1.getLegalMoves()){
                GameState s = move2.applyTo(state1);
                double value2 = shannon.eval(s);
                
                sb.append(move1.moveString(state));
                sb.append(";");
                sb.append(move2.moveString(state1));
                sb.append(";");
                sb.append(value2);
                sb.append(";");                
                sb.append(state1.toFEN());
                sb.append(";");
                sb.append(s.toFEN());
                sb.append(";\n");
              
            }
            
        }
        
        
        try {
            try (FileWriter file = new FileWriter(filePath)) {
                file.write(sb.toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(Job_006_reproduce_and_analyze_intermittent_bug.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }
}