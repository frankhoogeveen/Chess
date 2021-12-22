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
import nl.fh.gamereport.GameReport;
import nl.fh.gamestate.GameState;
import nl.fh.move.Move;
import nl.fh.player.Player;

/**
 * A minimalistic ascii interface
 * 
 */
public class TerminalPlayer implements Player {
    private static final String CURSOR = ">";
    
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    @Override
    public Move getMove(GameState currentState) {
        
        System.out.println();
        System.out.println("----------------------------");

        
        GameState previous = currentState.getParent();
        if(previous != null){
            Move previousMove = null;
            for(Move m : previous.getLegalMoves()){
                if(m.applyTo(previous).equals(currentState)){
                    previousMove = m;
                }
            }

            System.out.print("Opponent played: ");
            System.out.println(previousMove.moveString(previous));
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
        
        // get the legal moves from here
        Set<Move> legalMoves = currentState.getLegalMoves();
        StringBuilder sb = new StringBuilder();
        for(Move m : legalMoves){
            sb.append(m.moveString(currentState));
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
                            if(clean(code).equals(clean(m.moveString(currentState)))){
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