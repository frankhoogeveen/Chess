/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.player.evalplayer.Metric;

/**
 *  This wraps around a metric to create the negamax metric 
 *     // https://en.wikipedia.org/wiki/Negamax
 * 
 * This implementation does NOT make use of alpha/beta pruning
 * 
 */
public class NegaMax implements Metric<GameState> {

    private Metric<GameState> baseMetric;
    private int depth;

    public static NegaMax getInstance(Metric<GameState> baseMetric, int depth){
        NegaMax result = new NegaMax();
        result.baseMetric = baseMetric;
        result.depth = depth;
        return result;
    }
    
    @Override
    public double eval(GameState state) {
        return iteration(state, this.depth, state.getToMove().getSign());  
    }

    private double iteration(GameState state, int depth, int sign) {
        if(depth == 0){
            return sign * this.baseMetric.eval(state);
        } 
        
        double currentValue = - Double.MAX_VALUE;
        for(Move m : state.getLegalMoves()){
            GameState daughter = m.applyTo(state);
            double nextValue = - iteration(daughter, depth-1, -sign);
            if(nextValue > currentValue){
                currentValue = nextValue;
            }
        }
        
        return currentValue;
    }
}