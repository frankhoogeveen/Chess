/*
 * License: GPL v3
 * 
 */

package nl.fh.move;

import nl.fh.rules.ChessResultArbiter;
import java.util.Objects;
import nl.fh.chess.BoardSide;
import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.chess.PieceType;
import nl.fh.gamestate.GameState;
import nl.fh.rules.Chess;
import nl.fh.rules.GameDriver;

/**
 * copyright F. Hoogeveen
 * @author frank
 */
public class Castling extends ChessMove {

    private BoardSide boardSide;
    private boolean offeredDraw;

    private Castling(){
        
    }
    
    /**
     * 
     * @param boardSide 
     * @return 
     */
    public static Castling getInstance(BoardSide boardSide){
        Castling result = new Castling();
        result.boardSide = boardSide;
        result.offeredDraw = false;
        return result;
    }
    
    @Override
    public GameState applyTo(GameState state) {
        GameState result = state.copy();
        
        // uodate the counters
        result.increment();     
        
        // update the castling flags
        Color toMove = state.getToMove();        
        result.clearCastlingAllowedFlag(toMove, BoardSide.KINGSIDE);
        result.clearCastlingAllowedFlag(toMove, BoardSide.QUEENSIDE); 
        
        // update the en passant information
        result.clearEnPassant();
        
        // move the pieces
        if((toMove == Color.WHITE) && (this.boardSide == BoardSide.KINGSIDE)){
            result.setFieldContent(Field.getInstance("e1"), PieceType.EMPTY);
            result.setFieldContent(Field.getInstance("h1"), PieceType.EMPTY); 
            result.setFieldContent(Field.getInstance("g1"), PieceType.WHITE_KING);
            result.setFieldContent(Field.getInstance("f1"), PieceType.WHITE_ROOK);   
            
        } else if((toMove == Color.WHITE) && (this.boardSide == BoardSide.QUEENSIDE)){ 
            result.setFieldContent(Field.getInstance("e1"), PieceType.EMPTY);
            result.setFieldContent(Field.getInstance("a1"), PieceType.EMPTY); 
            result.setFieldContent(Field.getInstance("c1"), PieceType.WHITE_KING);
            result.setFieldContent(Field.getInstance("d1"), PieceType.WHITE_ROOK);               
            
        } else if((toMove == Color.BLACK) && (this.boardSide == BoardSide.KINGSIDE)){ 
            result.setFieldContent(Field.getInstance("e8"), PieceType.EMPTY);
            result.setFieldContent(Field.getInstance("h8"), PieceType.EMPTY); 
            result.setFieldContent(Field.getInstance("g8"), PieceType.BLACK_KING);
            result.setFieldContent(Field.getInstance("f8"), PieceType.BLACK_ROOK);               
            
        } else if((toMove == Color.BLACK) && (this.boardSide == BoardSide.QUEENSIDE)){   
            result.setFieldContent(Field.getInstance("e8"), PieceType.EMPTY);
            result.setFieldContent(Field.getInstance("a8"), PieceType.EMPTY); 
            result.setFieldContent(Field.getInstance("c8"), PieceType.BLACK_KING);
            result.setFieldContent(Field.getInstance("d8"), PieceType.BLACK_ROOK);               
        }
        
        //update the parent field
        result.setParent(state);
        
        return result;
    }

    @Override
    //TODO add + and # indicators
    public String formatPGN(GameState state, GameDriver driver) {
        switch(boardSide){
            case KINGSIDE:
                return "O-O";
            case QUEENSIDE:
                return "O-O-O";
            default:
                throw new IllegalStateException("This should not happen in castling");
        }   
    }
    
    @Override
    public String formatUCI(GameState state){
        Color toMove = state.getToMove();
        if((toMove == Color.WHITE) && (boardSide == BoardSide.KINGSIDE)){
            return "e1g1";
        }
        if((toMove == Color.WHITE) && (boardSide == BoardSide.QUEENSIDE)){
            return "e1c1";
        }
        if((toMove == Color.BLACK) && (boardSide == BoardSide.KINGSIDE)){
            return "e8f8";
        }
        if((toMove == Color.BLACK) && (boardSide == BoardSide.QUEENSIDE)){
            return "e8c8";
        }
        
        return "0000";

    }

    @Override
    public boolean offeredDraw() {
        return this.offeredDraw;
    }

    @Override
    public void offerDraw() {
        this.offeredDraw = true;
    }
    
    @Override
    public String toString(){
        return formatPGN(null, Chess.gameDriver);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.boardSide);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Castling other = (Castling) obj;
        if (this.boardSide != other.boardSide) {
            return false;
        }
        return true;
    }
    
    @Override
    public Field getTo() {
        throw new UnsupportedOperationException("Not defined"); 
    }

    @Override
    public Field getFrom() {
        throw new UnsupportedOperationException("Not defined"); 
    }  
    
    
}
