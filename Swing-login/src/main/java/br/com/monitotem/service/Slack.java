package br.com.monitotem.service;

import br.com.monitotem.entities.Usuario;
import java.time.LocalDateTime;
import org.silentsoft.slack.api.SlackAPI;
/**
 *
 * @author matsu69
 */
public class Slack {

    public void sendNewUser(Usuario usuario) {
        try {
            SlackAPI.postMessage("xoxb-3431609768566-3438312290354-jrJx31OSDXFCxKhVmcUx1NY7",
                    "usuarios", "O usuário de email " + usuario.getEmailUsuario()
                    + " e começou sua atividade as "
                    + LocalDateTime.now());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void sendAlert(String msg) {
        try {
            SlackAPI.postMessage("xoxb-3431609768566-3438312290354-jrJx31OSDXFCxKhVmcUx1NY7",
                    "alertas", msg);
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
