/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.Controller;
import Entity.Album;
import Entity.CentreInteret;
import Entity.Publication;
import Entity.Signaler;
import Entity.User;
import IService.IAlbumService;
import IService.ICentreInteretService;
import IService.IPublicationService;
import IService.ISignalerService;
import IService.IUserService;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author Achrafoun
 */
public class AutreJournalController extends Controller implements Initializable {

    private IUserService userService = this.getService().getUserService();
    private IPublicationService publicationService = this.getService().getPublicationService();
    private ICentreInteretService centreInteretService = this.getService().getCentreInteretService();
    private IAlbumService albumService = this.getService().getAlbumService();
    private ISignalerService signalerService = this.getService().getSignalerService();
    
    private static User autreUser;
    public static User getAutreUser() {
        return autreUser;
    }

    public static void setAutreUser(User autreUser) {
        AutreJournalController.autreUser = autreUser;
    }
    //--------------
    
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
    private Label descriptionLabel;
    @FXML
    private Label serieLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private Label filmLabel;
    @FXML
    private Label livreLabel;
    @FXML
    private GridPane albumGrid;
    @FXML
    private VBox vboxStatus;
    @FXML
    private Button signalerProfil;
    @FXML
    private Label facebook;
    @FXML
    private Label twitter;
    @FXML
    private Label instagram;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomp.setText(autreUser.getNom()+" "+autreUser.getPrenom());
        //-------------        
        photop.setImage(new Image(getClass().getResource("../Images/"+autreUser.getImage()).toExternalForm()));
        //-------------
        descriptionLabel.setText(autreUser.getApropos());
        //-------------
        facebook.setText(autreUser.getFacebook());
        facebook.setId(autreUser.getId().toString());
        facebook.setOnMouseClicked(this::openFacebook);
        twitter.setText(autreUser.getTwitter());
        twitter.setId(autreUser.getId().toString());
        twitter.setOnMouseClicked(this::openTwitter);
        instagram.setText(autreUser.getInstagram());
        instagram.setId(autreUser.getId().toString());
        instagram.setOnMouseClicked(this::openInstagram);
        //-------------
        String series="";
        List<CentreInteret> listSeries = centreInteretService.getSeriesByUser(autreUser);
        for(CentreInteret c:listSeries)
        {
            series+=c.getContenu()+",";
        }
        serieLabel.setText(series);
        //--------
        String films="";
        List<CentreInteret> listFilms = centreInteretService.getFilmsByUser(autreUser);
        for(CentreInteret c:listFilms)
        {
            films+=c.getContenu()+",";
        }
        filmLabel.setText(films);
        //--------
        String livres="";
        List<CentreInteret> listLivres = centreInteretService.getLivresByUser(autreUser);
        for(CentreInteret c:listLivres)
        {
            livres+=c.getContenu()+",";
        }
        livreLabel.setText(livres);
        //--------
        String artists="";
        List<CentreInteret> listArtists = centreInteretService.getArtistsByUser(autreUser);
        for(CentreInteret c:listArtists)
        {
            artists+=c.getContenu()+",";
        }
        artistLabel.setText(artists);
        //--------
        List<Album> lastPhotos = albumService.getLastPhotosByUser(autreUser);
        
