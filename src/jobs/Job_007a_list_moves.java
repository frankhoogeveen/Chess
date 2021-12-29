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
import nl.fh.gamestate.chess.ChessState;
import nl.fh.metric.chess.MaterialCountMetric;
import nl.fh.gamestate.chess.move.ChessMove;
import nl.fh.gamestate.Move;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rule.chess.FIDEchess;

/**
 * 
 */
public class Job_007a_list_moves {
    
        
    // list moves at depth two
    public static void main(String[] args){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime date = LocalDateTime.now();
        String dateString = date.format(formatter);
        
        String filePath = "../out/job_007a_"+ dateString + ".csv";
     
        String fen = "k7/8/8/8/8/3K4/3p4/8 b - - 0 1";
        ChessState state = ChessState.fromFEN(fen);

        Metric<ChessState> metric = MaterialCountMetric.getWrappedInstance();

        StringBuilder sb = new StringBuilder();
        
        sb.append(fen);
        sb.append("\n\n");
        sb.append("move1;move2;Value;\n");
        
        for(Move<ChessState> move1 : FIDEchess.getGameDriver().getMoveGenerator().calculateAllLegalMoves(state)){
            ChessState state1 = move1.applyTo(state);

            for(Move<ChessState> move2 : FIDEchess.getGameDriver().getMoveGenerator().calculateAllLegalMoves(state1)){
                ChessState s = move2.applyTo(state1);
                double value2 = metric.eval(s);
                
                sb.append(((ChessMove)move1).formatPGN(state, FIDEchess.getGameDriver()));
                sb.append(";");
                sb.append(((ChessMove)move2).formatPGN(state1, FIDEchess.getGameDriver()));
                sb.append(";");
                sb.append(value2);
                sb.append(";"); 
                sb.append("\n");
              
            }
            
        }
        
        
        try {
            try (FileWriter file = new FileWriter(filePath)) {
                file.write(sb.toString());
                System.out.println("Written to " + filePath);
            }
        } catch (IOException ex) {
            Logger.getLogger(Job_007a_list_moves.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }
}