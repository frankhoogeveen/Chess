/*
 * License: GPL v3
 * 
 */

package nl.fh.player.evalplayer;

import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.move.Resignation;
import nl.fh.player.Player;

/**
 * Chess player that decides on moves by evaluating a metric of the game state
 * and picking the move that maximizes (when playing white) the metric.
 * Black will strive to minimize the metric.
 * 
 * 
 * 
 */
public class MetricPlayer implements Player{

    private Metric<GameState> metric;
    
    private MetricPlayer(){
        
    }
    
   /**
    * 
    * @param metric
    * @return a player that evaluates the metric, but does not do any look-ahead
    */
    public static MetricPlayer getInstance(Metric<GameState> metric){
        MetricPlayer result = new MetricPlayer();
        result.metric = metric;
        return result;
    }
    
    @Override
    public Move getMove(GameState state) {
        
        double sign = state.getToMove().getSign();
        
        double currentBestValue = -Double.MAX_VALUE;
        Move currentBestMove = Resignation.getInstance();
        
        for(Move m : state.getLegalMoves()){
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