/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.DataSource;
import Entity.sujet;
import Service.serviceSujet;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class Ajouter_sujetController  implements Initializable {

    @FXML
    private TextField titre;
    @FXML
    private TextArea contenu;
    @FXML
    private Button valider;
     private DataSource conn;
    public Statement ste;
    PreparedStatement pst;
    ResultSet rs;
    @FXML
    private Label label;
    public int idc;
    @FXML
    private MediaView mv;
     MediaPlayer mediaplayer;
     FileChooser saveFileChooser = new FileChooser();    
    File saveFile;
    File srcFile, destFile;
    @FXML
    private ImageView imagex;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       


    }    
    
    public void setidc(int idc) {
        this.idc = idc;
        System.out.println("ici"+idc);
    }

    @FXML
    private void valider(ActionEvent event) throws SQLException, IOException {
        System.out.println("iciiiiiiiiiiiii"+this.idc);
         serviceSujet SV = new serviceSujet();
         
        if(titre.getText().isEmpty()){
             label.setText("veuillez insérer un titre");
         }
         
         else if(srcFile.getName().isEmpty()){
             label.setText("veuillez insérer une image");
         }
         else if(contenu.getText().isEmpty()){
             label.setText("contenu est vide !");
         }
         else{
             sujet V = new sujet(titre.getText(),contenu.getText(),srcFile.getName());
            SV.AjouterSujet(V,beblio.getIdc());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Sujet ajoutée avec succés!");
        alert.show();    
      
            Parent root = FXMLLoader.load(getClass().getResource("../View/LayoutFront_1.fxml"));
        
        Node node =(Node)event.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));

        
         }
        
    }

    @FXML
    private void uu(ActionEvent event) throws IOException {

 
        File file = saveFileChooser.showOpenDialog(null);  
        if (file != null) {                
            srcFile = file;
            if (srcFile != null) {         
                    String p = System.getProperty("user.dir")+"/src/images/"+srcFile.getName();
                    copyFile(srcFile, new File(p));

            }
        }
        
        Image image = new Image(srcFile.toURI().toString());
        imagex.setImage(image);


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
