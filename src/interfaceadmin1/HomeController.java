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
public class HomeController implements Initializable {

    @FXML
    private AnchorPane holderPane;

    @FXML
    private JFXButton btnEspace, btnProfile, btnInteraction, btnEvenement;
    @FXML
    private JFXButton btnForum;

    AnchorPane Home, Forum, Profile, Evenement, Interaction, Espace;

    /**
     * Initializes the controller class.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    //Set selected node to a content holder
    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    private void switchHome(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("../View_Admin/Home.fxml")));
        AnchorPane parentContent = fxmlLoader.load();
        holderPane.getChildren().clear();
        holderPane.getChildren().setAll(parentContent);
        System.out.println("dfsfsdf");

    }

    @FXML
    private void switchForum(ActionEvent event) throws IOException {
FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("../View_Admin/Forum.fxml")));
        AnchorPane parentContent = fxmlLoader.load();
        holderPane.getChildren().clear();
        holderPane.getChildren().setAll(parentContent);
    }

    @FXML
    private void switchEvenement(ActionEvent event) throws IOException {
FXMLLoader fxmlLoader =new FXMLLoader(getClass().getResource(("../View/Evenement.fxml")));
        AnchorPane parentContent = fxmlLoader.load();
        holderPane.getChildren().clear();
        holderPane.getChildren().setAll(parentContent);
    }

    @FXML
    private void switchInteraction(ActionEvent event) throws IOException {
FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("../View_Admin/Interaction.fxml")));
        AnchorPane parentContent = fxmlLoader.load();
        holderPane.getChildren().clear();
        holderPane.getChildren().setAll(parentContent);
    }

    @FXML
    private void switchProfile(ActionEvent event) throws IOException {
FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("../View_Admin/Profile.fxml")));
        AnchorPane parentContent = fxmlLoader.load();
        holderPane.getChildren().clear();
        holderPane.getChildren().setAll(parentContent);
    }

    @FXML
    private void switchEspace(ActionEvent event) throws IOException {
FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("../View_Admin/Espace.fxml")));
        AnchorPane parentContent = fxmlLoader.load();
        holderPane.getChildren().clear();
        holderPane.getChildren().add(parentContent);
    }

}
