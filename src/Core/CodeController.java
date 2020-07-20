/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import com.twilio.type.PhoneNumber;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CodeController implements Initializable {

    @FXML
    private TextField mailid;
    @FXML
    private Button btnid;

   private DataSource cnx;
    public ResultSet rs;
    public int x;
    public String y,z;
    public String username,pass,mesg,email,code;

    Stage stage= new Stage();
    @FXML
    private Label remarque;
        public static final String ACCOUNT_SID = "AC2d16da0dbba767423fed1f3ce0b9e5ab";
    public static final String AUTH_TOKEN = "c08638c47a87bc59de353a37cc78b8b7";
 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }  
    
 public void setEmail(String email) {
        this.email = email;
    }
 
  public void setCode(String code) {
        this.code = code;
        System.out.println("====>"+code);
    }

    
    @FXML
    private void SendMail(ActionEvent event) throws AddressException, MessagingException, SQLException, IOException  {
        System.out.println(this.code);

        if( mailid.getText().equals(this.code)    )
        {
            
          Connection conn = DataSource.getInstance().getConnection();
          String req= "Select username,password from user where email=? ";
          PreparedStatement prs= conn.prepareStatement(req);
          prs.setString(1, this.email);
          rs= prs.executeQuery();
          while (rs.next()){
                 username= rs.getString("username");
                 pass=rs.getString("password");
          }

          mesg="votre nom d'utilisateur: "+username+"\n"+"votre mode de pass: "+pass; ;
          
          
       String from ="medaymen.saadani@esprit.tn";
       String pass="Aymen006";
       String [] to={this.email};
       String host="mail.javatpoint.com";
       String sub="Password Recovery";
       
        Properties props = new Properties();    
          props.put("mail.smtp.host", "smtp.gmail.com");    
          props.put("mail.smtp.socketFactory.port", "465");    
          props.put("mail.smtp.socketFactory.class",    
                    "javax.net.ssl.SSLSocketFactory");    
          props.put("mail.smtp.auth", "true");    
          props.put("mail.smtp.port", "465");    
          //get Session   
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(from,pass);
    }
}); 
          //compose message                
           MimeMessage message = new MimeMessage(session);    
           message.addRecipient(Message.RecipientType.TO,new InternetAddress(this.email));    
           message.setSubject(sub);    
           message.setText(mesg);    
           //send message  
           Transport.send(message);    
           System.out.println("message sent successfully");  
            com.twilio.rest.api.v2010.account.Message messages = com.twilio.rest.api.v2010.account.Message.creator(new PhoneNumber("+21694053114"),
        new PhoneNumber("+18315349834"), mesg).create();
            
 
        Node node =(Node)event.getSource();
            stage = (Stage)node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../View/Login.fxml")));
            stage.setScene(scene);
            stage.show();

           
           
    }
        else { remarque.setText("Code Invalide");}
    }


    @FXML
    private void exit(MouseEvent event) {
                            Node node =(Node)event.getSource();
            stage = (Stage)node.getScene().getWindow();
            stage.close();
    }
    
    
}
