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
    WHITE, BLACK, UNDEFINED;

    /**
     * 
     * @param color
     * @return  the other value of the color 
     */
    public static Color flip(Color color) {
        if(color == WHITE){
            return BLACK;
        } else if(color == BLACK){
            return WHITE;
        } else {
            return UNDEFINED;
        }
    }
}
