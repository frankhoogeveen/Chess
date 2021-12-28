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
import nl.fh.gamestate.chess.ChessState;
import nl.fh.gamestate.chess.move.ChessMove;
import nl.fh.gamestate.Move;
import nl.fh.player.Player;
import nl.fh.rule.FIDEchess;
import nl.fh.rule.GameDriver;
import nl.fh.rule.ResultArbiter;

/**
 * A minimalistic ASCII interface
 * 
 */
//TODO make TerminalPlayer generic rather than chess specific
public class TerminalPlayer implements Player<ChessState> {
    private static final String CURSOR = ">";
    private static final GameDriver<ChessState> driver =FIDEchess.getGameDriver();
    private static final ResultArbiter<ChessState> arbiter = driver.getResultArbiter();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private ChessState previous = null;
        
    @Override
    public Move getMove(ChessState currentState, Set<Move<ChessState>> legalMoves) {
        
        System.out.println();
        System.out.println("----------------------------");

        if(previous != null){
            ChessMove previousMove = null;
            for(Move<ChessState> m : driver.getMoveGenerator().calculateAllLegalMoves(previous)){
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
                        for(Move<ChessState> m : legalMoves){
                            if(clean(code).equals(clean(((ChessMove)m).formatPGN(currentState, driver)))){
                                previous = m.applyTo(currentState);
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