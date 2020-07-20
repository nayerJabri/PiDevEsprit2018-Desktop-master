/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class Ajouter_categorieController implements Initializable {

    @FXML
    private TextField libelle;
    @FXML
    private TextField description;
    @FXML
    private DatePicker date_ajout;
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
    Connection cnx= DataSource.getInstance().getConnection();
    java.util.Date date;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
           
        
        
        btn1.setOnAction(e->{        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(null);
        if (file != null) {
        Image images = new Image(file.toURI().toString(),100,100,true,true);
        image.setImage(images);
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Ajouter_categorieController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }});
        
        
        
        
    }    


    @FXML
    private void Button2Action(ActionEvent event) throws SQLException, FileNotFoundException {

        if(libelle.getText().isEmpty()){
             remarque.setText("veuillez insérer un titre");
         }
         
         else if(description.getText().isEmpty()){
             remarque.setText("veuillez insérer une image");
         }
         else{
            String req= "INSERT INTO categorie  (libelle,description,date_ajout,image,nombrePost) VALUES (?,?,?,?,0)";
        PreparedStatement prs= cnx.prepareStatement(req);
        prs.setString(1, libelle.getText());
        prs.setString(2, description.getText());

         java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        prs.setDate(3, sqlDate);
        
        prs.setString(4, image.getClass().getName());

        int executeUpdate = prs.executeUpdate();
       

        System.out.println(" Ajoutée");
        
         }
        
        
    }
    
}
