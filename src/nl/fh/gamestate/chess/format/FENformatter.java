/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate.chess.format;

import nl.fh.gamestate.StateFormatter;
import nl.fh.gamestate.chess.BoardSide;
import nl.fh.gamestate.chess.ChessState;
import nl.fh.gamestate.chess.Color;
import nl.fh.gamestate.chess.Field;
import nl.fh.gamestate.chess.PieceType;

/**
 * 
 * 
 */
public class FENformatter implements StateFormatter<ChessState> {
    
    @Override
    public String format(ChessState state) {
        return format(state, 1);
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
        
    void enPassantToFEN(ChessState state, StringBuilder sb) {
        Field enPassantField = state.getEnPassantField();
        if(enPassantField != null){
            sb.append(enPassantField.toString());
        } else {
            sb.append("-");
        }
    }
    

    void moveNumberToFEN(ChessState state, StringBuilder sb, int moveNumber) {
        sb.append(state.getHalfMoveClock());
        sb.append(" ");
        sb.append(moveNumber);
    }

    void castlingToFen(ChessState state, StringBuilder sb) {
        boolean whiteCanCastleKingside   = state.getCastlingAllowedFlag(Color.WHITE, BoardSide.KINGSIDE);
        boolean whiteCanCastleQueenside  = state.getCastlingAllowedFlag(Color.WHITE, BoardSide.QUEENSIDE);
        boolean blackCanCastleKingside   = state.getCastlingAllowedFlag(Color.BLACK, BoardSide.KINGSIDE);     
        boolean blackCanCastleQueenside  = state.getCastlingAllowedFlag(Color.BLACK, BoardSide.QUEENSIDE); 
        
        if(whiteCanCastleKingside||whiteCanCastleQueenside||blackCanCastleKingside||blackCanCastleQueenside ){
            if(whiteCanCastleKingside){
                sb.append("K");
            }
            if(whiteCanCastleQueenside){
                sb.append("Q");
            }
            if(blackCanCastleKingside){
                sb.append("k");
            }
            if(blackCanCastleQueenside){
                sb.append("q");
            }
            
        } else {
            sb.append("-");
        }
    }

    void playerToFen(ChessState state, StringBuilder sb) {
        if(state.getColor() == Color.WHITE){
            sb.append("w");
        } else {
            sb.append("b");
        }
    }

    void boardToFEN(ChessState state, StringBuilder sb) {
        for(int i = 7; i >= 0; i--){
            int count = 0;
            for(int j = 0; j < 8; j++){
                PieceType piece = state.getBoard()[j][i];
                if(piece == PieceType.EMPTY){
                    count += 1;
                } else {
                    if(count > 0){
                        sb.append(count);
                    }
                    sb.append(piece.getFENcode());
                    count = 0;
                }
            }
            if(count > 0){
                sb.append(count);
            }
            if(i != 0){
                sb.append("/");
            }
        }
    }    
}