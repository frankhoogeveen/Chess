/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.chess.format;

import nl.fh.gamestate.Move;
import nl.fh.gamestate.chess.ChessState;
import nl.fh.gamestate.chess.Field;
import nl.fh.gamestate.chess.move.ChessMove;
import nl.fh.gamestate.chess.move.EnPassantCapture;
import nl.fh.rule.MoveGenerator;

/**
 * 
 * 
 */
public class XFENformatter extends FENformatter {

    private final MoveGenerator<ChessState> moveGenerator;
    
    public XFENformatter(MoveGenerator<ChessState> moveGenerator){
        super();
        this.moveGenerator = moveGenerator;
    }
    
    @Override
    public String format(ChessState state, int moveNumber) {
        StringBuilder sb = new StringBuilder();
        
        boardToFEN(state, sb);
        sb.append(" ");
        
        playerToFen(state, sb);
        sb.append(" ");
        
        castlingToFen(state, sb);
        sb.append(" ");
        
        enPassantToFEN(state, sb);
        sb.append(" ");
        
        moveNumberToFEN(state, sb, moveNumber);
        
        return sb.toString();
    }
    
    
    /**
     * 
     * @param sb
     * @param moveGenerator
     * 
     * the en passant information in the XFEN string.
     * This will add "-", unless an en passant capture can 
     * actually be made. When a pawn has moved two squares
     * in the previous ply, but an en passant capture cannot be made
     * (e.g due to empty squares or an absolute pin) "-" will be 
     * written to the string buffer.
     * 
     * Since the result depends on legal moves from this position, one
     * has to supply a move generator.
     */
    @Override
    void enPassantToFEN(ChessState state, StringBuilder sb) {
        Field enPassantField = state.getEnPassantField();        
        
        if(enPassantField == null){
            sb.append("-");
            return;
        }
        
        // determine if can we actually make an en passant capture (keeping track of 
        // pawns present, pins etc. 
        boolean actual = false;
        for(Move<ChessState> m : moveGenerator.calculateAllLegalMoves(state)){
            if((m instanceof EnPassantCapture) 
                    && (((ChessMove)m).getTo().equals(enPassantField))){
                actual = true;
            }
        }
        
        if(actual){
            sb.append(enPassantField.toString());
        } else {
            sb.append("-");
        }
    }    


}