/*
 * License: GPL v3
 * 
 */
package nl.fh.chess;

/**
 * This Enum describes all possible contents of a field, including EMPTY
 * @author frank
 */
public enum PieceType{
   
    EMPTY("", Color.UNDEFINED),
    WHITE_PAWN("P", Color.WHITE),
    WHITE_ROOK("R", Color.WHITE),
    WHITE_KNIGHT("N", Color.WHITE),
    WHITE_BISHOP("B", Color.WHITE),
    WHITE_QUEEN("Q", Color.WHITE),
    WHITE_KING("K", Color.WHITE),
    BLACK_PAWN("p", Color.BLACK),
    BLACK_ROOK("r", Color.BLACK),
    BLACK_KNIGHT("n", Color.BLACK),
    BLACK_BISHOP("b", Color.BLACK),
    BLACK_QUEEN("q", Color.BLACK),
    BLACK_KING("k", Color.BLACK);
      
    private String FENcode;
    private Color color;
    
    PieceType(String FENcode, Color color){
        this.FENcode = FENcode;
        this.color = color;
    }

    /**
     * 
     * @return the zero or one character code to represent this piece in a FEN string 
     */
    public String getFENcode() {
        return FENcode;
    }
    
    /**
     * 
     * @return the color of this piece type. Returns UNDEFINED in case of EMPTY
     */
    public Color getColor(){
        return color;
    }
    
}
