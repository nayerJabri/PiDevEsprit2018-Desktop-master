/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.DataSource;
import Entity.sujet;
import Service.serviceSujet;
import java.io.File;
import java.io.FileInputStream;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static org.testng.internal.Utils.copyFile;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class Modifier_sujetController extends beblio implements Initializable {

    @FXML
    private TextArea contenu;
    @FXML
    private TextField libelle;
    @FXML
    private Button image;
    @FXML
    private Button modifier;
         PreparedStatement pst;
    ResultSet rs;
    Connection cnx= DataSource.getInstance().getConnection();
    public Statement ste;
    @FXML
    private Label label;
      FileChooser saveFileChooser = new FileChooser();    
    File saveFile;
    File srcFile, destFile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         String req= "Select * from post where id=?";
          PreparedStatement prs = null;
        try {
            prs = cnx.prepareStatement(req);
        } catch (SQLException ex) {
            Logger.getLogger(Modifier_sujetController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            prs.setInt(1, getIds());
        } catch (SQLException ex) {
            Logger.getLogger(Modifier_sujetController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rs= prs.executeQuery();
            
            
            
            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(Modifier_sujetController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            rs.first();
            libelle.setText(rs.getString("titre"));
            contenu.setText(rs.getString("contenu"));
            
        } catch (SQLException ex) {
            Logger.getLogger(Modifier_sujetController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void Modifier(ActionEvent event) throws SQLException, IOException {
     
         serviceSujet SV = new serviceSujet();
         
        if(libelle.getText().isEmpty()){
             label.setText("*veuillez insérer un titre");
         }
         
         else if(image.getText().isEmpty()){
             label.setText("*veuillez insérer une image");
         }
         else if(contenu.getText().isEmpty()){
             label.setText("*contenu est vide !");
         }
         else{
             sujet V = new sujet(libelle.getText(),contenu.getText(),srcFile.getName());
            SV.ModifierSujet(V,beblio.getIdc());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Sujet modifier avec succés!");
        alert.show();    
      
            Parent root = FXMLLoader.load(getClass().getResource("../View/LayoutFront_1.fxml"));
        
        Node node =(Node)event.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
                    
        
        }
    
    }

    @FXML
    private void insertion(ActionEvent event) throws IOException {
        
           File file = saveFileChooser.showOpenDialog(null);  
        if (file != null) {                
            srcFile = file;
            if (srcFile != null) {         
                    String p = System.getProperty("user.dir")+"/src/images/"+srcFile.getName();
                    copyFile(srcFile, new File(p));

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
