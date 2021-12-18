/*
 * License: GPL v3
 * 
 */
package nl.fh.chess;

/**
 * Enumerates the colors of field contents (pieces or emptiness)
 * and players. The case UNDEFINED is included to describe the color of the
 * contents of an empty field.
 */
public enum Color {
 
    WHITE(+1), 
    BLACK(-1), 
    UNDEFINED(Integer.MAX_VALUE);

    private int sign;
    
    Color(int sign){
        this.sign = sign;
    }
    
    /**
     * 
     * @param color
     * @return  the other value of the color 
     */
    public  Color flip() {
        if(this == WHITE){
            return BLACK;
        } else if(this == BLACK){
            return WHITE;
        } else {
            return UNDEFINED;
        }
    }
    /**
     * 
     * @return + 1 for white, -1 for black. For UNDEFINED the behavior is undefined
     */
    public int getSign(){
        return sign;
    }
}
