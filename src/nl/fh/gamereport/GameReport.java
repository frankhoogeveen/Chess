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
import nl.fh.move.Move;
import nl.fh.rules.Rules;

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

    private static final List<String> SevenTagRoster = Arrays.asList(new String[] {
        "Event", 
        "Site",
        "Date",
        "Round",
        "White",
        "Black",
        "Result"
    });
    
    private final ArrayList<GameState> stateList;
    private final ArrayList<Move> moveList;
    private GameResult gameResult;
    private final HashMap<String, String> TagValuePairs;

    public GameReport(){
        stateList = new ArrayList<GameState>();
        moveList = new ArrayList<Move>();
        gameResult = GameResult.UNDECIDED;
        TagValuePairs = new HashMap<String, String>();
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

    public List<Move> getMoveList() {
        return moveList;
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
        return TagValuePairs.keySet();
    }
    
    /**
     * 
     * @param tag
     * @return an empty string if the tag has not been defined, the value
     * corresponding to the tag otherwise. Unlike the get method of a map,
     * this will not return null;
     */
    public String getTag(String tag){
        if(TagValuePairs.containsKey(tag)){
            return TagValuePairs.get(tag);
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
        TagValuePairs.put(key, value);
    }
    
    /**
     * add a move to this record
     * @param move
     */
    public void addMove(Move move){
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
     * set the result of this game. In case a tag "Result" is defined,
     * it is overwritten
     * @param result 
     */
    public void setResult(GameResult result){
       gameResult = result;
       if(this.TagValuePairs.keySet().contains("Result")){
            this.addTag("Result", result.toString());
       }
    }
    
    /** write the report in .pgn format
     * 
     * @return a formatted report of the tags, moves and result; 
     */
    public String toPGN(Rules rules){
        StringBuilder sb = new StringBuilder();
        sb.append(sevenTagRoster());
        sb.append(otherTags());
        sb.append("\n");
        sb.append(movesString(rules));
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
        for(String tag : this.TagValuePairs.keySet()){
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
            sb.append(escape(TagValuePairs.get(tag)));
            sb.append("\"]");
            sb.append("\n");        
        return sb.toString();
    }

    private String movesString(Rules rules) {
        //TODO this deviates from the pgn standard by having two plies per line
        // in stead of lines filled out to 80 char
        StringBuilder sb = new StringBuilder();
        int moveCounter = 0;
        int currentPly = 0;

        GameState state;
        if(this.TagValuePairs.keySet().contains("FEN")){
            state = GameState.fromFEN(TagValuePairs.get("FEN"));
        } else {
            state = rules.getInitialState();
        }
        
        while(currentPly < moveList.size()){
            if(currentPly % 2 == 0){
                // white's moves
                moveCounter += 1;
                sb.append(Integer.toString(moveCounter));
                sb.append(". ");
                sb.append(moveList.get(currentPly).moveString(state, rules));
                sb.append(" ");
            } else {
                // black's moves
                sb.append(moveList.get(currentPly).moveString(state, rules));
                sb.append("\n");
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
}

