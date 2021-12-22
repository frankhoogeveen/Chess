/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
import nl.fh.output_file.OutputToFile;
import nl.fh.rules.Perft;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;

/**
 * 
 */
public class Job_009_perft_job {
    
        
    // list moves at depth two
    public static void main(String[] args){
      
        Rules rules = new SimpleRules();
        GameState state = rules.getInitialState();
        
        String result = Perft.details(state, 7);
        
        OutputToFile.write("perft7", result);
        System.out.println(result);        
        
//        Move m1 = PieceMove.getInstance(Field.getInstance("c2"), Field.getInstance("c4"));
//        GameState state = m1.applyTo(state);
//        
//        result = Perft.details(state, 4);
//        System.out.println(result);  

        

    }
}