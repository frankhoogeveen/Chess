/*
 * License: GPL v3
 * 
 */

package nl.fh.rules;

import java.util.Arrays;
import java.util.Set;
import nl.fh.chess.BoardSide;
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
    
   private final MoveGenerator moveGenerator;
   
   /**
    * The result arbiter needs to have access to the move generator in order
    * to decide on threefold repetition. A position only counts as a repetition,
    * if the moves that can be made from that position are the identical. Art 9.2
    * FIDE laws of Chess.
    * 
    * Example of a game where this matters:
    * [Variant "From Position"]
    * [FEN "7k/2p5/8/K2P3r/8/8/8/8 b - - 0 1"]
    *
    * 1... c5 2. Ka4 Kg8 3. Ka5 Kh8 4. Ka4 Kg8 5. Ka5 Kh8
    * 
    * 
    * @param moveGenerator 
    */
   public ChessResultArbiter(MoveGenerator moveGenerator){
       this.moveGenerator = moveGenerator;
   }

    @Override
    public GameResult determineResult(GameReport report, Set<Move> legalMoves) {
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
                return GameResult.DRAW_BY_THREEFOLD_REPETITION;
            }
            
            if(isAtFiftyMoveRule(state)){
                return GameResult.DRAW_BY_50_MOVE_RULE;                
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
    
    /**
     * 
     * @param report
     * @return true is the FINAL state of the report is a threefold repetition 
     * 
     * 
     */
    public boolean isThreeFoldRepetition(GameReport report) {
        
        int nrep = 0;
        GameState state = report.getFinalState();
        
        for(int n = report.getStateList().size()-1; n >= 0; n--){
            GameState state2 = report.getStateList().get(n);
            if(repeats(state, state2)){
                nrep += 1;
            }  
        }

        return (nrep >= 2);
    }  
    
    /**
     * 
     * @param state
     * @return true if this repeats this game state in the sense of the FIDE 
     * rules of chess art 9.2:
     * - the same player is to move
     * - the castling information should be the same* 
     * - the location of the pieces on the board should be the same
     * - the same moves (e.g. en passant captures should be allowed). This differs from just
     *   having the en passant fields being equal.
     * 
     * This method returns false if state is equal to state2
     */
    boolean repeats(GameState state, GameState state2) {
        if (state == state2) {
            return false;
        }
        
        if (state.getColor() != state2.getColor()) {
            return false;
        }        

        if (state.getCastlingAllowedFlag(Color.WHITE, BoardSide.KINGSIDE) 
                != state2.getCastlingAllowedFlag(Color.WHITE, BoardSide.KINGSIDE))  {
            return false;
        }
        if (state.getCastlingAllowedFlag(Color.WHITE, BoardSide.QUEENSIDE) 
                != state2.getCastlingAllowedFlag(Color.WHITE, BoardSide.QUEENSIDE))  {
            return false;
        }
        if (state.getCastlingAllowedFlag(Color.BLACK, BoardSide.KINGSIDE) 
                != state2.getCastlingAllowedFlag(Color.BLACK, BoardSide.KINGSIDE))  {
            return false;
        }
        if (state.getCastlingAllowedFlag(Color.BLACK, BoardSide.QUEENSIDE) 
                != state2.getCastlingAllowedFlag(Color.BLACK, BoardSide.QUEENSIDE))  {
            return false;
        }

        for(Field field : Field.getAll()){
            if(state.getFieldContent(field) != state2.getFieldContent(field)){
                return false;
            }
        }
        
        if(!moveGenerator.calculateAllLegalMoves(state).equals(moveGenerator.calculateAllLegalMoves(state2))){
            return false;
        }

        return true;
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
    
    /**
     * 
     * @param state
     * @param legalMoves the legal moves arising from state
     * @param report     the report of the game upto and including state or null
     * @return  true if the outcome of the game is a draw
     * If the report is null, no check for three fold repetition is made.
     * 
     */
    //TODO the construction with report != is kludgy.
    // there should be two slightly different result arbiters. w/ and w/o the three fold repetition
    // (one wrapped around the other??, or subclassing??)
    // A Metric<GameState> could use either one. Which of course impacts performance
    public boolean isDraw(GameState state, Set<ChessMove> legalMoves, GameReport report){
        boolean result = false;
        
        result |= isStaleMate(state, legalMoves);
        result |= isAtFiftyMoveRule(state);
        if(report != null){
                result |= isThreeFoldRepetition(report);
        }
        
        return result;
    }

    boolean isStaleMate(GameState state, Set<ChessMove> legalMoves){
        return legalMoves.isEmpty() && !isCheck(state);
    }
    
    boolean isAtFiftyMoveRule(GameState currentState) {
         return (currentState.getHalfMoveClock() >= 100);
    }       
}