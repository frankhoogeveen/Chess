/*
 * License: GPL v3
 * 
 */

package nl.fh.gamestate;

import nl.fh.move.Move;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import nl.fh.chess.BoardSide;
import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.chess.PieceType;
import nl.fh.move.EnPassantCapture;
import nl.fh.rules.Rules;

/**
 * copyright F. Hoogeveen
 * @author frank
 * 
 * Keeps track of the state of the game and acts as a buffer to ensure 
 * that derived information (in particular, the set of legal moves) is not
 * unnecessarily recalculated. The mechanism to achieve this is the dirty flag.
 * 
 */
public class GameState {

////////////////////////////////////////////////////////////////////////////////
// static data
////////////////////////////////////////////////////////////////////////////////    
    
    private static char[] file = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    private static char[] rank = {'1', '2', '3', '4', '5', '6', '7', '8'};   
    
////////////////////////////////////////////////////////////////////////////////
// the data independently describing the board state
////////////////////////////////////////////////////////////////////////////////     
    
    private Rules rules;          // the rules used for this gamestate    
    
    private PieceType[][] board;  // first coordinate is file, second is rank:
                                  // board[0][0] is a1
                                  // board[7][0] is h1 
                                  // board[0][7] is a8
    
    private Color activeColor;    // the player that moves next
    
    private boolean whiteCanCastleKingside;
    private boolean whiteCanCastleQueenside;
    private boolean blackCanCastleKingside;
    private boolean blackCanCastleQueenside;
 
    private Field enPassantField;  // field where an en passent capture is possible, null otherwise
    
    private int halfMoveClock;
    private int fullMoveNumber;
    
    private boolean drawOffered;
    
////////////////////////////////////////////////////////////////////////////////
// the dirty flag, the rules used and the derived information
////////////////////////////////////////////////////////////////////////////////     
    
    private boolean isDirty;
    private Set<Move> legalMoves;
    
    
    /**
     * @param rules the Rules object that governs the play
     * set up the board for a new game
     */
    public GameState(Rules rules){
        board = new PieceType[8][8];
        isDirty = true;
        this.rules = rules;
        clear();
    }
    
