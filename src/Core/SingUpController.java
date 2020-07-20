/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class SingUpController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private TextField prenom;
    @FXML
    private TextField nom;
    @FXML
    private TextField email;
    @FXML
    private PasswordField repeat_password;
    @FXML
    private PasswordField password;
    @FXML
    private DatePicker date_naissance;
    @FXML
    private Button ajouter;
    
    private DataSource cnx;
    Stage stage= new Stage();
    Scene scene;
    @FXML
    private Label exit;
    @FXML
    private Button reset;
    @FXML
    private Label info;
    @FXML
    private ImageView back;
    @FXML
    private JFXCheckBox terms;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouter_utilisateur(ActionEvent event) throws SQLException, IOException {
        Connection conn = DataSource.getInstance().getConnection(); 
        String req= "INSERT INTO user  (username,prenom,nom,email,password,date_naissance,roles,username_canonical) VALUES (?,?,?,?,?,?,'a:0:{}',?)";
        String req1= "SELECT * FROM user WHERE email=?";
        PreparedStatement prs= conn.prepareStatement(req);
        PreparedStatement prs1= conn.prepareStatement(req1);
        prs1.setString(1, email.getText());
        ResultSet rs = prs1.executeQuery();
        prs.setString(1, username.getText());
        prs.setString(2, prenom.getText());
        prs.setString(3, nom.getText());
        prs.setString(4, email.getText());
        String pwd = BCrypt.hashpw(password.getText(),BCrypt.gensalt(13));
        prs.setString(5, pwd.substring(0,2)+"y"+pwd.substring(3));
        prs.setDate(6, new java.sql.Date(new Date().getTime()));
        prs.setString(7, username.getText());
        if (username.getText().equals(""))
        {   info.setText("username is empty");  }
        else if (rs.next()){
             info.setText("email deja existant");
         }
         else if (email.getText().isEmpty()){
             info.setText("Please insert your Email");
         }
         else if (!email.getText().matches("[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]{3}")){
             info.setText("Incorrect Email");
         }
         else if (nom.getText().equals(""))
        {   info.setText("name is empty");  }
         
         else if (prenom.getText().equals(""))
        {   info.setText("prenom is empty");  }
         
         else if (date_naissance.toString().equals(""))
        {   info.setText("date de naissance est vide");  }
         
         else if (password.getText().equals(""))
        {   info.setText("mot de passe est vide");  }
         
         else if (repeat_password.getText().equals(""))
        {   info.setText("repeter mot de passe est vide");  }
         
          else if (!repeat_password.getText().equals(password.getText()))
        {   info.setText("mot de passe n'est pas compatible");  }
          
        else if (!terms.isSelected()){
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Term.fxml"));
            Parent root= loader.load();
            Scene sc = new Scene(root);
            Stage stage=  new Stage();
            stage.setScene(sc);
            stage.show();
            terms.setSelected(true);
              
              
              }
         
        else {
            
 
            int executeUpdate = prs.executeUpdate();
        Node node =(Node)event.getSource();
            stage = (Stage)node.getScene().getWindow();
            stage.close();
            scene = new Scene(FXMLLoader.load(getClass().getResource("../View/Login.fxml")));
            stage.setScene(scene);
            stage.show();}

        
    }

    @FXML
    private void exit(MouseEvent event) {
            Node node =(Node)event.getSource();
            stage = (Stage)node.getScene().getWindow();
            stage.close();
    }

    @FXML
    private void ResetAll(ActionEvent event) throws IOException {
            
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/SingUp.fxml"));
        Parent root = loader.load();
        username.getScene().setRoot(root);
        
    }

    @FXML
    private void back(MouseEvent event) throws IOException {
          Node node =(Node)event.getSource();
            stage = (Stage)node.getScene().getWindow();
            stage.close();
            scene = new Scene(FXMLLoader.load(getClass().getResource("../View/Login.fxml")));
            stage.setScene(scene);
            stage.show();
    }
    
}
