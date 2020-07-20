/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.DataSource;
import Service.LoginService;
import Service.serviceSujet;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.User;
import java.io.File;
import java.io.FileInputStream;
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
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Core.Controller;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class SujetController extends beblio implements Initializable  {

    public int idc;
    Connection cnx= DataSource.getInstance().getConnection();
    @FXML
    private ScrollPane BIG;
    @FXML
    private AnchorPane BIGBIG;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setIdc1(getIdc());
            System.out.println("tawa "+beblio.getIdc());
        } catch (SQLException ex) {
            Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    

 public void setIdc(int idc) throws SQLException, IOException {
        this.idc = idc;
        System.out.println(idc);
        hb();
    }
 
 
  public void setIdc1(int idc) throws SQLException, IOException {
        this.idc = idc;
        System.out.println(idc);
        hb1();
    }
public ScrollPane hb() throws SQLException, FileNotFoundException, IOException {

ScrollPane pp = new ScrollPane();
VBox anchorpane = new VBox();
HBox f = new HBox();
JFXButton ajouter = new JFXButton();
                 ajouter.setPrefHeight(26);
                 ajouter.setPrefWidth(180);
  
                 ajouter.setStyle("-fx-background-color: #26B99A; -fx-text-fill: white;");
                 ajouter.setText("ajouter un sujet");
                 
                 ajouter.setOnAction(e->{
                     anchorpane.getChildren().clear();
                     beblio b = new beblio();
 
        b.setIdc(this.idc);
   
                      FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource(("../View/Ajouter_sujet.fxml")));
    try {
        anchorpane.getChildren().add(fxmlloader.load())
                ;
    } catch (IOException ex) {
        Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
    }
                      
                  }); 
                 
                 
                 
JFXButton quitter = new JFXButton();
                 quitter.setPrefHeight(26);
                 quitter.setPrefWidth(100);
  
                 quitter.setStyle("-fx-background-color: #337AB7; -fx-text-fill: white;");
                 quitter.setText("Revenir");
JFXButton favorie = new JFXButton();
                 favorie.setPrefHeight(26);
                 favorie.setPrefWidth(140);
  
                 favorie.setStyle("-fx-background-color: #f2218a; -fx-text-fill: white;");
                 favorie.setText("Liste Des Favories");
                 
    JFXTextField field = new JFXTextField();
                  field.setPrefHeight(26);
                 field.setPrefWidth(300);

                 field.setPromptText("Recherche");
                 
                 
                 
                 f.getChildren().addAll(ajouter,quitter,favorie,field);
                 
               
                 
                 
                 
                 
                 
                 
                 
                  quitter.setOnAction(e->{
 
        
                      Parent root = null;
                 try {
                     root = FXMLLoader.load(getClass().getResource("../View/LayoutFront.fxml"));
                 } catch (IOException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }
        
        Node node =(Node)e.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
                      
                  });
                 
anchorpane.getChildren().add(f);
anchorpane.setPrefHeight(730);
anchorpane.setPrefWidth(1013);

         favorie.setOnAction(e->{
 
             Parent root = null;
             Stage stage = new Stage();
    try {
        root = FXMLLoader.load(getClass().getResource("../View/Liste_favoris.fxml"));
    } catch (IOException ex) {
        Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        
        Scene scene = new Scene(root);
        
 
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
                      
                  });

    serviceSujet ss = new serviceSujet();
    LoginService s = new LoginService();

        ResultSet rs = ss.Listsujet(this.idc);
        rs.last();
int count = rs.getRow();
rs.first();
for (int i=0 ; i<count ; i++) {
    
              HBox hbox = new HBox();
      
              hbox.setStyle("-fx-background-color: #dae1ea; -fx-border-color: black;");
              
              
                 File ff = new File(System.getProperty("user.dir")+"/src/Images/" + rs.getString("image"));
            Image img = new Image(ff.toURI().toString());
              
              ImageView image = new ImageView();

              
              image.setFitHeight(105);
              image.setFitWidth(117);
              image.setPickOnBounds(true);
              image.setPreserveRatio(true);
              hbox.setMargin(image, new Insets(5, 5, 5, 5));
              image.setImage(img);
              
              VBox vbox = new VBox();
              vbox.setPrefHeight(94.0);
              vbox.setPrefWidth(850);
              
              HBox hbox1 = new HBox();
              HBox hbox2 = new HBox();
              HBox hbox3 = new HBox();
              hbox1.setPrefHeight(30.0);
              hbox1.setPrefWidth(476.0);
              hbox2.setPrefHeight(55.0);
              hbox2.setPrefWidth(476.0);
              hbox3.setPrefHeight(30.0);
              hbox3.setPrefWidth(476.0);
              
              Label label1 = new Label();
              label1.setPrefHeight(18.0);
              label1.setPrefWidth(234.0);
              label1.setText(rs.getString("titre")+""+":");
              hbox1.setMargin(label1, new Insets(7.0, 0, 0, 6));
              label1.setFont(Font.font("Bold", 22.0));
              Text text = new Text();
              text.setStrokeType(StrokeType.OUTSIDE);
              text.setStrokeWidth(0.0);
              text.setWrappingWidth(700);
              text.setText(rs.getString("contenu"));
              
    String teext = rs.getString("contenu");
    int id_user = Integer.parseInt(rs.getObject("IdUser").toString());
              
              hbox2.setMargin(text, new Insets(5.0, 0, 0, 6));
              
              int now = Integer.parseInt(rs.getObject("IdUser").toString());

             ResultSet rs1 = s.userbyid(now);
             
             rs1.first();
             Button x = new Button();
    x.setText(String.valueOf(rs.getInt("id")));
              Button btn1 = new Button();
              
               btn1.setPrefHeight(26);
              btn1.setPrefWidth(67.0);
              btn1.setStyle("-fx-background-color: #337AB7; -fx-text-fill: white;");
              btn1.setText("Entrer");
               
                 JFXButton partager = new JFXButton();
                 partager.setPrefHeight(26);
                 partager.setPrefWidth(100);
  
                 partager.setStyle("-fx-background-color: #f4b642; -fx-text-fill: white;");
                 partager.setText("Partager");
         
         
              hbox3.getChildren().addAll(btn1,partager);
    String u = rs.getString("image");
    String u1 = rs.getString("contenu");
              
              partager.setOnAction(e->{
 
                   String accessToken = "EAAB4Ydgv3yABAOiZCv2mq3p07BJXKaWZAnNzKtNnUNqeSMBSc11snPKQetwBFoi6jC25k5mZAcj8BTUoO8GZCBjIP17BBGwg39IOCePG9CS7LpEGj1FUoRHoWPsdRZAkDqrEwT1jIqqhLdknZCWntBXdzfoZAVaR1SSOGhZCyAg3HgXL2m1Nq79jhPNa9WUkHP0ZD";
        FacebookClient fbClient = new DefaultFacebookClient(accessToken);
        User me = fbClient.fetchObject("me", User.class);
       
        System.out.println(me.getLanguages());
        FileInputStream fis = null;
                  try {
                      fis = new FileInputStream(new File(System.getProperty("user.dir")+"/src/Images/"+u ));
                  } catch (FileNotFoundException ex) {
                      Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                  }
        System.out.println(fis.toString());
        
        FacebookType response = fbClient.publish("me/photos", FacebookType.class,
        BinaryAttachment.with("photo.jpg",fis),Parameter.with("message", u1));
        
 
                      
                  }); 
              
              
               Button btn3 = new Button();
                 btn3.setPrefHeight(26);
                 btn3.setPrefWidth(100);
                 
                 btn3.setStyle("-fx-background-color: #86B49F; -fx-text-fill: white;");
                 btn3.setText("Modifier");
              
              if(now == Controller.getUserId()){ 
        
             Button btn2 = new Button();
                 btn2.setPrefHeight(26);
                 btn2.setPrefWidth(200);
                 
                 btn2.setStyle("-fx-background-color: #26B99A; -fx-text-fill: white;");
                 btn2.setText("Supprimer votre sujet");
                 
                 
 
         hbox3.getChildren().addAll(btn2,btn3);
         
         
         
         btn2.setOnAction(e->{
 
             serviceSujet SP = new serviceSujet();
        
        int ids = Integer.parseInt(x.getText()) ;
              
                 try {
                     SP.SupprimerSujet(ids,this.idc);
                 } catch (SQLException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }
                  beblio bb = new beblio();
         
                      bb.setIdc(this.idc);

                
                      Parent root = null;
                 try {
                     root = FXMLLoader.load(getClass().getResource("../View/LayoutFront_1.fxml"));
                 } catch (IOException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }
        
        Node node =(Node)e.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
                      
                  });
          btn3.setOnAction(e->{
          
            anchorpane.getChildren().clear();
                     beblio b = new beblio();
 
        b.setIdc(this.idc);
   b.setIds(Integer.parseInt(x.getText()));
                      FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource(("../View/modifier_sujet.fxml")));
    try {
        anchorpane.getChildren().add(fxmlloader.load())
                ;
    } catch (IOException ex) {
        Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
    }
                 });
        
       
         
        }
              
              JFXButton ajout_fav = new JFXButton();
                 ajout_fav.setPrefHeight(26);
                 ajout_fav.setPrefWidth(140);
  
                 ajout_fav.setStyle("-fx-background-color: #f2218a; -fx-text-fill: white;");
                 ajout_fav.setText("Ajouter au favorie");
         hbox3.getChildren().add(ajout_fav);
         
         ajout_fav.setOnAction(e->{
                  try {
                      ss.Ajouterfavoris(Controller.getUserId(), Integer.parseInt(x.getText()));
                  } catch (SQLException ex) {
                      Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                  } catch (IOException ex) {
                      Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                  }
          
        System.out.println(" Ajoutée");
        
       beblio bb = new beblio();
         
                      bb.setIdc(this.idc);

                  try {
                      Parent root = FXMLLoader.load(getClass().getResource("../View/LayoutFront_1.fxml"));
        
        Node node =(Node)e.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
        

                  } catch (IOException ex) {
                      Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                  }
                    
                      
                  });
         
         
        ResultSet rs3 = ss.ConditionButton(Controller.getUserId(), Integer.parseInt(x.getText()));
        
        while (rs3.next()) 
        {
         hbox3.getChildren().clear();
         hbox3.getChildren().addAll(btn1,partager);
         
                       if(now == Controller.getUserId()){ 
        
             Button btn2 = new Button();
                 btn2.setPrefHeight(26);
                 btn2.setPrefWidth(200);
                 
                 btn2.setStyle("-fx-background-color: #26B99A; -fx-text-fill: white;");
                 btn2.setText("Supprimer votre sujet");
 
         hbox3.getChildren().addAll(btn2,btn3);
         
                  btn3.setOnAction(e->{
          
            anchorpane.getChildren().clear();
                     beblio b = new beblio();
 
        b.setIdc(this.idc);
   b.setIds(Integer.parseInt(x.getText()));
                      FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource(("../View/modifier_sujet.fxml")));
    try {
        anchorpane.getChildren().add(fxmlloader.load())
                ;
    } catch (IOException ex) {
        Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
    }
                 });
         
         btn2.setOnAction(e->{
 
             serviceSujet SP = new serviceSujet();
        
        int ids = Integer.parseInt(x.getText()) ;
                 
                 try {
                     SP.SupprimerSujet(ids,this.idc);
                 } catch (SQLException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }
            Parent root = null;
                 try {
                     root = FXMLLoader.load(getClass().getResource("../View/cat.fxml"));
                 } catch (IOException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }
        
        Node node =(Node)e.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
                      
                  });
         
         
        }
        
        }
        
        
        
         
         
              hbox2.getChildren().add(text);
              hbox1.getChildren().add(label1);
              vbox.getChildren().addAll(hbox1,hbox2,hbox3);
              
              VBox vbox2 = new VBox();
         
      
              Label label = new Label();
              vbox2.setMargin(label, new Insets(0, 0, 0, 15));
              
              label.setText(rs1.getString("username"));
              vbox2.getChildren().add(label);
              
              ImageView imageView = new ImageView();
               File fff = new File(System.getProperty("user.dir")+"/src/Images/" + rs1.getString("image"));
            Image img1 = new Image(fff.toURI().toString());
              imageView.setFitHeight(105);
              imageView.setFitWidth(100);
              imageView.setPickOnBounds(true);
              imageView.setPreserveRatio(true);
              imageView.setImage(img1);
        Circle clip = new Circle(50, 50, 30);
        imageView.setClip(clip);
        vbox2.getChildren().add(imageView);
        Label label2 = new Label();
        label2.setText(rs.getString("datePublication"));
        vbox2.getChildren().add(label2);
              hbox.getChildren().addAll(image,vbox,vbox2);
    
    
    anchorpane.getChildren().add(hbox);
    
     btn1.setOnAction(e->{
 
                      Stage stag=new Stage();  
             CommentaireController y = new CommentaireController();
             beblio yx = new beblio();
                  try {
                      yx.setIds(Integer.parseInt(x.getText()), teext, id_user);
                  } catch (SQLException ex) {
                      Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                  } catch (IOException ex) {
                      Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                  }
                          
                  Parent root = null;
             Stage stage = new Stage();
   
                  try {
                      root = FXMLLoader.load(getClass().getResource("../View/Commentaire.fxml"));
                  } catch (IOException ex) {
                      Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                  }
    
        
        Scene scene = new Scene(root);
        
 
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
                      
                      
                  });
     
          
     
    
    rs.next();
            }
  
                  field.setOnAction(e->{
                      
                      beblio.setIdc(this.idc);
                      beblio.setRecherche(field.getText());
           Parent root = null;
                 try {
                     root = FXMLLoader.load(getClass().getResource("../View/LayoutFront_1_1.fxml"));
                 } catch (IOException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }
        
        Node node =(Node)e.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
           
                 });
