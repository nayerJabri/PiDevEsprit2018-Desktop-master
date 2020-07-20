/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.Controller;
import Entity.Album;
import Entity.Signaler;
import Entity.User;
import IService.IAlbumService;
import IService.ISignalerService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Achrafoun
 */
public class AutreAlbumController extends Controller implements Initializable {
    
    private IAlbumService albumService = this.getService().getAlbumService();
    private ISignalerService signalerService = this.getService().getSignalerService();
    
    private static User autreUser;
    public static User getAutreUser() {
        return autreUser;
    }

    public static void setAutreUser(User autreUser) {
        AutreAlbumController.autreUser = autreUser;
    }

    @FXML
    private Label nomp;
    @FXML
    private ImageView photop;
    @FXML
    private Button autreJournalButton;
    @FXML
    private Button autreAproposButton;
    @FXML
    private Button autreAlbumButton;
    @FXML
    private ScrollPane scrollPanGrid;
    @FXML
    private GridPane album;
    @FXML
    private Button signalerProfil;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomp.setText(autreUser.getNom()+" "+autreUser.getPrenom());
        //-------------        
        photop.setImage(new Image(getClass().getResource("../Images/"+autreUser.getImage()).toExternalForm()));
        //-------------
        List<Album> all_photos = albumService.getPhotosByUser(autreUser);
        int c=0;
        int l=0;
        for(Album photo:all_photos)
        {
            ImageView img = new ImageView(getClass().getResource("../Images/"+photo.getUrl()).toExternalForm());
            img.setFitWidth(228);
            img.setFitHeight(155);
            img.setPickOnBounds(true);
            img.setPreserveRatio(true);
            album.add(img, c++, l);
            if(c==4)
            {
                c=0;
                l++;
            }
        }
    }    

    @FXML
    private void autreJournalAction(ActionEvent event) {
        AutreJournalController.setAutreUser(autreUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/autreJournal.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            AutreJournalController autreJournalController = loader.getController();
            autreJournalController.setAutreUser(autreUser);
        } catch (IOException ex) {
            Logger.getLogger(AutreAlbumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void autreAproposAction(ActionEvent event) {
        AutreAproposController.setAutreUser(autreUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/autreApropos.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            AutreAproposController autreAproposController = loader.getController();
            autreAproposController.setAutreUser(autreUser);
        } catch (IOException ex) {
            Logger.getLogger(AutreAlbumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void signalerProfilAction(ActionEvent event) {
        Signaler s = new Signaler();
        s.setIdUser(autreUser.getId());
        //------------------
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Ajouter un emploi");
        //dialog.setContentText("Please enter your name:");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        //dialog.initStyle(StageStyle.UNDECORATED);
        dialog.getDialogPane().setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(2), new Insets(2))));
        dialog.getDialogPane().setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        //dialog.getDialogPane().setStyle("-fx-border-color: black");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextArea textSignal = new TextArea();
        textSignal.setPrefWidth(300);
        textSignal.setPrefHeight(100);

        grid.add(new Label("Raison de signal: "), 0, 0);
        grid.add(textSignal, 1, 0);

        dialog.getDialogPane().setContent(grid);

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){       
            s.setCause(textSignal.getText());
            signalerService.ajouterSignal(s);
            //----
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/autreJournal.fxml"));
            try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(ParamsEmpController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
