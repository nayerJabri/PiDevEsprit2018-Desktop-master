/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.Controller;
import Entity.AvisEspace;
import Entity.Espace;
import Entity.User;
import Entity.PhotoEspace;
import Service.UserService;
import Service.AvisEspaceService;
import Service.EspaceService;
import Service.PhotoEspaceService;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Nayer Ben Jaber
 */
public class EspaceContenuController extends Controller implements Initializable {

    public static int ide;
    public static String nom;
    public static String prenom;
   
    @FXML
    private AnchorPane holderPane;
    EspaceService esp = new EspaceService();
            UserService user = new UserService();
    PhotoEspaceService pht = new PhotoEspaceService();
    @FXML
    private Label titre;
    @FXML
    private ImageView image;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image3;
    @FXML
    private Label description;
    @FXML
    private Label adresse;
    @FXML
    private Label email;
    private Pane p;
    @FXML
    private VBox bb;
    @FXML
    private ImageView image11;
    @FXML
    private ImageView image31;
    @FXML
    private JFXButton map;
    @FXML
    private Rating rating;
    @FXML
    private JFXButton switchUpdate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        infoespace();
    }

    public void infoespace() {
        InfoEspacefrontController ics = new InfoEspacefrontController();
        List<AvisEspace> ratings = new ArrayList<AvisEspace>();
        AvisEspaceService avis = new AvisEspaceService();
        int id = ics.idp;
        VBox v = new VBox();
        HBox b = new HBox();
        Espace espace = esp.getEspaceById(id);
        PhotoEspace photo = pht.getPhotoById(espace.getId());
         User   usr = user.getUserById(espace.getIdUser());
        ide = espace.getId();
        nom = usr.getNom();
        prenom = usr.getPrenom();
        titre.setText(espace.getTitre());
        titre.setWrapText(true);
        description.setText(espace.getDescription());
        description.setWrapText(true);
        description.setPrefWidth(400);
        adresse.setText(espace.getAdresse());
        adresse.setWrapText(true);
        email.setText(espace.getEmail());
        adresse.setWrapText(true);
        ratings = avis.getRating(espace.getId());
        if (this.getUser().getId() == espace.getIdUser()) {
            switchUpdate.setVisible(true);
        } else {
            switchUpdate.setVisible(false);
        }
        if (!ratings.isEmpty()) {
            int h = 0;
            int d = 0;
            for (int i = 0; i < ratings.size(); i++) {
                d = d + ratings.get(i).getRating();
                h++;

                float m = d / h;
                rating.setRating(m);
            }
        } else {
            rating.setRating(0);
        }
        rating.setTranslateX(0);
        rating.setDisable(true);
        Image img = new Image(getClass().getResource("../Images/").toExternalForm() + espace.getPhoto());
        image.setImage(img);
        Image img1 = new Image(getClass().getResource("../Images/").toExternalForm() + photo.getPhoto1());
        image1.setImage(img1);
        Image img2 = new Image(getClass().getResource("../Images/").toExternalForm() + photo.getPhoto2());
        image3.setImage(img2);
        Image img3 = new Image(getClass().getResource("../Images/").toExternalForm() + photo.getPhoto3());
        image11.setImage(img3);
        Image img4 = new Image(getClass().getResource("../Images/").toExternalForm() + photo.getPhoto4());
        image31.setImage(img4);
    }

    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);

    }

    @FXML
    private void switchMap(ActionEvent event) throws IOException {
 Stage stage = new Stage();
          Parent root = FXMLLoader.load(getClass().getResource("../View/Map.fxml")); 
        Scene scene = new Scene(root,1100,700);     
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void switchUpdate(ActionEvent event) throws IOException {
 Stage stage = new Stage();
          Parent root = FXMLLoader.load(getClass().getResource("../View/UpdateEspace.fxml")); 
        Scene scene = new Scene(root,465,657);     
        stage.setScene(scene);
        stage.show();

    }

}
