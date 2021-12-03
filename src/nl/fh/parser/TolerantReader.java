/*
 * License: GPL v3
 * 
 */

package nl.fh.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.fh.chess.BoardSide;
import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.chess.PieceType;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.GameState;
import nl.fh.move.Castling;
import nl.fh.move.EnPassantCapture;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
import nl.fh.move.Promotion;
import nl.fh.rules.Rules;
import nl.fh.rules.SimpleRules;

/**
 * A basic reader implementing parts of the standard and designed to be tolerant.
 * - inconsistent numbering of moves is ignores
 * - incorrect '+' of '#' are ignored
 * - the game result is read from the pgn file and may be inconsistent with the moves
 * - in case of inconsistencies in the pgn file, this reader tries to produce a
 *   game report. It is not necessary that the game report is consistent
 * - if there is a tag with key "FEN" present, its value is used as the starting position
 *   having other keys present ("SetUp"  or "VariantFromPosition") is NOT required
 * 
 * In the current version the brackets (), {} and &lt;&gt; are not supported yet.
 * 
 * 
 * copyright F. Hoogeveen
 * @author frank
 */
public class TolerantReader implements PGN_Reader{
    String pgn;
    Rules rules;
    
    ArrayList<GameReport> result;
    GameReport currentReport;
    int index;                  // the character that is about to be read
    boolean processingTags;     // could possibly be removed
    String currentKey;          // stores the current key while processing tags
    String currentValue;        // idem for the value
    boolean terminated;         // is set to true when a game result has been encountered in the move text
    ArrayList<String> moveCodes;// temporary list of moveCodes
   
    @Override
    public List<GameReport> getGames(String pgn) {
        this.pgn = pgn;
        this.rules = new SimpleRules();
        
        result = new ArrayList<GameReport>();
        currentReport = new GameReport();
        processingTags = true;
        moveCodes = new ArrayList<String>();
        
        index = 0;
        while(index < pgn.length()){
            try {
                processLine();
            } catch (PgnException ex) {
                dropCurrentGame();
            }
        }
        
        return result;
    }

    /**
     * processes a single line of PGN code. It is assumed that the tokens 
     * are not spread out over multiple lines. I.e. the text of the token should
     * not contain the "\n" character.
     * 
     * When this method is called, the index should be at the start of a line.
     * (either at the start of a string, or right after '\n'
     */
    private void processLine() throws PgnException {
        if(pgn.charAt(index) == '%'){
            skipToNextLine();
            return;
        }
        processNonEscapedLine();
    }

    /**
     * skip the remainder of the current line
     */
    private void skipToNextLine() {

        while((index < pgn.length()) && (pgn.charAt(index) != '\n')){
            index += 1;
        }
        if(index < pgn.length()){
            index += 1;
        }
    }

    private void processNonEscapedLine() throws PgnException {        
        skipWhiteSpace();
        if(index >= pgn.length()){
            return;
        }
        switch(pgn.charAt(index)){
            case '[' :
                index += 1;
                processingTags = true;
                processTag();
                return;
            case ']':
                index += 1;
                processNonEscapedLine();
                return;
            case ';' :
                index += 1;
                skipToNextLine();
                return;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '*':
                processingTags = false;
                processMoveText();
                return;
            case '\n':
                index +=1;
                return;
            default:
                throw new IllegalArgumentException(Long.toString(index)+ ":" + pgn.substring(index, index+1));
        }
        
    }

    /**
     * skips white space between tokens. Should not treat '\n' as whitespace.
     * 
     * Skipping tabs and whitespace other than ' ' is currently not implemented
     */
    private void skipWhiteSpace() {
        while((index < pgn.length()) && (pgn.charAt(index) == ' '))
        {
            index += 1;
        };
    }

    private void processTag() throws PgnException {
        processTagKey();
        skipWhiteSpace();
        processTagValue();
        
        this.currentReport.addTag(currentKey, currentValue);
    }

    private void processMoveText() throws PgnException {
        this.terminated = false;
        checkTerminator();  
        while(!terminated){
          switch(pgn.charAt(index)){
            case '\n':
                  return;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                processMoveNumber();
                break;
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'K':
            case 'Q':
            case 'R':
            case 'B':
            case 'N':
            case 'O':    // used for castling
                processMove();
                break;
            default:
                throw new PgnException("unexpected character in MoveText");
            } 
          checkTerminator();            
        }
    }

