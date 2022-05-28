package br.com.monitotem.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

    public Log() {
    }

    String strLocalDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).toString();
    public void logar(String texto) {
//        File log = new File("Log.txt");

        File log = new File("Log-Monitotem.txt");
        try {
            if (!log.exists()) {
                System.out.println("Criei um novo arquivo");
                log.createNewFile();
            }
            
            
            FileWriter fileWriter = new FileWriter(log, true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(strLocalDate + texto + "\n" );
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }

    }

    
}

