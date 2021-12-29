/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.chess.parser;

/**
 * copyright F. Hoogeveen
 * @author frank
 */
public class PgnException extends Exception {

    private final String explanation;

    PgnException(String explanation) {
        this.explanation = explanation;
    }
    
    public String toString(){
        return "PgnException: " + this.explanation;
    }

}
