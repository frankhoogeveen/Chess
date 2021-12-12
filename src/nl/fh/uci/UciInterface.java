/*
 * License: GPL v3
 * 
 */

package nl.fh.uci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fh.player.Player;
import nl.fh.player.random.RandomPlayer;

/**
 * Wraps around a Player and communicates using the uci protocol
 * 
 */
public class UciInterface implements Runnable {
   
    private final Player player;
    private BufferedReader reader;

    private UciInterface(Player player) {
        this.player = player;
        reader = new BufferedReader(new InputStreamReader(System.in));          
    }

    @Override
    public void run() {
        boolean done = false;
        while(!done){
            String input = getFromServer();
            String output = process(input);
            putToServer(output);
        }
    }

    private String getFromServer() {
        
        // Reading data using readLine
        String result = null;
        try {
            result = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(UciInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return result;
    }
    
    private void putToServer(String output) {
       System.out.println(output);
    }    

    private String process(String input) {
        String[] split = input.split(" ");
        if(split.length == 0){
            System.exit(-1);
        }
        
        if(split[0].equals("uci")){
            return "id system xxxx \nid author fh\nreadyok";
        }
        
        if(split[0].equals("isready")){
            System.exit(2);
        }
        System.exit(-1);
        return null;
    }

    public static void main(String[] args){
        Player player = new RandomPlayer();
        UciInterface uci = new UciInterface(player);
        uci.run();
    }
}