        int c=0;
        int l=0;
        for(Album album:lastPhotos)
        {
            ImageView img = new ImageView(getClass().getResource("../Images/"+album.getUrl()).toExternalForm());
            img.setFitWidth(75);
            img.setFitHeight(61);
            img.setPickOnBounds(true);
            img.setPreserveRatio(true);
            albumGrid.add(img, c++, l);
            if(c==3)
            {
                c=0;
                l++;
            }
        }
        //--------
        List<Publication> pulications = publicationService.getPublicationByUser(autreUser);
        vboxStatus.getChildren().clear();
        for(Publication p :pulications)
        {
            vboxStatus.getChildren().add(publicationItem(autreUser, p));
        }
    }
    
    private AnchorPane publicationItem(User user,Publication publication)
    {
        Font prefFont = new Font("System Bold", 12);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(435, 128);
        ImageView userImage = new ImageView(getClass().getResource("../Images/"+user.getImage()).toExternalForm());
        userImage.setFitWidth(60);
        userImage.setFitHeight(60);
        userImage.setPickOnBounds(true);
        userImage.setPreserveRatio(true);
        //userImage.setClip(new Circle(30, 30, 30));
        Label userName = new Label(user.getPrenom()+" "+user.getNom());
        userName.setLayoutX(63);
        userName.setLayoutY(3);
        userName.setFont(prefFont);
        
        SimpleDateFormat formater = new SimpleDateFormat("EEEE, dd-MM-yyyy HH:mm");        
        Label pubDate = new Label(formater.format(publication.getDatePublication()));
        pubDate.setLayoutX(63);
        pubDate.setLayoutY(20);
        pubDate.setFont(prefFont);
        Text contenu = new Text(publication.getContenu());
        contenu.setLayoutX(13);
        contenu.setLayoutY(73);
        contenu.setStrokeType(StrokeType.OUTSIDE);
        contenu.setStrokeWidth(0);
        contenu.setWrappingWidth(350);
        
        anchorPane.getChildren().addAll(userImage,userName,pubDate,contenu);
        VBox.setMargin(anchorPane, new Insets(0, 0, 30, 0));        
        return anchorPane;
    }

    @FXML
    private void autreAProposAction(ActionEvent event) {
        AutreAproposController.setAutreUser(autreUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/autreApropos.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            AutreAproposController autreAproposController = loader.getController();
            autreAproposController.setAutreUser(autreUser);
        } catch (IOException ex) {
            Logger.getLogger(AutreJournalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void autreAlbumAction(ActionEvent event) {
        AutreAlbumController.setAutreUser(autreUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/autreAlbum.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            AutreAlbumController autreAlbumController = loader.getController();
            autreAlbumController.setAutreUser(autreUser);
        } catch (IOException ex) {
            Logger.getLogger(AutreJournalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void signalerProfilAction(ActionEvent event) {
        Signaler s = new Signaler();
        s.setIdUser(autreUser.getId());
        //------------------
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Signaler profil");
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

    @FXML
    private void openFacebook(MouseEvent event) {
        Label x = (Label) event.getSource();
        User uw = userService.getUserById(Integer.parseInt(x.getId()));        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Messages envoyés !");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setResizable(true);
        
        WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        webEngine.load(uw.getFacebook());
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>()
	        {
	            public void changed(ObservableValue<? extends State> ov, State oldState, State newState)
	            {
	                if (newState == State.SUCCEEDED)
	                {
	                    //stage.setTitle(webEngine.getLocation());
	                    alert.setTitle(webEngine.getTitle());
	                }
	            }
	        });        
        alert.getDialogPane().setContent(webView);
        alert.showAndWait();
    }

    @FXML
    private void openTwitter(MouseEvent event) {
        Label x = (Label) event.getSource();
        User uw = userService.getUserById(Integer.parseInt(x.getId()));        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Messages envoyés !");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setResizable(true);
        
        WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        webEngine.load(uw.getTwitter());
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>()
	        {
	            public void changed(ObservableValue<? extends State> ov, State oldState, State newState)
	            {
	                if (newState == State.SUCCEEDED)
	                {
	                    //stage.setTitle(webEngine.getLocation());
	                    alert.setTitle(webEngine.getTitle());
	                }
	            }
	        });        
        alert.getDialogPane().setContent(webView);
        alert.showAndWait();
    }

    @FXML
    private void openInstagram(MouseEvent event) {
        Label x = (Label) event.getSource();
        User uw = userService.getUserById(Integer.parseInt(x.getId()));        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Messages envoyés !");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setResizable(true);
        
        WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        webEngine.load(uw.getInstagram());
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>()
	        {
	            public void changed(ObservableValue<? extends State> ov, State oldState, State newState)
	            {
	                if (newState == State.SUCCEEDED)
	                {
	                    //stage.setTitle(webEngine.getLocation());
	                    alert.setTitle(webEngine.getTitle());
	                }
	            }
	        });        
        alert.getDialogPane().setContent(webView);
        alert.showAndWait();
    }
    
}
