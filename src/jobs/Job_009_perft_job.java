/*
 * License: GPL v3
 * 
 */

package jobs;

import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
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
        
        String result = Perft.details(state, 5);
        System.out.println(result);        
        
//        Move m1 = PieceMove.getInstance(Field.getInstance("c2"), Field.getInstance("c4"));
//        GameState state2 = m1.applyTo(state);
//        
//        String result2 = Perft.details(state2, 4);
//        System.out.println(result2);  
//        
//        Move m2= PieceMove.getInstance(Field.getInstance("b7"), Field.getInstance("b6"));
//        GameState state3 = m2.applyTo(state2);
//        
//        String result3 = Perft.details(state3, 3);
//        System.out.println(result3);   
//   
//        Move m3= PieceMove.getInstance(Field.getInstance("c4"), Field.getInstance("c5"));
//        GameState state4 = m3.applyTo(state3);
//        
//        String result4 = Perft.details(state4, 2);
//        System.out.println(result4);  
//        
//        Move m4= PieceMove.getInstance(Field.getInstance("b6"), Field.getInstance("b5"));
//        GameState state5 = m4.applyTo(state4);
//        
//        String result5 = Perft.details(state5, 1);
//        System.out.println(result5);          

    }
}