pp.setContent(anchorpane);
return pp;


}
public void hb1() throws SQLException, FileNotFoundException, IOException {
    
    
ScrollPane pp = new ScrollPane();
VBox anchorpane = new VBox();
HBox f = new HBox();
JFXButton ajouter = new JFXButton();
                 ajouter.setPrefHeight(26);
                 ajouter.setPrefWidth(180);
  
                 ajouter.setStyle("-fx-background-color: #26B99A; -fx-text-fill: white;");
                 ajouter.setText("ajouter un sujet");
                 
                 ajouter.setOnAction(e->{
                     anchorpane.getChildren().clear();
                     beblio b = new beblio();
 
        b.setIdc(this.idc);
   
                      FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource(("../View/Ajouter_sujet.fxml")));
    try {
        anchorpane.getChildren().add(fxmlloader.load())
                ;
    } catch (IOException ex) {
        Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
    }
                      
                  }); 
                 
                 
                 
JFXButton quitter = new JFXButton();
                 quitter.setPrefHeight(26);
                 quitter.setPrefWidth(100);
  
                 quitter.setStyle("-fx-background-color: #337AB7; -fx-text-fill: white;");
                 quitter.setText("Revenir");
JFXButton favorie = new JFXButton();
                 favorie.setPrefHeight(26);
                 favorie.setPrefWidth(140);
  
                 favorie.setStyle("-fx-background-color: #f2218a; -fx-text-fill: white;");
                 favorie.setText("Liste Des Favories");
                 
    JFXTextField field = new JFXTextField();
                  field.setPrefHeight(26);
                 field.setPrefWidth(300);

                 field.setPromptText("Recherche");
                 
                 
                 
                 f.getChildren().addAll(ajouter,quitter,favorie,field);
                 
               
                 
                 
                 
                 
                 
                 
                 
                  quitter.setOnAction(e->{
 
        
                      Parent root = null;
                 try {
                     root = FXMLLoader.load(getClass().getResource("../View/LayoutFront.fxml"));
                 } catch (IOException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }
        
        Node node =(Node)e.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
                      
                  });
                 
