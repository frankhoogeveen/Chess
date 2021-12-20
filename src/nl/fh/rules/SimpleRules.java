/*
 * License: GPL v3
 * 
 */
package nl.fh.rules;

import nl.fh.gamestate.GameState;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import nl.fh.chess.BoardSide;
import nl.fh.chess.Color;
import nl.fh.chess.Field;
import nl.fh.chess.MoveRange;
import nl.fh.chess.MoveRangeType;
import nl.fh.chess.PieceKind;
import nl.fh.chess.PieceType;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.move.Castling;
import nl.fh.move.DrawOfferAccepted;
import nl.fh.move.EnPassantCapture;
import nl.fh.move.Move;
import nl.fh.move.PieceMove;
import nl.fh.move.Promotion;
import nl.fh.move.Resignation;
import nl.fh.player.Player;

/**
 * Represent the rules of the game
 */
public class SimpleRules implements Rules{

    @Override
    public GameState getInitialState() {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        return GameState.fromFEN(fen, this);
    }

    @Override
    public boolean isLegalMove(Move move, GameState state) {
        return state.getRules().equals(this) && state.getLegalMoves().contains(move);
    }
    
    @Override
    public Set<Move> calculateAllLegalMoves(GameState state) {
        // if the legal moves are already buffered, reuse them
        if(!state.isDirty(this)){
            return state.getLegalMoves();
        }
        
        Set<Move> result = new HashSet<Move>();
       
        // checks to check e.g. 50 move rule, or king remaining in check here
        
        if(state.isDrawOffered()){
            result.add(DrawOfferAccepted.getInstance());
        }
       
        addAllPieceMoves(state, result);
        
        addAllCastlingMoves(state, result);
        
        if(state.allowsEnPassant()){
            addAllEnPassantMoves(state, result);
        }
        
        //remove all moves that leave the king in check
        Set<Move> excludes = movesLeavingKingInCheck(result, state);
        result.removeAll(excludes);
        return result;
    } 

    /**
     * 
     * @param state of the game
     * @param result the set to which all valid piece moves are added
     *  The moves resulting in promotion are included here
     */
    private void addAllPieceMoves(GameState state, Set<Move> result) {
        Color toMove = state.getToMove();
        for(Field field : Field.getAll()){
            if(state.getFieldContent(field).getColor() == toMove){
                addAllPieceMovesFromField(state, field, result);
            }
        }
    }

    /**
     * 
     * @param state
     * @param field
     * @param result set to which all the moves from a given field are added
     */
    private void addAllPieceMovesFromField(GameState state, Field field, Set<Move> result) {
        Set<MoveRange> ranges = field.getMoveRanges(state.getFieldContent(field));
        for(MoveRange range : ranges){
            addAllPieceMovesFromRange(state, field, range, result);
        }
    }

    /**
     * 
     * @param state
     * @param field
     * @param range
     * @param result to which all piece moves or promotions are added
     */
    private void addAllPieceMovesFromRange(GameState state, Field field, MoveRange range, Set<Move> result) {
        boolean done = false;
        for(Field to : range.getRange()){
            if(!done){
                PieceType captured = state.getFieldContent(to);
                if(captured == PieceType.EMPTY){
                    if(range.getType() != MoveRangeType.CAPTURE_OBLIGATORY){
                        addPieceMoveOrPromotions(state, field, to, result);
                    }
                } else if(captured.getColor() == state.getToMove()){
                    done = true;
                } else {
                    if(range.getType() != MoveRangeType.CAPTURE_FORBIDDEN){
                        addPieceMoveOrPromotions(state, field, to, result);
                    }
                    done = true;
                }
            }
        }
    }
    
    /**\
     * 
     * @param state
     * @param from
     * @param to
     * @param result
     * 
     * The logic to either add a PieceMove or promotions is contained in this method
     */
    private void addPieceMoveOrPromotions(GameState state, Field from, Field to, Set<Move> result){
        
        PieceKind movingPiece = state.getFieldContent(from).getKind();
        int y = to.getY();
        
        boolean promotion =  (movingPiece == PieceKind.PAWN)&&((y == 7) || (y==0));
        
        if(!promotion){
            result.add(PieceMove.getInstance(from, to));
            return;
        }
        
        if(promotion){
            result.add(Promotion.getInstance(from, to, PieceKind.QUEEN));
            result.add(Promotion.getInstance(from, to, PieceKind.ROOK));
            result.add(Promotion.getInstance(from, to, PieceKind.BISHOP));
            result.add(Promotion.getInstance(from, to, PieceKind.KNIGHT));            
        } 
    }

