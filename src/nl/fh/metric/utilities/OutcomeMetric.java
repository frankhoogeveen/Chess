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
import nl.fh.rule.MoveGenerator;
import nl.fh.rule.ResultArbiter;

/**
 * Evaluates the state of the board, in line with
 * https://www.pi.infn.it/%7Ecarosi/chess/shannon.txt
 * 
 */

public class OutcomeMetric<S extends GameState> implements Metric<S>{
    
    private final double mateValue;
    private final ResultArbiter<S> arbiter;
    private final MoveGenerator<S> moveGenerator;
    private final Metric<S> baseMetric;
    

    /**
     * 
     * @param baseMetric
     * @param mateValue
     * @param driver 
     * 
     * 
     * This metric returns +/- mateValue if the position is mate, 0 if it is a
     * draw and the value of the baseMetric otherwise. 
     * The decision if the position is mate or a draw is made by the result arbiter
     * off the game driver, but NOT using the history of the game state. 
     */
    public OutcomeMetric(Metric<S> baseMetric, double mateValue, GameDriver<S> driver){
        this.baseMetric = baseMetric;
        this.arbiter = driver.getResultArbiter();
        this.moveGenerator = driver.getMoveGenerator();
        
        this.mateValue = mateValue;
    }

    @Override
    public double eval(S state) {
        
        double sign = state.getMover().getSign();
        
        Set<Move<S>> legalMoves = moveGenerator.calculateAllLegalMoves(state);
        
        if(arbiter.isWin(state, legalMoves)){
            return - sign * mateValue;
        }

        if(arbiter.isLoss(state, legalMoves)){
            return +sign * mateValue;
        }
        
        if(arbiter.isDraw(state, legalMoves, null)){
            return 0.;
        }
        
        return this.baseMetric.eval(state);
    }
    


    @Override
    public String getDescription() {
        return "OutcomeMetric/" + baseMetric.getDescription();
    }
}