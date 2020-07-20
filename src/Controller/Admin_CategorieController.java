/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.DataSource;
import interfaceadmin1.UIController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static org.testng.internal.Utils.copyFile;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class Admin_CategorieController implements Initializable {

    @FXML
    private TextField libelle;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    
    @FXML
    private ImageView image;
    private DataSource conn;
    public Statement ste;
    PreparedStatement pst;
    ResultSet rs;
    
    private File x;
    private FileInputStream fis;
    @FXML
    private Label remarque;
    @FXML
    private TextArea description;
     FileChooser saveFileChooser = new FileChooser();    
    File saveFile;
    File srcFile, destFile;
    Connection cnx= DataSource.getInstance().getConnection();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File files = new File(System.getProperty("user.dir")+"/src/Images/avatar.jpg");
        Image img = new Image(files.toURI().toString());
        image.setImage(img);
          
        btn1.setOnAction(e->{    
            
              File file = saveFileChooser.showOpenDialog(null);  
        if (file != null) {                
            srcFile = file;
            if (srcFile != null) {         
                    String p = System.getProperty("user.dir")+"/src/images/"+srcFile.getName();
                try {
                    copyFile(srcFile, new File(p));
                } catch (IOException ex) {
                    Logger.getLogger(Admin_CategorieController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            
             Image images = new Image(file.toURI().toString(),100,100,true,true);
        image.setImage(images);
        }
            
        });
        
    }    

    @FXML
    private void Button2Action(ActionEvent event) throws SQLException {
                if(libelle.getText().isEmpty()){
             remarque.setText("veuillez insérer un titre");
         }
         
         else if(description.getText().isEmpty()){
             remarque.setText("veuillez insérer une image");
         }
         else{
            String req= "INSERT INTO categorie  (libelle,description,image,nombrePost) VALUES (?,?,?,?)";
        PreparedStatement prs= cnx.prepareStatement(req);
        prs.setString(1, libelle.getText());
        prs.setString(2, description.getText());

        
        prs.setString(3, srcFile.getName());
        prs.setInt(4, 0);

        prs.executeUpdate();
       
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Categorie ajoutée avec succés!");
        alert.show(); 
        
           Parent root = null;
                 try {
                     root = FXMLLoader.load(getClass().getResource("../View/Liste_categrorie.fxml"));
                     UIController.setNode(root);
                 } catch (IOException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }
 
         }
        
        
    }
    
    
     public void copyFile(File sourceFile, File destFile) throws IOException {
        if ( !destFile.exists() ) { destFile.createNewFile(); }

    try (
        FileChannel in = new FileInputStream( sourceFile ).getChannel();
        FileChannel out = new FileOutputStream( destFile ).getChannel(); ) {

        out.transferFrom( in, 0, in.size() );
    }
    }
    
    
     
     
}
