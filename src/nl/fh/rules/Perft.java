/*
 * License: GPL v3
 * 
 */

package nl.fh.rules;

import nl.fh.gamestate.GameState;
import nl.fh.move.Move;

/**
 *  Doing standard tests of move generators
 * 
 */
public class Perft {
    
    /**
     * 
     * @param state
     * @param depth
     * @return the number of descendents of state at the given depth
     */
    public static long value(GameState state, int depth){
        if(depth <0){
            throw new IllegalArgumentException("Perft with negative depth");
        }
        
        if(depth == 0){
            return 1;
        }
        
        long result = 0;
        for(GameState child : state.getChildren()){
            result += value(child, depth -1);
        }
        
        state.forgetChildren();
        return result;
    }
    
    
    public static String details(GameState state, int depth){
        long t0 = System.currentTimeMillis();
        
        StringBuilder sb = new StringBuilder();
        sb.append("===Perft===\n");
        sb.append(state.toFEN());
        sb.append("\n");
        
        long total = 0;
        for(Move move : state.getLegalMoves()){
            GameState child = move.applyTo(state);
            
            long perft = Perft.value(child, depth-1);
            total += perft;
            
            sb.append(move.getUCI(state));
            sb.append(" ");
            sb.append(perft);
            sb.append("\n");
        }
        
        sb.append("\n");
        sb.append("Total: ");
        sb.append(total);
        sb.append("\n");
        
        sb.append("Time:  ");
        sb.append(System.currentTimeMillis()-t0);
        sb.append(" msec\n");
        
        return sb.toString();
    }
    
    
}