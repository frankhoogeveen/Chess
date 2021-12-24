/*
 * License: GPL v3
 * 
 */
package nl.fh.rules;

import nl.fh.gamestate.GameState;
import java.util.HashSet;
import java.util.Set;
import nl.fh.chess.BoardSide;
import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.chess.MoveRange;
import nl.fh.chess.MoveRangeType;
import nl.fh.chess.PieceKind;
import nl.fh.chess.PieceType;
import nl.fh.move.Castling;
import nl.fh.move.DrawOfferAccepted;
import nl.fh.move.EnPassantCapture;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
import nl.fh.move.Promotion;

/**
 * Represent the rules of the game
 */
public class ChessMoveGenerator implements MoveGenerator{
    
    
    @Override
    public Set<GameState> calculateChildren(GameState state) {
        Set<GameState> result = new HashSet<GameState>();
        for(Move m : calculateAllLegalMoves(state)){
            result.add(m.applyTo(state));
        }
        return result;
    }    
    
    @Override
    public Set<Move> calculateAllLegalMoves(GameState state) {
        
        Set<Move> result = new HashSet<Move>();
        
        if(state.isDrawOffered()){
            result.add(DrawOfferAccepted.getInstance());
        }
       
        addAllPieceMoves(state, result);
        
        addAllCastlingMoves(state, result);
        
        if(state.allowsEnPassant()){
            addAllEnPassantMoves(state, result);
        }
        
        //remove all moves that leave the king in check
        Set<Move> excludes = movesLeavingKingInCheck(result, state);
        result.removeAll(excludes);
        return result;
    } 

    /**
     * 
     * @param state of the game
     * @param result the set to which all valid piece moves are added
     *  The moves resulting in promotion are included here
     */
    private void addAllPieceMoves(GameState state, Set<Move> result) {
        Color toMove = state.getToMove();
        for(Field field : Field.getAll()){
            if(state.getFieldContent(field).getColor() == toMove){
                addAllPieceMovesFromField(state, field, result);
            }
        }
    }

    /**
     * 
     * @param state
     * @param field
     * @param result set to which all the moves from a given field are added
     */
    private void addAllPieceMovesFromField(GameState state, Field field, Set<Move> result) {
        Set<MoveRange> ranges = field.getMoveRanges(state.getFieldContent(field));
        for(MoveRange range : ranges){
            addAllPieceMovesFromRange(state, field, range, result);
        }
    }

    /**
     * 
     * @param state
     * @param field
     * @param range
     * @param result to which all piece moves or promotions are added
     */
    private void addAllPieceMovesFromRange(GameState state, Field field, MoveRange range, Set<Move> result) {
        boolean done = false;
        for(Field to : range.getRange()){
            if(!done){
                PieceType captured = state.getFieldContent(to);
                if(captured == PieceType.EMPTY){
                    if(range.getType() != MoveRangeType.CAPTURE_OBLIGATORY){
                        addPieceMoveOrPromotions(state, field, to, result);
                    }
                } else if(captured.getColor() == state.getToMove()){
                    done = true;
                } else {
                    if(range.getType() != MoveRangeType.CAPTURE_FORBIDDEN){
                        addPieceMoveOrPromotions(state, field, to, result);
                    }
                    done = true;
                }
            }
        }
    }
    
    /**\
     * 
     * @param state
     * @param from
     * @param to
     * @param result
     * 
     * The logic to either add a PieceMove or promotions is contained in this method
     */
    private void addPieceMoveOrPromotions(GameState state, Field from, Field to, Set<Move> result){
        
        PieceKind movingPiece = state.getFieldContent(from).getKind();
        int y = to.getY();
        
        boolean promotion =  (movingPiece == PieceKind.PAWN)&&((y == 7) || (y==0));
        
        if(!promotion){
            result.add(PieceMove.getInstance(from, to));
            return;
        }
        
        if(promotion){
            result.add(Promotion.getInstance(from, to, PieceKind.QUEEN));
            result.add(Promotion.getInstance(from, to, PieceKind.ROOK));
            result.add(Promotion.getInstance(from, to, PieceKind.BISHOP));
            result.add(Promotion.getInstance(from, to, PieceKind.KNIGHT));            
        } 
    }

    /**
     * 
     * @param state
     * @param set to which all legal castling moves are added
     */
    private void addAllCastlingMoves(GameState state, Set<Move> set) {
        if(state.getCastlingAllowedFlag(state.getToMove(), BoardSide.KINGSIDE) && checkCastlingConditions(state, BoardSide.KINGSIDE)){
            set.add(Castling.getInstance(BoardSide.KINGSIDE));
        }
        if(state.getCastlingAllowedFlag(state.getToMove(), BoardSide.QUEENSIDE) && checkCastlingConditions(state, BoardSide.QUEENSIDE)){
            set.add(Castling.getInstance(BoardSide.QUEENSIDE));
        }        
    }

