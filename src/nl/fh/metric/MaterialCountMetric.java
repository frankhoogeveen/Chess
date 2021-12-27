/*
 * License: GPL v3
 * 
 */

package nl.fh.metric;

import java.util.HashSet;
import java.util.Set;
import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.metric.utilities.OutcomeMetric;
import nl.fh.move.ChessMove;
import nl.fh.move.Move;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rules.Chess;
import nl.fh.rules.ChessMoveGenerator;
import nl.fh.rules.ChessResultArbiter;
import nl.fh.rules.GameDriver;
import nl.fh.rules.MoveGenerator;
import nl.fh.rules.ResultArbiter;

/**
 * Evaluates the state of the board, in line with
 * https://www.pi.infn.it/%7Ecarosi/chess/shannon.txt
 * 
 */

public class MaterialCountMetric implements Metric<GameState>{
    
    
    /**
     *  
     */
    public MaterialCountMetric(){

    }
    
    /**
     * returns a  MaterialCount Metric that keeps track of  mate and drawn positions
     */
    public static Metric<GameState> getWrappedInstance(){
        double mateValue = 1.e6;
        GameDriver driver = Chess.getGameDriver();
        Metric<GameState> base = new MaterialCountMetric();
        Metric<GameState> result = new OutcomeMetric(base, mateValue, driver);
        return result;
    }

    @Override
    public double eval(GameState state) {
        double score = 0.;
        
        score += materialScore(state);
        
        return score;
    }
    
    private double materialScore(GameState state){
        double score = 0.;
        for(Field f : Field.getAll()){
            switch(state.getFieldContent(f)){
                case WHITE_QUEEN:
                    score += 9;
                    break;
                case BLACK_QUEEN:
                    score += -9;
                    break;
                case WHITE_ROOK:
                    score += 5;
                    break;
                case BLACK_ROOK:
                    score += -5;
                    break;
                case WHITE_BISHOP:
                    score += 3;
                    break;
                case BLACK_BISHOP:
                    score += -3;
                    break;
                case WHITE_KNIGHT:
                    score += 3;
                    break;
                case BLACK_KNIGHT:
                    score += -3;
                    break;
                case WHITE_PAWN:
                    score += 1;
                    break;
                case BLACK_PAWN:
                    score += -1;
                    break;  
                default:
            }
        }
        return score;
    }  

    @Override
    public String getDescription() {
        return "MaterialCount";
    }
}