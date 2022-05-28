package br.com.monitotem.service;

import br.com.monitotem.entities.Usuario;
import java.time.LocalDateTime;
import org.silentsoft.slack.api.SlackAPI;
/**
 *
 * @author matsu69
 */
public class Slack {
    
             String tokenPart1 = "xoxb-";
      String tokenPart2 = "3431609768566-";
      String tokenPart3 = "3438312290354-";
      String tokenPart4 = "gft7jQDEpHkO1OIBQSJYAkM3";

    public void sendNewUser(Usuario usuario) {
        
  
        try {
            SlackAPI.postMessage(tokenPart1+tokenPart2+tokenPart3+tokenPart4,
                    "usuarios", "O usuário de email " + usuario.getEmailUsuario()
                    + " e começou sua atividade as "
                    + LocalDateTime.now());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void sendAlert(String msg) {
        try {
            SlackAPI.postMessage(tokenPart1+tokenPart2+tokenPart3+tokenPart4,
                    "alertas", msg);
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
