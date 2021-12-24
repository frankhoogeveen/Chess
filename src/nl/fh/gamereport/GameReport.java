/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.move.ChessMove;
import nl.fh.move.Move;
import nl.fh.player.Player;
import nl.fh.rules.ChessResultArbiter;
import nl.fh.rules.GameDriver;
import nl.fh.rules.MoveGenerator;

/**
 * 
 * This class maintains a record a Game. It contains:
 * 
 * - The moves
 * - The game states
 * - annotations
 * 
 * The moves and game states have to be added both. 
 * It is not the concern of this game report to ensure that the
 * moves and game states are legal and consistent. 
 * 
 * copyright F. Hoogeveen
 * @author frank
 */
public class GameReport {

    //TODO move the seventag roster and the toPGN method out of the GameReport class to make it non chess specific
    //TODO separate the generic and the chess specific
    
    private static final List<String> SevenTagRoster = Arrays.asList(new String[] {
        "Event", 
        "Site",
        "Date",
        "Round",
        "White",
        "Black",
        "Result"
    });
    
    private GameDriver gameDriver;
    private final ArrayList<GameState> stateList;
    private final ArrayList<ChessMove> moveList;
    private GameResult gameResult;
    
    private final HashMap<String, String> tagValuePairs;

    public GameReport(){
        stateList = new ArrayList<GameState>();
        moveList = new ArrayList<ChessMove>();
        gameResult = GameResult.UNDECIDED;
        tagValuePairs = new HashMap<String, String>();
    }
    
    public List<GameState> getStateList() {
        return stateList;
    }
    
    /**
     * 
     * @return the final state of the reported game 
     * 
     * throws an exception when the stateList is empty
     */
    public GameState getFinalState(){
        if(stateList.isEmpty()){
            throw new IllegalStateException();
        }
        
        return this.stateList.get(this.stateList.size()-1);
    }

    public List<ChessMove> getMoveList() {
        return moveList;
    }
    
    /**
     * 
     * @return the final state of the reported game 
     * 
     * throws an exception when the stateList is empty
     */
    public Move getFinalMove(){
        if(moveList.isEmpty()){
            return null;
        }
        
        return this.moveList.get(this.moveList.size()-1);
    }    

    /**
     * 
     * @return the result of the game. 
     */
    public GameResult getGameResult() {
        return gameResult;
    }

    /**
     * 
     * @return the tags of this game report 
     */
    public Set<String> getTags(){
        return tagValuePairs.keySet();
    }
    
    /**
     * 
     * @param tag
     * @return an empty string if the tag has not been defined, the value
     * corresponding to the tag otherwise. Unlike the get method of a map,
     * this will not return null;
     */
    public String getTag(String tag){
        if(tagValuePairs.containsKey(tag)){
            return tagValuePairs.get(tag);
        } else {
            return "";
        }
    }
    
    /**
     * Sets a tag, possible overwriting earlier key value pairs, without notice
     * 
     * @param key
     * @param value
     */
    public void addTag(String key, String value){
        tagValuePairs.put(key, value);
    }
    
    /**
     * add a move to this record
     * @param move
     */
    public void addMove(ChessMove move){
        moveList.add(move);
    }
    
    /**
     * add a game state to this record
     * @param state 
     */
    public void addGameState(GameState state){
        stateList.add(state);
    }
    
    
    /**
     * add a ply ( a move and the resulting state to the record)
     * @param move
     * @param state 
     */
    public void addPly(ChessMove move, GameState state){
        moveList.add(move);
        stateList.add(state);        
    }
    
    /**
     * set the result of this game. In case a tag "Result" is defined,
     * it is overwritten
     * @param result 
     */
    public void setResult(GameResult result){
       gameResult = result;
       if(this.tagValuePairs.keySet().contains("Result")){
            this.addTag("Result", result.toString());
       }
    }
    
    /**
     * set the driver of the game that determines e.g. the initial state,
     * who is to move and when the game is finished with what outcome.
     * @param driver 
     */
    public void setGameDriver(GameDriver driver){
        this.gameDriver = driver;
    }
    
