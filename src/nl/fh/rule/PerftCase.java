/*
 * License: GPL v3
 * 
 */

package nl.fh.rule;

/**
 * 
 * 
 */
public class PerftCase {
    String fen;
    String comment;
    int depth;
    long perftValue;
    
    /**
     * Class to describe PerftCase
     * 
     * @param fen
     * @param comment e.g. the rationale of this case, or the source of the perftValue
     * @param depth
     * @param perftValue the expected outcome
     */
    public PerftCase(String fen, String comment, int depth, long perftValue){
        this.fen = fen;
        this.comment = comment;
        this.depth = depth;
        this.perftValue = perftValue;
    }

    public String getFen() {
        return fen;
    }

    public String getComment() {
        return comment;
    }

    public int getDepth() {
        return depth;
    }

    public long getPerftValue() {
        return perftValue;
    }
    
}