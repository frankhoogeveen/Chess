/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport;

public enum ChessGameResult {
    WIN_WHITE(+1), 
    WIN_BLACK(-1), 
    UNDECIDED(0),     // for use when the game is not finished yet
    ILLEGAL_MOVE_BY_WHITE(-1), 
    ILLEGAL_MOVE_BY_BLACK(+1), 
    DRAW(0),
    DRAW_AGREED(0), 
    DRAW_STALEMATE(0), 
    DRAW_BY_THREEFOLD_REPETITION(0),
    DRAW_BY_50_MOVE_RULE(0), 
    RESIGNATION_BY_WHITE(-1), 
    RESIGNATION_BY_BLACK(+1), 
    DRAW_INSUFFICIENT_MATERIAL(0);
    
    private int value;
    
    ChessGameResult(int value){
        this.value = value;
    }
    
    /**
     * return +1 for games won by white, -1 for black wins and 0 otherwise
     * Note that illegal moves are valued the same as regular losses. 
     */
    public int getValue(){
        return this.value;
    }
}

