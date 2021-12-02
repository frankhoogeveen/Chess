/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fh.gamereport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;

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
     * set the result of this game
     * @param result 
     */
    public void setResult(GameResult result){
       gameResult = result;
    }
    
    /** write the report in .pgn format
     * 
     * @return a formatted report of the tags, moves and result; 
     */
    public String toPGN(){
        StringBuilder sb = new StringBuilder();
        sb.append(sevenTagRoster());
        sb.append(otherTags());
        sb.append("\n");
        sb.append(movesString());
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

    private String movesString() {
        StringBuilder sb = new StringBuilder();
        int moveCounter = 0;
        int currentPly = 0;
        
        while(currentPly < moveList.size()){
            if(currentPly % 2 == 0){
                // white's moves
                moveCounter += 1;
                sb.append(Integer.toString(currentPly));
                sb.append(". ");
                sb.append(moveList.get(currentPly).moveString());
                sb.append(" ");
            } else {
                // black's moves
                sb.append(moveList.get(currentPly).moveString());
                sb.append("\n");
            }
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
                return "1-0";
            case WIN_BLACK:
                return "0-1";
            case DRAW :
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
        String result = s.replaceAll("\\", "\\\\");
        result = result.replaceAll("\"", "\\\"");
        return result;
    }
}

