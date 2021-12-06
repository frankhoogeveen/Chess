/*
 * License: GPL v3
 * 
 */
package nl.fh.move;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import nl.fh.chess.BoardSide;
import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.chess.PieceType;
import nl.fh.gamestate.GameState;
import nl.fh.rules.Rules;

/**
 *
 * @author frank
 * 
 * This class represents a move of a single piece or pawn
 */
public class PieceMove implements Move {

    private Field from;
    private Field to;
    private boolean drawOffer;
    
    private PieceMove(){
        
    }
    
    /**
     * 
     * @param from
     * @param to
     * @return moves the contents of 'from' to 'to'
     */
    public static PieceMove getInstance( Field from, Field to) {
        PieceMove result = new PieceMove();
        result.from = from;
        result.to = to;
        result.drawOffer = false;
        
        return result;
        
    }

    @Override
    public String moveString(GameState state){       
        StringBuilder sb = new StringBuilder();

        String piece = state.getFieldContent(from).getPGNcode();
        sb.append(piece);
        
        // determine if there is ambiguity and, if yes, add resolver
        boolean resolver = false;
        Set<Move> movesTo = new HashSet<Move>();
        for(Move m : state.getLegalMoves()){
            if(m instanceof PieceMove){
                boolean samePiece = (state.getFieldContent(from) == state.getFieldContent(((PieceMove) m).from));
                boolean sameTo    = to.equals(((PieceMove)m).getTo());
                if (samePiece && sameTo) {
                    movesTo.add(m);
                }
            }
        }
        if(movesTo.size() > 1){
            resolver = true;
            
            int fromX = from.getX();
            int fromY = from.getY();
            int countSameX = 0;
            int countSameY = 0;
            for(Move m : movesTo){
                if(m.getFrom().getX() == fromX){
                    countSameX += 1;
                }
                if(m.getFrom().getY() == fromY){
                    countSameY += 1;
                }
            }
            
            if(countSameX == 1){
                sb.append(from.toString().substring(0,1));
            } else if(countSameY == 1){
                sb.append(from.toString().substring(1, 2));
            }
        }
        
        // add the indicator for capture and make sure that the
        // file is added for all pawn moves, even if there is no 
        // resolver
        if(!state.getFieldContent(to).equals(PieceType.EMPTY)){
            if(!resolver && piece.equals("")){
                sb.append(from.toString().substring(0,1));
            }
            sb.append("x");
        }
        
        // the destination field
        sb.append(to);
        
        //the indicators for check and checkmate
        GameState state2 = this.applyTo(state);
        if(state.getRules().isMate(state2)){
            sb.append("#");
        } else {
            if(state.getRules().isCheck(state2)){
                sb.append("+");
            }
        }
        
        return sb.toString();
        
        
    }

    @Override
    public Field getFrom() {
        return from;
    }

    @Override
    public Field getTo() {
        return to;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.from.toString());
        sb.append("-");
        sb.append(this.to.toString());
        return sb.toString();
    }

    @Override
    public GameState applyTo(GameState state) {
        GameState result = state.copy();
        
        // increment the counters
        result.increment();
        if(result.getFieldContent(to) != PieceType.EMPTY 
                || result.getFieldContent(from) == PieceType.WHITE_PAWN 
                || result.getFieldContent(from) == PieceType.BLACK_PAWN){
            result.resetHalfMoveClock();
        }
        
        // update the en passant information
        if((result.getFieldContent(from) == PieceType.WHITE_PAWN) && (to.getY() == 3)){
            result.setEnPassantField(Field.getInstance(to.getX(), 2));
        } else if((result.getFieldContent(from) == PieceType.BLACK_PAWN) && (to.getY() == 4)){
            result.setEnPassantField(Field.getInstance(to.getX(), 5));
        } else {
            result.clearEnPassant();
        }
        
        // update the castling flags
        Color mover = state.getToMove();
        boolean affectWhiteKingside = from.equals(Field.getInstance("e1")) 
                                   || from.equals(Field.getInstance("h1"));
        affectWhiteKingside = affectWhiteKingside && (mover == Color.WHITE);
        if(affectWhiteKingside){
            result.clearCastlingAllowedFlag(mover, BoardSide.KINGSIDE);
        }
        
        boolean affectBlackKingside = from.equals(Field.getInstance("e8")) 
                                   || from.equals(Field.getInstance("h8"));
        affectBlackKingside = affectBlackKingside && (mover == Color.BLACK);
        if(affectBlackKingside){
            result.clearCastlingAllowedFlag(mover, BoardSide.KINGSIDE);
        }   
        
        boolean affectWhiteQueenside = from.equals(Field.getInstance("e1")) 
                                   || from.equals(Field.getInstance("a1"));
        affectWhiteQueenside = affectWhiteQueenside && (mover == Color.WHITE);
        if(affectWhiteQueenside){
            result.clearCastlingAllowedFlag(mover, BoardSide.QUEENSIDE);
        }
        
        boolean affectBlackQueenside = from.equals(Field.getInstance("e8")) 
                                   || from.equals(Field.getInstance("a8"));
        affectBlackQueenside = affectBlackQueenside && (mover == Color.BLACK);
        if(affectBlackQueenside){
            result.clearCastlingAllowedFlag(mover, BoardSide.QUEENSIDE);
        }          
        
        //move the piece
        PieceType piece = result.getFieldContent(this.from);
        result.setFieldContent(this.from, PieceType.EMPTY);
        result.setFieldContent(this.to, piece);
        
        return result;
    }

    @Override
    public boolean offeredDraw() {
        return this.drawOffer;
    }

    @Override
    public void offerDraw() {
        this.drawOffer = true;
    }  

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.from);
        hash = 79 * hash + Objects.hashCode(this.to);
        hash = 79 * hash + (this.drawOffer ? 1 : 0);
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
        final PieceMove other = (PieceMove) obj;
        if (this.drawOffer != other.drawOffer) {
            return false;
        }
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.to, other.to)) {
            return false;
        }
        return true;
    }
    
    
    
}