    /**
     * 
     * @param state
     * @param set to which all legal castling moves are added
     */
    private void addAllCastlingMoves(GameState state, Set<Move> set) {
        if(state.getCastlingAllowedFlag(state.getToMove(), BoardSide.KINGSIDE) && checkCastlingConditions(state, BoardSide.KINGSIDE)){
            set.add(Castling.getInstance(BoardSide.KINGSIDE));
        }
        if(state.getCastlingAllowedFlag(state.getToMove(), BoardSide.QUEENSIDE) && checkCastlingConditions(state, BoardSide.QUEENSIDE)){
            set.add(Castling.getInstance(BoardSide.QUEENSIDE));
        }        
    }

    private boolean checkCastlingConditions(GameState state, BoardSide boardSide) {
        boolean result = true;
        if((state.getToMove() == Color.WHITE) && (boardSide == BoardSide.KINGSIDE)){
            result = result && (state.getFieldContent(4, 0) == PieceType.WHITE_KING);
            result = result && (state.getFieldContent(5, 0) == PieceType.EMPTY);
            result = result && (state.getFieldContent(6, 0) == PieceType.EMPTY);            
            result = result && (state.getFieldContent(7, 0) == PieceType.WHITE_ROOK);
            result = result && (!isCovered(Field.getInstance(4,0), state, Color.BLACK));
            result = result && (!isCovered(Field.getInstance(5,0), state, Color.BLACK));
            result = result && (!isCovered(Field.getInstance(6,0), state, Color.BLACK));            
        }
        
        if((state.getToMove() == Color.BLACK) && (boardSide == BoardSide.KINGSIDE)){
            result = result && (state.getFieldContent(4,7) == PieceType.BLACK_KING);
            result = result && (state.getFieldContent(5,7) == PieceType.EMPTY);
            result = result && (state.getFieldContent(6,7) == PieceType.EMPTY);             
            result = result && (state.getFieldContent(7,7) == PieceType.BLACK_ROOK);
            result = result && (!isCovered(Field.getInstance(4,7), state, Color.WHITE));
            result = result && (!isCovered(Field.getInstance(5,7), state, Color.WHITE));
            result = result && (!isCovered(Field.getInstance(6,7), state, Color.WHITE));            
        }    
        
        if((state.getToMove() == Color.WHITE) && (boardSide == BoardSide.QUEENSIDE)){
            result = result && (state.getFieldContent(4,0) == PieceType.WHITE_KING);
            result = result && (state.getFieldContent(3,0) == PieceType.EMPTY);
            result = result && (state.getFieldContent(2,0) == PieceType.EMPTY);   
            result = result && (state.getFieldContent(1,0) == PieceType.EMPTY);              
            result = result && (state.getFieldContent(0,0) == PieceType.WHITE_ROOK);
            result = result && (!isCovered(Field.getInstance(4,0), state, Color.BLACK));
            result = result && (!isCovered(Field.getInstance(3,0), state, Color.BLACK));
            result = result && (!isCovered(Field.getInstance(2,0), state, Color.BLACK));            
        }
        
        if((state.getToMove() == Color.BLACK) && (boardSide == BoardSide.QUEENSIDE)){
            result = result && (state.getFieldContent(4,7) == PieceType.BLACK_KING);
            result = result && (state.getFieldContent(3,7) == PieceType.EMPTY);
            result = result && (state.getFieldContent(2,7) == PieceType.EMPTY);   
            result = result && (state.getFieldContent(1,7) == PieceType.EMPTY);              
            result = result && (state.getFieldContent(0,7) == PieceType.BLACK_ROOK);
            result = result && (!isCovered(Field.getInstance(4,7), state, Color.WHITE));
            result = result && (!isCovered(Field.getInstance(3,7), state, Color.WHITE));
            result = result && (!isCovered(Field.getInstance(2,7), state, Color.WHITE));            
        }             
        
        return result;
    }
    
    
    /**
     * 
     * @param field
     * @param state
     * @param color of a player
     * @return true if the field is covered by the player color 
     */
    boolean isCovered(Field field, GameState state, Color color){
        return (controllingFields(field, state, color).size() > 0);
    }
    
