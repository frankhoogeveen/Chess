/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fh.chess;

/**
 *
 * @author frank
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
