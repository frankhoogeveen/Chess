/*
 * License: GPL v3
 * 
 */

package nl.fh.gamereport;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.fh.gamestate.GameState;
import nl.fh.match.MatchReport;
import nl.fh.match.MatchReportFormatter;
import nl.fh.move.ChessMove;
import nl.fh.move.Move;
import nl.fh.player.Player;
import nl.fh.rule.GameDriver;

/**
 * Turns game reports of chess games into PGN strings.
 * 
 */
public class PGNformatter implements GameReportFormatter, MatchReportFormatter {

    private static final List<String> SevenTagRoster = Arrays.asList(new String[] {
        "Event", 
        "Site",
        "Date",
        "Round",
        "White",
        "Black",
        "Result"
    });
    private final GameDriver gameDriver;
    private Map<String, String> tagValuePairs;

    public PGNformatter(GameDriver gameDriver){
        this.gameDriver = gameDriver; 
    }
    
    @Override
    public String formatGame(GameReport report) {
        
        this.tagValuePairs = new HashMap(report.getTagValuePairs());
        if(tagValuePairs.containsKey("Player1") && !tagValuePairs.containsKey("White")){
            tagValuePairs.put("White", tagValuePairs.get("Player1"));
            tagValuePairs.remove("Player1");
        }
        if(tagValuePairs.containsKey("Player2") && !tagValuePairs.containsKey("Black")){
            tagValuePairs.put("Black", tagValuePairs.get("Player2"));
            tagValuePairs.remove("Player2");            
        }        
        
        return formatGamePGN(report.getMoveList(), report.getGameResult());
        
    }
    
    @Override
    /**
     * 
     * @return format the selected games. The overall match data are 
     * written into comment lines that start with a ;.
     */
    public String formatMatch(MatchReport report){
        
        List<GameReport> gameSelection = report.getGameSelection();
        
        StringBuilder sb = new StringBuilder();
        sb.append(matchHeader(report));
        
        for(GameReport gReport : gameSelection){
            sb.append(formatGame(gReport));
            sb.append("\n");
        }
        return sb.toString();
    }  
    
    
    
    /** write the report in .pgn format
     * 
     * @return a formatted report of the tags, moves and result; 
     */
    private String formatGamePGN(List<Move> moveList, GameResult result){

        
        StringBuilder sb = new StringBuilder();
        sb.append(sevenTagRoster());
        sb.append(otherTags());
        sb.append("\n");
        sb.append(movesString(moveList));
        sb.append("\n");
        sb.append(resultString(result));
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

    private String movesString(List<Move> moveList) {
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
                sb.append(((ChessMove)moveList.get(currentPly)).formatPGN(state, this.gameDriver));
                sb.append(" ");
            } else {
                // black's moves
                sb.append(((ChessMove)moveList.get(currentPly)).formatPGN(state, this.gameDriver));
                sb.append(" ");
            }
            state = moveList.get(currentPly).applyTo(state);
            currentPly += 1;
        }
        
        return sb.toString();
    }

    private String resultString(GameResult gameResult) {
        if(gameResult == null){
            return "null";
        }
        
        switch(gameResult){
            case WIN_FIRST_MOVER:
                return "1-0";
                
            case WIN_SECOND_MOVER:
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
        String result = s.replace("\\", "\\\\");
        result = result.replace("\"", "\\\"");
        return result;
    }
    
    private String matchHeader(MatchReport report){
        
        Player player1 = report.getPlayer1();
        Player player2 = report.getPlayer2();
        int score = report.getScore();
        int count = report.getCount();
        Map<GameResult, Integer> player1White = report.getPlayer1WhiteResults();
        Map<GameResult, Integer> player2White = report.getPlayer2WhiteResults();        
        
        StringBuilder sb = new StringBuilder();
        sb.append(";");
        sb.append("=== MatchResult ===\n");
        
        sb.append(";Players\n");
        
        sb.append(";");        
        sb.append(player1.getDescription());
        sb.append("\n");
        
        sb.append(";");          
        sb.append(player2.getDescription());
        sb.append("\n;\n");             
        
        sb.append(";");          
        sb.append(count);
        sb.append(" games played\n");
        
        sb.append(";score differential: ");
        sb.append(score);
        sb.append("\n");
        
        sb.append(";");          
        sb.append(player1.getDescription());
        sb.append(" playing as white\n");
        for(ChessGameResult g : ChessGameResult.values()){
           sb.append(";");              
           sb.append(g.toString());
           sb.append(" : ");
           sb.append(player1White.get(g));
           sb.append("\n");
        }
        sb.append(";\n");
        sb.append(";");         
        sb.append(player2.getDescription());
        sb.append(" playing as white\n");
        for(ChessGameResult g : ChessGameResult.values()){
           sb.append(";");             
           sb.append(g.toString());
           sb.append(" : ");
           sb.append(player2White.get(g));
           sb.append("\n");
        }  
        
        sb.append(";\n");
        
        return sb.toString();
    }    
}