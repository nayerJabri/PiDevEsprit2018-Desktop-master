/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceadmin1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import interfaceadmin1.EspaceController;
import Entity.Espace;
import Entity.EspaceCopy;
import Entity.PhotoEspace;
import IService.IPhotoEspaceService;
import Service.EspaceCopyService;
import Service.EspaceService;
import Service.PhotoEspaceService;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Nayer Ben Jaber
 */
public class Espace_work3Controller implements Initializable {

    private Alert ActionsAlert;
    private Alert Description;
    private ButtonType confirmer;
    private ButtonType supprimer;
    private ButtonType annuler;
    private Alert ConfirmDelete;
    private ButtonType Oui;
    private EspaceService esp;

    @FXML
    private AnchorPane container;
    @FXML
    private TableColumn<EspaceCopy, String> titre;
    @FXML
    private TableColumn<EspaceCopy, String> description;
    @FXML
    private TableView<EspaceCopy> listEspaceCopy;
    @FXML
    private TableColumn<Espace, String> nom;
    @FXML
    private TableColumn<Espace, String> prenom;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init_buttons();

        EspaceCopyService esp = new EspaceCopyService();
        ArrayList arrayList = (ArrayList) esp.getAll();
        EspaceService ep = new EspaceService();
        ObservableList observableList = FXCollections.observableArrayList(arrayList);
        titre.setCellValueFactory(new PropertyValueFactory<>("Titre"));
        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        nom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        listEspaceCopy.setItems(observableList);

        listEspaceCopy.setRowFactory(tv -> {

            TableRow<EspaceCopy> row = new TableRow<>();

            row.setOnMouseClicked(e -> {

                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    Optional<ButtonType> result = ActionsAlert.showAndWait();
                    if (result.isPresent() && result.get() == confirmer) {
                        int id = row.getItem().getId();
                        esp.confirmerEspaceModif(row.getItem());
                        esp.removeEspaceCopy(id);
                       Espace epp = ep.getEspaceById(id);
                        listEspaceCopy.getItems().removeAll(listEspaceCopy.getSelectionModel().getSelectedItem());
                        listEspaceCopy.getSelectionModel().select(null);
                    } else if (result.isPresent() && result.get() == supprimer) {
                        esp.removeEspaceCopy(row.getItem().getId());

                        // esp.removeEspaceCopy(row.getItem().getId());
                        listEspaceCopy.getItems().removeAll(listEspaceCopy.getSelectionModel().getSelectedItem());
                        listEspaceCopy.getSelectionModel().select(null);

                    }

                }

            });
            return row;
        }
        );
    }

    public void set_content(Node widget) {
        container.getChildren().clear();
        container.getChildren().add(widget);
    }

    private void init_buttons() {
        confirmer = new ButtonType("Confirmer");
        supprimer = new ButtonType("Supprimer");
        annuler = new ButtonType("Annuler");
        EspaceService esp = new EspaceService();
        ActionsAlert = new Alert(Alert.AlertType.CONFIRMATION);
        Description = new Alert(Alert.AlertType.CONFIRMATION);
        ActionsAlert.setContentText("");
        Description.setTitle("Description");
        Description.setHeaderText("Description d'espace");
        ActionsAlert.setTitle("MySoulMate");
        ActionsAlert.setHeaderText("Gestion Espace");
        ActionsAlert.getButtonTypes().clear();
        ActionsAlert.getButtonTypes().addAll(confirmer, supprimer, annuler);

    }
    
}
