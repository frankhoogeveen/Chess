/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.chess;

/**
 * 
 * 
 */
public enum PieceKind {
    PAWN(""),
    KNIGHT("N"), 
    BISHOP("B"), 
    ROOK("R"), 
    QUEEN("Q"), 
    KING("K");
    
    private String moveCode;

    PieceKind(String code){
        this.moveCode = code;
    }

    public String getMoveCode() {
        return this.moveCode;
    }
}