    /**
     * reset the board to the state for a new game, without any pieces
     */
    private void clear(){
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = PieceType.EMPTY;
            }
        }
        
        activeColor = Color.WHITE;
        
        whiteCanCastleKingside = true;
        whiteCanCastleQueenside = true;
        blackCanCastleKingside = true;
        blackCanCastleQueenside = true;
        
        enPassantField = null;
        
        halfMoveClock = 0;
        fullMoveNumber = 1;   
        
        drawOffered = false;
    }    
    
    /**
     * 
     * @return the game state in Forsyth-Edwards notation 
     * https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
     */
    public String toFEN(){
        StringBuilder sb = new StringBuilder();
        
        boardToFEN(sb);
        sb.append(" ");
        
        playerToFen(sb);
        sb.append(" ");
        
        castlingToFen(sb);
        sb.append(" ");
        
        enPassantToFEN(sb);
        sb.append(" ");
        
        moveNumberToFEN(sb);
        
        return sb.toString();
    }
    
    /**
     * 
     * @return the game state in Extended Forsyth-Edwards notation 
     * https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
     * https://en.wikipedia.org/wiki/X-FEN
     * 
     * In this case the en passant information is only written when
     * there is a pawn on an adjacent file that can ACTUALLY capture
     * en passant. 
     * 
     * In this interpretation, the en passant info is NOT written, if there
     * is a pawn that is in a proper location to capture en passant, but cannot
     * since it is pinned to the king. 
     * 
     * Given this choice, repeating XFEN three times in a game, implies that the
     * 3 fold repetition draw can be claimed. This is unlike the traditional FEN.
     */
    public String toXFEN(){
        StringBuilder sb = new StringBuilder();
        
        boardToFEN(sb);
        sb.append(" ");
        
        playerToFen(sb);
        sb.append(" ");
        
        castlingToFen(sb);
        sb.append(" ");
        
        enPassantToXFEN(sb);
        sb.append(" ");
        
        moveNumberToFEN(sb);
        
        return sb.toString();
    }    

    
    
    private void enPassantToFEN(StringBuilder sb) {
        // the en passent information
        if(enPassantField != null){
            sb.append(enPassantField.toString());
        } else {
            sb.append("-");
        }
    }
    
    /**
     * 
     * @param sb
     * 
     * the en passant information in the XFEN string.
     * This will add "-", unless an en passant capture can 
     * actually be made. When a pawn has moved two squares
     * in the previous ply, but an en passant capture cannot be made
     * (e.g due to empty squares or an absolute pin) "-" will be 
     * written to the string buffer
     */
    private void enPassantToXFEN(StringBuilder sb) {
        
        boolean actual = false;
        for(Move m : this.getLegalMoves()){
            if((m instanceof EnPassantCapture) 
                    && (m.getTo().equals(this.enPassantField))){
                actual = true;
            }
        }
        
        if(enPassantField != null && actual){
            sb.append(enPassantField.toString());
        } else {
            sb.append("-");
        }
    }    

    private void moveNumberToFEN(StringBuilder sb) {
        // the move numbers
        sb.append(halfMoveClock);
        sb.append(" ");
        sb.append(fullMoveNumber);
    }

    private void castlingToFen(StringBuilder sb) {
        // the castling information
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

    private void playerToFen(StringBuilder sb) {
        // the next player to move
        if(activeColor == Color.WHITE){
            sb.append("w");
        } else {
            sb.append("b");
        }
    }

    private void boardToFEN(StringBuilder sb) {
        for(int i = 7; i >= 0; i--){
            int count = 0;
            for(int j = 0; j < 8; j++){
                PieceType piece = board[j][i];
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
    
    /**
     * 
     * @param fen a string that contains a game state in FEN notation
     * @return a game state corresponding to the FEN string
     * 
     * https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
     * https://www.chessclub.com/help/PGN-spec
     * 
     * The FEN parser is tolerant of small deviations in the syntax
     */
    public static GameState fromFEN(String fen, Rules rules){
        
        GameState result = new GameState(rules);
        result.clear();
        
        // remove leading and trailing blanks
        String str = fen.replaceAll("^\\s+|\\s+$}","");
        
        //replace multiple blanks with a single blank
        str = str.replaceAll("\\s+"," ");
        
        //split the FEN string on the blank spaces
        String[] piece = str.split("\\s");
        if(piece.length != 6){
            throw new IllegalArgumentException("FEN string does not contain 6 parts: " + fen);
        }
        
        //get the location of the pieces
        //start by replacing all numbers by repetitions of '.'
        if(piece[0].contains(".")){
            throw new IllegalArgumentException("FEN string contains illegal character \".\":  "+ fen);
        }
        piece[0] = piece[0].replaceAll("8","........");
        piece[0] = piece[0].replaceAll("7",".......");
        piece[0] = piece[0].replaceAll("6","......");
        piece[0] = piece[0].replaceAll("5",".....");
        piece[0] = piece[0].replaceAll("4","....");
        piece[0] = piece[0].replaceAll("3","...");
        piece[0] = piece[0].replaceAll("2","..");
        piece[0] = piece[0].replaceAll("1",".");        
        
        //split the location information into rows
        String[] row = piece[0].split("/");
        if(row.length != 8){
            throw new IllegalArgumentException("FEN with incorrect number of rows: " + fen);
        }

        //parse row by row, keeping sight of the fact that a FEN string starts with the 8th row
        for(int nRow = 0; nRow < 8; nRow++){
            String currentRow = row[7 - nRow];
            if(currentRow.length() != 8){
                throw new IllegalArgumentException("FEN string with incorrect row length: " + fen);
            }
            for(int nCol = 0; nCol < 8; nCol ++){
                char c = currentRow.charAt(nCol);
                switch(c){
                    case 'R':
                        result.board[nCol][nRow] = PieceType.WHITE_ROOK;
                        break;
                    case 'N':
                        result.board[nCol][nRow] = PieceType.WHITE_KNIGHT;
                        break;
                    case 'B':
                        result.board[nCol][nRow] = PieceType.WHITE_BISHOP;
                        break;
                    case 'Q':
                        result.board[nCol][nRow] = PieceType.WHITE_QUEEN;
                        break;
                    case 'K':
                        result.board[nCol][nRow] = PieceType.WHITE_KING;
                        break;  
                    case 'P':
                        result.board[nCol][nRow] = PieceType.WHITE_PAWN;
                        break;                         
                    case 'r':
                        result.board[nCol][nRow] = PieceType.BLACK_ROOK;
                        break;
                    case 'n':
                        result.board[nCol][nRow] = PieceType.BLACK_KNIGHT;
                        break;
                    case 'b':
                        result.board[nCol][nRow] = PieceType.BLACK_BISHOP;
                        break;
                    case 'q':
                        result.board[nCol][nRow] = PieceType.BLACK_QUEEN;
                        break;
                    case 'k':
                        result.board[nCol][nRow] = PieceType.BLACK_KING;
                        break;   
                    case 'p':
                        result.board[nCol][nRow] = PieceType.BLACK_PAWN;
                        break;   
                    case '.':
                        result.board[nCol][nRow] = PieceType.EMPTY;
                        break;
                    default:
                        throw new IllegalArgumentException("FEN contains illegal character for piece: " + fen);
                }
            }
        }
        
        // get the player that is to move next
        // note that both upper and lower case are accepted
        switch(piece[1]){
            case "w":
            case "W":
                result.activeColor = Color.WHITE;
                break;
            case "b":
            case "B":
                result.activeColor = Color.BLACK;
                break;
            default:
                throw new IllegalArgumentException("FEN player to move is illegal: " + fen);
        }
        
        // the castling opportunities
        // note that this parser does not demand the KQkq order
        // multiple instances of the same character are allowed to appear
        
        result.whiteCanCastleKingside = false;
        result.whiteCanCastleQueenside = false;
        result.blackCanCastleKingside = false;
        result.blackCanCastleQueenside = false;        
        
        if(piece[2].length() > 4){
            throw new IllegalArgumentException("FEN contains too long castling string: " + fen);
        }
        if(!piece[2].equals("-")){
            for(int ix = 0; ix < piece[2].length(); ix++){
                char c = piece[2].charAt(ix);
                switch(c){
                    case 'K':
                        result.whiteCanCastleKingside = true;
                        break;
                    case 'Q':
                        result.whiteCanCastleQueenside = true;
                        break;
                    case 'k':
                        result.blackCanCastleKingside = true;
                        break;
                    case 'q':
                        result.blackCanCastleQueenside = true;
                        break;
                    default:
                        throw new IllegalArgumentException("FEN castling string contains illegal characters: " + fen);
                }
            }
        }
        
        // the enpassant information
        if(piece[3].equals("-")){
            result.enPassantField = null;
        } else if(piece[3].length() != 2){
            throw new IllegalArgumentException("FEN enpassant information incorrect length: " + fen);
        } else {
       
            int nFile = -1;
            int nRank = -1;
            for(int ix = 0; ix < 8; ix++){
                if(piece[3].charAt(1) == rank[ix]){
                    nRank= ix;
                }
                if(piece[3].charAt(0) == file[ix]){
                    nFile = ix;
                }
            }
            if((nFile == -1) || (nRank == -1)){
                throw new IllegalArgumentException("FEN enpassant information incorrect square: " + fen);
            }

            result.enPassantField = Field.getInstance(nFile, nRank);
 

            if(((result.activeColor == Color.BLACK) && (result.enPassantField.getY()) != 2)
                    || ((result.activeColor == Color.WHITE) && (result.enPassantField.getY()) != 5))     
              {
                throw new IllegalArgumentException("FEN enpassant information inconsistent with who is to move: " + fen);
            }
           }  
    
        // the half move clock
        result.halfMoveClock = Integer.parseInt(piece[4]);
        if(result.halfMoveClock < 0){
            throw new IllegalArgumentException("FEN negative number of half moves: " + fen);
        }
        
        // the full move number
        result.fullMoveNumber = Integer.parseInt(piece[5]);
        if(result.fullMoveNumber < 0){
            throw new IllegalArgumentException("FEN negative number of full moves: " + fen);
        }
        
        return result;
    }

    /**
     * 
     * @return the game state as an ASCII string suitable for display
     * on a terminal
     */
    public String toASCII(Color color){
        StringBuilder sb = new StringBuilder();
        
        
        if(color == Color.WHITE){
            for(int i = 7; i >= 0; i--){
                for(int j = 0; j < 8; j++){
                    PieceType piece = board[j][i];
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
                sb.append("\n");
            }
        } else {
            for(int i = 0; i <8; i++){
                for(int j = 7; j >= 0; j--){
                    PieceType piece = board[j][i];
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
                sb.append("\n");
            }            
            
        }
        
        return sb.toString();
    }    
    
    /**
     * 
     * @return the color of the player that is to move next 
     */
    public Color getToMove() {
        return activeColor;
    }
    
    /**
     * 
     * @return true if draw was offered on the last move 
     */
    public boolean isDrawOffered(){
        return this.drawOffered;
    }

    /**
     * 
     * @param ptype
     * @return the set of fields that contain piece of the given type 
     */
    public Set<Field> getPieceLocations(PieceType ptype) {
        Set<Field> result = new HashSet<Field>();
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                if(this.board[x][y] == ptype){
                    result.add(Field.getInstance(x,y));
                }
            }
        }
        return result;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return the piece at location x,y 
     */
    public PieceType getFieldContent(int x, int y){
        return this.board[x][y];
    }
    /**
     * 
     * @param field
     * @return the contents of the field in this game state 
     */
    public PieceType getFieldContent(Field field){
        return getFieldContent(field.getX(), field.getY());
    }
    
    /**
     * Puts a piece of a given type on the given field
     * @param field
     * @param type 
     */
    public void setFieldContent(Field field, PieceType type){
        this.board[field.getX()][field.getY()] = type;
        this.isDirty = true;
    }
    
    /**
     * 
     * @return 
     */
    public Rules getRules(){
        return this.rules;
    }
    
    /**
     * 
     * @param move
     * @return a new game state by applying the move to this state
     * 
     * This may or may not be a legal move according to a rule set. All it does
     * it picking up a piece, moving it and possibly replacing it when promoting.
     * 
     */
    public GameState apply(Move move) {
        return move.applyTo(this);
    } 
    
    /**
     * increments the counters and changes who is to move
     */
    public void increment(){
        this.halfMoveClock += 1;
        if(this.activeColor == Color.BLACK){
            this.fullMoveNumber += 1;
        }
        this.activeColor = Color.flip(this.activeColor);
        
        this.isDirty = true;
    }
    
    /**
     * 
     * @return a copy of this game state 
     */
    public GameState copy(){
        GameState result = new GameState(this.rules);
        
        result.board = new PieceType[8][8];
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                result.board[x][y] = this.board[x][y];
            }
        }
        result.activeColor = this.activeColor;   

        result.whiteCanCastleKingside = this.whiteCanCastleKingside;
        result.whiteCanCastleQueenside = this.whiteCanCastleQueenside;
        result.blackCanCastleKingside = this.blackCanCastleKingside;
        result.blackCanCastleQueenside = this.blackCanCastleQueenside;

        result.enPassantField = this.enPassantField;

        result.halfMoveClock =this.halfMoveClock;
        result.fullMoveNumber =this.fullMoveNumber;

        result.drawOffered = this.drawOffered; 
        
        // 
        result.isDirty = true;
        result.rules = this.rules;
        
        return result;
    }
    
    /**
     * 
     * @param color
     * @param side
     * @return true if the color can castle on the given side
     * 
     * This only checks on whether the king and/or rook have 
     * already moved in the past. It does not check for the
     * other conditions on castling. This is the concern of the rules
     */
    public boolean getCastlingAllowedFlag(Color color, BoardSide side){
        if((color == Color.WHITE) && (side == BoardSide.KINGSIDE)){
            return this.whiteCanCastleKingside;
        }
        if((color == Color.WHITE) && (side == BoardSide.QUEENSIDE)){
            return this.whiteCanCastleQueenside;
        }
        if((color == Color.BLACK) && (side == BoardSide.KINGSIDE)){
            return this.blackCanCastleKingside;
        }
        if((color == Color.BLACK) && (side == BoardSide.QUEENSIDE)){
            return this.blackCanCastleQueenside;
        }
        throw new IllegalStateException("This should not happen");
    }
    
    /**
     * 
     * @param color
     * @param boardSide 
     * 
     * Clears the specified castling flag. I.e., after calling this method,
     * castling is not allowed anymore for the given color on the specified 
     * board side.
     */
    public void clearCastlingAllowedFlag(Color color, BoardSide boardSide) {
        if((color == Color.WHITE) && (boardSide == BoardSide.KINGSIDE)){
            this.whiteCanCastleKingside = false;
        } else if((color == Color.WHITE) && (boardSide == BoardSide.QUEENSIDE)){
            this.whiteCanCastleQueenside = false;
        } else if((color == Color.BLACK) && (boardSide == BoardSide.KINGSIDE)){
            this.blackCanCastleKingside = false;
        } else if((color == Color.BLACK) && (boardSide == BoardSide.QUEENSIDE)){
            this.blackCanCastleQueenside = false;
        } 
    }    
    
    /**
     * 
     * @return true if en passant moves are allowed 
     */
    public boolean allowsEnPassant() {
        return (this.enPassantField != null);
    }

    public Field getEnPassantField(){
        return this.enPassantField;
    }
    
    /**
     * 
     * @param field the field where en passant capture can take place
     * 
     * E.g. after the move e2-e4, this method should return e3
     */
    public void setEnPassantField(Field field){
        this.enPassantField = field;
        this.isDirty = true;
    }
    
    /**
     * calling this method resets the en passant information of this GameState
     */
    public void clearEnPassant(){
        this.enPassantField = null;
        this.isDirty = true;
    }
    
    /**
     * resets the half move clock. This is needed to keep track of e.g.
     * the fifty move rule.
     */
    public void resetHalfMoveClock() {
        this.halfMoveClock = 0;
        this.isDirty = true;
    }
    
    /**
     * 
     * @return the half move clock 
     */
    public int getHalfMoveClock(){
        return this.halfMoveClock;
    }
    
    /**
     * 
     * @param rules a rule set
     * @return all legal moves, given the rule set used to pre-calculate the
     * legal Moves
     */
    public Set<Move> getLegalMoves(){
        
        if(isDirty){
            this.legalMoves = rules.calculateAllLegalMoves(this);
            isDirty = false;
        }
        
        return this.legalMoves;
    }
    
    /**
     * 
     * @param rules
     * @return true if the cached information needs to be recalculated or is 
     * calculated using a different rules
     */
    public boolean isDirty(Rules rules) {
        return isDirty || !(rules.equals(this.rules));
    }   
    

    /**
     * 
     * @param state
     * @return true if this repeats this game state in the sense of the FIDE 
     * rules of chess art 9.2:
     * - the same player is to move
     * - the castling information should be the same* 
     * - the location of the pieces on the board should be the same
     * - the same moves (e.g. en passant captures should be allowed). This differs from just
     *   having the en passant fields being equal.
     */
    public boolean repeats(GameState state) {
        if (this == state) {
            return true;
        }
        if (state == null) {
            return false;
        }
        
        if (this.activeColor != state.activeColor) {
            return false;
        }        

        if (this.whiteCanCastleKingside != state.whiteCanCastleKingside) {
            return false;
        }
        if (this.whiteCanCastleQueenside != state.whiteCanCastleQueenside) {
            return false;
        }
        if (this.blackCanCastleKingside != state.blackCanCastleKingside) {
            return false;
        }
        if (this.blackCanCastleQueenside != state.blackCanCastleQueenside) {
            return false;
        }

        if (!Arrays.deepEquals(this.board, state.board)) {
            return false;
        }
        
        if(!this.getLegalMoves().equals(state.getLegalMoves())){
            return false;
        }

        return true;
    }    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Arrays.deepHashCode(this.board);
        hash = 79 * hash + Objects.hashCode(this.activeColor);
        hash = 79 * hash + (this.whiteCanCastleKingside ? 1 : 0);
        hash = 79 * hash + (this.whiteCanCastleQueenside ? 1 : 0);
        hash = 79 * hash + (this.blackCanCastleKingside ? 1 : 0);
        hash = 79 * hash + (this.blackCanCastleQueenside ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.enPassantField);
        hash = 79 * hash + this.halfMoveClock;
        hash = 79 * hash + this.fullMoveNumber;
        hash = 79 * hash + (this.drawOffered ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameState other = (GameState) obj;
        if (this.whiteCanCastleKingside != other.whiteCanCastleKingside) {
            return false;
        }
        if (this.whiteCanCastleQueenside != other.whiteCanCastleQueenside) {
            return false;
        }
        if (this.blackCanCastleKingside != other.blackCanCastleKingside) {
            return false;
        }
        if (this.blackCanCastleQueenside != other.blackCanCastleQueenside) {
            return false;
        }
        if (this.halfMoveClock != other.halfMoveClock) {
            return false;
        }
        if (this.fullMoveNumber != other.fullMoveNumber) {
            return false;
        }
        if (this.drawOffered != other.drawOffered) {
            return false;
        }
        if (!Arrays.deepEquals(this.board, other.board)) {
            return false;
        }
        if (this.activeColor != other.activeColor) {
            return false;
        }
        if (!Objects.equals(this.enPassantField, other.enPassantField)) {
            return false;
        }
        return true;
    }
}
