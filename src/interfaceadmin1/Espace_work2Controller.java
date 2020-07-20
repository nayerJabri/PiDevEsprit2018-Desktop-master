/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceadmin1;

import interfaceadmin1.EspaceController;
import Entity.Espace;
import Entity.PhotoEspace;
import IService.IPhotoEspaceService;
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
public class Espace_work2Controller implements Initializable {

    private Alert ActionsAlert;
    private Alert Description;
    private ButtonType confirmer;
    private ButtonType supprimer;
    private ButtonType annuler;
    private Alert ConfirmDelete;
    private ButtonType Oui;
    private EspaceService esp;

    @FXML
    private AnchorPane holderPane;
    private StackPane container;
    @FXML
    private TableView<Espace> listEspace;
    @FXML
    private TableColumn<Espace, String> titre;
    @FXML
    private TableColumn<Espace, String> description;
    @FXML
    private TableColumn<Espace, String> email;
    @FXML
    private TableColumn<Espace, String> adresse;
    @FXML
    private TableColumn<Espace, String> photo;
    @FXML
    private TableColumn<Espace, Float> longitude;
    @FXML
    private TableColumn<Espace, Float> latitude;
    @FXML
    private TableColumn<Espace, Integer> iduser;
    @FXML
    private ImageView image;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image11;
    @FXML
    private ImageView image31;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init_buttons();
        EspaceController espace = new EspaceController();
        PhotoEspaceService pht = new PhotoEspaceService();

        esp = new EspaceService();
        ArrayList arrayList = (ArrayList) espace.afficherEspaceNonConfirmer();
        ObservableList observableList = FXCollections.observableArrayList(arrayList);
        titre.setCellValueFactory(new PropertyValueFactory<>("Titre"));
        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("Adresse"));
        photo.setCellValueFactory(new PropertyValueFactory<>("Photo"));
        longitude.setCellValueFactory(new PropertyValueFactory<>("longi"));
        latitude.setCellValueFactory(new PropertyValueFactory<>("lat"));
        iduser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        listEspace.setItems(observableList);
        listEspace.setRowFactory(tv -> {
            TableRow<Espace> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                image1.setVisible(false);
                image3.setVisible(false);
                image11.setVisible(false);
                image31.setVisible(false);
                PhotoEspace phot = pht.getPhotoById(row.getItem().getId());
                Image img = new Image(getClass().getResource("../Images/").toExternalForm() + row.getItem().getPhoto());
                image.setImage(img);
                if (phot != null) {
                    image1.setVisible(true);
                    image3.setVisible(true);
                    image11.setVisible(true);
                    image31.setVisible(true);
                    Image img1 = new Image(getClass().getResource("../Images/").toExternalForm() + phot.getPhoto1());
                    image1.setImage(img1);
                    Image img2 = new Image(getClass().getResource("../Images/").toExternalForm() + phot.getPhoto2());
                    image3.setImage(img2);
                    Image img3 = new Image(getClass().getResource("../Images/").toExternalForm() + phot.getPhoto3());
                    image11.setImage(img3);
                    Image img4 = new Image(getClass().getResource("../Images/").toExternalForm() + phot.getPhoto4());
                    image31.setImage(img4);
                }

                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    Optional<ButtonType> result = ActionsAlert.showAndWait();
                    if (result.isPresent() && result.get() == confirmer) {
                        esp.confirmeEspace(row.getItem().getId());
                        esp.send_mail(row.getItem());
                        listEspace.getItems().removeAll(listEspace.getSelectionModel().getSelectedItem());
                        listEspace.getSelectionModel().select(null);
                    } else if (result.isPresent() && result.get() == supprimer) {

                        esp.removeEspace(row.getItem().getId());
                        esp.send_maildel(row.getItem());
                        listEspace.getItems().removeAll(listEspace.getSelectionModel().getSelectedItem());
                        listEspace.getSelectionModel().select(null);

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
