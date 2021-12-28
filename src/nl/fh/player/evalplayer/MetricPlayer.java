/*
 * License: GPL v3
 * 
 */

package nl.fh.player.evalplayer;

import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.gamestate.Move;
import nl.fh.gamestate.chess.move.ChessResignation;
import nl.fh.player.Player;

/**
 * Chess player that decides on moves by evaluating a metric of the game state
 * and picking the move that maximizes (when playing white) the metric.
 * Black will strive to minimize the metric.
 * 
 * 
 * 
 */
public class MetricPlayer<S extends GameState> implements Player<S>{

    private Metric<S> metric;
    
    private MetricPlayer(){
        
    }
    
   /**
    * 
    * @param metric
    * @return a player that evaluates the metric, but does not do any look-ahead
    */
    public static MetricPlayer getInstance(Metric metric){
        MetricPlayer result = new MetricPlayer();
        result.metric = metric;
        return result;
    }
    
    @Override
    public Move<S> getMove(S state, Set<Move<S>> legalMoves) {
        
        int sign = state.getMover().getSign();
        
        double currentBestValue = -Double.MAX_VALUE;
        Move<S> currentBestMove = ChessResignation.getInstance();
        
        for(Move<S> m : legalMoves){
            double value = sign * this.metric.eval(m.applyTo(state));
            if(value > currentBestValue){
                currentBestMove = m;
                currentBestValue = value;
            }
        }
        
        return currentBestMove;
    }

    @Override
    public String getDescription() {
        return "Metric: " + this.metric.getDescription(); 
    }
}