/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.DataSource;
import Entity.Evenement;
import Service.EvenementService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import jdk.nashorn.internal.runtime.logging.Loggable;

/**
 * FXML Controller class
 *
 * @author DJAZIA
 */
public class RamyController implements Initializable {

    @FXML
    private AnchorPane content;
    @FXML
    private AnchorPane holderPane;
    @FXML
    private JFXTextField recherche;
    ObservableList<Evenement> list;
    private Connection con = DataSource.getInstance().getConnection();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            EvenementAfficherClientController a = new EvenementAfficherClientController();
            content.getChildren().add(a.afficherliste());
          
            EvenementService es = new EvenementService();
            list = es.getEv();

        } catch (Exception ex) {
            Logger.getLogger(RamyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setNode(Node node) {
        content.getChildren().clear();
        content.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void recherche(KeyEvent event) {
        FilteredList<Evenement> filteredData = new FilteredList<>(list, e -> true);
        recherche.setOnKeyReleased(e
                    -> {

            recherche.textProperty().addListener((ObservableValue<? extends String> ObservableValue, String oldValue, String newValue) -> {
                filteredData.setPredicate((Predicate<? super Evenement>) new Predicate<Evenement>() {
                    @Override
                    public boolean test(Evenement d) {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        return d.getTitre().toLowerCase().contains(lowerCaseFilter.subSequence(0, lowerCaseFilter.length()));
                    }
                });
            });
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/AfficherClient.fxml"));
                Parent root = loader.load();
                EvenementAfficherClientController ac = loader.getController();
                if("".equals(recherche.getText())){
                  AnchorPane a = ac.afficherliste();
                content.getChildren().add(a);
                }else{
                AnchorPane a = ac.partitre(recherche.getText());
                content.getChildren().add(a);}
            } catch (IOException | SQLException ex) {
                Logger.getLogger(RamyController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    @FXML
    private void showCalendar(ActionEvent event) throws Exception {
        holderPane.getChildren().setAll(new  CalendarApp().startCalendar());
       
    }
}
