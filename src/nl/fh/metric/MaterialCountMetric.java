/*
 * License: GPL v3
 * 
 */

package nl.fh.metric;

import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.player.evalplayer.Metric;

/**
 * Evaluates the state of the board, in line with
 * https://www.pi.infn.it/%7Ecarosi/chess/shannon.txt
 * 
 */

public class MaterialCountMetric implements Metric<GameState>{
    
    public static final double MATE_VALUE = 1.e6;    

    @Override
    public double eval(GameState state) {
        double score = 0.;
        
        if(state.getRules().isDrawn(state)){
            return 0.;
        }
        
        score += mateScore(state);
        
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

    private double mateScore(GameState state) {
        double result = 0.;
        
        if(state.getRules().isMate(state)){
            result = - MATE_VALUE * state.getToMove().getSign();
            return result;
        }
        
        if(state.getRules().isMate(state.changeColor())){
            result = + MATE_VALUE * state.getToMove().getSign();
            return result;
        }        

        return result;
    }

    @Override
    public String getDescription() {
        return "MaterialCount";
    }
}