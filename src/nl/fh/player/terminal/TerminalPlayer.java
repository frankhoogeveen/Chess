/*
 * License: GPL v3
 * 
 */

package nl.fh.player.terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fh.gamestate.GameState;
import nl.fh.move.ChessMove;
import nl.fh.move.Move;
import nl.fh.player.Player;
import nl.fh.rules.Chess;
import nl.fh.rules.ChessResultArbiter;
import nl.fh.rules.GameDriver;
import nl.fh.rules.ResultArbiter;

/**
 * A minimalistic ASCII interface
 * 
 */
public class TerminalPlayer implements Player {
    private static final String CURSOR = ">";
    private static final GameDriver driver =Chess.gameDriver;
    private static final ChessResultArbiter arbiter = (ChessResultArbiter) driver.getResultArbiter();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    
    
    @Override
    public Move getMove(GameState currentState, Set<Move> legalMoves) {
        
        System.out.println();
        System.out.println("----------------------------");

        
        GameState previous = currentState.getParent();
        if(previous != null){
            ChessMove previousMove = null;
            for(Move m : driver.getMoveGenerator().calculateAllLegalMoves(previous)){
                if(m.applyTo(previous).equals(currentState)){
                    previousMove = (ChessMove) m;
                }
            }

            System.out.print("Opponent played: ");
            System.out.println(previousMove.formatPGN(previous, driver));
            System.out.println();
        }
        

        // display the current state
        System.out.println();
        System.out.println("#help to get help");
        System.out.println("#quit to quit");
        System.out.println(currentState.toFEN());
        System.out.println();
        System.out.println(currentState.toASCII(currentState.getToMove()));
        System.out.println();
        
        // display the legal moves
        StringBuilder sb = new StringBuilder();
        for(Move m : legalMoves){
            sb.append(((ChessMove)m).formatPGN(currentState, driver));
            sb.append(" ");
        }
        System.out.println(sb.toString());
        
        // receive the move from the terminal
        while(true){
            System.out.println(CURSOR);                 
            String name;
            try {
                String code = reader.readLine();
                if(code.length() > 0){
                    if(code.charAt(0) == '#'){
                        processEscapedCommand(code);
                    } else {
                        for(Move m : legalMoves){
                            if(clean(code).equals(clean(((ChessMove)m).formatPGN(currentState, driver)))){
                                return m;
                            }
                        }
                        System.out.println("Input is not a legal move");
                    }
                }
                
            } catch (IOException ex) {
                Logger.getLogger(TerminalPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * 
     * @param moveCode
     * @return the same string with a number of characters deleted 
     */
    private String clean(String moveCode){
        String clean = moveCode;
        clean = clean.replace("+","");
        clean = clean.replace("#","");
        clean = clean.replace("e.p.", "");
        clean = clean.replace("x","");
        clean = clean.replace("=","");
        return clean;        
    }

    private void processEscapedCommand(String code) {
        switch(code){
            case "#quit":
                System.exit(0);
                break;
            case "#help":
            default:
                displayHelp();
        }
    }

    private void displayHelp() {
        System.out.println("there is no help yet");
    }

    @Override
    public String getDescription() {
        return "Terminal player";
    }

}