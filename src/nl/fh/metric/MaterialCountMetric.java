/*
 * License: GPL v3
 * 
 */

package nl.fh.metric;

import java.util.HashSet;
import java.util.Set;
import nl.fh.chess.Field;
import nl.fh.gamestate.GameState;
import nl.fh.move.ChessMove;
import nl.fh.move.Move;
import nl.fh.player.evalplayer.Metric;
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
    
    public static final double MATE_VALUE = 1.e6;
    public final ChessResultArbiter arbiter;
    private final ChessMoveGenerator moveGenerator;
    
    /**
     * 
     * @param arbiter object that detects (stale)mate and other draws 
     */
    public MaterialCountMetric(GameDriver driver){
        this.arbiter = (ChessResultArbiter)driver.getResultArbiter();
        this.moveGenerator = (ChessMoveGenerator) driver.getMoveGenerator();
    }

    @Override
    public double eval(GameState state) {
        double score = 0.;
        
        //TODO repair the kludgy casting here is a code smell
        Set<Move> legalMoves = moveGenerator.calculateAllLegalMoves(state);
        HashSet<ChessMove> legalChessMoves = new HashSet<ChessMove>();
        for(Move m : legalMoves){
            legalChessMoves.add((ChessMove)m);
        }
        
        if(arbiter.isDrawn(state, legalChessMoves)){
            return 0.;
        }
        
        score += mateScore(state, legalChessMoves);
        
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

    private double mateScore(GameState state, Set<ChessMove> legalChessMoves) {
        double result = 0.;
        
        if(arbiter.isMate(state, legalChessMoves)){
            result = - MATE_VALUE * state.getToMove().getSign();
            return result;
        }
        
        
        GameState opponentState = state.changeColor();
        Set<Move> opponentLegalMoves = this.moveGenerator.calculateAllLegalMoves(opponentState);
        Set<ChessMove> opponentChessMoves = new HashSet<ChessMove>();
        for(Move m : opponentLegalMoves){
            opponentChessMoves.add((ChessMove) m);
        }
        if(arbiter.isMate(opponentState, opponentChessMoves)){
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