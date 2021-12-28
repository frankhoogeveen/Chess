/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport;

/**
 * 
 * 
 */
public enum GameResult {
    WIN_FIRST_MOVER(+1),
    WIN_SECOND_MOVER(-1),
    DRAW(0),
    UNDECIDED(0);
    
    private int value;
    
    GameResult(int value){
        this.value = value;
    }
    
    public int getValue(){
        return this.value;
    }
}