/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import APIs.ChatListener;
import APIs.MatchingListener;
import Controller.FriendListController;
import Controller.NotificationController;
import Entity.User;
import Service.LoginService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import static javafx.scene.media.MediaPlayer.Status.PLAYING;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mindrot.jbcrypt.BCrypt;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class LoginController extends Controller implements Initializable {
    @FXML
    private MediaView mv;
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private Button button;
    @FXML
    private Label msg;
    
    private DataSource cnx;
    Stage stage= new Stage();
    Scene scene;
    MediaPlayer mediaplayer;
    @FXML
    private Button signup;
    @FXML
    private Hyperlink mail;
    int x;
    @FXML
    private JFXRadioButton mute;
    @FXML
    private JFXCheckBox remember;
    private final String path="src\\LoginData.ini";
    LoginService loginService = new LoginService();
    @FXML
    private JFXButton facebook;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String VUrl =getClass().getResource("../Images/").toExternalForm()+"pp.mp4";
        Media media = new Media(VUrl);
        mediaplayer = new MediaPlayer(media);
        mv.setMediaPlayer(mediaplayer);
        mediaplayer.play();
        loginService.readinifile(path,username,password,remember);
    }
    
    private void init()
    {
        ChatListener chatListener = new ChatListener("127.0.0.1", 9001,getUser());
        Thread chatThread = new Thread(chatListener);
        chatThread.start();
        
        MatchingListener matchingListener = new MatchingListener("127.0.0.1", 9002, getUser());
        Thread matchingThread = new Thread(matchingListener);
        matchingThread.start();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/NotificationWindow.fxml"));
            Parent root = loader.load();
            NotificationController notificationController = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            Controller.setNotificationController(notificationController);
            notificationController.setStage(stage);
        } catch (IOException ex) {
            Logger.getLogger(FriendListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void LoginAction(ActionEvent event) throws SQLException, IOException {
        if("admin".equals(username.getText()) && "admin".equals(password.getText()))
        {
            Parent root = FXMLLoader.load(getClass().getResource("../View_Admin/UI.fxml"));   
            Scene scene = new Scene(root);
             Node node =(Node)event.getSource();
                stage = (Stage)node.getScene().getWindow();
                stage.close();
                mediaplayer.stop();
     
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
            return;
        }
        Connection conn = DataSource.getInstance().getConnection();
        String req= "Select * from user where (username=? or email=?)";
        PreparedStatement prs= conn.prepareStatement(req);
        prs.setString(1, username.getText());
        prs.setString(2, username.getText());
        ResultSet rs = prs.executeQuery();
        if (!rs.next()){
            msg.setText("Username incorrect");
        } 
        else {
            if(BCrypt.checkpw(password.getText(),rs.getString("password").substring(0,2)+"a"+rs.getString("password").substring(3)))
            {
            if (!remember.isSelected()){
                String req1= "Select id from user where username=? ";
                PreparedStatement prs1= conn.prepareStatement(req1);
                prs.setString(1, username.getText());
                ResultSet res= prs.executeQuery();
                while (res.next()){
                 x= res.getInt("id");       
                }
                User user= new User();
                user.setId(x);
                Controller.setUserId(x);
                try {
                    getLocationByIp(x);
                } catch (GeoIp2Exception ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                init();
                Node node =(Node)event.getSource();
                stage = (Stage)node.getScene().getWindow();
                stage.close();
                mediaplayer.stop();
                Parent root = FXMLLoader.load(getClass().getResource("../View/LayoutFront.fxml"));   
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);          
                
            }
            else {
                loginService.createiniFile(path,username.getText(),password.getText());
                System.out.println("success");           
                String req1= "Select id from user where username=? ";
                PreparedStatement prs1= conn.prepareStatement(req1);
                prs.setString(1, username.getText());
                ResultSet res= prs.executeQuery();
                while (res.next()){
                    x= res.getInt("id");
                } 
                User user= new User();
                Controller.setUserId(x);
                try {
                    getLocationByIp(x);
                } catch (GeoIp2Exception ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                init();
                Node node =(Node)event.getSource();
                stage = (Stage)node.getScene().getWindow();
                stage.close();
                mediaplayer.stop();
                Parent root = FXMLLoader.load(getClass().getResource("../View/LayoutFront.fxml"));  
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);
                
            }
            }
            else
            {
                msg.setText("Mot de passe incorrecte!");
            }
        }     
    }
    
    @FXML
    public void SignUp(ActionEvent event) throws IOException{
        mediaplayer.stop();
        Node node =(Node)event.getSource();
        stage = (Stage)node.getScene().getWindow();
        stage.close();
        scene = new Scene(FXMLLoader.load(getClass().getResource("../View/SingUp.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    


    private void exit(MouseEvent event) {
        Node node =(Node)event.getSource();
        stage = (Stage)node.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void sendmail(ActionEvent event) throws IOException {
        mediaplayer.stop();
        Node node =(Node)event.getSource();
        stage = (Stage)node.getScene().getWindow();
        stage.close();
        scene = new Scene(FXMLLoader.load(getClass().getResource("../View/ForgotPass.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void OnClick_btn_play(){
        if(mediaplayer.getStatus()==PLAYING){
        mediaplayer.pause();
        }else {
        mediaplayer.play();
        }
    
    }

    private void OnClick_btn_stop(){
        mediaplayer.stop();
    }

    @FXML
    private void OnClick_btn_mute() {
        if(mediaplayer.getStatus()==PLAYING ){
        mediaplayer.setMute(true);
        }else {
        mediaplayer.setMute(false);
        }  
    }

    @FXML
    private void Login_avec_facebook(ActionEvent event) throws SQLException, IOException {
        
                String domain = "https://google.fr/";
        String appId = "132361634176800";
       
        String authUrl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id="+appId+"&redirect_uri="+domain+"&scope=email"
                ;
       
        System.setProperty("webdirver.chrome.driver", "chromedriver.exe");
       
        WebDriver driver = new ChromeDriver();
        driver.get(authUrl);
        String accessToken;
        while(true){
                Connection conn = DataSource.getInstance().getConnection(); 
            if(!driver.getCurrentUrl().contains("facebook.com")){
            String url = driver.getCurrentUrl();
            accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");
           
            driver.close();
           
                FacebookClient fbClient = new DefaultFacebookClient(accessToken);
                com.restfb.types.User user = fbClient.fetchObject("me",com.restfb.types.User.class);
            
                System.out.println(user.getName());
                System.out.println(user.getFirstName());
                System.out.println(user.getLastName());
                System.out.println(user.getBirthday());
                System.out.println(user.getBirthdayAsDate());
                System.out.println(user.getShortName());
                        System.out.println(user.getTimezone());
                        System.out.println(user.getEmail());
                        
                  String req2= "SELECT * FROM user WHERE email=?";   
                  PreparedStatement prs2= conn.prepareStatement(req2);
                  prs2.setString(1, user.getName());
                 ResultSet rs = prs2.executeQuery();
                 if(!rs.next()){
                     
                     String req= "INSERT INTO user  (username,prenom,nom,email,password,date_naissance) VALUES (?,?,?,?,?,?)";
       
        PreparedStatement prs= conn.prepareStatement(req);

             
            prs.setString(1, user.getName());
        prs.setString(2, user.getName());
        prs.setString(3, user.getName());
        prs.setString(4, user.getName());
        String pwd = BCrypt.hashpw(user.getName(),BCrypt.gensalt(13));
        prs.setString(5, pwd.substring(0,2)+"y"+pwd.substring(3));
        prs.setDate(6, new java.sql.Date(new Date().getTime()));
            prs.executeUpdate();
     String req1= "Select id from user where username=? ";
                PreparedStatement prs1= conn.prepareStatement(req1);
                prs1.setString(1, user.getName());
                ResultSet res= prs1.executeQuery();
                while (res.next()){
                 x= res.getInt("id");       
                }
                User user1= new User();
                user1.setId(x);
                Controller.setUserId(x);
                init();
                Node node =(Node)event.getSource();
                stage = (Stage)node.getScene().getWindow();
                stage.close();
                mediaplayer.stop();
                Parent root = FXMLLoader.load(getClass().getResource("../View/LayoutFront.fxml"));   
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);  
                 }
                 else 
                 
               
                 {

     String req1= "Select id from user where username=? ";
                PreparedStatement prs1= conn.prepareStatement(req1);
                prs1.setString(1, user.getName());
                ResultSet res= prs1.executeQuery();
                while (res.next()){
                 x= res.getInt("id");       
                }
                User user1= new User();
                user1.setId(x);
                Controller.setUserId(x);
                init();
                Node node =(Node)event.getSource();
                stage = (Stage)node.getScene().getWindow();
                stage.close();
                mediaplayer.stop();
                Parent root = FXMLLoader.load(getClass().getResource("../View/LayoutFront.fxml"));   
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);    }
                  
            
            }
      
    }
        
          
    }

}