    private boolean checkCastlingConditions(GameState state, BoardSide boardSide) {
        boolean result = true;
        if((state.getToMove() == Color.WHITE) && (boardSide == BoardSide.KINGSIDE)){
            result = result && (state.getFieldContent(4, 0) == PieceType.WHITE_KING);
            result = result && (state.getFieldContent(5, 0) == PieceType.EMPTY);
            result = result && (state.getFieldContent(6, 0) == PieceType.EMPTY);            
            result = result && (state.getFieldContent(7, 0) == PieceType.WHITE_ROOK);
            result = result && (!Field.isCovered(Field.getInstance(4,0), state, Color.BLACK));
            result = result && (!Field.isCovered(Field.getInstance(5,0), state, Color.BLACK));
            result = result && (!Field.isCovered(Field.getInstance(6,0), state, Color.BLACK));            
        }
        
        if((state.getToMove() == Color.BLACK) && (boardSide == BoardSide.KINGSIDE)){
            result = result && (state.getFieldContent(4,7) == PieceType.BLACK_KING);
            result = result && (state.getFieldContent(5,7) == PieceType.EMPTY);
            result = result && (state.getFieldContent(6,7) == PieceType.EMPTY);             
            result = result && (state.getFieldContent(7,7) == PieceType.BLACK_ROOK);
            result = result && (!Field.isCovered(Field.getInstance(4,7), state, Color.WHITE));
            result = result && (!Field.isCovered(Field.getInstance(5,7), state, Color.WHITE));
            result = result && (!Field.isCovered(Field.getInstance(6,7), state, Color.WHITE));            
        }    
        
        if((state.getToMove() == Color.WHITE) && (boardSide == BoardSide.QUEENSIDE)){
            result = result && (state.getFieldContent(4,0) == PieceType.WHITE_KING);
            result = result && (state.getFieldContent(3,0) == PieceType.EMPTY);
            result = result && (state.getFieldContent(2,0) == PieceType.EMPTY);   
            result = result && (state.getFieldContent(1,0) == PieceType.EMPTY);              
            result = result && (state.getFieldContent(0,0) == PieceType.WHITE_ROOK);
            result = result && (!Field.isCovered(Field.getInstance(4,0), state, Color.BLACK));
            result = result && (!Field.isCovered(Field.getInstance(3,0), state, Color.BLACK));
            result = result && (!Field.isCovered(Field.getInstance(2,0), state, Color.BLACK));            
        }
        
        if((state.getToMove() == Color.BLACK) && (boardSide == BoardSide.QUEENSIDE)){
            result = result && (state.getFieldContent(4,7) == PieceType.BLACK_KING);
            result = result && (state.getFieldContent(3,7) == PieceType.EMPTY);
            result = result && (state.getFieldContent(2,7) == PieceType.EMPTY);   
            result = result && (state.getFieldContent(1,7) == PieceType.EMPTY);              
            result = result && (state.getFieldContent(0,7) == PieceType.BLACK_ROOK);
            result = result && (!Field.isCovered(Field.getInstance(4,7), state, Color.WHITE));
            result = result && (!Field.isCovered(Field.getInstance(3,7), state, Color.WHITE));
            result = result && (!Field.isCovered(Field.getInstance(2,7), state, Color.WHITE));            
        }             
        
        return result;
    }
    
    

    
    /**
     * 
     * @param state
     * @param result set to which all allowed castling moves are added 
     */
    private void addAllEnPassantMoves(GameState state, Set<Move> result) {
        if(!state.allowsEnPassant()){
            return;
        }
        
        Field to = state.getEnPassantField();
        
        int x = to.getX();
        if(state.getToMove() == Color.WHITE){
            if(x>0){
                Field from = Field.getInstance(x-1, 4);
                if(state.getFieldContent(from) == PieceType.WHITE_PAWN){
                    result.add(EnPassantCapture.getInstance(from, to));
                }
            } 
            if(x < 7){
                Field from = Field.getInstance(x+1, 4);
                if(state.getFieldContent(from) == PieceType.WHITE_PAWN){                
                    result.add(EnPassantCapture.getInstance(from, to)); 
                }
            }
            
        } else if(state.getToMove() == Color.BLACK) {
            if(x>0){
                Field from = Field.getInstance(x-1, 3);
                if(state.getFieldContent(from) == PieceType.BLACK_PAWN){
                    result.add(EnPassantCapture.getInstance(from, to));
                }
            } 
            if(x < 7){
                Field from = Field.getInstance(x+1, 3);
                if(state.getFieldContent(from) == PieceType.BLACK_PAWN){                
                    result.add(EnPassantCapture.getInstance(from, to)); 
                }
            }            
            
        }
        
        
    }    

    /**
     * 
     * @param set
     * @param state 
     * @return all moves that leave the king in check
     */
    private Set<Move> movesLeavingKingInCheck(Set<Move> set, GameState state) {
        Set<Move> result = new HashSet<Move>();
        for(Move move : set){
            GameState postState = state.apply(move);
            if(kingRemainsInCheckAfterMove(postState)){
                result.add(move);
            }
        }
        return result;
    }

    
    /**
     * 
     * @param postState
     * @return true if the king remained in check after the last move, 
     * or when there is no king (of the color that just moved) on the board.
     */
    private boolean kingRemainsInCheckAfterMove(GameState postState) {
        Color attackerColor = postState.getToMove();
        Color kingColor = attackerColor.flip();
        
        Field kingField = postState.findKing(kingColor);
        
        if(kingField == null){
            // in case there is no king on the board, this makes the postState illegal 
            return true;
        }
        
        boolean result = Field.isCovered(kingField, postState, attackerColor);
        return result;
    }
}

