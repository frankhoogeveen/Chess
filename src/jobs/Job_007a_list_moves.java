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
     
        String fen = "rnb1kbnr/pppp1ppp/8/4p3/4P2q/3P4/PPP2PPP/RNBQKBNR w KQkq - 1 3";
        Rules rules = new SimpleRules();
        GameState state = GameState.fromFEN(fen, rules);

        Metric<GameState> shannon = new ShannonMetric();

        StringBuilder sb = new StringBuilder();
        
        for(Move move1 : state.getLegalMoves()){
            GameState state1 = move1.applyTo(state);
            // minimize the value of the second move;
            GameState stateMin = null;
            double valueMin = + Double.MAX_VALUE;
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