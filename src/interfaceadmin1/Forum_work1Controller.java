/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceadmin1;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class Forum_work1Controller implements Initializable {

    @FXML
    private AnchorPane holderPane;
    @FXML
    private TableView<?> table;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> libelle;
    @FXML
    private TableColumn<?, ?> description;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private Button ajouter;
    @FXML
    private Button delete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
         AnchorPane parentContent = null;
        try {
            parentContent = FXMLLoader.load(getClass().getResource(("../View_Admin/Forum_Work1.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(Forum_work1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
            holderPane.getChildren().setAll(parentContent);
    
    }

    @FXML
    private void ajouter(ActionEvent event) {
    }

    @FXML
    private void deleteData(ActionEvent event) {
    }


}
