/*
 * License: GPL v3
 * 
 */

package nl.fh.output_file;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to write a string to a given file
 * 
 */
public class OutputToFile {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("_yyyy_MM_dd_HH_mm_ss");    
    
    public static void write(String head, String content){

        LocalDateTime date = LocalDateTime.now();
        String dateString = date.format(formatter);
        String filePath = "../out/" + head + dateString + ".csv";
        
        try {
            try (FileWriter file = new FileWriter(filePath)) {
                file.write(content);
                System.out.println(content.length() + " characters written to " + filePath);
            }
        } catch (IOException ex) {
            Logger.getLogger(OutputToFile.class.getName()).log(Level.SEVERE, null, ex);
        }          
    }
    
    
    private OutputToFile(){
        // no instantiation
    }

}