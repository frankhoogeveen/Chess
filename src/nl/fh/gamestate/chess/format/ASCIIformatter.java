/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.chess.format;

import nl.fh.gamestate.StateFormatter;
import nl.fh.gamestate.chess.ChessState;
import nl.fh.gamestate.chess.Color;
import nl.fh.gamestate.chess.PieceType;

/**
 * 
 * 
 */
public class ASCIIformatter implements StateFormatter<ChessState>{

    private Color color;
    
    /** 
     * 
     * @param color the side from the board from which it is seen in the output 
     */
    public ASCIIformatter(Color color){
        this.color = color;
    }
    
    /**
     * 
     * @param color the side from the board from which it is seen in the output 
     */
    public void setColor(Color color){
        this.color = color;
    }
    
    @Override
    public String format(ChessState state) {
        return  format(state, 1);
    }   
    
    @Override
    public String format(ChessState state, int moveNumber) {
        return toASCII(state);
    }
    
    /**
     * 
     * @return the game state as an ASCII string suitable for display
     * on a terminal
     */
    private String toASCII(ChessState state){
        String sep = "+---+---+---+---+---+---+---+---+";
        
        StringBuilder sb = new StringBuilder();
        sb.append(sep);
        sb.append("\n");
        
        if(color == Color.WHITE){
            for(int i = 7; i >= 0; i--){
                for(int j = 0; j < 8; j++){
                    if(j == 0){
                        sb.append("| ");
                    } else {
                        sb.append(" | ");
                    }
                    
                    PieceType piece = state.getBoard()[j][i];
                    if(piece == PieceType.EMPTY){
                        if( ((i+j)%2) == 0){
                            sb.append(".");
                        } else {
                            sb.append(" ");
                        }
                    } else {
                        sb.append(piece.getFENcode());                    
                    }
                    
                }
                sb.append(" |");
                sb.append("\n");
                sb.append(sep);
                sb.append("\n");
            }
        } else {
            for(int i = 0; i <8; i++){
                for(int j = 7; j >= 0; j--){
                    if(j == 7){
                        sb.append("| ");
                    } else {
                        sb.append(" | ");
                    }                    
                    
                    PieceType piece = state.getBoard()[j][i];
                    if(piece == PieceType.EMPTY){
                        if( ((i+j)%2) == 0){
                            sb.append(".");
                        } else {
                            sb.append(" ");
                        }
                    } else {
                        sb.append(piece.getFENcode());                    
                    }
                }
                sb.append(" |");
                sb.append("\n");                
                sb.append(sep);
                sb.append("\n");
            }            
            
        }
        
        return sb.toString();
    }       
 

}