    /** write the report in .pgn format
     * 
     * @return a formatted report of the tags, moves and result; 
     */
    public String toPGN(){
        MoveGenerator moveGenerator = this.gameDriver.getMoveGenerator();
        
        StringBuilder sb = new StringBuilder();
        sb.append(sevenTagRoster());
        sb.append(otherTags());
        sb.append("\n");
        sb.append(movesString(moveGenerator));
        sb.append("\n");
        sb.append(resultString());
        sb.append("\n");
        return sb.toString();
    }

    private String sevenTagRoster() {
        StringBuilder sb = new StringBuilder();
        for(String tag : SevenTagRoster){
            sb.append(formatTagValuePair(tag));
            
        }
        return sb.toString();
    }

    private String otherTags() {
        StringBuilder sb = new StringBuilder();
        for(String tag : this.tagValuePairs.keySet()){
            if(!SevenTagRoster.contains(tag)){
                sb.append(formatTagValuePair(tag));
            }
        }
        return sb.toString();
    }
    
    private String formatTagValuePair(String tag){
        StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(tag);
            sb.append(" \"");
            sb.append(escape(tagValuePairs.get(tag)));
            sb.append("\"]");
            sb.append("\n");        
        return sb.toString();
    }

    private String movesString(MoveGenerator moveGenerator) {
        int triggerLineLength = 65;
        int startSBcontent = 0;
        
        StringBuilder sb = new StringBuilder();
        int moveCounter = 0;
        int currentPly = 0;

        GameState state;
        if(this.tagValuePairs.keySet().contains("FEN")){
            state = GameState.fromFEN(tagValuePairs.get("FEN"));
        } else {
            state = this.gameDriver.getInitialState();
        }
        
        while(currentPly < moveList.size()){
            if(currentPly % 2 == 0){
                // break the line when it gets too large
                if((sb.length() - startSBcontent) > triggerLineLength){
                    sb.append("\n");
                    startSBcontent = sb.length();
                }                
                // white's moves
                moveCounter += 1;
                sb.append(Integer.toString(moveCounter));
                sb.append(". ");
                sb.append(moveList.get(currentPly).formatPGN(state, this.gameDriver));
                sb.append(" ");
            } else {
                // black's moves
                sb.append(moveList.get(currentPly).formatPGN(state, this.gameDriver));
                sb.append(" ");
            }
            state = moveList.get(currentPly).applyTo(state);
            currentPly += 1;
        }
        
        return sb.toString();
    }

    private String resultString() {
        if(gameResult == null){
            return "null";
        }
        
        switch(gameResult){
            case WIN_WHITE:
            case ILLEGAL_MOVE_BY_BLACK:
            case RESIGNATION_BY_BLACK:
                return "1-0";
                
            case WIN_BLACK:
            case ILLEGAL_MOVE_BY_WHITE:
            case RESIGNATION_BY_WHITE:
                return "0-1";
                
            case DRAW :
            case DRAW_AGREED:
            case DRAW_STALEMATE:
            case DRAW_BY_THREEFOLD_REPETITION:
            case DRAW_BY_50_MOVE_RULE:
            case DRAW_INSUFFICIENT_MATERIAL:
                return "1/2-1/2";
                
            case UNDECIDED:
                return "*";
        }
        throw new IllegalStateException("switch/ case statement is missing cases");
    }

    /**
     * 
     * @param s a string
     * @return the string with double quotes and backslashes escaped
     * with a backslash
     */
    private String escape(String s) {
        if(s == null){
            return "null";
        }
        String result = s.replace("\\", "\\\\");
        result = result.replace("\"", "\\\"");
        return result;
    }

    /**
     * 
     * @param firstPlayer
     * @param secondPlayer 
     * 
     * Adds tags White and Black for the two players
     */
    public void setPlayers(Player firstPlayer, Player secondPlayer) {
        this.tagValuePairs.put("White", firstPlayer.toString());
        this.tagValuePairs.put("Black", secondPlayer.toString());
    }
}

