/*
 * License: GPL v3
 * 
 */

package nl.fh.move;

import java.util.Objects;
import nl.fh.chess.Field;
import nl.fh.chess.PieceType;
import nl.fh.gamestate.GameState;
import nl.fh.rules.Rules;

/**
 * copyright GPL v3
 * @author frank
 */
public class EnPassantCapture implements Move {

    private Field from;
    private Field to;
    private boolean offeredDraw;
    
    private EnPassantCapture(){
        
    }
    
    /**
     * 
     * @param from field where the pawn resided before the capture
     * @param to   field where the pawn resides after the capture. This is NOT
     * the field where the captured pawn resided.
     * 
     * @return a move 
     */
    public static Move getInstance(Field from, Field to){
        EnPassantCapture result = new EnPassantCapture();
        
        result.from = from;
        result.to = to;
        result.offeredDraw = false;
        
        return result;
    }

    @Override
    public GameState applyTo(GameState state) {
        GameState result = state.copy();
        
        // increment the counters
        result.increment();
        result.resetHalfMoveClock();   
        
        // update the en passant information
        result.clearEnPassant();
        
        //move the piece
        PieceType piece = result.getFieldContent(this.from);
        result.setFieldContent(this.from, PieceType.EMPTY);
        result.setFieldContent(this.to, piece);
        
        //remove the captured pawn
        int y = to.getY();
        int x = to.getX();
        
        Field capturedPawn;
        switch(y){
            case 5:
                capturedPawn = Field.getInstance(x, 4);
                break;
            case 2:
                capturedPawn = Field.getInstance(x, 3);
                break;                
            default:
                throw new IllegalStateException("illegal en passant " 
                        + this.from.toString() 
                        + this.to.toString()
                        + " applied to "
                        + state.toFEN());
        }
        
        result.setFieldContent(capturedPawn, PieceType.EMPTY);
        
        return result;
    }

    @Override
    public Field getTo() {
        return this.to;
    }

    @Override
    public Field getFrom() {
        return this.from;
    }

    @Override
    public String moveString(GameState state) {
        Rules rules = state.getRules();
        StringBuilder sb = new StringBuilder();
        
        sb.append(this.from.toString().substring(0,1));
        sb.append("x");
        sb.append(this.to.toString());
        
        //the indicators for check and checkmate
        GameState state2 = this.applyTo(state);
        if(rules.isMate(state2)){
            sb.append("#");
        } else {
            if(rules.isCheck(state2)){
                sb.append("+");
            }
        }        

        return sb.toString();
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
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.from);
        hash = 59 * hash + Objects.hashCode(this.to);
        hash = 59 * hash + (this.offeredDraw ? 1 : 0);
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
        final EnPassantCapture other = (EnPassantCapture) obj;
        if (this.offeredDraw != other.offeredDraw) {
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