    private void processTagKey() {
        StringBuilder sb = new StringBuilder();
        do{
            sb.append(pgn.charAt(index));            
            index += 1;
        } while(pgn.charAt(index) != ' ');        
        this.currentKey = sb.toString();
    }

    private void processTagValue() throws PgnException {
        StringBuilder sb = new StringBuilder();
        
        // the first character needs to be a quote
        if(pgn.charAt(index) != '"'){
            sb.append("expected \", found ");
            sb.append(pgn.charAt(index));
            throw new PgnException(sb.toString());
        }
        index += 1;
        
        // loop until we find a quote, skipping over all(!) escape characters
        // while the pgn standard only defines \" and \\,  this implementation
        // substitutes "x" for "\x" for all x;
        boolean escaped = false;
        while((index < pgn.length()) && ((pgn.charAt(index)!= '"' || escaped))){
            if(escaped){
                sb.append(pgn.charAt(index));
                escaped = false;
            } else if(pgn.charAt(index) == '\\'){
                escaped = true;
            } else {
                sb.append(pgn.charAt(index));           
            }
            
            index += 1;               
         
        }
        
        // skip the ending quote
        index += 1;

        currentValue = sb.toString();
        
    }

    // called when a game can be stored
    private void wrapUp() throws PgnException {
        playMoveCodes();        
        this.result.add(currentReport);

        this.currentReport = new GameReport();
        this.moveCodes = new ArrayList<String>();
        skipToNextLine();
    }
    
    // called when a game cannot be parsed
    private void dropCurrentGame() {       
        this.currentReport = new GameReport();
        this.moveCodes = new ArrayList<String>();
        skipToNextLine();
    }    

    // get the code of Move and add it to the list
    private void processMove() {
        StringBuilder sb = new StringBuilder();
        while(index < pgn.length() && !isWhiteSpace(pgn.charAt(index))){
            sb.append(pgn.charAt(index));
            index += 1;
        }
        moveCodes.add(sb.toString());
        skipWhiteSpace();
    }
    
    /**
     * 
     * @param c
     * @return true if c is a whitespace character, false otherwise 
     */
    private boolean isWhiteSpace(char c){
        if(c == ' '){
            return true;
        } else if (c == '\n'){
            return true;
        }
        return false;
    }

    private void checkTerminator() throws PgnException {
        if(pgn.charAt(index) == '*'){
            currentReport.setResult(GameResult.UNDECIDED);
            wrapUp();
            this.terminated = true;
            return;
        }
        
        if(index >= pgn.length()-1){
            return;
        }
        
        if(pgn.charAt(index) == '1' && pgn.charAt(index+1) == '-'){
            // we are checking here for "1-", but we actually need "1-0"
            index += 3; 
            currentReport.setResult(GameResult.WIN_WHITE);
            wrapUp();
            this.terminated = true;
            return;            
        }
        
        if(pgn.charAt(index) == '1' && pgn.charAt(index+1) == '/'){
            // we are checking here for "1/", but we actually need "1/2-1/2"
            index += 7; 
            currentReport.setResult(GameResult.DRAW);
            wrapUp();
            this.terminated = true;
            return;            
        }
        
        if(pgn.charAt(index) == '0' && pgn.charAt(index+1) == '-'){
            // we are checking here for "0-", but we actually need "0-1"
            index += 3; 
            currentReport.setResult(GameResult.WIN_BLACK);
            wrapUp();
            this.terminated = true;          
        }        
    }

    // skips any identifier and the spaces trailing it
    private void processMoveNumber() {
        while((index < pgn.length()) && ('0' <= pgn.charAt(index)) && (pgn.charAt(index) <='9')){            
            index += 1;
        }
        if((index < pgn.length()) &&(pgn.charAt(index) == '.')){
            index += 1;
        }
        skipWhiteSpace();
    }
    
    // this method takes the movecodes and converts them into
    // gamestates
    public void playMoveCodes() throws PgnException{
        GameState state;
        
        // if the initial state has been specified, use that. Otherwise
        // get the initial state from the ruleset
        if(currentReport.getTags().contains("FEN")){
            String fen = currentReport.getTag("FEN");
            state = GameState.fromFEN(fen);
        } else {
            state = this.rules.getInitialState();
        }
        currentReport.addGameState(state);
        
        // process all the moves
        for(String moveCode : this.moveCodes){
                     
            Move move = decodeMove(moveCode, state);
            state = state.apply(move);
            
            currentReport.addGameState(state);
            currentReport.addMove(move);
        }
    }

