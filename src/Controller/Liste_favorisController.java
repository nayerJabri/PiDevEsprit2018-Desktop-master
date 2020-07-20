/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.DataSource;
import Service.serviceSujet;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import Core.Controller;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class Liste_favorisController implements Initializable {
    Connection cnx= DataSource.getInstance().getConnection();
    @FXML
    private AnchorPane anchor;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     try {
         hb2();
         // TODO
     } catch (SQLException ex) {
         Logger.getLogger(Liste_favorisController.class.getName()).log(Level.SEVERE, null, ex);
     } catch (IOException ex) {
         Logger.getLogger(Liste_favorisController.class.getName()).log(Level.SEVERE, null, ex);
     }
    }    
    
    
    
    public void hb2() throws SQLException, FileNotFoundException, IOException 
         
            {
                serviceSujet ss = new serviceSujet();
                
      
        ResultSet rs = ss.Listfavoris(Controller.getUserId()) ;
        rs.last();
int count = rs.getRow();
rs.first();
                VBox vb = new VBox();
                vb.setPrefHeight(502.0);
                vb.setPrefWidth(483.0);
for (int i=0 ; i<count ; i++)
            {
                
               
        ResultSet rs1 = ss.donnes_favoris(Integer.parseInt(rs.getString("IdPost")));
        rs1.next();
            
               HBox hb = new HBox();
               hb.setPrefHeight(66.0);
               hb.setPrefWidth(462.0);
               hb.setStyle("-fx-border-color: black; -fx-background-color: #dae1ea;");
            
                ImageView image = new ImageView();
                image.setFitHeight(71.0);
                 image.setFitWidth(78.0);
                 image.setPickOnBounds(true);
                 image.setPreserveRatio(true);
               
                   File ff = new File(System.getProperty("user.dir")+"/src/Images/" + rs1.getString("image"));
            Image img = new Image(ff.toURI().toString());
                 
                 image.setImage(img);
                 Button val = new Button();
                 val.setText(String.valueOf(rs.getInt("id")));
                 val.setVisible(false);
                 JFXButton btn = new JFXButton();
                 btn.setPrefHeight(71.0);
                 btn.setPrefWidth(797.0);
                 
                 btn.setText("supprimer");
             btn.setOnAction(e->{
             
        
            try {
                ss.Supprimerfavoris(Integer.parseInt(val.getText()));
            } catch (SQLException ex) {
                Logger.getLogger(Liste_favorisController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Liste_favorisController.class.getName()).log(Level.SEVERE, null, ex);
            }
             
              Parent root = null;
                 try {
                     root = FXMLLoader.load(getClass().getResource("../View/Liste_favoris.fxml"));
                 } catch (IOException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }
        
        Node node =(Node)e.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
                      
 
             
             });
           
             
                 VBox vbox1 = new VBox();
                 vbox1.setPrefHeight(66.0);
                 vbox1.setPrefWidth(300.0);
                
                 
                 HBox hb1 = new HBox();
                 hb1.setPrefHeight(16.0);
                 hb1.setPrefWidth(300.0);
                 Label label = new Label();
                 label.setPrefHeight(18.0);
                 label.setPrefWidth(162.0);
                 label.setStyle("-fx-text-fill: #337AB7;");
                 
                  
                 label.setText(rs1.getString("titre") + ":");
                  hb1.setMargin(label, new Insets(0, 0, 0, 10));
                 hb1.getChildren().add(label);
                 
                 
                 HBox hb2 = new HBox();
                 hb2.setPrefHeight(46.0);
                 hb2.setPrefWidth(300.0);
                 Text text = new Text();
                 text.setStrokeType(StrokeType.OUTSIDE);
                 text.setStrokeWidth(0.0);
                 text.setWrappingWidth(300.7366828918457);
                 
                 text.setText(rs1.getString("contenu"));
                 hb2.setMargin(text, new Insets(0, 0, 0, 10));
                 
                 hb2.getChildren().add(text);
                 vbox1.setMargin(hb2, new Insets(0, 0, 0, 10));
                 
                 HBox hb3 = new HBox();
                 hb3.setPrefHeight(10.0);
                 hb3.setPrefWidth(300.0);
                  Rating rating = new Rating();
                  rating.setPrefHeight(10.0);
                  rating.setPrefWidth(207.0);
                  hb3.getChildren().add(rating);
                 
                 
                 
                vbox1.getChildren().addAll(hb1,hb2,hb3);
             hb.getChildren().addAll(image,vbox1,btn);
            vb.getChildren().add(hb);
            rs.next();
            }
    
    anchor.getChildren().add(vb);
    }
    

}
