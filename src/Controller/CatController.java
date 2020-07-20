/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.DataSource;
import Service.serviceCategory;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;



/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CatController implements Initializable {

    
      Connection cnx= DataSource.getInstance().getConnection();
    @FXML
    private AnchorPane id_anchor;
    public Integer idc;
    
    public void setIdc(int idc) throws SQLException, IOException {
        this.idc = idc;
        System.out.println(idc);
        test();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          try {
              cat();
          } catch (SQLException ex) {
              Logger.getLogger(CatController.class.getName()).log(Level.SEVERE, null, ex);
          } catch (IOException ex) {
              Logger.getLogger(CatController.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    
    public void cat() throws SQLException, FileNotFoundException, IOException{
    
      
              AnchorPane anchorpane = new AnchorPane();
              FlowPane f = new FlowPane();
              
              
 

              serviceCategory sv = new serviceCategory();
              
              ResultSet rs = sv.ConsulterTT();
             
                  
              
            
              rs.last();
              int count = rs.getRow();
              rs.first();
              
              for (int i=1 ; i<count+1 ; i++){
                  
                  Button btn = new Button(String.valueOf(rs.getInt("id")));
                 
                  
                  
                  AnchorPane anchorpane1 = new AnchorPane();
                  Separator separtor = new Separator();
                  VBox vbox = new VBox();
                  
                  Label label1 = new Label();
                  Label label2 = new Label();
                  Label label3 = new Label();
                  Label label4 = new Label();
                  Label label5 = new Label();
                  
                  Rating rating = new Rating();
                  rating.setLayoutX(14.0);
                  rating.setLayoutY(9.0);
                  rating.setPrefHeight(20.0);
                  rating.setPrefWidth(207.0);
                  if (rs.getInt("nombrePost")<3)
                  {rating.setRating(1);}
                  else if (rs.getInt("nombrePost")<5 && rs.getInt("nombrePost")>=3)
                  {rating.setRating(2);}
                  else if (rs.getInt("nombrePost")<8 && rs.getInt("nombrePost")>=5)
                  {rating.setRating(3);}
                  else if (rs.getInt("nombrePost")<10 && rs.getInt("nombrePost")>=8)
                  {rating.setRating(4);}
                  else if ( rs.getInt("nombrePost")>=10)
                  {rating.setRating(7);}
                  else {rating.setRating(0);}
                  
                  
                  
                  Pane pane = new Pane();
                  Circle circle = new Circle();
                  ImageView imageview = new ImageView();
                  JFXButton btn1 = new JFXButton();
                  JFXButton btn2 = new JFXButton();
                  
                  btn1.setLayoutX(372.0);
                  btn1.setLayoutY(9.0);
                  btn1.setPrefHeight(32.0);
                  btn1.setPrefWidth(118.0);
                  btn1.setStyle("-fx-background-color: #337AB7; -fx-text-fill: white; -fx-font-size: 14;");
                  btn1.setText("entrer");
                  
                  btn2.setLayoutX(298.0);
                  btn2.setLayoutY(9.0);
                  btn2.setPrefHeight(32.0);
                  btn2.setPrefWidth(53.0);
                  btn2.setStyle("-fx-background-color: #26B99A; -fx-text-fill: white; -fx-font-size: 14;");
                  btn2.setText("signler");
                 
                 File ff = new File(System.getProperty("user.dir")+"/src/Images/" + rs.getString("image"));
            Image img = new Image(ff.toURI().toString());
                  String x= "-fx-border-box:box-sizing";
                  imageview.setStyle(x);
                  imageview.setFitHeight(120.0);
                  imageview.setFitWidth(120.0);
                  imageview.setLayoutX(339.0);
                  
                  imageview.setLayoutY(44.0);
                  imageview.setPickOnBounds(true);
                  imageview.setPreserveRatio(true);
                  imageview.setImage(img);
                  
                  
                  
                  Paint e5e8eb = null;
                  circle.setFill(e5e8eb);
                  circle.setLayoutX(399.0);
                  circle.setLayoutY(104.0);
                  circle.setRadius(75.0);
                  Paint c3c3c3 = null;
                  circle.setStroke(c3c3c3);
                  circle.setStrokeType(StrokeType.INSIDE);
                  
                  
                  pane.setPrefHeight(50.0);
                  pane.setPrefWidth(504.0);
                  pane.setLayoutY(200.0);
                  pane.setStyle("-fx-border-radius: 0 0 3 3; -fx-background-color: #E5E8EB; -fx-border-color: #C3C3C3; -fx-border-width: 1 0 0 0;");
                  pane.getChildren().add(rating);
                  
                  
                  label1.setPrefHeight(30.0);
                  label1.setPrefWidth(246.0);
                  label1.setLayoutX(34.0);
                  label1.setLayoutY(52.0);
                  label1.setStyle("-fx-text-fill: #73879C; -fx-font-weight: bold; -fx-font-size: 15;");
                  label1.setText(rs.getString("description"));
                  
                  label2.setPrefHeight(30.0);
                  label2.setPrefWidth(246.0);
                  label2.setLayoutX(34.0);
                  label2.setLayoutY(82.0);
                  label2.setStyle("-fx-text-fill: #73879C; -fx-font-weight: bold; -fx-font-size: 15;");
                  label2.setText("karim massoudi");
                  
                  label3.setPrefHeight(30.0);
                  label3.setPrefWidth(246.0);
                  label3.setLayoutX(34.0);
                  label3.setLayoutY(155.0);
                  label3.setStyle("-fx-text-fill: #73879C; -fx-font-weight: bold; -fx-font-size: 15;");
                  label3.setText((String.valueOf(rs.getInt("nombrePost"))));
                  
                  label4.setPrefHeight(30.0);
                  label4.setPrefWidth(246.0);
                  label4.setLayoutX(14.0);
                  label4.setLayoutY(14.0);
                  label4.setStyle("-fx-text-fill: #73879C;");
                  label4.setText(rs.getString("libelle"));
                  label4.setFont(Font.font("System Italic", 18.0));
                  
                  label5.setPrefHeight(30.0);
                  label5.setPrefWidth(246.0);
                  label5.setLayoutX(34.0);
                  label5.setLayoutY(125.0);
                  label5.setStyle("-fx-text-fill: #73879C; -fx-font-weight: bold; -fx-font-size: 15;");
                  label5.setText("Nombre Des Postes :");
                  
                  anchorpane1.setPrefHeight(250.0);
                  anchorpane1.setPrefWidth(300.0);
                  anchorpane1.setStyle("-fx-background-radius: 3; -fx-border-radius: 3; -fx-border-color: #c3c3c3;");
                  
                  
                  vbox.setSpacing(30.0);
                  separtor.setLayoutX(3.0);
                  separtor.setLayoutY(55.0);
                  separtor.setPrefHeight(4.0);
                  separtor.setPrefWidth(1213.0);
                  
                  
                  
                  pane.getChildren().addAll(btn1,btn2);
                  
                  
                  anchorpane1.getChildren().addAll(label1,label2,label3,label4,label5,pane,circle,imageview);
                  vbox.getChildren().add(anchorpane1);
                  f.getChildren().addAll(vbox );
                  f.setPrefWidth(1013);
                  
                  btn1.setOnAction(e->{
                      
                      
                      
                      Stage stag=new Stage();  
             SujetController y = new SujetController();
                    
                      try {
                          y.setIdc(Integer.parseInt(btn.getText()));
                      } catch (SQLException ex) {
                          Logger.getLogger(CatController.class.getName()).log(Level.SEVERE, null, ex);
                      } catch (IOException ex) {
                          Logger.getLogger(CatController.class.getName()).log(Level.SEVERE, null, ex);
                      }

                      try {
                          AnchorPane a = new AnchorPane();
                          a.setPrefWidth(1013);
                          a.setPrefHeight(730);
                          a.setStyle("-fx-background-color:  #edf2f6;");
                       id_anchor.getChildren().add(a);
                          id_anchor.getChildren().add(y.hb());
                      } catch (SQLException ex) {
                          Logger.getLogger(CatController.class.getName()).log(Level.SEVERE, null, ex);
                      } catch (IOException ex) {
                          Logger.getLogger(CatController.class.getName()).log(Level.SEVERE, null, ex);
                      }
                 
                  });
                  rs.next();
                  
              }
              
           
             
              
           
id_anchor.getChildren().add(f);
              
              
              
                  
        
    
    }
    
    public void test() throws SQLException, IOException
    { 
        
     Stage stag=new Stage();  
             SujetController y = new SujetController();
                    
            
                          y.setIdc(this.idc);


                      try {
                          AnchorPane a = new AnchorPane();
                          a.setPrefWidth(1013);
                          a.setPrefHeight(730);
                       id_anchor.getChildren().add(a);
                          id_anchor.getChildren().add(y.hb());
                      } catch (SQLException ex) {
                          Logger.getLogger(CatController.class.getName()).log(Level.SEVERE, null, ex);
                      } catch (IOException ex) {
                          Logger.getLogger(CatController.class.getName()).log(Level.SEVERE, null, ex);
                      }
                   
    
    
    }
    
    public VBox createPage(int pageIndex) {
        VBox pageBox = new VBox();
        Label pagelabel = new Label("Label :"+(pageIndex+1));
        pageBox.getChildren().add(pagelabel);
        return pageBox;
    }
          

    
    
    
}
