/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Espace;
import Entity.EspaceCopy;
import Service.EspaceCopyService;
import Service.EspaceService;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Nayer Ben Jaber
 */
public class UpdateEspaceController implements Initializable {

    @FXML
    private AnchorPane holderPane;
    @FXML
    private TextField titre;
    @FXML
    private TextArea description;
    @FXML
    private JFXButton update;
                  EspaceContenuController ics = new EspaceContenuController();
                int id = ics.ide;
                String nom = ics.nom;
                String prenom = ics.prenom;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

                EspaceService esp = new EspaceService();
                Espace espace = esp.getEspaceById(id);
                titre.setText(espace.getTitre());
                description.setText(espace.getDescription());
                

    }    

    @FXML
    private void update(ActionEvent event) {
        EspaceCopy esp = new EspaceCopy(titre.getText(), description.getText(), nom, prenom , id);
        EspaceCopyService espc = new EspaceCopyService();
        espc.ajoutEspaceCopy(esp);
        
    }
    
}
