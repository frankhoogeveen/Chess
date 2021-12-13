/*
 * License: GPL v3
 * 
 */

package nl.fh.metric.minimax;

/**
 * 
 * 
 */
public enum SearchMode {
    MINIMAX(-1),
    MAXIMIN(+1);
    
    private final int sign;
    
    SearchMode(int sign){
        this.sign = sign;
    }

    public int getSign(){
        return this.sign;
    }
}