/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.utilities;

import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.move.ChessMove;
import nl.fh.move.Move;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rule.ChessMoveGenerator;
import nl.fh.rule.ChessResultArbiter;
import nl.fh.rule.GameDriver;

/**
 * Evaluates the state of the board, in line with
 * https://www.pi.infn.it/%7Ecarosi/chess/shannon.txt
 * 
 */

public class OutcomeMetric implements Metric<GameState>{
    
    private final double mateValue;
    private final ChessResultArbiter arbiter;
    private final ChessMoveGenerator moveGenerator;
    private final Metric<GameState> baseMetric;
    

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
    public OutcomeMetric(Metric<GameState> baseMetric, double mateValue, GameDriver driver){
        this.baseMetric = baseMetric;
        this.arbiter = (ChessResultArbiter)driver.getResultArbiter();
        this.moveGenerator = (ChessMoveGenerator) driver.getMoveGenerator();
        
        this.mateValue = mateValue;
    }

    @Override
    public double eval(GameState state) {
        
        double sign = state.getToMove().getSign();
        
        Set<Move> legalMoves = moveGenerator.calculateAllLegalMoves(state);
        Set<ChessMove> legalChessMoves = (Set<ChessMove>)(Set)legalMoves;
        
        if(arbiter.isMate(state, legalChessMoves)){
            return - sign * mateValue;
        }
        
        GameState state2 = state.changeColor();
        if(arbiter.isMate(state2, legalChessMoves)){
            return + sign * mateValue;
        }        
        
        if(arbiter.isDraw(state, legalChessMoves, null)){
            return 0.;
        }
        
        return this.baseMetric.eval(state);
    }
    


    @Override
    public String getDescription() {
        return "OutcomeMetric/" + baseMetric.getDescription();
    }
}