/*
 * License: GPL v3
 * 
 */

package nl.fh.metric;

import java.util.HashSet;
import java.util.Set;
import nl.fh.chess.Color;
import nl.fh.gamestate.GameState;
import nl.fh.move.ChessMove;
import nl.fh.move.Move;
import nl.fh.player.evalplayer.Metric;
import nl.fh.rules.Chess;
import nl.fh.rules.ChessMoveGenerator;
import nl.fh.rules.ChessResultArbiter;
import nl.fh.rules.GameDriver;

/**
 * Evaluates the state of the board, in line with
 * https://www.pi.infn.it/%7Ecarosi/chess/shannon.txt
 * 
 */

public class ShannonMetric implements Metric<GameState>{ 
    private static GameDriver gameDriver = Chess.getGameDriver();
    
    private static MaterialCountMetric material = new MaterialCountMetric(gameDriver);
    public final ChessResultArbiter arbiter = (ChessResultArbiter) gameDriver.getResultArbiter();
    private final ChessMoveGenerator moveGenerator = (ChessMoveGenerator) gameDriver.getMoveGenerator();
    
    @Override
    public double eval(GameState state) {
        double score = 0.;
        
        //TODO we are checking twice for draw by calculating the legalmoves here and in the MaterialMetric
        Set<Move> legalMoves = moveGenerator.calculateAllLegalMoves(state);
        HashSet<ChessMove> legalChessMoves = new HashSet<ChessMove>();
        for(Move m : legalMoves){
            legalChessMoves.add((ChessMove)m);
        }
        
        if(arbiter.isDraw(state, legalChessMoves, null)){
            return 0.;
        }    
        
        
        score = material.eval(state);
        score += 0.1 * movesScore(state, legalChessMoves);

        return score;
    }
    
    private double movesScore(GameState state, Set<ChessMove> legalChessMoves){
       GameState opponent = state.changeColor();
       Set<Move> opponentMoves = this.moveGenerator.calculateAllLegalMoves(opponent);
       
       double score = legalChessMoves.size() - opponentMoves.size();
       if(state.getToMove() == Color.BLACK){
           score = -score;
       }
       return score;
       
    }    
    
    @Override
    public String getDescription() {
        return "Shannon Metric";
    }
}