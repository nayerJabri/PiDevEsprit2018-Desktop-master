/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import APIs.ArtistAPI;
import APIs.AutoCompleteAPI;
import APIs.BookAPI;
import APIs.LoisirAPI;
import APIs.TheMovieDBAPI;
import Core.Controller;
import Entity.CentreInteret;
import Entity.Loisir;
import Entity.Publication;
import Entity.User;
import IService.ICentreInteretService;
import IService.ILoisirService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Achrafoun
 */
public class ParamsCentreController extends Controller implements Initializable {
    
    private ICentreInteretService centreInteretService = this.getService().getCentreInteretService();
    private ILoisirService loisirService = this.getService().getLoisirService();
    
    private static User connectedUser;
    public static User getConnectedUser() {
        return connectedUser;
    }

    public static void setConnectedUser(User connectedUser) {
        ParamsCentreController.connectedUser = connectedUser;
    }

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
    private Button paramsProfil;
    @FXML
    private Button paramsCentre;
    @FXML
    private Button paramsEduc;
    @FXML
    private Button paramsEmp;
    @FXML
    private VBox vboxFilm;
    @FXML
    private VBox vboxSerie;
    @FXML
    private VBox vboxArtist;
    @FXML
    private VBox vboxLivre;
    @FXML
    private VBox vboxLoisir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomp.setText(connectedUser.getNom()+" "+connectedUser.getPrenom()); 
        photop.setImage(new Image(getClass().getResource("../Images/"+connectedUser.getImage()).toExternalForm()));
        //------------------------
        List<CentreInteret> films = centreInteretService.getFilmsByUser(connectedUser);
        vboxFilm.getChildren().clear();
        for(CentreInteret f :films)
        {
            vboxFilm.getChildren().add(centreItem(f));
        }
        //--------
        List<CentreInteret> series = centreInteretService.getSeriesByUser(connectedUser);
        vboxSerie.getChildren().clear();
        for(CentreInteret f :series)
        {
            vboxSerie.getChildren().add(centreItem(f));
        }
        //--------
        List<CentreInteret> artists = centreInteretService.getArtistsByUser(connectedUser);
        vboxArtist.getChildren().clear();
        for(CentreInteret f :artists)
        {
            vboxArtist.getChildren().add(centreItem(f));
        }
        //--------
        List<CentreInteret> livres = centreInteretService.getLivresByUser(connectedUser);
        vboxLivre.getChildren().clear();
        for(CentreInteret f :livres)
        {
            vboxLivre.getChildren().add(centreItem(f));
        }
        //-------
        List<Loisir> loisirs = loisirService.getAllLoisirsByUser(connectedUser);
        vboxLoisir.getChildren().clear();
        for(Loisir f :loisirs)
        {
            vboxLoisir.getChildren().add(loisirItem(f));
        }
    }
    
    //----------------------
    private HBox centreItem(CentreInteret centre)
    {
        Font prefFont = new Font("System", 12);
        HBox hbox = new HBox();
        hbox.setPrefSize(330, 17);
        hbox.setStyle("-fx-border-color: black;");
                
        ImageView deleteImage = new ImageView(getClass().getResource("../Images/delete.png").toExternalForm());
        deleteImage.setId(centre.getId().toString());
        deleteImage.setOnMouseClicked(this::supprimerCentre);
        deleteImage.setFitWidth(17);
        deleteImage.setFitHeight(17);
        deleteImage.setPickOnBounds(true);
        deleteImage.setPreserveRatio(true);
        HBox.setMargin(deleteImage, new Insets(0, 0, 0, 30));
        
        Label centreName = new Label(centre.getContenu());
        centreName.setFont(prefFont);
        centreName.setPrefWidth(300);
        HBox.setMargin(centreName, new Insets(0, 0, 0, 1));                
        
        hbox.getChildren().addAll(centreName, deleteImage);
        //VBox.setMargin(hbox, new Insets(0, 0, 0, 0));
        
        return hbox;
    }
    
    private HBox loisirItem(Loisir loisir)
    {
        Font prefFont = new Font("System", 12);
        HBox hbox = new HBox();
        hbox.setPrefSize(330, 17);
        hbox.setStyle("-fx-border-color: black;");
                
        ImageView deleteImage = new ImageView(getClass().getResource("../Images/delete.png").toExternalForm());
        deleteImage.setId(loisir.getId().toString());
        deleteImage.setOnMouseClicked(this::supprimerLoisir);
        deleteImage.setFitWidth(17);
        deleteImage.setFitHeight(17);
        deleteImage.setPickOnBounds(true);
        deleteImage.setPreserveRatio(true);
        HBox.setMargin(deleteImage, new Insets(0, 0, 0, 30));
        
        Label centreName = new Label(loisir.getContenu());
        centreName.setFont(prefFont);
        centreName.setPrefWidth(300);
        HBox.setMargin(centreName, new Insets(0, 0, 0, 1));                
        
        hbox.getChildren().addAll(centreName, deleteImage);
        //VBox.setMargin(hbox, new Insets(0, 0, 0, 0));
        
        return hbox;
    }
    //----------------------

    @FXML
    private void journalAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Journal.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            ProfilController profilController = loader.getController();
            profilController.setConnectedUser(connectedUser);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    private void paramsCentreAction(ActionEvent event) {
    }

    @FXML
    private void paramsEducAction(ActionEvent event) {
        ParamsEducController.setConnectedUser(connectedUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsEduc.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            ParamsEducController paramsEducController = loader.getController();
            paramsEducController.setConnectedUser(connectedUser);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void paramsEmpAction(ActionEvent event) {
        ParamsEmpController.setConnectedUser(connectedUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsEmp.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            ParamsEmpController paramsEmpController = loader.getController();
            paramsEmpController.setConnectedUser(connectedUser);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ajouterFilmAction(ActionEvent event) {
        CentreInteret c = new CentreInteret();
        c.setIdUser(connectedUser);
        c.setType("film");
        //------------------
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Ajouter un Film");
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
        
        ComboBox<String> filmsCB = new ComboBox<>();
        filmsCB.setEditable(true);
        filmsCB.setPrefWidth(350);
        
        new AutoCompleteAPI(filmsCB,new TheMovieDBAPI(TheMovieDBAPI.Movie));

        grid.add(new Label("Choisir un film: "), 0, 0);
        grid.add(filmsCB, 1, 0);

        dialog.getDialogPane().setContent(grid);

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){       
            //System.out.println(contenuModifie.getText());
            c.setContenu(filmsCB.getSelectionModel().getSelectedItem());
            centreInteretService.ajouterCentreInteret(c,connectedUser);
            //----
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsCentre.fxml"));
            try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void ajouterSerieAction(ActionEvent event) {
        CentreInteret c = new CentreInteret();        
        c.setIdUser(connectedUser);
        c.setType("serie");
        //------------------
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Ajouter une Série");
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
        
        ComboBox<String> seriesCB = new ComboBox<>();
        seriesCB.setEditable(true);
        seriesCB.setPrefWidth(350);
        
        new AutoCompleteAPI(seriesCB,new TheMovieDBAPI(TheMovieDBAPI.Serie));

        grid.add(new Label("Choisir une série: "), 0, 0);
        grid.add(seriesCB, 1, 0);

        dialog.getDialogPane().setContent(grid);

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){       
            //System.out.println(contenuModifie.getText());
            c.setContenu(seriesCB.getSelectionModel().getSelectedItem());
            centreInteretService.ajouterCentreInteret(c,connectedUser);
            //----
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsCentre.fxml"));
            try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void ajouterLivreAction(ActionEvent event) {
        CentreInteret c = new CentreInteret();        
        c.setIdUser(connectedUser);
        c.setType("livre");
        //------------------
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Ajouter un Livre");
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

        ComboBox<String> livresCB = new ComboBox<>();
        livresCB.setEditable(true);
        livresCB.setPrefWidth(350);

        new AutoCompleteAPI(livresCB,BookAPI.getInstance());

        grid.add(new Label("Choisir un livre: "), 0, 0);
        grid.add(livresCB, 1, 0);

        dialog.getDialogPane().setContent(grid);

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){       
            //System.out.println(contenuModifie.getText());
            c.setContenu(livresCB.getSelectionModel().getSelectedItem());
            centreInteretService.ajouterCentreInteret(c,connectedUser);
            //----
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsCentre.fxml"));
            try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void ajouterArtistAction(ActionEvent event) {
        CentreInteret c = new CentreInteret();        
        c.setIdUser(connectedUser);
        c.setType("artist");
        //------------------
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Ajouter un Artist");
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
        
        ComboBox<String> artistsCB = new ComboBox<>();
        artistsCB.setEditable(true);
        artistsCB.setPrefWidth(350);

        new AutoCompleteAPI(artistsCB,ArtistAPI.getInstance());

        grid.add(new Label("Choisir un artist: "), 0, 0);
        grid.add(artistsCB, 1, 0);

        dialog.getDialogPane().setContent(grid);

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){       
            //System.out.println(contenuModifie.getText());
            c.setContenu(artistsCB.getSelectionModel().getSelectedItem());
            centreInteretService.ajouterCentreInteret(c,connectedUser);
            //----
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsCentre.fxml"));
            try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void ajouterLoisirAction(ActionEvent event) {
        Loisir l = new Loisir();        
        l.setIdUser(connectedUser);
        //------------------
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Ajouter une activité");
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
        
        ComboBox<String> loisirsCB = new ComboBox<>();
        loisirsCB.setEditable(true);
        loisirsCB.setPrefWidth(350);
        
        LoisirAPI loisirApi = LoisirAPI.getInstance();
        ObservableList<String> listLoisirs = FXCollections.observableArrayList(loisirApi.fetchResult());        
        loisirsCB.setItems(listLoisirs);

        grid.add(new Label("Choisir une activité: "), 0, 0);
        grid.add(loisirsCB, 1, 0);

        dialog.getDialogPane().setContent(grid);

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){       
            //System.out.println(contenuModifie.getText());
            l.setContenu(loisirsCB.getSelectionModel().getSelectedItem());
            loisirService.ajouterLoisir(l,connectedUser.getId());
            //----
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsCentre.fxml"));
            try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void supprimerCentre(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        //alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Voulez vous supprimer cet element ?");
        alert.setHeaderText(null);
        alert.setGraphic(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ImageView x = (ImageView) event.getSource();
            CentreInteret c = new CentreInteret();
            c.setId(Integer.parseInt(x.getId()));
            centreInteretService.supprimerCentreInteret(c);
            //---
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsCentre.fxml"));
            try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(ParamsCentreController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
    
    @FXML
    private void supprimerLoisir(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        //alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Voulez vous supprimer cet element ?");
        alert.setHeaderText(null);
        alert.setGraphic(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ImageView x = (ImageView) event.getSource();
            Loisir l = new Loisir();
            l.setId(Integer.parseInt(x.getId()));
            loisirService.supprimerLoisir(l);
            //---
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsCentre.fxml"));
            try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(ParamsCentreController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
    
}
