/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate;

/**
 * 
 * Indicates whether a player has made the first move
 */
public enum Mover {
    FIRST_MOVER(+1),
    SECOND_MOVER(-1);
    
    private int sign;
    
    private Mover(int sign){
        this.sign = sign;
    }
    
    public int getSign(){
        return this.sign;
    }
}