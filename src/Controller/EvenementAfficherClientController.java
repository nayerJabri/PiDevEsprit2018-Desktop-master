/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.Controller;
import Core.DataSource;
import Entity.Evenement;
import IService.IEvenementService;
import IService.IParticipationService;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
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
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author DJAZIA
 */
public class EvenementAfficherClientController extends Controller implements Initializable {

    @FXML
    private ImageView image;
    @FXML
    private Label titre;
    @FXML
    private Label date;
    private final IEvenementService es = this.getService().getEvenementService();
    private final IParticipationService p = this.getService().getParticipationService();
    @FXML
    private AnchorPane anchorpane;
    @FXML
    public static VBox vbox;
    @FXML
    private HBox hbox;
    private Connection con = DataSource.getInstance().getConnection();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            afficherliste();
        } catch (SQLException ex) {
            Logger.getLogger(EvenementAfficherClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AnchorPane afficherliste() throws SQLException {

        ScrollPane sp = new ScrollPane();
        AnchorPane anchor = new AnchorPane();
        anchor.setPrefHeight(500.0);
        anchor.setPrefWidth(1013.0);
        VBox vbox = new VBox();
        vbox.setLayoutX(79.0);
        vbox.setLayoutY(70.0);
        vbox.setPrefHeight(500.0);
        vbox.setPrefWidth(800.0);
        vbox.setStyle("-fx-background-color: white;");
        ResultSet rs = con.createStatement().executeQuery("select * from evenement where dateEvenement > now()");
        rs.last();
        int x = rs.getRow();
        rs.first();

        for (int i = 0; i < x; i++) {

            ScrollBar scrollbar = new ScrollBar();
            HBox hbox = new HBox();
            hbox.setPrefHeight(63.0);
            hbox.setPrefWidth(343.0);
            vbox.setLayoutX(79.0);
            vbox.setLayoutY(35.0);
            vbox.setSpacing(10);
            vbox.setStyle("-fx-background-color: #f8edff; ");
            

            ImageView image = new ImageView();
            image.setFitHeight(195.0);
            image.setFitWidth(195.0);
            image.setPickOnBounds(true);
            image.setPreserveRatio(true);
            //File f = new File("G:/PiDev/Sprint2/PiDevEsprit2018-Desktop/src/Images/" + rs.getString("imageEve"));
            Image img = new Image(getClass().getResource("../Images/").toExternalForm() + rs.getString("image_eve"));
            image.setImage(img);
            
            Label label1 = new Label();
            label1.setPrefHeight(195.0);
            label1.setPrefWidth(433.0);
            label1.setText("  Titre :  " + rs.getString("titre"));
            label1.setFont(new Font("Cambria", 20));
            label1.setTextFill(Color.web("#0076a3"));
            //label1.setStyle("-fx-background-color: #c1f2de; ");

//            Label label2 = new Label();
//            label2.setPrefHeight(86.0);
//            label2.setPrefWidth(299.0);
//            label2.setText(rs.getString("dateEvenement"));
            JFXButton btn = new JFXButton();
            btn.setPrefHeight(87.0);
            btn.setPrefWidth(160.0);
            
            //setFont(Font.font("Verdana", FontWeight.BOLD, 70));
            btn.setFont(new Font("Cambria", 20));
            btn.setStyle("-fx-background-color: #aaffff; ");
            btn.setText("Read more");
            btn.setTextFill(Color.web("#0076a3"));

            //btn.setTextFill("WHITE");
//            hbox.getChildren().addAll(image, label1, label2, btn);
            hbox.getChildren().addAll(image, label1, btn);

            vbox.getChildren().add(hbox);
            Evenement evenement = new Evenement(rs.getInt("id"), rs.getString("image_eve"), rs.getInt("nbplaces"), rs.getDate("dateEvenement"), rs.getString("titre"), rs.getString("description"), rs.getString("titreCordination"));
            btn.setOnAction(e -> {
                try {
                    EvenementController.evenement = evenement;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/evenement.fxml"));
                    AnchorPane root = loader.load();
                    
                    JFXButton participer = new JFXButton();
                    participer.setText("Participer");
                    participer.setPrefHeight(25.0);
                    participer.setPrefWidth(88.0);
                    participer.setLayoutX(603);
                    participer.setLayoutY(476);
                    JFXButton annuler = new JFXButton();
                    annuler.setText("Annuler");
                    annuler.setPrefHeight(25.0);
                    annuler.setPrefWidth(88.0);
                    annuler.setLayoutX(703);
                    annuler.setLayoutY(476);
                    Label nbr = new Label();
                    nbr.setLayoutX(764);
                    nbr.setLayoutY(446);
                    nbr.setText(String.valueOf(evenement.getNbplaces()));
                    
                    root.getChildren().add(nbr);
                    //setFont(Font.font("Verdana", FontWeight.BOLD, 70));
                    participer.setFont(new Font("Cambria", 15));
                    participer.setStyle("-fx-background-color: #724848; ");
                    participer.setTextFill(Color.web("#e8f8ff"));
                    
                    root.getChildren().add(participer);
                    if(p.checkParticipation(evenement, this.getUser()))
                    {
                        participer.setDisable(true);
                        annuler.setDisable(false);
                    }
                    else
                    {
                        participer.setDisable(false);
                        annuler.setDisable(true);
                    }
                    participer.setOnMouseClicked(s
                                -> {
                                                 
                       nbr.setText(String.valueOf(Integer.parseInt(nbr.getText())+1));
                       evenement.setNbplaces(Integer.parseInt(nbr.getText())-1);
                        p.increment(evenement,this.getUser());
                        participer.setDisable(true);
                        annuler.setDisable(false);
                    });

                    annuler.setFont(new Font("Cambria", 15));
                    annuler.setStyle("-fx-background-color: #724848; ");
                    annuler.setTextFill(Color.web("#e8f8ff"));
                    root.getChildren().add(annuler);
                    annuler.setOnMouseClicked(k
                                -> {                    
                       nbr.setText(String.valueOf(Integer.parseInt(nbr.getText())-1));
                       evenement.setNbplaces(Integer.parseInt(nbr.getText())+1);
                        p.decrement(evenement, this.getUser());
                        participer.setDisable(false);
                        annuler.setDisable(true);
                                                        
                    });
                    EvenementController ec = loader.getController();
                    ec.setIdEvenement(evenement.getId());
                    ec.setTitre("Titre de l'evenement : "+evenement.getTitre());
                    ec.setDate("Date de l'evenement : "+evenement.getDateEvenement().toString());
                    ec.setDescription("Description sur l'evenement : "+evenement.getDescription());
                    String aa = String.valueOf(evenement.getNbplaces());
                    ec.setNbplaces(aa);
                    ec.setPlace("La place est : "+evenement.getTitreCordination());
                    ec.setImageEve(evenement.getImageEve());
                    Controller.holderPane.getChildren().clear();
                    Controller.holderPane.getChildren().add(root);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            });
            rs.next();
        }

        sp.setContent(vbox);
        sp.setMaxHeight(500);
        sp.setMaxWidth(1013);
        anchor.getChildren().add(sp);

        return anchor;
    }

    public AnchorPane partitre(String e) throws SQLException {

        ScrollPane sp = new ScrollPane();
        AnchorPane anchor = new AnchorPane();
        anchor.setPrefHeight(500.0);
        anchor.setPrefWidth(1013.0);
        VBox vbox = new VBox();
        vbox.setLayoutX(79.0);
        vbox.setLayoutY(70.0);
        vbox.setPrefHeight(500.0);
        vbox.setPrefWidth(800.0);
        vbox.setStyle("-fx-background-color: white;");
        ResultSet rs = con.createStatement().executeQuery("select * from evenement where titre LIKE '%" + e + "%' and dateEvenement > now()");
        rs.last();
        int x = rs.getRow();
        rs.first();

        for (int i = 0; i < x; i++) {
            ScrollBar scrollbar = new ScrollBar();
            HBox hbox = new HBox();
            hbox.setPrefHeight(63.0);
            hbox.setPrefWidth(343.0);
            vbox.setLayoutX(79.0);
            vbox.setLayoutY(35.0);
            vbox.setSpacing(10);
            vbox.setStyle("-fx-background-color: #f8edff; ");
            

            ImageView image = new ImageView();
            image.setFitHeight(195.0);
            image.setFitWidth(195.0);
            image.setPickOnBounds(true);
            image.setPreserveRatio(true);
            System.out.print(rs.getString("imageEve"));
            //File f = new File(getClass().getResource("../Images/").toExternalForm() + rs.getString("imageEve"));
            Image img = new Image(getClass().getResource("../Images/").toExternalForm() + rs.getString("image_eve"));
            image.setImage(img);
            
            Label label1 = new Label();
            label1.setPrefHeight(195.0);
            label1.setPrefWidth(433.0);
            label1.setText("  Titre :  " + rs.getString("titre"));
            label1.setFont(new Font("Cambria", 20));
            label1.setTextFill(Color.web("#0076a3"));
            //label1.setStyle("-fx-background-color: #c1f2de; ");

//            Label label2 = new Label();
//            label2.setPrefHeight(86.0);
//            label2.setPrefWidth(299.0);
//            label2.setText(rs.getString("dateEvenement"));
            JFXButton btn = new JFXButton();
            btn.setPrefHeight(87.0);
            btn.setPrefWidth(160.0);
            //setFont(Font.font("Verdana", FontWeight.BOLD, 70));
            btn.setFont(new Font("Cambria", 20));
            btn.setStyle("-fx-background-color: #aaffff; ");
            btn.setText("Read more");
            btn.setTextFill(Color.web("#0076a3"));

            //btn.setTextFill("WHITE");
//            hbox.getChildren().addAll(image, label1, label2, btn);
            hbox.getChildren().addAll(image, label1, btn);

            vbox.getChildren().add(hbox);
            Evenement evenement = new Evenement(rs.getInt("id"), rs.getString("image_eve"), rs.getInt("nbplaces"), rs.getDate("dateEvenement"), rs.getString("titre"), rs.getString("description"), rs.getString("titreCordination"));
            btn.setOnAction(a -> {
                try {
                    EvenementController.evenement = evenement;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/evenement.fxml"));
                    AnchorPane root = loader.load();
                    
                    JFXButton participer = new JFXButton();
                    participer.setText("Participer");
                    participer.setPrefHeight(25.0);
                    participer.setPrefWidth(88.0);
                    participer.setLayoutX(603);
                    participer.setLayoutY(476);
                    JFXButton annuler = new JFXButton();
                    annuler.setText("Annuler");
                    annuler.setPrefHeight(25.0);
                    annuler.setPrefWidth(88.0);
                    annuler.setLayoutX(703);
                    annuler.setLayoutY(476);
                    Label nbr = new Label();
                    nbr.setLayoutX(764);
                    nbr.setLayoutY(446);
                    nbr.setText(String.valueOf(evenement.getNbplaces()));
                    
                    root.getChildren().add(nbr);
                    //setFont(Font.font("Verdana", FontWeight.BOLD, 70));
                    participer.setFont(new Font("Cambria", 15));
                    participer.setStyle("-fx-background-color: #724848; ");
                    participer.setTextFill(Color.web("#e8f8ff"));
                    
                    root.getChildren().add(participer);
                    if(p.checkParticipation(evenement, this.getUser()))
                    {
                        participer.setDisable(true);
                        annuler.setDisable(false);
                    }
                    else
                    {
                        participer.setDisable(false);
                        annuler.setDisable(true);
                    }
                    participer.setOnMouseClicked(s
                                -> {
                                                 
                       nbr.setText(String.valueOf(Integer.parseInt(nbr.getText())+1));
                       evenement.setNbplaces(Integer.parseInt(nbr.getText())-1);
                        p.increment(evenement,this.getUser());
                        participer.setDisable(true);
                        annuler.setDisable(false);
                    });

                    annuler.setFont(new Font("Cambria", 15));
                    annuler.setStyle("-fx-background-color: #724848; ");
                    annuler.setTextFill(Color.web("#e8f8ff"));
                    root.getChildren().add(annuler);
                    annuler.setOnMouseClicked(k
                                -> {                    
                       nbr.setText(String.valueOf(Integer.parseInt(nbr.getText())-1));
                       evenement.setNbplaces(Integer.parseInt(nbr.getText())+1);
                        p.decrement(evenement, this.getUser());
                        participer.setDisable(false);
                        annuler.setDisable(true);
                                                        
                    });
                    EvenementController ec = loader.getController();
                    ec.setIdEvenement(evenement.getId());
                    ec.setTitre("Titre de l'evenement : "+evenement.getTitre());
                    ec.setDate("Date de l'evenement : "+evenement.getDateEvenement().toString());
                    ec.setDescription("Description sur l'evenement : "+evenement.getDescription());
                    String aa = String.valueOf(evenement.getNbplaces());
                    ec.setNbplaces(aa);
                    ec.setPlace("La place est : "+evenement.getTitreCordination());
                    ec.setImageEve(evenement.getImageEve());
                    Controller.holderPane.getChildren().clear();
                    Controller.holderPane.getChildren().add(root);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            });
            rs.next();
        }

        sp.setContent(vbox);
        sp.setMaxHeight(500);
        sp.setMaxWidth(1013);
        anchor.getChildren().add(sp);

        return anchor;
    }
}
