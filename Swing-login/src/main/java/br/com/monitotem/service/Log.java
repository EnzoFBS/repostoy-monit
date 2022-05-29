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
    
    public void emergencia(String texto){
            logar( " ERRO ", texto);
    }
    
    public void normalizado(String texto){
            logar(" OK ", texto);
    }
    
  
  
    public void logar(String tipo,String texto) {
//        File log = new File("Log.txt");

        File log = new File("Log-Monitotem.txt");
        try {
            if (!log.exists()) {
                System.out.println("Criei um novo arquivo");
                log.createNewFile();
            }
            
            
            FileWriter fileWriter = new FileWriter(log, true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            if (log.length() == 0) {
                bufferedWriter.write("-----------Monitotem Log ------------");
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            bufferedWriter.write( LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")).toString()  + tipo + texto + "\n" );
            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        }

    }

    
}

