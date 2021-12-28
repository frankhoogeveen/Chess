/*
 * License: GPL v3
 * 
 */
package nl.fh.gamestate.chess;

/**
 * This Enum describes all possible contents of a field, including EMPTY
 * @author frank
 */
public enum PieceType{
   
    EMPTY("", Color.UNDEFINED, null),
    WHITE_PAWN("P", Color.WHITE, PieceKind.PAWN),
    WHITE_ROOK("R", Color.WHITE, PieceKind.ROOK),
    WHITE_KNIGHT("N", Color.WHITE, PieceKind.KNIGHT),
    WHITE_BISHOP("B", Color.WHITE, PieceKind.BISHOP),
    WHITE_QUEEN("Q", Color.WHITE, PieceKind.QUEEN),
    WHITE_KING("K", Color.WHITE, PieceKind.KING),
    BLACK_PAWN("p", Color.BLACK, PieceKind.PAWN),
    BLACK_ROOK("r", Color.BLACK, PieceKind.ROOK),
    BLACK_KNIGHT("n", Color.BLACK, PieceKind.KNIGHT),
    BLACK_BISHOP("b", Color.BLACK, PieceKind.BISHOP),
    BLACK_QUEEN("q", Color.BLACK, PieceKind.QUEEN),
    BLACK_KING("k", Color.BLACK, PieceKind.KING);
      
    private String FENcode;
    private Color color;
    private PieceKind kind;
    
    PieceType(String FENcode, Color color, PieceKind kind){
        this.FENcode = FENcode;
        this.color = color;
        this.kind = kind;
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
    
    /**
     * 
     * @return the kind of piece. In case of empty, an exception is thrown 
     */
    public PieceKind getKind(){
        if(this.equals(PieceType.EMPTY)){
            throw new IllegalStateException("EMPTY does not have a PieceKind");
        }
        return this.kind;
    }

    /**
     * 
     * @return the character the is used to represent the piece type in a PGN file 
     */
    public String getPGNcode() {
        if((this == WHITE_PAWN) || (this == BLACK_PAWN)){
            return "";
        } else{
            return getFENcode().toUpperCase();
        }
    }
    
    /**
     * 
     * @param color
     * @param kind 
     * @return the colored piece
     */
    public static PieceType get(Color color, PieceKind kind){
        if(color == Color.WHITE){
            switch(kind){
                case PAWN:
                    return WHITE_PAWN;
                case KNIGHT:
                    return WHITE_KNIGHT;
                case BISHOP:
                    return WHITE_BISHOP;
                case ROOK:
                    return WHITE_ROOK;
                case QUEEN:
                    return WHITE_QUEEN;
                case KING:
                    return WHITE_KING;
            }
             
        } else if(color == Color.BLACK){
            switch(kind){
                case PAWN:
                    return BLACK_PAWN;
                case KNIGHT:
                    return BLACK_KNIGHT;
                case BISHOP:
                    return BLACK_BISHOP;
                case ROOK:
                    return BLACK_ROOK;
                case QUEEN:
                    return BLACK_QUEEN;
                case KING:
                    return BLACK_KING;     
            }
            
        } else {
           throw new IllegalStateException("EMPTY cannot be colored");
        }
        
        throw new IllegalStateException("UNDEFINED color cannot be used here");
    }
}
