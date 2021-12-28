/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.utilities;

import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.gamestate.Move;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rule.GameDriver;

/**
 * Wrapping this around a given baseMetric, it goes one level deep into the 
 * rabbit hole and evaluates the max of the base metric over the children.
 * Use for testing purposes only.
 * 
 */
public class MaxOfChildren<S extends GameState> implements Metric<S>{

    private final Metric<S> baseMetric;
    private final GameDriver driver;
    
    public MaxOfChildren(Metric<S> baseMetric, GameDriver driver){
     this.baseMetric = baseMetric;
     this.driver = driver;
    }

    @Override
    public double eval(S state) {
        double currentValue = - Double.MAX_VALUE;
        Set<Move> moves = driver.getMoveGenerator().calculateAllLegalMoves(state);
        for(Move m : moves){
            S child = (S) m.applyTo(state);
            double value = baseMetric.eval(child);
            if(value > currentValue){
                currentValue = value;
            }
        }
        
        return currentValue;
    }

    @Override
    public String getDescription() {
        return "Max over children of " + baseMetric.getDescription();
    }

}