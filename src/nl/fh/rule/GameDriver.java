/*
 * License: GPL v3
 * 
 */

package nl.fh.rule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import nl.fh.gamereport.GameReport;
import nl.fh.gamereport.GameResult;
import nl.fh.gamestate.GameState;
import nl.fh.gamestate.Move;
import nl.fh.player.Player;

/**
 * License GPL v3
 */
public class GameDriver<S extends GameState> {
    
    private S initialState;
    private MoveGenerator<S> moveGenerator;
    private ResultArbiter<S> resultArbiter;
    
    private GameDriver(){
    }
    /**
     * 
     * @param initialState
     * @param moveGenerator
     * @param resultArbiter
     * @return a game driver 
     * 
     * Is is the concern of the GameDriver to:
     * 1)call the move generator and the result arbiter
     * 2)maintain the report
     */
    public static GameDriver getInstance(GameState initialState, MoveGenerator moveGenerator, ResultArbiter resultArbiter){
        
        GameDriver result = new GameDriver();
        result.initialState = initialState;
        result.moveGenerator = moveGenerator;
        result.resultArbiter = resultArbiter;
        
        return result;
    }
    
    /**
     * 
     * @return a copy of the initial state of the game
     * The initial state does not have to be deterministic. It might be
     * random. Repeated calls to this method may return different values.
     * 
     * What is currently implemented is a fixed initial state, but this may
     * change over time.
     */
    public S getInitialState(){
        return (S) this.initialState.copy();
    }
    
    /**
     * 
     * @return the object that generates the legal moves from a given state. 
     */
    public MoveGenerator<S> getMoveGenerator(){
        return this.moveGenerator;
    }
    
    /**
     * 
     * @return the object that determines when the game is over and who won.
     */
    public ResultArbiter<S> getResultArbiter(){
        return this.resultArbiter;
    }

    /**
     * 
     * @param whitePlayer
     * @param blackPlayer
     * @return the report of a time unlimited game between the two players 
     */
    public GameReport playGame(Player whitePlayer, Player blackPlayer){
        return playGame(whitePlayer, blackPlayer, initialState);
    }
    
    /**
     * 
     * @param firstPlayer       the player making the first move of the game
     * @param secondPlayer
     * @param initialState 
     * @return the report of a time unlimited game between the two players starting
     * from the initial state
     */
    public GameReport<S> playGame(Player firstPlayer, Player secondPlayer, S initialState){
        GameReport report = setUpReport(firstPlayer, secondPlayer);
        
        S currentState = initialState;
        Set<Move<S>> legalMoves = moveGenerator.calculateAllLegalMoves(currentState);      
        report.addGameState(currentState);
        
        Player currentPlayer = firstPlayer;
        GameResult currentStatus = GameResult.UNDECIDED;
        
        while(currentStatus == GameResult.UNDECIDED){        

            Move<S> move = currentPlayer.getMove(currentState, legalMoves);        
            
            report.addMove(move);
            if(moveIsIllegal(legalMoves, move, (currentPlayer==firstPlayer), report)){
                return report;
            }            
            
            currentState = move.applyTo(currentState);
            legalMoves = moveGenerator.calculateAllLegalMoves(currentState);            
            report.addGameState(currentState);
            
            currentStatus = resultArbiter.determineResult(report, legalMoves);          
            
            report.setResult(currentStatus);
            
            currentPlayer = (currentPlayer == firstPlayer) ? secondPlayer : firstPlayer;
        }
        
        return report;
    }

    private boolean moveIsIllegal(Set<Move<S>> legalMoves, Move move, boolean currentPlayerIsWhite, GameReport report) {
        // making an illegal move ends the game on the spot
        if (!legalMoves.contains(move)) {
            if(currentPlayerIsWhite){
                report.setResult(GameResult.WIN_SECOND_MOVER);
            } else {
                report.setResult(GameResult.WIN_FIRST_MOVER);
            }
            return true;
        }
        return false;
    }

    private GameReport setUpReport(Player firstPlayer, Player secondPlayer) {
        GameReport report = new GameReport();

        // add the required tags
        report.addTag("Player1", firstPlayer.getDescription());
        report.addTag("Player2", secondPlayer.getDescription());
        report.addTag("Result", "*");

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        report.addTag("DateTime", formattedDate);

        report.setPlayers(firstPlayer, secondPlayer);
        
        return report;
    }
}
