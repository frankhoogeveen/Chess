/*
 * License: GPL v3
 * 
 */

package nl.fh.rule;

import java.util.Set;
import nl.fh.gamestate.chess.BoardSide;
import nl.fh.gamestate.chess.Color;
import nl.fh.gamestate.chess.Field;
import nl.fh.gamestate.chess.PieceType;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.chess.ChessState;
import nl.fh.gamestate.chess.move.ChessMove;
import nl.fh.gamestate.chess.move.DrawOfferAccepted;
import nl.fh.gamestate.Move;
import nl.fh.gamestate.chess.move.ChessResignation;

/**
 * 
 * 
 */
public class ChessResultArbiter implements ResultArbiter<ChessState> {
    
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
    public GameResult determineResult(GameReport<ChessState> report, Set<Move<ChessState>> legalMoves) {
        Move move = report.getFinalMove();
        ChessState state = report.getFinalState();
        
            // resignation ends the game on the spot
            if(move instanceof ChessResignation){
                if(state.getToMove() == Color.WHITE){
                    return GameResult.WIN_FIRST_MOVER;
//                    return ChessGameResult.RESIGNATION_BY_WHITE;
                } else {
                    return GameResult.WIN_SECOND_MOVER;
//                    return ChessGameResult.RESIGNATION_BY_BLACK; 
                }
            }
            
            // accepted draw offers end the game on the spot
            if(move instanceof DrawOfferAccepted){
                return GameResult.DRAW;
//                return ChessGameResult.DRAW_AGREED;
            }             
           
            //insufficient material ends the game on the spot
            if(!sufficientMaterial(state)){
                return GameResult.DRAW;
//                return ChessGameResult.DRAW_INSUFFICIENT_MATERIAL;                
            }

            // (stale) mate ends the game
            if(legalMoves.isEmpty()){
                if(isCheck(state)){
                    if(state.getToMove() == Color.WHITE){
                        return GameResult.WIN_FIRST_MOVER;
//                        return ChessGameResult.WIN_WHITE;
                    } else {
                        return GameResult.WIN_SECOND_MOVER;
//                        return ChessGameResult.WIN_BLACK;
                    }
                } else {
                    return GameResult.DRAW;
//                    return ChessGameResult.DRAW_STALEMATE;
                }
            }
            
            if(isThreeFoldRepetition(report)){
                return GameResult.DRAW;
//                return ChessGameResult.DRAW_BY_THREEFOLD_REPETITION;
            }
            
            if(isAtFiftyMoveRule(state)){
                return GameResult.DRAW;
//                return ChessGameResult.DRAW_BY_50_MOVE_RULE;                
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
    private boolean sufficientMaterial(ChessState currentState) {
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
     * @return true is the FINAL state of the report is a threefold repetition.
     * 
     * In case report is null, false is returned.
     * 
     * 
     */
    public boolean isThreeFoldRepetition(GameReport<ChessState> report) {
        
        if(report == null){
            return false;
        }
        
        
        int nrep = 0;
        ChessState state = report.getFinalState();
        
        for(int n = report.getStateList().size()-1; n >= 0; n--){
            ChessState state2 = report.getStateList().get(n);
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
    public boolean repeats(ChessState state, ChessState state2) {
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
    public boolean isCheck(ChessState state){
        Color playerColor = state.getToMove();
        Color opponentColor = playerColor.flip();
        Field kingField = findKing(playerColor, state);
        
        boolean result = kingField.isCovered(state, opponentColor);
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
    private Field findKing(Color kingColor, ChessState postState) {
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
    
    public boolean isMate(ChessState state, Set<Move<ChessState>> legalMoves){
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
    public boolean isDraw(ChessState state, Set<Move<ChessState>> legalMoves, GameReport<ChessState> report){
        boolean result = false;
        
        result |= isStaleMate(state, legalMoves);
        result |= isAtFiftyMoveRule(state);
        result |= isThreeFoldRepetition(report);
        
        return result;
    }

    boolean isStaleMate(ChessState state, Set<Move<ChessState>> legalMoves){
        return legalMoves.isEmpty() && !isCheck(state);
    }
    
    boolean isAtFiftyMoveRule(ChessState currentState) {
         return (currentState.getHalfMoveClock() >= 100);
    }       

    @Override
    public boolean isSelfMate(ChessState state, Set<Move<ChessState>> legalMoves) {
        return false;
    }
}