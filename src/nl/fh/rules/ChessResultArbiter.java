/*
 * License: GPL v3
 * 
 */

package nl.fh.rules;

import java.util.Set;
import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.chess.PieceType;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.GameState;
import nl.fh.move.ChessMove;
import nl.fh.move.DrawOfferAccepted;
import nl.fh.move.Move;
import nl.fh.move.Resignation;

/**
 * 
 * 
 */
public class ChessResultArbiter implements ResultArbiter {

    @Override
    public GameResult updateResult(GameReport report, Set<Move> legalMoves) {
        Move move = report.getFinalMove();
        GameState state = report.getFinalState();
        
            // resignation ends the game on the spot
            if(move instanceof Resignation){
                if(state.getToMove() == Color.WHITE){
                    return GameResult.RESIGNATION_BY_WHITE;
                } else {
                    return GameResult.RESIGNATION_BY_BLACK; 
                }
            }
            
            // accepted draw offers end the game on the spot
            if(move instanceof DrawOfferAccepted){
                return GameResult.DRAW_AGREED;
            }             
           
            //insufficient material ends the game on the spot
            if(!sufficientMaterial(state)){
                return GameResult.DRAW_INSUFFICIENT_MATERIAL;                
            }

            // (stale) mate ends the game
            if(legalMoves.isEmpty()){
                if(isCheck(state)){
                    if(state.getToMove() == Color.WHITE){
                        return GameResult.WIN_WHITE;
                    } else {
                        return GameResult.WIN_BLACK;
                    }
                } else {
                    return GameResult.DRAW_STALEMATE;
                }
            }
            
            if(isThreeFoldRepetition(report)){
                report.setResult(GameResult.DRAW_BY_THREEFOLD_REPETITION);
            }
            
            // fifty move rule
            if(isAtFiftyMoveRule(state)){
                report.setResult(GameResult.DRAW_BY_50_MOVE_RULE);                
            }

                
       return GameResult.UNDECIDED;

    }
    
    /**
     * 
     * @param currentState
     * @return true if there still is sufficient material on the board to mate 
     * 
     * This method only declares insufficient material for 
     * - K vs K
     * - K vs KN
     * - K vs KB
     */
    private boolean sufficientMaterial(GameState currentState) {
        int whiteB = 0;
        int whiteN = 0;
        int blackB = 0;
        int blackN = 0;
        for(Field f : Field.getAll()){
            switch(currentState.getFieldContent(f)){
                case EMPTY:
                    break;
                case WHITE_PAWN:
                case BLACK_PAWN:
                case WHITE_ROOK:
                case BLACK_ROOK:
                case WHITE_QUEEN:
                case BLACK_QUEEN:
                    return true;
                case WHITE_KING:
                case BLACK_KING:
                    break;
                case WHITE_BISHOP:
                    whiteB += 1;
                    if(whiteB > 1){
                        return true;
                    }
                    break;
                case BLACK_BISHOP:
                    blackB += 1;
                    if(blackB > 1){
                        return true;
                    }
                    break;
                case WHITE_KNIGHT:
                    whiteN += 1;
                    if(whiteN > 1){
                        return true;
                    }
                    break;
                case BLACK_KNIGHT:
                    blackN += 1;
                    if(blackN > 1){
                        return true;
                    }
                    break;
                default:
                    System.out.println(currentState.toFEN());
                    System.out.println(f);
                    System.out.println(currentState.getFieldContent(f));
                    throw new IllegalStateException("switch statement not complete");
            }
        }
        
        return false;
    }    
    
    private boolean isThreeFoldRepetition(GameReport report) {
        int nrep = 0;
        GameState state = report.getFinalState();
        for(GameState state2 : report.getStateList()){
            if(state2.repeats(state)){
                nrep += 1;
            }
        }
        return (nrep >= 3);
    }    
    
    
    /**
     * 
     * @param state
     * @return true if the king that has to move next is in check 
     */
    public boolean isCheck(GameState state){
        Color playerColor = state.getToMove();
        Color opponentColor = playerColor.flip();
        Field kingField = findKing(playerColor, state);
        
        boolean result =Field.isCovered(kingField, state, opponentColor);
        return result;
    }    

     /**
     * 
     * @param state
     * @param color
     * @return the field where the king of the given color stands 
     * 
     * If there is no king of the given color on the board, null is returned.
     * If there are more than one kings of the given color on the board,
     * a field containing the king is returned.
     */
    private Field findKing(Color kingColor, GameState postState) {
        PieceType kingPiece;
        if(kingColor == Color.WHITE){
            kingPiece = PieceType.WHITE_KING;
        } else {
            kingPiece = PieceType.BLACK_KING;
        }
        Field kingField = null;
        for(Field f : Field.getAll()){
            if(postState.getFieldContent(f) == kingPiece){
                kingField = f;
            }
        }
        return kingField;
    }
    
    public boolean isMate(GameState state, Set<ChessMove> legalMoves){
        return legalMoves.isEmpty() && isCheck(state);
    }
    
    public boolean isDrawn(GameState state, Set<ChessMove> legalMoves){
        boolean result = false;
        
        result |= isStaleMate(state, legalMoves);
        result |= isThreeFoldRepetition(state);
        result |= isAtFiftyMoveRule(state);
        
        return result;
    }

    boolean isStaleMate(GameState state, Set<ChessMove> legalMoves){
        return legalMoves.isEmpty() && !isCheck(state);
    }
    
    boolean isAtFiftyMoveRule(GameState currentState) {
         return (currentState.getHalfMoveClock() >= 100);
    }   
    
    boolean isThreeFoldRepetition(GameState state) {
        return (state.countRepetitions() > 2);
    }        


}