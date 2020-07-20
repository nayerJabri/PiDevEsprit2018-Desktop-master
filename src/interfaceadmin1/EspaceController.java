/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceadmin1;

import Entity.Espace;
import Service.EspaceService;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Nayer Ben Jaber
 */
public class EspaceController implements Initializable {

    @FXML
    private AnchorPane holderPane;

    AnchorPane Work1, Work2, Work3, Work4;
    @FXML
    private JFXButton btnWork1;
    @FXML
    private JFXButton btnWork2;
    @FXML
    private JFXButton btnWork3;
   EspaceService espaceService = new EspaceService();
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

        FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource(("../View_Admin/Espace_Work1.fxml")));
        AnchorPane parentContent = fxmlLoader.load();

        holderPane.getChildren().clear();
        holderPane.getChildren().add(parentContent);

    }

    @FXML
    private void switchWork2(ActionEvent event) throws IOException {

FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource(("../View_Admin/Espace_Work2.fxml")));
        AnchorPane parentContent = fxmlLoader.load();
        holderPane.getChildren().clear();
        holderPane.getChildren().setAll(parentContent);

    }

    @FXML
    private void switchWork3(ActionEvent event) throws IOException {

FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource(("../View_Admin/Espace_Work3.fxml")));
        AnchorPane parentContent = fxmlLoader.load();
        holderPane.getChildren().clear();
        holderPane.getChildren().setAll(parentContent);

    }
public void getEspace()
    {
        Espace e = espaceService.getEspaceById(4);
        System.out.println(e.getTitre());
    }
    
    public List<Espace> afficherEspaceConfirmer()
    {
        List<Espace> espaces = espaceService.getEspaceConfirmer();
        return espaces;
        

    }
    
    public List<Espace> afficherEspaceNonConfirmer()
    {
        List<Espace> espaces = espaceService.getEspaceNonConfirmer();
       return espaces;
    }

     @FXML
    private void consulterEspaces(ActionEvent event) throws IOException {

        FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource(("../View/Espace_Work1.fxml")));
                     AnchorPane parentContent = fxmlloader.load();
                     holderPane.getChildren().setAll(parentContent);

           
 
    }
    
     @FXML
    private void consulterEspacesNonConfirmer(ActionEvent event) throws IOException {

        FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource(("../View/Espace_Work2.fxml")));
                     AnchorPane parentContent = fxmlloader.load();
                     holderPane.getChildren().setAll(parentContent);

           
 
    }
         @FXML
    private void consulterEspacesModifier(ActionEvent event) throws IOException {

        FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource(("../View/Espace_Work3.fxml")));
                     AnchorPane parentContent = fxmlloader.load();
                     holderPane.getChildren().setAll(parentContent);

           
 
    }
        //Set selected node to a content holder

   
      private Button table(){
         
     Button b=new Button("");
   
   
   return b;
   }
}
