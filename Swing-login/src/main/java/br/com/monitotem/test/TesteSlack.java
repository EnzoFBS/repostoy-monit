/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.monitotem.test;

import org.silentsoft.slack.api.SlackAPI;

public class TesteSlack {
        
   
    public static void main(String[] args) throws Exception {
        
             String tokenPart1 = "xoxb-";
      String tokenPart2 = "3431609768566-";
      String tokenPart3 = "3438312290354-";
      String tokenPart4 = "gft7jQDEpHkO1OIBQSJYAkM3";
        
            SlackAPI.postMessage(tokenPart1+tokenPart2+tokenPart3+tokenPart4, "usuarios", "Voce logou");
        
        
    }
    
}
