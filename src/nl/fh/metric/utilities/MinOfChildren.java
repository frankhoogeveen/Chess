/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.utilities;

import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rules.GameDriver;

/**
 * Wrapping this around a given baseMetric, it goes one level deep into the 
 * rabbit hole and evaluates the min of the base metric over the children.
 * 
 */
public class MinOfChildren<T extends GameState> implements Metric<T>{

    private final Metric<T> baseMetric;
    private final GameDriver driver;
    
    public MinOfChildren(Metric<T> baseMetric, GameDriver driver){
     this.baseMetric = baseMetric;
     this.driver = driver;     
    }

    @Override
    public double eval(T t) {
        double currentValue = + Double.MAX_VALUE;
        Set<Move> moves = driver.getMoveGenerator().calculateAllLegalMoves(t);
        for(Move m : moves){
            T child = (T) m.applyTo(t);
            double value = baseMetric.eval(child);
            if(value < currentValue){
                currentValue = value;
            }
        }
        
        return currentValue;
    }

    @Override
    public String getDescription() {
        return "Min over children of " + baseMetric.getDescription();
    }

}