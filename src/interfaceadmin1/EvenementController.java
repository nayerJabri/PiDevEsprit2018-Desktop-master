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
public class EvenementController implements Initializable {

    @FXML
    private AnchorPane holderPane;

    @FXML
    private JFXButton btnWork1, btnWork2;

    AnchorPane Work1, Work2;

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

    @FXML
    private void switchWork1(ActionEvent event) throws IOException {

    
            AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("../View_Admin/Evenement_Work1.fxml")));
            holderPane.getChildren().setAll(parentContent);
     
    }

    @FXML
    private void switchWork2(ActionEvent event) throws IOException {

      

            AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("../View_Admin/Evenement_Work2.fxml")));
            holderPane.getChildren().setAll(parentContent);
       
    }

   
}
