/*
 * License: GPL v3
 * 
 */
package nl.fh.rule.chess;

import nl.fh.gamestate.chess.ChessState;
import java.util.HashSet;
import java.util.Set;
import nl.fh.gamestate.chess.BoardSide;
import nl.fh.gamestate.chess.Color;
import nl.fh.gamestate.chess.Field;
import nl.fh.gamestate.chess.MoveRange;
import nl.fh.gamestate.chess.MoveRangeType;
import nl.fh.gamestate.chess.PieceKind;
import nl.fh.gamestate.chess.PieceType;
import nl.fh.gamestate.chess.move.Castling;
import nl.fh.gamestate.chess.move.DrawOfferAccepted;
import nl.fh.gamestate.chess.move.EnPassantCapture;
import nl.fh.gamestate.Move;
import nl.fh.gamestate.chess.move.PieceMove;
import nl.fh.gamestate.chess.move.Promotion;
import nl.fh.rule.MoveGenerator;

/**
 * Represent the rules of the game
 */
public class ChessMoveGenerator implements MoveGenerator<ChessState>{
    
    
    @Override
    public Set<ChessState> calculateChildren(ChessState state) {
        Set<ChessState> result = new HashSet<ChessState>();
        for(Move<ChessState> m : calculateAllLegalMoves(state)){
            result.add(m.applyTo(state));
        }
        return result;
    }    
    
    @Override
    public Set<Move<ChessState>> calculateAllLegalMoves(ChessState state) {
        
        Set<Move<ChessState>> result = new HashSet<Move<ChessState>>();
        
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
    private void addAllPieceMoves(ChessState state, Set<Move<ChessState>> result) {
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
    private void addAllPieceMovesFromField(ChessState state, Field field, Set<Move<ChessState>> result) {
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
    private void addAllPieceMovesFromRange(ChessState state, Field field, MoveRange range, Set<Move<ChessState>> result) {
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
    private void addPieceMoveOrPromotions(ChessState state, Field from, Field to, Set<Move<ChessState>> result){
        
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
    private void addAllCastlingMoves(ChessState state, Set<Move<ChessState>> set) {
        if(state.getCastlingAllowedFlag(state.getToMove(), BoardSide.KINGSIDE) && checkCastlingConditions(state, BoardSide.KINGSIDE)){
            set.add(Castling.getInstance(BoardSide.KINGSIDE));
        }
        if(state.getCastlingAllowedFlag(state.getToMove(), BoardSide.QUEENSIDE) && checkCastlingConditions(state, BoardSide.QUEENSIDE)){
            set.add(Castling.getInstance(BoardSide.QUEENSIDE));
        }        
    }

    private boolean checkCastlingConditions(ChessState state, BoardSide boardSide) {
        boolean result = true;
        if((state.getToMove() == Color.WHITE) && (boardSide == BoardSide.KINGSIDE)){
            result = result && (state.getFieldContent(4, 0) == PieceType.WHITE_KING);
            result = result && (state.getFieldContent(5, 0) == PieceType.EMPTY);
            result = result && (state.getFieldContent(6, 0) == PieceType.EMPTY);            
            result = result && (state.getFieldContent(7, 0) == PieceType.WHITE_ROOK);
            result = result && (!Field.getInstance(4,0).isCovered(state, Color.BLACK));
            result = result && (!Field.getInstance(5,0).isCovered(state, Color.BLACK));
            result = result && (!Field.getInstance(6,0).isCovered(state, Color.BLACK));            
        }
        
        if((state.getToMove() == Color.BLACK) && (boardSide == BoardSide.KINGSIDE)){
            result = result && (state.getFieldContent(4,7) == PieceType.BLACK_KING);
            result = result && (state.getFieldContent(5,7) == PieceType.EMPTY);
            result = result && (state.getFieldContent(6,7) == PieceType.EMPTY);             
            result = result && (state.getFieldContent(7,7) == PieceType.BLACK_ROOK);
            result = result && (!Field.getInstance(4,7).isCovered(state, Color.WHITE));
            result = result && (!Field.getInstance(5,7).isCovered(state, Color.WHITE));
            result = result && (!Field.getInstance(6,7).isCovered(state, Color.WHITE));            
        }    
        
        if((state.getToMove() == Color.WHITE) && (boardSide == BoardSide.QUEENSIDE)){
            result = result && (state.getFieldContent(4,0) == PieceType.WHITE_KING);
            result = result && (state.getFieldContent(3,0) == PieceType.EMPTY);
            result = result && (state.getFieldContent(2,0) == PieceType.EMPTY);   
            result = result && (state.getFieldContent(1,0) == PieceType.EMPTY);              
            result = result && (state.getFieldContent(0,0) == PieceType.WHITE_ROOK);
            result = result && (!Field.getInstance(4,0).isCovered(state, Color.BLACK));
            result = result && (!Field.getInstance(3,0).isCovered(state, Color.BLACK));
            result = result && (!Field.getInstance(2,0).isCovered(state, Color.BLACK));            
        }
        
        if((state.getToMove() == Color.BLACK) && (boardSide == BoardSide.QUEENSIDE)){
            result = result && (state.getFieldContent(4,7) == PieceType.BLACK_KING);
            result = result && (state.getFieldContent(3,7) == PieceType.EMPTY);
            result = result && (state.getFieldContent(2,7) == PieceType.EMPTY);   
            result = result && (state.getFieldContent(1,7) == PieceType.EMPTY);              
            result = result && (state.getFieldContent(0,7) == PieceType.BLACK_ROOK);
            result = result && (!Field.getInstance(4,7).isCovered(state, Color.WHITE));
            result = result && (!Field.getInstance(3,7).isCovered(state, Color.WHITE));
            result = result && (!Field.getInstance(2,7).isCovered(state, Color.WHITE));         
        }             
        
        return result;
    }
    
    

    
    /**
     * 
     * @param state
     * @param result set to which all allowed castling moves are added 
     */
    private void addAllEnPassantMoves(ChessState state, Set<Move<ChessState>> result) {
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
    private Set<Move> movesLeavingKingInCheck(Set<Move<ChessState>> set, ChessState state) {
        Set<Move> result = new HashSet<Move>();
        for(Move move : set){
            ChessState postState = state.apply(move);
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
    private boolean kingRemainsInCheckAfterMove(ChessState postState) {
        Color attackerColor = postState.getToMove();
        Color kingColor = attackerColor.flip();
        
        Field kingField = postState.findKing(kingColor);
        
        if(kingField == null){
            // in case there is no king on the board, this makes the postState illegal 
            return true;
        }
        
        boolean result = kingField.isCovered(postState, attackerColor);
        return result;
    }
}

