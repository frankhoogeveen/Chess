/*
 * License: GPL v3
 * 
 */

package jobs;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
public class job_006a_test_filewriter {
    
        
    
    public static void main(String[] args){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime date = LocalDateTime.now();
        String dateString = date.format(formatter);
        
        String filePath = "../out/job_006a_"+ dateString + ".txt";
        
        try {
            try (FileWriter file = new FileWriter(filePath)) {
                file.write("=====================");
            }
        } catch (IOException ex) {
            Logger.getLogger(job_006_reproduce_and_analyze_intermittent_bug.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}