/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.Espace;
import Entity.PhotoEspace;
import IService.IEspaceService;
import IService.IPhotoEspaceService;
import com.mysql.jdbc.EscapeTokenizer;
import java.awt.Desktop.Action;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javafx.scene.image.ImageView;

/**
 *
 * @author hero
 */
public class EspaceService implements IEspaceService {

    private final Connection con = DataSource.getInstance().getConnection();

    @Override
    public Espace getEspaceById(int id) {
        try {
            String query = "select * from espace where id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Espace espace;
            while (rs.next()) {
                espace = new Espace(rs.getInt("id"), rs.getString("titre"), rs.getString("description"), rs.getString("email"), rs.getString("adresse"), rs.getString("photo"), rs.getInt("etat"), rs.getInt("idUser"), rs.getDouble("longi"), rs.getDouble("lat"));
                return espace;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EspaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Espace> getEspaceConfirmer() {
        try {
            String query = "select * from espace where etat=1";
            Statement ste = con.createStatement();
            ResultSet rs = ste.executeQuery(query);
            List<Espace> espaces = new ArrayList<>();
            while (rs.next()) {
                espaces.add(new Espace(rs.getInt("id"), rs.getString("titre"), rs.getString("description"), rs.getString("email"), rs.getString("adresse"), rs.getString("photo"), rs.getInt("etat"), rs.getInt("idUser"), rs.getDouble("longi"), rs.getDouble("lat")));

            }
            return espaces;
        } catch (SQLException ex) {
            Logger.getLogger(EspaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Espace> getEspaceNonConfirmer() {
        try {
            String query = "select * from espace where etat=0";
            Statement ste = con.createStatement();
            ResultSet rs = ste.executeQuery(query);

            List<Espace> espaces = new ArrayList<>();
            while (rs.next()) {

                espaces.add(new Espace(rs.getInt("id"), rs.getString("titre"), rs.getString("description"), rs.getString("email"), rs.getString("adresse"), rs.getString("photo"), rs.getInt("etat"), rs.getInt("idUser"), rs.getDouble("longi"), rs.getDouble("lat")));

            }
            return espaces;
        } catch (SQLException ex) {
            Logger.getLogger(EspaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void removeEspace(int id) {
        try {
            String query = "delete from espace where id =?";
            PreparedStatement pstmt = con.prepareStatement(query);

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(EspaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void confirmeEspace(int id) {
        try {
            String query = "Update espace set etat=1 where id =?";
            PreparedStatement pstmt = con.prepareStatement(query);

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(EspaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void send_mail(Espace espace) {
        final String username = "thepentagon4@gmail.com"; // enter your mail id
        final String password = "Azerty1234567";// enter ur password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("thepentagon4@gmail.com")); // same email id
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(espace.getEmail()));// whome u have to send mails that person id
            message.setSubject("Confirmation d'Espace" + espace.getTitre());
            message.setText("Votre Espace a été confirmé ! ");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void send_mailconf(Espace espace) {
        final String username = "thepentagon4@gmail.com"; // enter your mail id
        final String password = "Azerty1234567";// enter ur password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("thepentagon4@gmail.com")); // same email id
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(espace.getEmail()));// whome u have to send mails that person id
            message.setSubject("Confirmation d'Espace" + espace.getTitre());
            message.setText("Votre modification a été accepter ! ");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public void send_maildel(Espace espace) {
        final String username = "thepentagon4@gmail.com"; // enter your mail id
        final String password = "Azerty1234567";// enter ur password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("thepentagon4@gmail.com")); // same email id
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(espace.getEmail()));// whome u have to send mails that person id
            message.setSubject("Confirmation d'Espace" + espace.getTitre());
            message.setText("Votre Espace a été annuler ! ");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Espace ajoutEspace(Espace e) {
        try {

            String query = "INSERT INTO `espace`(`titre`, `description`, `adresse`, `photo`, `etat`, `idUser`,`longi`,`lat`,`email`) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement prs = con.prepareStatement(query);

            prs.setString(1, e.getTitre());

            prs.setString(2, e.getDescription());
            prs.setString(3, e.getAdresse());
            prs.setString(4, e.getPhoto());
            prs.setInt(5, e.getEtat());
            prs.setInt(6, e.getIdUser());
            prs.setDouble(7, e.getLongi());
            prs.setDouble(8, e.getLat());
            prs.setString(9, e.getEmail());

            int id = prs.executeUpdate();
            e.setId(id);

            return e;

        } catch (SQLException ex) {
            Logger.getLogger(EspaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Espace lastEspaces() {
        try {
            Espace c = new Espace();
            Statement myStmt = con.createStatement();
            ResultSet myRes = myStmt.executeQuery("SELECT * from Espace order by id DESC");
            while (myRes.next()) {
                c.setId(myRes.getInt("id"));
                break;
            }
            return c;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Espace> findByDistance(double latitude, double longitude, double distance) {
        try {
            String req = "select * from espace where etat=1 AND (((acos(sin((?*pi()/180)) * sin((`longi`*pi()/180))+cos((?*pi()/180)) * cos((`longi`*pi()/180)) * cos(((? - `lat`)*pi()/180))))*180/pi())*60*1.1515)  < ?";
            List<Espace> espaces = new ArrayList<>();
            PreparedStatement prs = con.prepareStatement(req);

            prs.setDouble(1, longitude);
            prs.setDouble(2, longitude);

            prs.setDouble(3, latitude);
            prs.setDouble(4, distance);
            ResultSet resultSet = prs.executeQuery();
            try {

                while (resultSet.next()) {
                    Espace esp = new Espace();
                    esp.setId(resultSet.getInt(1));

                    espaces.add(esp);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return espaces;

        } catch (SQLException ex) {
            Logger.getLogger(EspaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
