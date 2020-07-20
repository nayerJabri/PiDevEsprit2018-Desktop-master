/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.Controller;
import Entity.AvisEspace;
import Entity.Espace;
import Service.AvisEspaceService;
import Service.EspaceService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Nayer Ben Jaber
 */
public class InfoEspacefrontController extends Controller implements Initializable {

    @FXML
    private AnchorPane holderPane;
    ScrollPane sp = new ScrollPane();
    public static int idp;

    @FXML
    private FlowPane flow;

    public InfoEspacefrontController() {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        EspaceService esp = new EspaceService();
        List<Espace> espace = new ArrayList<Espace>();
        espace = esp.getEspaceConfirmer();
        List<AvisEspace> ratings = new ArrayList<AvisEspace>();
        AvisEspaceService avis = new AvisEspaceService();
        AvisEspace rate = new AvisEspace();
        int i;
        for (i = 0; i < espace.size(); i++) {   
            ratings = avis.getRating(espace.get(i).getId());
            
        Image img = new Image(getClass().getResource("../Images/").toExternalForm() + espace.get(i).getPhoto());
            VBox b = new VBox();
            b.setPadding(new Insets(20, 0, 0, 20));
            VBox p = new VBox();
            p.setPrefHeight(300);
            p.setPrefWidth(300);
            Label titre = new Label();
            Label adresse = new Label();
            Label email = new Label();
            Label id = new Label();
            id.setText(String.valueOf(espace.get(i).getId()));
            id.setVisible(false);
            titre.setText("Titre: " + espace.get(i).getTitre());
            titre.setStyle("-fx-text-fill: #494c62;");
            titre.setFont(new javafx.scene.text.Font("Ebrima Bold", 20));
            email.setText("Email: " + espace.get(i).getEmail());
            email.setStyle("-fx-text-fill: #494c62;");
            email.setFont(new javafx.scene.text.Font("Ebrima ", 15));
            adresse.setText("Adresse: " + espace.get(i).getAdresse());
            adresse.setStyle("-fx-text-fill: #494c62;");
            adresse.setFont(new javafx.scene.text.Font("Ebrima", 15));
            p.getChildren().add(titre);
            titre.setTranslateX(50);
            p.getChildren().add(adresse);
            adresse.setTranslateX(20);
            p.getChildren().add(email);
            email.setTranslateX(20);
            p.getStyleClass().add("changevbox");
            ImageView im = new ImageView();
            im.setFitWidth(280);
            im.setFitHeight(180);
            im.setImage(img);
            im.setTranslateX(10);
            im.setTranslateY(20);
            titre.setTranslateY(5);
            email.setTranslateY(15);
            adresse.setTranslateY(10);
            p.getChildren().add(im);
            Rating rating = new Rating();
            int w=0;
            for(int k = 0;k< ratings.size();k++)
            {           
            if(this.getUser().getId() == ratings.get(k).getIdUser()){
                w++;
                  
                  
            }
            }
            if(w>0 || this.getUser().getId()==espace.get(i).getIdUser()  )
                rating.setDisable(true);
                        if(!ratings.isEmpty()){
                int h = 0;int d = 0;
                   for (int a = 0; a < ratings.size(); a++)   
                {
                   d =d+ ratings.get(a).getRating();
                 h++;
                }
                float m = d/h;
            rating.setRating(m);
            }
            else rating.setRating(0);
            rating.setTranslateX(50);
            rating.setId("rate");
            rating.setTranslateY(20);
            p.getChildren().add(rating);
            b.getChildren().add(p);
            int idesp = espace.get(i).getId();
            rating.setOnMouseClicked((MouseEvent e) -> {
                try {
                    AvisEspace av = new AvisEspace();
                    double rate1 = rating.getRating();
                    av.setIdEspace(idesp);
                    av.setIdUser(this.getUser().getId());
                    av.setNbrating(1);
                    av.setRating((int) rate1);
                    avis.ajouterRating(av);
                    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(("../View/InfoEspacefront.fxml")));
                    AnchorPane parentContent = fxmlloader.load(); 
                    holderPane.getChildren().clear();
                    holderPane.getChildren().add(parentContent);
                } catch (IOException ex) {
                    Logger.getLogger(InfoEspacefrontController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            flow.getChildren().add(b);
            flow.setPadding(new Insets(0, 0, 0, 20));
            im.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    try {
                        idp = Integer.parseInt(id.getText());
                        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(("../View/EspaceContenu.fxml")));
                        AnchorPane parentContent = fxmlloader.load();
                        holderPane.getChildren().add(parentContent);

                    } catch (IOException ex) {

                    }
                }
            });

            // flow.setPadding(new Insets(60,60,60,60));
        }

        sp.setContent(flow);

        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        holderPane.getChildren().add(sp);

    }

}
