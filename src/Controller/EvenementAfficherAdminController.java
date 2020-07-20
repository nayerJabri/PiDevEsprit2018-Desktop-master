/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.Controller;
import Core.DataSource;
import Entity.AvisEvenement;
import Entity.Evenement;
import IService.IAvisEvenementService;
import IService.IEvenementService;
import Service.EvenementService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author DJAZIA
 */
public class EvenementAfficherAdminController extends Controller implements Initializable {

    FileChooser saveFileChooser = new FileChooser();
    File saveFile;
    File srcFile, destFile;

    @FXML
    private TableColumn<Evenement, ?> image;
    @FXML
    private TableColumn<Evenement, Number> nbplaces;
    @FXML
    private TableColumn<Evenement, Date> date;
    @FXML
    private TableColumn<Evenement, String> titre;
    @FXML
    private TableColumn<Evenement, String> description;
    @FXML
    private TableColumn<Evenement, String> place;

    private final IEvenementService es = this.getService().getEvenementService();
    private final IAvisEvenementService as = this.getService().getAvisEvenementService();

    @FXML
    private TableView<Evenement> display;
    @FXML
    private DatePicker dateEvenement;
    @FXML
    private TextField nbplacess;
    @FXML
    private TextField placee;
    @FXML
    private TextField titree;
    @FXML
    private TextArea descriptionn;
    @FXML
    private JFXButton Update;
    @FXML
    private JFXButton DELETE;
    private ImageView picview;
    @FXML
    private JFXButton Image;
    @FXML
    private Label ramy;
    @FXML
    private Label ramy1;
    @FXML
    private Label ramy2;
    @FXML
    private Label datenow;
    @FXML
    private Label date_act;
    @FXML
    private TableColumn<?, ?> contenu;
    @FXML
    private TableView<AvisEvenement> display1;
    @FXML
    private TableColumn<AvisEvenement, String> ColumnIdEvenement;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherevent();
    }

    public void afficherevent() {
//        ObservableList ok = FXCollections.observableArrayList();
//        ok = es.getEv();
//        display.setItems(null);
//        display.setItems(ok);
        display.getItems().clear();
        nbplaces.setCellValueFactory(new PropertyValueFactory<>("nbplaces"));
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        place.setCellValueFactory(new PropertyValueFactory<>("titreCordination"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateEvenement"));
        display.setItems(es.getEv());

    }
    
    public void afficherAvis(ObservableList<AvisEvenement> listAvis)
    {
        display1.getItems().clear();
        contenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        display1.setItems(listAvis);
    }

    @FXML
    private void ajouter(ActionEvent event) {
        if (ValidateFields()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ajout impossible!");
            alert.setHeaderText(null);
            alert.setContentText("veuiller remplir les champs correctemenet SVP!");
            alert.showAndWait(); 
            return ;
            }
        else {
            LocalDate localDate = dateEvenement.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            java.util.Date date = Date.from(instant);
            java.sql.Date dtsql = new java.sql.Date(date.getTime());
            Evenement e = new Evenement(srcFile.getName(), 0, dtsql, titree.getText(), descriptionn.getText(), placee.getText());
            es.insertEvenement(e);
            afficherevent();
            nbplacess.setText(null);
            titree.setText(null);
            descriptionn.setText(null);
            placee.setText(null);
            dateEvenement.getEditor().setText(null);

        } 
    }



    @FXML
    private void supprimerEvenements(ActionEvent event) {
        Evenement e = display.getSelectionModel().getSelectedItem();
        EvenementService ev = new EvenementService();
        int ide = e.getId();
        ev.deleteEvenement(ide);
        display.getItems().removeAll(display.getSelectionModel().getSelectedItem());
        display.getSelectionModel().select(null);
    }

    @FXML
    private void UploadImage(ActionEvent event) {
        File file = saveFileChooser.showOpenDialog(null);
        if (file != null) {
            srcFile = file;
            if (srcFile != null) {
                try {
                    String p = System.getProperty("user.dir") + "/src/images/" + srcFile.getName();
                    copyFile(srcFile, new File(p));
                } catch (IOException ex) {
                    Logger.getLogger(EvenementAfficherAdminController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        try (
                    FileChannel in = new FileInputStream(sourceFile).getChannel();
                    FileChannel out = new FileOutputStream(destFile).getChannel();) {

            out.transferFrom(in, 0, in.size());
        }
    }

    public boolean ValidateFields() {
        int ramyy = 0, ramyyy = 0, ramyyyy = 0 , hamdi = 0 ;

        if (titree.getText().length() == 0) {
            ramyy = 1;
            ramy.setVisible(true);
        } else {
            ramy.setVisible(false);
        }
        if (placee.getText().length() == 0) {
            ramyyy = 1;
            ramy1.setVisible(true);
        } else {
            ramy1.setVisible(false);
        }
        if (descriptionn.getText().length() == 0) {
            ramyyyy = 1;
            ramy2.setVisible(true);
        } else {
            ramy2.setVisible(false);
        }
        //teste date
        if (this.dateEvenement.getEditor().getText().length() == 0) {
             hamdi = 1;
            datenow.setVisible(true);
            date_act.setVisible(false); }
            
            else if ((this.dateEvenement.getValue().isBefore(LocalDate.now()))) {
            hamdi = 1;
            date_act.setVisible(true);
            datenow.setVisible(false);
        } else {
            datenow.setVisible(false);
            date_act.setVisible(false);
            System.out.println("date :" + this.dateEvenement.getValue());
            System.out.println(LocalDate.now());
        }
        
        return (ramyy == 1 || ramyyy == 1 || ramyyyy == 1 || hamdi == 1);

    }



    @FXML
    private void Fetch(MouseEvent event) {
        titree.setText(display.getSelectionModel().getSelectedItem().getTitre());
        placee.setText(display.getSelectionModel().getSelectedItem().getTitreCordination());
        descriptionn.setText(display.getSelectionModel().getSelectedItem().getDescription());
        dateEvenement.setValue(LocalDate.parse(display.getSelectionModel().getSelectedItem().getDateEvenement().toString()));
        int ide = display.getSelectionModel().getSelectedItem().getId();
        afficherAvis(as.getAvis(ide));
    }

    @FXML
    private void modifierEvenements(ActionEvent event) {
            LocalDate localDate = dateEvenement.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            java.util.Date date = Date.from(instant);
            java.sql.Date dtsql = new java.sql.Date(date.getTime());
            Evenement e = new Evenement(dtsql, titree.getText(), descriptionn.getText(), placee.getText());
            es.updateEvenement(e, display.getSelectionModel().getSelectedItem().getId());
            afficherevent();
    }
}
