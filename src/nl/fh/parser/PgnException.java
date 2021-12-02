/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fh.parser;

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
