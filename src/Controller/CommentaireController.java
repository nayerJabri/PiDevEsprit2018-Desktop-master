/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.DataSource;
import Service.LoginService;
import Service.serviceCommentaire;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import Core.Controller;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CommentaireController extends beblio implements Initializable {

    
    public int ids;
    public String contenu;
    public int id_user;
    public int idc;
    Connection cnx= DataSource.getInstance().getConnection();
    @FXML
    private AnchorPane an;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            hb();
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
     public void setIds(int ids , String contenu , int id_user) throws SQLException, IOException {
        this.ids = ids;
        this.contenu = contenu;
        this.id_user = id_user;

        hb();
    }
    
    public void hb() throws SQLException, FileNotFoundException, IOException
    
    {
        
        
    AnchorPane anchorpane = new AnchorPane();
    anchorpane.setPrefHeight(730);
    anchorpane.setPrefWidth(900);
    
    AnchorPane anchorpane1 = new AnchorPane();
    anchorpane1.setPrefHeight(451.0);
    anchorpane1.setPrefWidth(570.0);
    anchorpane1.setStyle("-fx-background-color: #26B99A;");
    Pane pane = new Pane();
    pane.setPrefHeight(39.0);
    pane.setPrefWidth(570.0);
    pane.setStyle("-fx-background-color: white;");
    
    Label label = new Label();
        label.setLayoutX(194.0);
    label.setLayoutY(3.0);
    label.setPrefHeight(326);
    label.setPrefWidth(366);
    label.setText("Les Reponses");
    label.setTextAlignment(TextAlignment.CENTER);
    
    label.setFont(Font.font("Arial Rounded MT Bold", 23.0));
    pane.getChildren().add(label);
    
    JFXTextField text = new JFXTextField();
    text.setLayoutX(64.0);
    text.setLayoutY(63.0);
    text.setPrefHeight(26.0);
    text.setPrefWidth(430.0);
    text.setPromptText("Recherche");
    
    VBox vbox = new VBox();
    vbox.setLayoutX(18.0);
    vbox.setLayoutY(112.0);
    vbox.setPrefHeight(617.0);
    vbox.setPrefWidth(530);

    Pane pane1 = new Pane();
    pane1.setLayoutX(570.0);
    pane1.setPrefHeight(39.0);
    pane1.setPrefWidth(442.0);
    pane1.setStyle("-fx-background-color: #337AB7;");
    Pane pane2 = new Pane();
     pane2.setLayoutX(601.0);
     pane2.setLayoutY(110.0);
    pane2.setPrefHeight(326.0);
    pane2.setPrefWidth(400);
    pane2.setStyle("-fx-background-radius: 2em; -fx-background-color: white;");
    
        ImageView imageview = new ImageView();
        imageview.setFitHeight(100.0);
        imageview.setFitWidth(100.0);
        imageview.setLayoutX(14.0);
        imageview.setLayoutY(14.0);
        imageview.setPickOnBounds(true);
        imageview.setPreserveRatio(true);
        LoginService service = new LoginService();
        ResultSet rs = service.userbyid(beblio.getId_user());
        rs.first();
           File ff = new File(System.getProperty("user.dir")+"/src/Images/" + rs.getString("image"));
            Image img = new Image(ff.toURI().toString());
Circle clip = new Circle(30, 30, 30);
       
        Text text1 = new Text();
    text1.setLayoutX(17.0);
        text1.setLayoutY(120.0);
        text1.setStrokeType(StrokeType.OUTSIDE);
        text1.setStrokeWidth(0.0);
        text1.setWrappingWidth(380);
        text1.setText(getContenus());
    imageview.setImage(img);
     imageview.setClip(clip);
        serviceCommentaire ss = new serviceCommentaire();
     
        ResultSet rs1 = ss.CommentaireByPost(beblio.getIds()) ;
        
         rs1.last();
         int count = rs1.getRow();
         System.out.println("iciii"+count);
         rs1.first();
              for (int i=1 ; i<count+1 ; i++)
              
              
              {
              
                    int id = Integer.parseInt(rs1.getObject("IdUser").toString());
                     
        ResultSet rs2 = service.userbyid(id);
        rs2.first();
        File fff = new File(System.getProperty("user.dir")+"/src/Images/" + rs2.getString("image"));
            Image img1 = new Image(fff.toURI().toString());
                     HBox hbox = new HBox();
                     hbox.setStyle("-fx-background-color: white; -fx-background-radius: 2em;-fx-border-color: black; -fx-border-radius: 2em;");
                     hbox.setPrefHeight(75.0);
                     hbox.setPrefWidth(530.0);

                     ImageView imagees = new ImageView();
                     hbox.setMargin(imagees, new Insets(10, 0, 0, 5));
                     imagees.setFitHeight(64.0);
                     imagees.setFitWidth(65.0);
                     imagees.setPickOnBounds(true);
                     imagees.setPreserveRatio(true);
                     imagees.setImage(img1);
                     Circle clip1 = new Circle(25, 25, 25);
                    imagees.setClip(clip1);
                     VBox vbox2 = new VBox();
                     HBox hbox2 = new HBox();
                     HBox hbox4 = new HBox();
                     Text text3 = new Text();
                     text3.setText(rs2.getString("username"));
                      hbox4.setMargin(text3, new Insets(20, 0, 0, 5));
                      text3.setFill(Paint.valueOf("#0f1492"));
                     hbox4.getChildren().add(text3);
                     hbox2.setPrefHeight(61.0);
                     hbox2.setPrefWidth(442.0);
                     Text text2 = new Text();
                     text2.setStrokeType(StrokeType.OUTSIDE);
                     text2.setStrokeWidth(0.0);
                     text2.setText(rs1.getString("contenu"));
                     text2.setWrappingWidth(409.3367347717285);
                     hbox2.setMargin(text2, new Insets(10, 0, 0, 0));
                     HBox hbox3 = new HBox();
                     hbox3.setPrefHeight(22.0);
                     hbox3.setPrefWidth(442.0);
                    
                     if (rs1.getInt("IdUser") == Controller.getUserId()){
                      JFXButton btn1 = new JFXButton();
                     btn1.setText("Modifier");
                     btn1.setStyle("-fx-background-color: #337AB7; -fx-text-fill: white;");
                     JFXButton btn2 = new JFXButton();
                     btn2.setText("Supprimer");
                     Button btntest = new Button();
                     btntest.setText(String.valueOf(rs1.getInt("id")));
                     btntest.setVisible(false);
                     System.out.println(btntest);
                      btn2.setStyle("-fx-background-color: #26B99A; -fx-text-fill: white;");
                     hbox3.getChildren().addAll(btn2);
                     
                                          btn2.setOnAction(e->{
                     
                          try {
                              ss.SupprimerCommentaire(Integer.parseInt(btntest.getText()));
                          } catch (SQLException ex) {
                              Logger.getLogger(CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
                          }
                          Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/Commentaire.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Node node =(Node)e.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
                     
                     
                     });
                     
                     }
                     

                     hbox2.getChildren().add(text2);
                     vbox2.getChildren().addAll(hbox4,hbox2,hbox3);
                     hbox.getChildren().addAll(imagees,vbox2);
                     vbox.getChildren().add(hbox);
                    
              rs1.next();
              
              }
        
        
    JFXTextArea textarea = new JFXTextArea();
    textarea.setLayoutX(601.0);
    textarea.setLayoutY(592.0);
    textarea.setPrefWidth(390);
    textarea.setPrefHeight(90.0);
    textarea.setPromptText("Posez votre commentaire");
    textarea.setStyle("-fx-background-color: white;");
    
    JFXButton commentez = new JFXButton();
        commentez.setLayoutX(894);
    commentez.setLayoutY(689);
    commentez.setPrefWidth(89.0);
    commentez.setPrefHeight(26.0);
    commentez.setStyle("-fx-background-color: #337AB7;");

    commentez.setTextFill(Paint.valueOf("#ffffff"));
    commentez.setText("commentez");
    pane2.getChildren().addAll(text1,imageview);
    anchorpane1.getChildren().addAll(pane,text,vbox);
    an.getChildren().addAll(anchorpane1,pane1,pane2,commentez,textarea);
        
    commentez.setOnAction(e->
    {

        try {
            ss.AjouterCommentaire(textarea.getText(),Controller.getUserId(), beblio.getIds());
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        
          Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/Commentaire.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(CommentaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Node node =(Node)e.getSource();
                 Stage stage = (Stage)node.getScene().getWindow();
            stage.setScene(new Scene(root));
    
    
    });
    
    

    }
    
    
}
