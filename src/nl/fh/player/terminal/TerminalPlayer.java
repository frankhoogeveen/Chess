/*
 * License: GPL v3
 * 
 */

package nl.fh.player.terminal;

import nl.fh.gamestate.StateFormatter;
import nl.fh.gamestate.MoveFormatter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fh.gamestate.GameState;
import nl.fh.gamestate.Move;
import nl.fh.player.Player;
import nl.fh.rule.GameDriver;

/**
 * A minimalistic ASCII interface for a generic game
 * 
 */
public class TerminalPlayer<S extends GameState> implements Player<S> {
    private static final String CURSOR = ">";
    
    private final GameDriver<S> driver;
    private final MoveFormatter<S> moveFormatter;
    private final StateFormatter<S> stateFormatter;
    private S previousState;
    
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    public TerminalPlayer(GameDriver<S> driver, MoveFormatter<S> mFormatter, StateFormatter<S> sFormatter){
        this.driver = driver;
        this.moveFormatter = mFormatter;
        this.stateFormatter = sFormatter;
        previousState = null;          
    }
    
    @Override
    public Move<S> getMove(S currentState, Set<Move<S>> legalMoves) {
        
        System.out.println();
        System.out.println("----------------------------");

        if(previousState != null){
            Move<S> previousMove = null;
            for(Move<S> m : driver.getMoveGenerator().calculateAllLegalMoves(previousState)){
                if(m.applyTo(previousState).equals(currentState)){
                    previousMove = m;
                }
            }

            System.out.print("Opponent played: ");
            System.out.println(moveFormatter.format(previousMove,previousState, driver));
            System.out.println();
        }
        
        // display the current state
        System.out.println();
        System.out.println("#help to get help");
        System.out.println("#quit to quit");
        System.out.println();
        System.out.println(stateFormatter.format(currentState));
        System.out.println();
        
        // display the legal moves
        StringBuilder sb = new StringBuilder();
        for(Move<S> m : legalMoves){
            sb.append(moveFormatter.format(m, currentState, driver));
            sb.append(" ");
        }
        System.out.println(sb.toString());
        
        // receive the move from the terminal
        while(true){
            System.out.println(CURSOR);                 
            try {
                String code = reader.readLine();
                if(code.length() > 0){
                    if(code.charAt(0) == '#'){
                        processEscapedCommand(code);
                    } else {
                        for(Move<S> m : legalMoves){
                            if(clean(code).equals(clean(moveFormatter.format(m, currentState, driver)))){
                                previousState = m.applyTo(currentState);
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