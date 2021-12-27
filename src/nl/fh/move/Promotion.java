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
import nl.fh.chess.PieceKind;
import nl.fh.chess.PieceType;
import nl.fh.gamestate.GameState;
import nl.fh.rules.ChessResultArbiter;
import nl.fh.rules.GameDriver;

/**
 *
 * @author frank
 * 
 * This class represents a move of a single piece or pawn
 */
public class Promotion extends ChessMove {

    private Field from;
    private Field to;
    private boolean drawOffer;
    private PieceKind piece;
    
    private Promotion(){
        
    }
    
    /**
     * 
     * @param from
     * @param to
     * @param piece
     * @return moves the contents of 'from' to 'to' and changes it to piece
     * 
     * It is NOT the concern of the move to ensure:
     * - that the piece moving is a pawn
     * - that the pawn is moving to the final rank
     * - that the piece if of the correct color
     * - that the piece of one of the allowed types (Q,R,B,N)
     */
    public static Promotion getInstance( Field from, Field to, PieceKind piece) {
        Promotion result = new Promotion();
        result.from = from;
        result.to = to;
        result.piece = piece;
        result.drawOffer = false;
        
        return result;
        
    }

    @Override
    public String formatPGN(GameState state, GameDriver driver){      
        StringBuilder sb = new StringBuilder();
        
        // determine if there is ambiguity and, if yes, add resolver
        boolean resolver = false;        
        Set<ChessMove> movesTo = new HashSet<ChessMove>();
        Set<Move> legalMoves = driver.getMoveGenerator().calculateAllLegalMoves(state);
        
        for(Move m : legalMoves){
            if(m instanceof Promotion){
                if (to.equals(((Promotion)m).getTo()) && 
                        this.piece == ((Promotion) m).getPieceKind()) {
                    movesTo.add((ChessMove)m);
                }
            }
        }
        if(movesTo.size() > 1){
            resolver = true;
            
            int fromX = from.getX();
            int fromY = from.getY();
            int countSameX = 0;
            int countSameY = 0;
            for(ChessMove m : movesTo){
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
            
            PieceType movingPiece = state.getFieldContent(from);
            if(!resolver && (movingPiece.getKind() == PieceKind.PAWN)  ){
                sb.append(from.toString().substring(0,1));
            }
            sb.append("x");
        }
        
        // the destination field
        sb.append(to);
        
        //the promotion info
        sb.append("=");
        sb.append(piece.getMoveCode());
        
        //the indicators for check and checkmate
        ChessResultArbiter arbiter = (ChessResultArbiter) driver.getResultArbiter();
        GameState state2 = this.applyTo(state);
        Set<Move> legalMoves2 = driver.getMoveGenerator().calculateAllLegalMoves(state2);
        
        Set<ChessMove> legalChessMoves2 = (Set<ChessMove>)(Set<?>) legalMoves2;        
        if(arbiter.isMate(state2, legalChessMoves2)){
            sb.append("#");
        } else {
            if(arbiter.isCheck(state2)){
                sb.append("+");
            }
        }
        
        return sb.toString();
    }
    
    @Override
    public String formatUCI(GameState state) {
        return getFrom().toString() + getTo().toString() + this.piece.getMoveCode().toLowerCase();
    }        

    @Override
    public Field getFrom() {
        return from;
    }

    @Override
    public Field getTo() {
        return to;
    }
    
    public PieceKind getPieceKind(){
        return this.piece;
    }
    
    public PieceType getPiece(){

        return PieceType.get(movingColor(), piece);
    }
    
    /**
     * 
     * @return the color of the piece that is moving 
     */
    private Color movingColor(){
        Color color;
        switch(to.getY()){
            case 0:
                color = Color.BLACK;
                break;
            case 7:
                color = Color.WHITE;
                break;
            default:
                throw new IllegalStateException("Illegal promotion");
        }
        return color;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.from.toString());
        sb.append("-");
        sb.append(this.to.toString());
        sb.append("=");
        sb.append(piece.getMoveCode());
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
        result.clearEnPassant();
        
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
        result.setFieldContent(this.from, PieceType.EMPTY);
        result.setFieldContent(this.to, PieceType.get(movingColor(), piece));
        
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
        hash = 59 * hash + Objects.hashCode(this.from);
        hash = 59 * hash + Objects.hashCode(this.to);
        hash = 59 * hash + (this.drawOffer ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.piece);
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
        final Promotion other = (Promotion) obj;
        if (this.drawOffer != other.drawOffer) {
            return false;
        }
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.to, other.to)) {
            return false;
        }
        if (this.piece != other.piece) {
            return false;
        }
        return true;
    }


    
    
    
}
