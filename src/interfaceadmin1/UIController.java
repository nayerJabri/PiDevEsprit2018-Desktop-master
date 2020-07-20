/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceadmin1;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Nayer Ben Jaber
 */
public class UIController implements Initializable {

    @FXML
    private AnchorPane holderPane;
    public JFXButton btnEspace;
    public JFXButton btnProfile;
    public JFXButton btnInteraction;
    public JFXButton btnEvenement;
    public JFXButton btnHome;
    public static AnchorPane anchor ;
    @FXML
    public JFXButton tnEspace1, btnProfile1, btnInteraction1, btnEvenement1, btnHome1;
    @FXML
    private JFXButton btnForum;

    AnchorPane Home, Forum, Profile, Evenement, Interaction, Espace;

    public static AnchorPane rootP;
    @FXML
    private JFXButton btnExit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rootP = holderPane;
    }

    //Set selected node to a content holder
    public static void setNode(Node node) {
        rootP.getChildren().clear();
        rootP.getChildren().add(node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void switchHome(ActionEvent event) throws IOException {
     Home = FXMLLoader.load(getClass().getResource("../View_Admin/Home.fxml"));
        setNode(Home);

    }

    @FXML
    private void switchForum(ActionEvent event) throws IOException {
Forum = FXMLLoader.load(getClass().getResource("../View_Admin/Forum.fxml"));
        setNode(Forum);

    }

    @FXML
    private void switchInteraction(ActionEvent event) throws IOException {
 Interaction = FXMLLoader.load(getClass().getResource("../View_Admin/Interaction.fxml"));
        setNode(Interaction);

    }

    @FXML
    private void switchProfile(ActionEvent event) throws IOException {
        Profile = FXMLLoader.load(getClass().getResource("../View_Admin/Profile.fxml"));
        setNode(Profile);

    }

    @FXML
    private void switchEspace(ActionEvent event) throws IOException {
        System.out.println("dfsfsdf");
         Espace = FXMLLoader.load(getClass().getResource("../View_Admin/Espace.fxml"));
        setNode(Espace);

    }

    @FXML
    private void switchEvenement(ActionEvent event) throws IOException {
Evenement = FXMLLoader.load(getClass().getResource("../View_Admin/Evenement.fxml"));
        setNode(Evenement);

    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

}