    private Move decodeMove(String moveCode, GameState state) throws PgnException {

        String moveCode2 = cleanMove(moveCode);
        
        if(Character.isUpperCase(moveCode.charAt(0))){
            return decodePieceMove(moveCode2, state);
        } else {
            return decodePawnMove(moveCode2, state);
        }
    }

    /**
     * 
     * @param moveCode
     * @return the move code with redundant information removed 
     */
    private String cleanMove(String moveCode) {
        String clean = moveCode;
        clean = clean.replace("+","");
        clean = clean.replace("#","");
        clean = clean.replace("e.p.", "");
        clean = clean.replace("x","");
        return clean;
    }  
    
    private Move decodePieceMove(String moveCode, GameState state) throws PgnException {
        if(moveCode.equals("O-O")){
            return Castling.getInstance(BoardSide.KINGSIDE);
        } 
        
        if(moveCode.equals("O-O-O")){
            return Castling.getInstance(BoardSide.QUEENSIDE);            
        }
        
        char[] c = moveCode.toCharArray();
        int len = c.length;
        
        char pieceChar = c[0];
        char rowChar = c[len-1];
        char colChar = c[len-2];
        
        char extraChar = '?';
        if(len == 4){
            extraChar = c[1];
        }
        
        Move move = reconstructMove(pieceChar, rowChar, colChar, extraChar, state);
        return move;
        
    }

    private Move decodePawnMove(String moveCode, GameState state) throws PgnException {        
        char[] c = moveCode.toCharArray();
        int len = c.length;

        if( c[len-2] != '='){
            return decodeRegularPawnMove(c, state);          
        }

        // deal with promotions
        char promotionChar = c[len-1];
        char[] cc = new char[len-2]; 
        System.arraycopy(c, 0, cc, 0, len-2);
        
        Move temp =  decodeRegularPawnMove(cc, state);
        Field from = temp.getFrom();
        Field to   = temp.getTo();
        
        PieceType movingPiece = state.getFieldContent(from);
        if((movingPiece != PieceType.WHITE_PAWN) && (movingPiece != PieceType.BLACK_PAWN)){
            throw new PgnException("Piece other than pawn is promoting");
        }
        
        Color toMove = state.getToMove();
        PieceType promotedPiece = promotionPiece(promotionChar, toMove);
            
        return Promotion.getInstance(from, to, promotedPiece);
    }    
    
    private PieceType promotionPiece(char c, Color color) throws PgnException{
        if(color == Color.WHITE){
            switch(c){
                case 'Q':
                    return PieceType.WHITE_QUEEN;
                case 'R':
                    return PieceType.WHITE_ROOK;
                case 'B':
                    return PieceType.WHITE_BISHOP;
                case 'N':
                    return PieceType.WHITE_KNIGHT;
                default:
                    throw new PgnException("Piece Code for promotion piece invalid");
            }
        } else if(color == Color.BLACK){
            switch(c){
                case 'Q':
                    return PieceType.BLACK_QUEEN;
                case 'R':
                    return PieceType.BLACK_ROOK;
                case 'B':
                    return PieceType.BLACK_BISHOP;
                case 'N':
                    return PieceType.BLACK_KNIGHT;
                default:
                    throw new PgnException("Piece Code for promotion piece invalid");
            }            
        }
        throw new PgnException("Color Code for promotion piece invalid");
    }
    
    
    private Move decodeRegularPawnMove(char[] c, GameState state) throws PgnException {        
        int len = c.length;
        
        char pieceChar = 'P';
        char rowChar = c[len-1];
        char colChar = c[len-2];
        
        char extraChar = '?';
        if(len == 3){
            extraChar = c[0];
        }
        
        Field to = Field.getInstance(colChar-'a', rowChar-'1');
        if(state.allowsEnPassant() && state.getEnPassantField().equals(to)){
            return decodeEnPassantMove(rowChar, colChar, extraChar, state);
        }
        
        Move move = reconstructMove(pieceChar, rowChar, colChar, extraChar, state);
        return move;
    }
    
