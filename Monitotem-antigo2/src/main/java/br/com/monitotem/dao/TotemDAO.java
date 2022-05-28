package br.com.monitotem.dao;

import br.com.monitotem.entities.Totem;
import br.com.monitotem.entities.Usuario;
import br.com.monitotem.service.ConnectionFactorySQL;
import br.com.monitotem.view.ThreadHardware;
import com.github.britooo.looca.api.core.Looca;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.silentsoft.slack.api.SlackAPI;

/**
 *
 * @author enzo.f.silvestre
 */
public class TotemDAO {

    private Connection con;

    public TotemDAO(Connection cnn) {
        this.con = cnn;
    }

    public void salvar(Totem totem, Integer idEmpresa) throws SQLException, UnknownHostException, ClassNotFoundException {

        ConnectionFactorySQL connectionFactory = new ConnectionFactorySQL();
        Connection con = connectionFactory.recuperarConexao();

        InetAddress infoMaquina = InetAddress.getLocalHost();

        Looca looca = new Looca();

        String sql = "INSERT INTO totem(hostname,sistema,frequenciaCpu,memoria,fabricante,modeloCpu,"
                + "ipTotem,dataRegistro,fk_empresa) VALUES(?,?,?,?,?,?,?,?,?)";

        String verify = "SELECT hostname from TOTEM WHERE hostname = ?";

        String hostName = "";

        try ( PreparedStatement pstm = con.prepareStatement(verify)) {
            pstm.setString(1, infoMaquina.getHostName());
            pstm.execute();
            try ( ResultSet rs = pstm.getResultSet()) {
                while (rs.next()) {
                    hostName = rs.getString(1);
                }
                System.out.println("HOSTNAME É: " + hostName);
            }

            if (!hostName.equals(infoMaquina.getHostName())) {
                try ( PreparedStatement pstm2 = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    pstm2.setString(1, infoMaquina.getHostName());
                    pstm2.setString(2, looca.getSistema().getSistemaOperacional().toString());
                    pstm2.setString(3, looca.getProcessador().getFrequencia().toString());
                    pstm2.setString(4, looca.getMemoria().getTotal().toString());
                    pstm2.setString(5, looca.getProcessador().getFabricante().toString());
                    pstm2.setString(6, looca.getProcessador().getMicroarquitetura().toString());
                    pstm2.setString(7, infoMaquina.getHostAddress());
                    pstm2.setString(8, LocalDateTime.now().toString());
                    pstm2.setInt(9, idEmpresa);

                    pstm2.execute();
                    System.out.println("adicionando maquina");
                    try ( ResultSet rs2 = pstm2.getGeneratedKeys()) {
                        while (rs2.next()) {
                            totem.setIdTotem(rs2.getInt(1));
                        }
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            } else {
                System.out.println("Máquina já está registrada ! ");
            }
        }
    }

    public void reiniciaTotem() throws SQLException {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Integer NumberReboot = 0;

                String sql = "select reiniciarTotem from totem where idTotem = ?";

                try ( PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    pstm.setInt(1, getIdTotem());
                    pstm.execute();

                    try ( ResultSet rs = pstm.getResultSet()) {
                        while (rs.next()) {
                            NumberReboot = rs.getInt(1);
                            System.out.println(NumberReboot);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(TotemDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (NumberReboot == 1) {
                    String sql2 = "UPDATE totem SET reiniciarTotem = 0 WHERE idTotem = ?";
                    try ( PreparedStatement pstm = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS)) {
                        pstm.setInt(1, getIdTotem());
                        pstm.execute();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (UnknownHostException ex) {
                        ex.printStackTrace();
                    }

                    try {
                        Runtime.getRuntime().exec("reboot");
                    } catch (IOException ex) {
                        ex.getMessage();
                    }
                    System.out.println("Deu certo carambaaa");
                }
            }
        }, 2, 5000);

    }

    public Integer getIdTotem() throws UnknownHostException, SQLException {

        InetAddress infoMaquina = InetAddress.getLocalHost();
        Integer id = null;
        String verify = "SELECT idTotem from TOTEM WHERE hostname = ?";
        try ( PreparedStatement pstm = con.prepareStatement(verify)) {
            pstm.setString(1, infoMaquina.getHostName());

            pstm.execute();

            try ( ResultSet rs = pstm.getResultSet()) {
                while (rs.next()) {
                    id = rs.getInt(1);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public void sendInformation(Connection cnn) throws SQLException, PropertyVetoException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Timer timer = new Timer();

        String sql = "INSERT INTO registro(usoMemoria,usoCpu,tempoAtividade,dataRegistro,statusRegistro, fk_totem,memoriaTotal) VALUES(?,?,?,?,?,?,?)";
        String MySql = "INSERT INTO registro(usoMemoria,usoCpu,tempoAtividade,dataRegistro) VALUES(?,?,?,?)";

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                Looca pc = new Looca();

                try ( PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    System.out.println("enviando");

                    pstm.setInt(1, (int) ((pc.getMemoria().getEmUso() * 100) / pc.getMemoria().getTotal()) + 1);
                    pstm.setInt(2, pc.getProcessador().getUso().intValue());
                    pstm.setString(3, pc.getSistema().getTempoDeAtividade().toString());
                    pstm.setString(4, formatter.format(LocalDateTime.now()));
                    pstm.setString(5, "FUNCIONANDO");
                    pstm.setInt(6, getIdTotem());
                    pstm.setInt(7, (int) ((pc.getMemoria().getDisponivel() * 100) / pc.getMemoria().getTotal()));
                    
                    System.out.println(((pc.getMemoria().getEmUso() * 100) / pc.getMemoria().getTotal()) + 1);
                    System.out.println(pc.getProcessador().getUso().intValue());
                    System.out.println(pc.getSistema().getTempoDeAtividade().toString());
                    System.out.println(formatter.format(LocalDateTime.now()));
                    System.out.println(getIdTotem());
                    System.out.println(((pc.getMemoria().getDisponivel() * 100) / pc.getMemoria().getTotal()));
                    
                    if (((pc.getMemoria().getEmUso() * 100) / pc.getMemoria().getTotal()) + 1 > 70) {
                        SlackAPI.postMessage("xoxb-3431609768566-3438312290354-XJY3Bz1jDMI5IH6YUZm7g2dp", "alertas", "Cuidado sua memoria esta em nivel emergencial");
                    }

                    if (pc.getProcessador().getUso().intValue() > 60) {
                        SlackAPI.postMessage("xoxb-3431609768566-3438312290354-XJY3Bz1jDMI5IH6YUZm7g2dp", "alertas", "Cuidado seu processador esta em nivel emergencial");
                    }

                    pstm.execute();

                    try ( ResultSet rs = pstm.getGeneratedKeys()) {
                        while (rs.next()) {
                            new Totem().setIdTotem(rs.getInt(1));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Nao envia" + e.getMessage());
                    System.out.println(e.getMessage());
                    
                         
                    System.out.println(((pc.getMemoria().getEmUso() * 100) / pc.getMemoria().getTotal()) + 1);
                    System.out.println(pc.getProcessador().getUso().intValue());
                    System.out.println(pc.getSistema().getTempoDeAtividade().toString());
                    System.out.println(formatter.format(LocalDateTime.now()));
                    try {
                        System.out.println(getIdTotem());
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(TotemDAO.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(TotemDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(((pc.getMemoria().getDisponivel() * 100) / pc.getMemoria().getTotal()));
                }

                try ( PreparedStatement pstm = cnn.prepareStatement(MySql, Statement.RETURN_GENERATED_KEYS)) {
                    System.out.println("enviando para o MYSQL");

                    pstm.setInt(1, (int) ((pc.getMemoria().getEmUso() * 100) / pc.getMemoria().getTotal()) + 1);
                    pstm.setInt(2, pc.getProcessador().getUso().intValue());
                    pstm.setString(3, pc.getSistema().getTempoDeAtividade().toString());
                    pstm.setString(4, formatter.format(LocalDateTime.now()));

                    pstm.execute();

                    try ( ResultSet rs = pstm.getGeneratedKeys()) {
                        while (rs.next()) {
                            new Totem().setIdTotem(rs.getInt(1));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Nao envia");
                    System.out.println(e.getMessage());
                }
            }

        }, 2, 3000);
    }

}
