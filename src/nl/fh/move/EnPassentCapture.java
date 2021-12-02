/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fh.move;

import nl.fh.chess.BoardSide;
import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.chess.PieceType;
import nl.fh.gamestate.GameState;

/**
 * copyright GPL v3
 * @author frank
 */
public class EnPassentCapture implements Move {

    private Field from;
    private Field to;
    private boolean offeredDraw;
    
    private EnPassentCapture(){
        
    }
    
    /**
     * 
     * @param from field where the pawn resided before the capture
     * @param to   field where the pawn resides after the capture. This is NOT
     * the field where the captured pawn resided.
     * 
     * @return a move 
     */
    public Move getInstance(Field from, Field to){
        EnPassentCapture result = new EnPassentCapture();
        
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
        
        //move the piece
        PieceType piece = result.getFieldContent(this.from);
        result.setFieldContent(this.from, PieceType.EMPTY);
        result.setFieldContent(this.to, piece);
        
        //remove the captured pawn
        int y = to.getY();
        int x = to.getX();
        
        Field capturedPawn;
        switch(y){
            case 6:
                capturedPawn = Field.getInstance(x, 5);
                break;
            case 3:
                capturedPawn = Field.getInstance(x, 4);
                break;                
            default:
                throw new IllegalStateException("illegal en passent");
        }
        
        result.setFieldContent(capturedPawn, PieceType.EMPTY);
        
        return result;
    }

    @Override
    public Field getTo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Field getFrom() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String moveString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.from.toString());
        sb.append("x");
        sb.append(this.to.toString());
        sb.append(" e.p.");
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

}