    /**
     * 
     * @param field 
     * @param state
     * @param color
     * 
     * @return the set of all fields from which the field is controlled by color  
     */
    private Set<Field> controllingFields(Field field, GameState state, Color color){
        Set<Field> result = new HashSet<Field>();
        for(Field f : Field.getAll()){
            if(state.getFieldContent(f).getColor() == color){
                for(MoveRange range : f.getMoveRanges(state.getFieldContent(f))){
                    if(range.getType() != MoveRangeType.CAPTURE_FORBIDDEN){
                        boolean done = false;
                        for(Field f2 : range.getRange()){
                            if(f2.equals(field) && !done){
                                result.add(f2);
                            }
                            if(state.getFieldContent(f2) != PieceType.EMPTY){
                                done = true;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * 
     * @param state
     * @param result set to which all allowed castling moves are added 
     */
    private void addAllEnPassantMoves(GameState state, Set<Move> result) {
        if(!state.allowsEnPassant()){
            return;
        }
        
        Field to = state.getEnPassantField();
        
        int x = to.getX();
        if(state.getToMove() == Color.WHITE){
            if(x>0){
                Field from = Field.getInstance(x-1, 4);
                if(state.getFieldContent(from) == PieceType.WHITE_PAWN){
                    result.add(EnPassantCapture.getInstance(from, to));
                }
            } 
            if(x < 7){
                Field from = Field.getInstance(x+1, 4);
                if(state.getFieldContent(from) == PieceType.WHITE_PAWN){                
                    result.add(EnPassantCapture.getInstance(from, to)); 
                }
            }
            
        } else if(state.getToMove() == Color.BLACK) {
            if(x>0){
                Field from = Field.getInstance(x-1, 3);
                if(state.getFieldContent(from) == PieceType.BLACK_PAWN){
                    result.add(EnPassantCapture.getInstance(from, to));
                }
            } 
            if(x < 7){
                Field from = Field.getInstance(x+1, 3);
                if(state.getFieldContent(from) == PieceType.BLACK_PAWN){                
                    result.add(EnPassantCapture.getInstance(from, to)); 
                }
            }            
            
        }
        
        
    }    

    /**
     * 
     * @param set
     * @param state 
     * @return all moves that leave the king in check
     */
    private Set<Move> movesLeavingKingInCheck(Set<Move> set, GameState state) {
        Set<Move> result = new HashSet<Move>();
        for(Move move : set){
            GameState postState = state.apply(move);
            if(kingRemainsInCheckAfterMove(postState)){
                result.add(move);
            }
        }
        return result;
    }

    
    /**
     * 
     * @param postState
     * @return true if the king remained in check after the last move, 
     * or when there is no king (of the color that just moved) on the board.
     */
    private boolean kingRemainsInCheckAfterMove(GameState postState) {
        Color attackerColor = postState.getToMove();
        Color kingColor = attackerColor.flip();
        
        Field kingField = findKing(kingColor, postState);
        
        if(kingField == null){
            // in case there is no king on the board, this makes the postState illegal 
            return true;
        }
        
        boolean result = isCovered(kingField, postState, attackerColor);
        return result;
    }
    
    /**
     * 
     * @param state
     * @return true if the king that has to move next is in check 
     */
    @Override
    public boolean isCheck(GameState state){
        Color playerColor = state.getToMove();
        Color opponentColor = playerColor.flip();
        Field kingField = findKing(playerColor, state);
        
        boolean result =isCovered(kingField, state, opponentColor);
        return result;
    }    

     /**
     * 
     * @param state
     * @param color
     * @return the field where the king of the given color stands 
     * 
     * If there is no king of the given color on the board, null is returned.
     * If there are more than one kings of the given color on the board,
     * a field containing the king is returned.
     */
    private Field findKing(Color kingColor, GameState postState) {
        PieceType kingPiece;
        if(kingColor == Color.WHITE){
            kingPiece = PieceType.WHITE_KING;
        } else {
            kingPiece = PieceType.BLACK_KING;
        }
        Field kingField = null;
        for(Field f : Field.getAll()){
            if(postState.getFieldContent(f) == kingPiece){
                kingField = f;
            }
        }
        return kingField;
    }
    
    @Override
    public boolean isMate(GameState state){
        return state.getLegalMoves().isEmpty() && isCheck(state);
    }
    
    @Override
    public boolean isStaleMate(GameState state){
        return state.getLegalMoves().isEmpty() && !isCheck(state);
    }
    
    @Override
    public boolean isAtFiftyMoveRule(GameState currentState) {
         return (currentState.getHalfMoveClock() >= 100);
    }   
    
    @Override
    public boolean isThreeFoldRepetition(GameState state) {
        return (state.countRepetitions() > 2);
    }    
    
    @Override
    public boolean isDrawn(GameState state){
        boolean result = false;
        result = result || isStaleMate(state);
        result = result || !sufficientMaterial(state);
        result = result || isAtFiftyMoveRule(state);
        result = result || isThreeFoldRepetition(state);
        return result;
    }    
    
    @Override
    public GameReport playGame(Player whitePlayer, Player blackPlayer){
        return playGame(whitePlayer, blackPlayer, this.getInitialState());
    }   
    
    @Override
    public GameReport playGame(Player whitePlayer, Player blackPlayer, GameState initialState) {
        GameReport report = new GameReport();
        
        // add the required tags
        report.addTag("White", whitePlayer.getDescription());
        report.addTag("Black", blackPlayer.getDescription());
        report.addTag("Result", "*");
        
        if(!initialState.equals(this.getInitialState())){
            report.addTag("SetUp", "1");
            report.addTag("FEN", initialState.toFEN());
        }
        
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        report.addTag("Date", formattedDate);
        
        
        // start playing the game
        Player currentPlayer = whitePlayer;        
        GameState currentState = initialState;
        report.addGameState(currentState);        
        Set<Move> legalMoves = currentState.getLegalMoves();        
        
        while(report.getGameResult() == GameResult.UNDECIDED){
            // invite the current player to make a move
            Move move = currentPlayer.getMove(currentState);
            report.addMove(move); 
            
            // resignation ends the game on the spot
            if(move instanceof Resignation){
                if(currentPlayer == whitePlayer){
                    report.setResult(GameResult.RESIGNATION_BY_WHITE);
                } else {
                    report.setResult(GameResult.RESIGNATION_BY_BLACK);
                }
                return report;                
            }
            
            // making an illegal move ends the game on the spot
            if(!legalMoves.contains(move)){
                if(currentPlayer == whitePlayer){
                    report.setResult(GameResult.ILLEGAL_MOVE_BY_WHITE);
                } else {
                    report.setResult(GameResult.ILLEGAL_MOVE_BY_BLACK);
                }
                return report;
            }
            
            // accepted draw offers end the game on the spot
            if(move instanceof DrawOfferAccepted){
                report.setResult(GameResult.DRAW_AGREED);
                return report;
            }             
            
            // update and register the currentstate
            currentState = move.applyTo(currentState);
            legalMoves = currentState.getLegalMoves();

            report.addGameState(currentState);
            
            //insufficient material ends the game on the spot
            if(!sufficientMaterial(currentState)){
                report.setResult(GameResult.DRAW_INSUFFICIENT_MATERIAL);                
                return report;
            }

            // (stale) mate ends the game
            if(legalMoves.isEmpty()){
                if(isCheck(currentState)){
                    if(currentPlayer == whitePlayer){
                        report.setResult(GameResult.WIN_WHITE);
                    } else {
                        report.setResult(GameResult.WIN_BLACK);
                    }
                } else {
                    report.setResult(GameResult.DRAW_STALEMATE);
                }
                
                return report;
            }
            
            if(isThreeFoldRepetition(report, currentState)){
                report.setResult(GameResult.DRAW_BY_THREEFOLD_REPETITION);
            }
            
            // fifty move rule
            if(isAtFiftyMoveRule(currentState)){
                report.setResult(GameResult.DRAW_BY_50_MOVE_RULE);                
            }
            
            // give the turn to the other player
            if(currentPlayer == whitePlayer){
                currentPlayer = blackPlayer;
            } else {
                currentPlayer = whitePlayer;
            }
        }
        
        return report;
    }

    private boolean isThreeFoldRepetition(GameReport report, GameState state) {
        int nrep = 0;
        for(GameState state2 : report.getStateList()){
            if(state2.repeats(state)){
                nrep += 1;
            }
        }
        return (nrep >= 3);
    }

    /**
     * 
     * @param currentState
     * @return true if there still is sufficient material on the board to mate 
     * 
     * This method only declares insufficient material for 
     * - K vs K
     * - K vs KN
     * - K vs KB
     */
    private boolean sufficientMaterial(GameState currentState) {
        int whiteB = 0;
        int whiteN = 0;
        int blackB = 0;
        int blackN = 0;
        for(Field f : Field.getAll()){
            switch(currentState.getFieldContent(f)){
                case EMPTY:
                    break;
                case WHITE_PAWN:
                case BLACK_PAWN:
                case WHITE_ROOK:
                case BLACK_ROOK:
                case WHITE_QUEEN:
                case BLACK_QUEEN:
                    return true;
                case WHITE_KING:
                case BLACK_KING:
                    break;
                case WHITE_BISHOP:
                    whiteB += 1;
                    if(whiteB > 1){
                        return true;
                    }
                    break;
                case BLACK_BISHOP:
                    blackB += 1;
                    if(blackB > 1){
                        return true;
                    }
                    break;
                case WHITE_KNIGHT:
                    whiteN += 1;
                    if(whiteN > 1){
                        return true;
                    }
                    break;
                case BLACK_KNIGHT:
                    blackN += 1;
                    if(blackN > 1){
                        return true;
                    }
                    break;
                default:
                    System.out.println(currentState.toFEN());
                    System.out.println(f);
                    System.out.println(currentState.getFieldContent(f));
                    throw new IllegalStateException("switch statement not complete");
            }
        }
        
        return false;
    }

}

