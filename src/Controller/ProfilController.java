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
import Entity.User;
import IService.IAlbumService;
import IService.ICentreInteretService;
import IService.IPublicationService;
import IService.IUserService;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.List;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

/**
 *
 * @author Achrafoun
 */
public class ProfilController extends Controller implements Initializable {

    private IUserService userService = this.getService().getUserService();
    private IPublicationService publicationService = this.getService().getPublicationService();
    private ICentreInteretService centreInteretService = this.getService().getCentreInteretService();
    private IAlbumService albumService = this.getService().getAlbumService();    
    
    @FXML
    private Label nomp;
    @FXML
    private ImageView photop;
    @FXML
    private Button journalButton;
    @FXML
    private Button aproposButton;
    @FXML
    private Button albumButton;
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
    private ImageView photoSuggestion;
    @FXML
    private Label nomSuggestion;
    @FXML
    private Label dnSuggestion;
    @FXML
    private TextArea statusContent;
    @FXML
    private Button statusPartage;
    @FXML
    private MenuItem modifierStatus;
    @FXML
    private MenuItem supprimerStatus;
    @FXML
    private HBox hboxSuggestion;
    @FXML
    private VBox vboxStatus;
    @FXML
    private VBox vboxSuggestion;

    private User connectedUser;
    @FXML
    private Button paramsProfil;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //connectedUser = userService.getUserById(1);
        connectedUser = this.getUser();
        nomp.setText(connectedUser.getNom()+" "+connectedUser.getPrenom());
        //--------
        descriptionLabel.setText(connectedUser.getApropos());
        //-------------        
        photop.setImage(new Image(getClass().getResource("../Images/"+connectedUser.getImage()).toExternalForm()));
        //-------------
        String series="";
        List<CentreInteret> listSeries = centreInteretService.getSeriesByUser(connectedUser);
        for(CentreInteret c:listSeries)
        {
            series+=c.getContenu()+",";
        }
        serieLabel.setText(series);
        //--------
        String films="";
        List<CentreInteret> listFilms = centreInteretService.getFilmsByUser(connectedUser);
        for(CentreInteret c:listFilms)
        {
            films+=c.getContenu()+",";
        }
        filmLabel.setText(films);
        //--------
        String livres="";
        List<CentreInteret> listLivres = centreInteretService.getLivresByUser(connectedUser);
        for(CentreInteret c:listLivres)
        {
            livres+=c.getContenu()+",";
        }
        livreLabel.setText(livres);
        //--------
        String artists="";
        List<CentreInteret> listArtists = centreInteretService.getArtistsByUser(connectedUser);
        for(CentreInteret c:listArtists)
        {
            artists+=c.getContenu()+",";
        }
        artistLabel.setText(artists);
        //--------
        List<Album> lastPhotos = albumService.getLastPhotosByUser(connectedUser);
        
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
        List<Publication> pulications = publicationService.getPublicationByUser(connectedUser);
        vboxStatus.getChildren().clear();
        for(Publication p :pulications)
        {
            vboxStatus.getChildren().add(publicationItem(connectedUser, p));
        }
        //--------------------
        List<User> suggestions = userService.getSuggestionUsers(connectedUser);
        vboxSuggestion.getChildren().clear();
        for(User u :suggestions)
        {
            vboxSuggestion.getChildren().add(suggestionItem(u));
        }
        //--------------
        aproposButton.setId(connectedUser.getId().toString());
        //---------------
        
    }

    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
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
        userName.setLayoutX(62);
        userName.setLayoutY(3);
        userName.setFont(prefFont);
        
        SimpleDateFormat formater = new SimpleDateFormat("EEEE, dd-MM-yyyy HH:mm");        
        Label pubDate = new Label(formater.format(publication.getDatePublication()));
        pubDate.setLayoutX(63);
        pubDate.setLayoutY(20);
        pubDate.setFont(prefFont);
        Text contenu = new Text(publication.getContenu());
        contenu.setLayoutX(13);
        contenu.setLayoutY(70);
        contenu.setStrokeType(StrokeType.OUTSIDE);
        contenu.setStrokeWidth(0);
        contenu.setWrappingWidth(350);
        MenuButton menuButton = new MenuButton("Action");
        menuButton.setLayoutX(358);
        menuButton.setLayoutY(9);
        menuButton.setMnemonicParsing(false);
        MenuItem updateItem = new MenuItem("Modifier");
        updateItem.setMnemonicParsing(false);
        
        updateItem.setId(publication.getId().toString());        
        updateItem.setOnAction(this::modifierPublicationAction);
        
        
        MenuItem deleteItem = new MenuItem("Supprimer");
        deleteItem.setMnemonicParsing(false);
        
        deleteItem.setId(publication.getId().toString());        
        deleteItem.setOnAction(this::supprimerPublicationAction);
        
        menuButton.getItems().addAll(updateItem,deleteItem);
        anchorPane.getChildren().addAll(userImage,userName,pubDate,contenu,menuButton);
        VBox.setMargin(anchorPane, new Insets(0, 0, 30, 0));
        return anchorPane;
    }
    
    private HBox suggestionItem(User user)
    {
        Font prefFont = new Font("System", 12);
        HBox hbox = new HBox();
        hbox.setPrefSize(239, 48);
        
        ImageView userImage = new ImageView(getClass().getResource("../Images/"+user.getImage()).toExternalForm());
        userImage.setFitWidth(60);
        userImage.setFitHeight(50);
        userImage.setPickOnBounds(true);
        userImage.setPreserveRatio(true);
        
        VBox vbx = new VBox();
        vbx.setPrefSize(179, 15);
                
        Label userName = new Label(user.getPrenom()+" "+user.getNom());
        userName.setFont(prefFont);
        VBox.setMargin(userName, new Insets(5, 0, 0, 5));
        
        Label dn = new Label(user.getDateNaissance().toString());
        dn.setFont(prefFont);
        VBox.setMargin(dn, new Insets(5, 0, 0, 5));
        
        vbx.getChildren().addAll(userName, dn);
        
        hbox.getChildren().addAll(userImage, vbx);
        VBox.setMargin(hbox, new Insets(5, 0, 5, 0));
        
        hbox.setId(user.getId().toString());
        hbox.setOnMouseClicked(this::autreJournal);
        
        return hbox;
    }
    
    @FXML
    private void aProposAction(ActionEvent event) {
        AProposController.setaProposUser(connectedUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/aPropos.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            AProposController aproposController = loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void albumAction(ActionEvent event) {
        AlbumController.setConnectedUser(connectedUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/album.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            AlbumController albumController = loader.getController();
            albumController.setConnectedUser(connectedUser);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public static void main(String[] Args)
    {
        
    }


    @FXML
    private void paramsProfilAction(ActionEvent event) {
        ParamsProfilController.setConnectedUser(connectedUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsProfil.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            ParamsProfilController paramsProfilController = loader.getController();
            paramsProfilController.setConnectedUser(connectedUser);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void partagerAction(ActionEvent event) {
        Publication p = new Publication(statusContent.getText(), connectedUser);
        publicationService.ajouterPublication(p);
        //---
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/journal.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void supprimerPublicationAction(ActionEvent event) {
        MenuItem x = (MenuItem) event.getSource();
        Publication p = new Publication();
        p.setId(Integer.parseInt(x.getId()));
        publicationService.supprimerPublication(p);
        //-------
        try {
            //---
            sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(AlbumController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //---
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/journal.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void modifierPublicationAction(ActionEvent event) {
        MenuItem x = (MenuItem) event.getSource();
        Publication p = new Publication();        
        p = publicationService.getPublicationById(Integer.parseInt(x.getId()));
        p.setIdUser(connectedUser);
        //System.out.println(p);
        //------------------
        TextInputDialog dialog = new TextInputDialog(p.getContenu());
        dialog.setTitle("Modifier publication");
        //dialog.setContentText("Please enter your name:");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.getDialogPane().setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(2), new Insets(2))));
        dialog.getDialogPane().setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        //dialog.getDialogPane().setStyle("-fx-border-color: black");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextArea contenuModifie = new TextArea();
        contenuModifie.setPrefWidth(300);
        contenuModifie.setPrefHeight(100);
        contenuModifie.setText(p.getContenu());

        grid.add(new Label("Contenu: "), 0, 0);
        grid.add(contenuModifie, 1, 0);

        dialog.getDialogPane().setContent(grid);
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){       
            //System.out.println(contenuModifie.getText());
            p.setContenu(contenuModifie.getText());
            publicationService.modifierPublication(p);
            //----
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/journal.fxml"));
            try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void autreJournal(Event event) {
        HBox x = (HBox) event.getSource();        
        User au = new User();
        System.out.println(Integer.parseInt(x.getId()));
        au = userService.getUserById(Integer.parseInt(x.getId()));        
        AutreJournalController.setAutreUser(au);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/autreJournal.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            AutreJournalController autreJournalController = loader.getController();
            autreJournalController.setAutreUser(au);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