anchorpane.getChildren().add(f);
anchorpane.setPrefHeight(730);
anchorpane.setPrefWidth(1013);

         favorie.setOnAction(e->{
 
             Parent root = null;
             Stage stage = new Stage();
    try {
        root = FXMLLoader.load(getClass().getResource("../View/Liste_favoris.fxml"));
    } catch (IOException ex) {
        Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        
        Scene scene = new Scene(root);
        
 
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
                      
                  });

    serviceSujet ss = new serviceSujet();
    LoginService s = new LoginService();

        ResultSet rs = ss.Listsujet(this.idc);
        rs.last();
int count = rs.getRow();
rs.first();
for (int i=0 ; i<count ; i++) {
    
              HBox hbox = new HBox();
      
              hbox.setStyle("-fx-background-color: #dae1ea; -fx-border-color: black;");
              
              
                 File ff = new File(System.getProperty("user.dir")+"/src/Images/" + rs.getString("image"));
            Image img = new Image(ff.toURI().toString());
              
              ImageView image = new ImageView();

              
              image.setFitHeight(105);
              image.setFitWidth(117);
              image.setPickOnBounds(true);
              image.setPreserveRatio(true);
              hbox.setMargin(image, new Insets(5, 5, 5, 5));
              image.setImage(img);
              
              VBox vbox = new VBox();
              vbox.setPrefHeight(94.0);
              vbox.setPrefWidth(850);
              
              HBox hbox1 = new HBox();
              HBox hbox2 = new HBox();
              HBox hbox3 = new HBox();
              hbox1.setPrefHeight(30.0);
              hbox1.setPrefWidth(476.0);
              hbox2.setPrefHeight(55.0);
              hbox2.setPrefWidth(476.0);
              hbox3.setPrefHeight(30.0);
              hbox3.setPrefWidth(476.0);
              
              Label label1 = new Label();
              label1.setPrefHeight(18.0);
              label1.setPrefWidth(234.0);
              label1.setText(rs.getString("titre")+""+":");
              hbox1.setMargin(label1, new Insets(7.0, 0, 0, 6));
              label1.setFont(Font.font("Bold", 22.0));
              Text text = new Text();
              text.setStrokeType(StrokeType.OUTSIDE);
              text.setStrokeWidth(0.0);
              text.setWrappingWidth(700);
              text.setText(rs.getString("contenu"));
              
    String teext = rs.getString("contenu");
    int id_user = Integer.parseInt(rs.getObject("IdUser").toString());
              
              hbox2.setMargin(text, new Insets(5.0, 0, 0, 6));
              
              int now = Integer.parseInt(rs.getObject("IdUser").toString());

             ResultSet rs1 = s.userbyid(now);
             
             rs1.first();
             Button x = new Button();
    x.setText(String.valueOf(rs.getInt("id")));
              Button btn1 = new Button();
              
               btn1.setPrefHeight(26);
              btn1.setPrefWidth(67.0);
              btn1.setStyle("-fx-background-color: #337AB7; -fx-text-fill: white;");
              btn1.setText("Entrer");
               
                 JFXButton partager = new JFXButton();
                 partager.setPrefHeight(26);
                 partager.setPrefWidth(100);
  
                 partager.setStyle("-fx-background-color: #f4b642; -fx-text-fill: white;");
                 partager.setText("Partager");
         
         
              hbox3.getChildren().addAll(btn1,partager);
    String u = rs.getString("image");
    String u1 = rs.getString("contenu");
              
              partager.setOnAction(e->{
 
                   String accessToken = "EAAB4Ydgv3yABAOiZCv2mq3p07BJXKaWZAnNzKtNnUNqeSMBSc11snPKQetwBFoi6jC25k5mZAcj8BTUoO8GZCBjIP17BBGwg39IOCePG9CS7LpEGj1FUoRHoWPsdRZAkDqrEwT1jIqqhLdknZCWntBXdzfoZAVaR1SSOGhZCyAg3HgXL2m1Nq79jhPNa9WUkHP0ZD";
        FacebookClient fbClient = new DefaultFacebookClient(accessToken);
        User me = fbClient.fetchObject("me", User.class);
       
        System.out.println(me.getLanguages());
        FileInputStream fis = null;
                  try {
                      fis = new FileInputStream(new File(System.getProperty("user.dir")+"/src/Images/"+u ));
                  } catch (FileNotFoundException ex) {
                      Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                  }
        System.out.println(fis.toString());
        
        FacebookType response = fbClient.publish("me/photos", FacebookType.class,
        BinaryAttachment.with("photo.jpg",fis),Parameter.with("message", u1));
        
 
                      
                  }); 
              
              
               Button btn3 = new Button();
                 btn3.setPrefHeight(26);
                 btn3.setPrefWidth(100);
                 
                 btn3.setStyle("-fx-background-color: #86B49F; -fx-text-fill: white;");
                 btn3.setText("Modifier");
              
              if(now == Controller.getUserId()){ 
        
             Button btn2 = new Button();
                 btn2.setPrefHeight(26);
                 btn2.setPrefWidth(200);
                 
                 btn2.setStyle("-fx-background-color: #26B99A; -fx-text-fill: white;");
                 btn2.setText("Supprimer votre sujet");
                 
                 
 
         hbox3.getChildren().addAll(btn2,btn3);
         
         
         
         btn2.setOnAction(e->{
             System.out.println("zefzffefef");
             serviceSujet SP = new serviceSujet();
        
        int ids = Integer.parseInt(x.getText()) ;
              
                 try {
                     SP.SupprimerSujet(ids,this.idc);
                 } catch (SQLException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }
                  beblio bb = new beblio();
         
                      bb.setIdc(this.idc);

                
                      Parent root = null;
                 try {
                     root = FXMLLoader.load(getClass().getResource("../View/LayoutFront_1.fxml"));
                 } catch (IOException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }
        
        Node node =(Node)e.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
                      
                  });
          btn3.setOnAction(e->{
          
            anchorpane.getChildren().clear();
                     beblio b = new beblio();
 
        b.setIdc(this.idc);
   b.setIds(Integer.parseInt(x.getText()));
                      FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource(("../View/modifier_sujet.fxml")));
    try {
        anchorpane.getChildren().add(fxmlloader.load())
                ;
    } catch (IOException ex) {
        Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
    }
                 });
        
       
         
        }
              
              JFXButton ajout_fav = new JFXButton();
                 ajout_fav.setPrefHeight(26);
                 ajout_fav.setPrefWidth(140);
  
                 ajout_fav.setStyle("-fx-background-color: #f2218a; -fx-text-fill: white;");
                 ajout_fav.setText("Ajouter au favorie");
         hbox3.getChildren().add(ajout_fav);
         
         ajout_fav.setOnAction(e->{
                  try {
                      ss.Ajouterfavoris(Controller.getUserId(), Integer.parseInt(x.getText()));
                  } catch (SQLException ex) {
                      Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                  } catch (IOException ex) {
                      Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                  }
          
        System.out.println(" Ajoutée");
        
       beblio bb = new beblio();
         
                      bb.setIdc(this.idc);

                  try {
                      Parent root = FXMLLoader.load(getClass().getResource("../View/LayoutFront_1.fxml"));
        
        Node node =(Node)e.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
        

                  } catch (IOException ex) {
                      Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                  }
                    
                      
                  });
         
         
        ResultSet rs3 = ss.ConditionButton(Controller.getUserId(), Integer.parseInt(x.getText()));
        
        while (rs3.next()) 
        {
         hbox3.getChildren().clear();
         hbox3.getChildren().addAll(btn1,partager);
         
                       if(now == Controller.getUserId()){ 
        
             Button btn2 = new Button();
                 btn2.setPrefHeight(26);
                 btn2.setPrefWidth(200);
                 
                 btn2.setStyle("-fx-background-color: #26B99A; -fx-text-fill: white;");
                 btn2.setText("Supprimer votre sujet");
 
         hbox3.getChildren().addAll(btn2,btn3);
         
          btn3.setOnAction(e->{
          
            anchorpane.getChildren().clear();
                     beblio b = new beblio();
 
        b.setIdc(this.idc);
   b.setIds(Integer.parseInt(x.getText()));
                      FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource(("../View/modifier_sujet.fxml")));
    try {
        anchorpane.getChildren().add(fxmlloader.load())
                ;
    } catch (IOException ex) {
        Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
    }
                 });
         
         btn2.setOnAction(e->{
 
             serviceSujet SP = new serviceSujet();
        
        int ids = Integer.parseInt(x.getText()) ;
                 
                 try {
                     SP.SupprimerSujet(ids,this.idc);
                 } catch (SQLException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }
            Parent root = null;
                 try {
                     root = FXMLLoader.load(getClass().getResource("../View/cat.fxml"));
                 } catch (IOException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }
        
        Node node =(Node)e.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
                      
                  });
         
         
        }
        
        }
        
        
        
         
         
              hbox2.getChildren().add(text);
              hbox1.getChildren().add(label1);
              vbox.getChildren().addAll(hbox1,hbox2,hbox3);
              
              VBox vbox2 = new VBox();
         
      
              Label label = new Label();
              vbox2.setMargin(label, new Insets(0, 0, 0, 15));
              
              label.setText(rs1.getString("username"));
              vbox2.getChildren().add(label);
              
              ImageView imageView = new ImageView();
               File fff = new File(System.getProperty("user.dir")+"/src/Images/" + rs1.getString("image"));
            Image img1 = new Image(fff.toURI().toString());
              imageView.setFitHeight(105);
              imageView.setFitWidth(100);
              imageView.setPickOnBounds(true);
              imageView.setPreserveRatio(true);
              imageView.setImage(img1);
        Circle clip = new Circle(30, 30, 30);
        imageView.setClip(clip);
        vbox2.getChildren().add(imageView);
        Label label2 = new Label();
        label2.setText(rs.getString("datePublication"));
        vbox2.getChildren().add(label2);
              hbox.getChildren().addAll(image,vbox,vbox2);
    
    
    anchorpane.getChildren().add(hbox);
    
     btn1.setOnAction(e->{
 
                      Stage stag=new Stage();  
             CommentaireController y = new CommentaireController();
             beblio yx = new beblio();
                  try {
                      yx.setIds(Integer.parseInt(x.getText()), teext, id_user);
                  } catch (SQLException ex) {
                      Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                  } catch (IOException ex) {
                      Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                  }
                          
                  Parent root = null;
             Stage stage = new Stage();
   
                  try {
                      root = FXMLLoader.load(getClass().getResource("../View/Commentaire.fxml"));
                  } catch (IOException ex) {
                      Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                  }
    
        
        Scene scene = new Scene(root);
        
 
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
                      
                      
                  });
     
          
     
    
    rs.next();
            }
  
                  field.setOnAction(e->{
                      
                      beblio.setIdc(this.idc);
                      beblio.setRecherche(field.getText());
           Parent root = null;
                 try {
                     root = FXMLLoader.load(getClass().getResource("../View/LayoutFront_1_1.fxml"));
                 } catch (IOException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }
        
        Node node =(Node)e.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
           
                 });
BIG.setContent(anchorpane);


}

    
    
}
