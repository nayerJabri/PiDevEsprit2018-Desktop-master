
package Core;

import com.jfoenix.controls.JFXRadioButton;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import static javafx.scene.media.MediaPlayer.Status.PLAYING;
import javafx.scene.media.MediaView;
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
public class ForgotPassController implements Initializable {

    @FXML
    private TextField mailid;
    @FXML
    private Button btnid;

    private DataSource cnx;
    public ResultSet rs;
    public int x;
    public String y,z;
    public String username,pass,mesg;

    Stage stage= new Stage();
    @FXML
    private Label label;
    @FXML
    private Label exit;
    MediaPlayer mediaplayer;
    @FXML
    private MediaView mv;
    @FXML
    private JFXRadioButton btn;
    
        public static final String ACCOUNT_SID = "AC2d16da0dbba767423fed1f3ce0b9e5ab";
    public static final String AUTH_TOKEN = "c08638c47a87bc59de353a37cc78b8b7";
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String VUrl = getClass().getResource("../Images/gif.mp4").toExternalForm();
        Media media = new Media(VUrl);
        mediaplayer = new MediaPlayer(media);
        mv.setMediaPlayer(mediaplayer);
        mediaplayer.play();
    }    

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    
    @FXML
    private void SendMail(ActionEvent event) throws AddressException, MessagingException, SQLException, IOException {
        if (mailid.getText().isEmpty()){ label.setText("remarque : email vide");  }
        else if (!mailid.getText().matches("[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]{3}") ){ label.setText("remarque : email non valide");  }
        else {
          Connection conn = DataSource.getInstance().getConnection();
          String req= "Select username,password from user where email=? ";
          PreparedStatement prs= conn.prepareStatement(req);
          prs.setString(1, mailid.getText());
          rs= prs.executeQuery();
          while (rs.next()){
                 username= rs.getString("username");
                 pass=rs.getString("password");
          }
          y = getSaltString();
          z = mailid.getText();
          mesg="votre code est : " + y;
          
          
              Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        com.twilio.rest.api.v2010.account.Message messages = com.twilio.rest.api.v2010.account.Message.creator(new PhoneNumber("+21694053114"),
        new PhoneNumber("+18315349834"), y).create();
          
       String from ="medaymen.saadani@esprit.tn";
       String pass="Aymen006";
       String [] to={mailid.getText()};
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
           message.addRecipient(Message.RecipientType.TO,new InternetAddress(mailid.getText()));    
           message.setSubject(sub);    
           message.setText(mesg);    
           //send message  
           Transport.send(message);    
           System.out.println("message sent successfully");  
           mediaplayer.stop();
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Code.fxml"));
            Parent root= loader.load();
            CodeController ccc = loader.getController();
            ccc.setEmail(z);
            ccc.setCode(y);
            mailid.getScene().setRoot(root);
           
           
    }
        }

    @FXML
    private void exit(MouseEvent event) {
                    Node node =(Node)event.getSource();
            stage = (Stage)node.getScene().getWindow();
            stage.close();
        
    }
    
    @FXML
    private void OnClick_btn_play(){
        if(mediaplayer.getStatus()==PLAYING){
        mediaplayer.pause();
        }else {
        mediaplayer.play();
        
        }
    
    }
    
}
