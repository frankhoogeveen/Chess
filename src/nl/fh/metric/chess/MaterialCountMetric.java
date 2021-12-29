/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.chess;

import java.util.HashSet;
import java.util.Set;
import nl.fh.gamestate.chess.Field;
import nl.fh.gamestate.chess.ChessState;
import nl.fh.metric.utilities.OutcomeMetric;
import nl.fh.gamestate.chess.move.ChessMove;
import nl.fh.gamestate.Move;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rule.chess.FIDEchess;
import nl.fh.rule.chess.ChessMoveGenerator;
import nl.fh.rule.chess.ChessResultArbiter;
import nl.fh.rule.GameDriver;
import nl.fh.rule.MoveGenerator;
import nl.fh.rule.ResultArbiter;

/**
 * Evaluates the state of the board, in line with
 * https://www.pi.infn.it/%7Ecarosi/chess/shannon.txt
 * 
 */

public class MaterialCountMetric implements Metric<ChessState>{
    
    
    /**
     *  
     */
    public MaterialCountMetric(){

    }
    
    /**
     * returns a  MaterialCount Metric that keeps track of  mate and drawn positions
     */
    public static Metric<ChessState> getWrappedInstance(){
        double mateValue = 1.e6;
        GameDriver driver = new FIDEchess().getGameDriver();
        Metric<ChessState> base = new MaterialCountMetric();
        Metric<ChessState> result = new OutcomeMetric(base, mateValue, driver);
        return result;
    }

    @Override
    public double eval(ChessState state) {
        double score = 0.;
        
        score += materialScore(state);
        
        return score;
    }
    
    private double materialScore(ChessState state){
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