    private Move decodeEnPassantMove(char rowChar, char colChar,char extraChar,GameState state) throws PgnException{
        Field from = null;
        Field to = null;
        if(state.getToMove() == Color.WHITE){
            from = Field.getInstance(extraChar - 'a', 4);
            to = Field.getInstance(colChar - 'a', 5);
            if(rowChar != '6'){
                throw new PgnException("en passant: this should not happen");
            }
        } else if(state.getToMove() == Color.BLACK) {
            from = Field.getInstance(extraChar - 'a', 3);
            to = Field.getInstance(colChar - 'a', 3);   
            if(rowChar != '3'){
                throw new PgnException("en passant: this should not happen");
            }            
        }
        
        return EnPassantCapture.getInstance(from, to);
    }

    private Move reconstructMove(char pieceChar, char rowChar, char colChar, char extraChar, GameState state) throws PgnException {
        PieceType ptype = PieceType.EMPTY;
        Color toMove = state.getToMove();
        
        if(toMove == Color.WHITE){
            switch(pieceChar){
                case 'K':
                    ptype = PieceType.WHITE_KING;
                    break;
                case 'Q':
                    ptype = PieceType.WHITE_QUEEN;
                    break;                    
                case 'R':
                    ptype = PieceType.WHITE_ROOK;
                    break;                    
                case 'B':
                    ptype = PieceType.WHITE_BISHOP;
                    break;                    
                case 'N':
                    ptype = PieceType.WHITE_KNIGHT;
                    break;                    
                case 'P':
                    ptype = PieceType.WHITE_PAWN;
                    break;                    
                default:
                    throw new IllegalArgumentException("cannot parse piece type " + Character.toString(pieceChar));
            }
        } else if(toMove == Color.BLACK){
            switch(pieceChar){
                case 'K':
                    ptype = PieceType.BLACK_KING;
                    break;
                case 'Q':
                    ptype = PieceType.BLACK_QUEEN;
                    break;                    
                case 'R':
                    ptype = PieceType.BLACK_ROOK;
                    break;                    
                case 'B':
                    ptype = PieceType.BLACK_BISHOP;
                    break;                    
                case 'N':
                    ptype = PieceType.BLACK_KNIGHT;
                    break;                    
                case 'P':
                    ptype = PieceType.BLACK_PAWN;
                    break;                    
                default:
                    throw new IllegalArgumentException("cannot parse piece type " + Character.toString(pieceChar));
            }
        }
        
        Set<Field> fields = state.getPieceLocations(ptype);        
        // if there are no pieces of the proper type on the board, abort 
        if(fields.isEmpty()){
            throw new PgnException("No pieces of the correct type on the board");
        }        
        
        // if there is only one piece of the proper type on the board, use that
        // piece in the move
        //
        if(fields.size() == 1){
            Field from = fields.iterator().next();
            Field to = Field.getInstance(colChar - 'a', rowChar - '1');
            Move move = PieceMove.getInstance(from, to);
            return move;
        }
        
        // if there is more than one piece of the type that has been moved, 
        // check which ones could have legally been moved
        //TODO take care of pen passent here
        Set<Move> legalMovesToField = new HashSet<Move>();
        Field to = Field.getInstance(colChar-'a',rowChar-'1');
        
        Set<Move> allLegalMoves = rules.getAllLegalMoves(state);
        
        for(Field from : fields){
            Move move = PieceMove.getInstance(from, to);
            if(allLegalMoves.contains(move)){
                legalMovesToField.add(move);
            }
            
            // for pawns, also check the promotions. Only promotion to queen need to be checked
            if(pieceChar == 'P'){
                move = Promotion.getInstance(from, to, PieceType.WHITE_QUEEN);
                if(allLegalMoves.contains(move)){
                    legalMovesToField.add(move);
                }
                
                move = Promotion.getInstance(from, to, PieceType.BLACK_QUEEN);
                if(allLegalMoves.contains(move)){
                    legalMovesToField.add(move);
                }                
            }
        }
        
        // if there is only one legal move, use that one
        if(legalMovesToField.size() ==  1){
            return legalMovesToField.iterator().next();
        }
        // if there is more than one legal move, use the extraChar to discriminate
        for(Move move : legalMovesToField){
            Field from = ((PieceMove)move).getFrom();
            if((from.getX() == (extraChar - 'a')) || (from.getY() == (extraChar - '1'))){
                return move;
            }
        }
     
        throw new PgnException("TolerantReader: cannot reconstruct move");
    }
}
