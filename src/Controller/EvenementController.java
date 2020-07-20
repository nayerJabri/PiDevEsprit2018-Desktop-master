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
import IService.IParticipationService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import jdk.nashorn.internal.codegen.CompilerConstants;

/**
 * FXML Controller class
 *
 * @author DJAZIA
 */
public class EvenementController extends Controller implements Initializable {

    @FXML
    private ImageView imageEve;
    @FXML
    private Label titre;
    @FXML
    private Label description;
    @FXML
    private Label date;
    @FXML
    private Label place;
    private Label nbplaces = new Label();
    @FXML
    private AnchorPane holderPane;
    public static Evenement evenement;
private final IParticipationService es = this.getService().getParticipationService();
private Connection con = DataSource.getInstance().getConnection();
private final IAvisEvenementService as = this.getService().getAvisEvenementService();
    @FXML
    private TextArea contenuu;
    private int idEvenement ;
    @FXML
    private WebView webView;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Calendar c = Calendar.getInstance();
        c.setTime(evenement.getDateEvenement());
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        WebEngine webEngine = webView.getEngine();
        String lien = getClass().getResource("../View/CountDown.html").toExternalForm();
        webEngine.load(lien);
        webEngine.reload();
        webView.getEngine().getLoadWorker().stateProperty().addListener((ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) -> {
            if( newValue != Worker.State.SUCCEEDED ) {
                return;  
            }
            webEngine.executeScript("document.init("+year+","+(month+1)+","+day+","+hour+","+minute+","+second+")");
        });
    }    

    public ImageView getImageEve() {
        return imageEve;
    }

    public void setImageEve(String imageEve) {
        //File f = new File( );
        Image img = new Image(getClass().getResource("../Images/").toExternalForm() + imageEve);

        this.imageEve.setImage(img);
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }
    

    public Label getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre.setText(titre);
    }

    public Label getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public Label getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    public Label getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place.setText(place);
    }

    public Label getNbplaces() {
        return nbplaces;
    }

    public void setNbplaces(String nbplaces) {
        this.nbplaces.setText(nbplaces);
    }

    public void setNbplaces() {
        this.nbplaces.setText(String.valueOf(Integer.parseInt(this.nbplaces.getText())+1));
    }

    @FXML
    private void ajouteravis(ActionEvent event) {
            
            AvisEvenement a = new AvisEvenement(contenuu.getText(), idEvenement);
            as.insertAvis(a);//tekhedh el id event mel label el hidden
            contenuu.setText(null);  
    }

    @FXML
    private void retour(ActionEvent event) throws IOException, SQLException {
        holderPane.getChildren().clear();
        
        holderPane.getChildren().add(new FXMLLoader().load(getClass().getResource("/View/ramy.fxml")));
    }

    }