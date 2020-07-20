/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.Controller;
import Entity.CentreInteret;
import Entity.Emploi;
import Entity.Loisir;
import Entity.Scolarite;
import Entity.Signaler;
import Entity.User;
import IService.ICentreInteretService;
import IService.IEmploiService;
import IService.ILoisirService;
import IService.IScolariteService;
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
import javafx.scene.control.TextArea;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Achrafoun
 */
public class AutreAproposController extends Controller implements Initializable {
    
    private ICentreInteretService centreInteretService = this.getService().getCentreInteretService();
    private ILoisirService loisirService = this.getService().getLoisirService();
    private IScolariteService scolariteService = this.getService().getScolariteService();
    private IEmploiService emploiService = this.getService().getEmploiService();
    private ISignalerService signalerService = this.getService().getSignalerService();
    
    private static User autreUser;
    public static User getAutreUser() {
        return autreUser;
    }

    public static void setAutreUser(User autreUser) {
        AutreAproposController.autreUser = autreUser;
    }

    @FXML
    private Label nomp;
    @FXML
    private ImageView photop;
    @FXML
    private Button autreJournalButton;
    @FXML
    private Button autreAlbumButton;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label dnLabel;
    @FXML
    private Label lnLabel;
    @FXML
    private Label sexeLabel;
    @FXML
    private Label vitEnLabel;
    @FXML
    private Label emploiLabel;
    @FXML
    private Label derConLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label telLabel;
    @FXML
    private Label religionLabel;
    @FXML
    private Label loisirsLabel;
    @FXML
    private Label seriesLabel;
    @FXML
    private Label filmsLabel;
    @FXML
    private Label artistsLabel;
    @FXML
    private Label livresLabel;
    @FXML
    private VBox vboxScolEmp;
    @FXML
    private VBox vboxScol;
    @FXML
    private VBox vboxEmp;
    @FXML
    private Button signalerProfil;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomp.setText(autreUser.getNom()+" "+autreUser.getPrenom());
        //--------
        descriptionLabel.setText(autreUser.getApropos());
        //-------------        
        photop.setImage(new Image(getClass().getResource("../Images/"+autreUser.getImage()).toExternalForm()));
        //-------------
        dnLabel.setText(autreUser.getDateNaissance().toString());
        //-------------
        if(autreUser.getGenre().equals("M"))
            sexeLabel.setText("Homme");
        else
            sexeLabel.setText("Femme");
        //-------------
        vitEnLabel.setText(autreUser.getVille());
        //-------------
        lnLabel.setText(autreUser.getPlaceNaiss());        
        //-------------
        derConLabel.setText(autreUser.getLastLogin().toString());
        //-------------
        emploiLabel.setText(autreUser.getOccupation());
        //-------------
        emailLabel.setText(autreUser.getEmail());
        //-------------
        telLabel.setText(autreUser.getTel());
        //-------------
        if(autreUser.getReligion().equals("I"))
            religionLabel.setText("Islam");
        if(autreUser.getReligion().equals("C"))
            religionLabel.setText("Christianisme");
        if(autreUser.getReligion().equals("J"))
            religionLabel.setText("Judaisme");
        if(autreUser.getReligion().equals("B"))
            religionLabel.setText("Budhisme");
        if(autreUser.getReligion().equals("H"))
            religionLabel.setText("Hindouisme");
        
        //-------------
        String livres="";
        List<CentreInteret> listLivres = centreInteretService.getLivresByUser(autreUser);
        for(CentreInteret c:listLivres)
        {
            livres+=c.getContenu()+",";
        }
        livresLabel.setText(livres);
        //-------------
        String artists="";
        List<CentreInteret> listArtists = centreInteretService.getArtistsByUser(autreUser);
        for(CentreInteret c:listArtists)
        {
            artists+=c.getContenu()+",";
        }
        artistsLabel.setText(artists);
        //-------------
        String films="";
        List<CentreInteret> listFilms = centreInteretService.getFilmsByUser(autreUser);
        for(CentreInteret c:listFilms)
        {
            films+=c.getContenu()+",";
        }
        filmsLabel.setText(films);
        //-------------
        String series="";
        List<CentreInteret> listSeries = centreInteretService.getSeriesByUser(autreUser);
        for(CentreInteret c:listSeries)
        {
            series+=c.getContenu()+",";
        }
        seriesLabel.setText(series);
        //-------------
        String loisirs="";
        List<Loisir> listLoisirs = loisirService.getAllLoisirsByUser(autreUser);
        for(Loisir l:listLoisirs)
        {
            loisirs+=l.getContenu()+",";
        }
        loisirsLabel.setText(loisirs);
        //-------------
        List<Scolarite> scolarites = scolariteService.getAllScolariteByUser(autreUser);
        vboxScol.getChildren().clear();
        for(Scolarite s :scolarites)
        {
            vboxScol.getChildren().add(scolItem(s));
        }
        //-------------
        List<Emploi> emplois = emploiService.getAllEmploisByUser(autreUser);
        vboxEmp.getChildren().clear();
        for(Emploi e :emplois)
        {
            vboxEmp.getChildren().add(empItem(e));
        }
        //-------------
    }
    
    private AnchorPane scolItem(Scolarite scolarite)
    {
        Font prefFont = new Font("System", 12);
        AnchorPane anchor = new AnchorPane();
        anchor.setPrefSize(200, 200);                
        
        Label scolName = new Label(scolarite.getContenu());
        scolName.setFont(prefFont);
        scolName.setLayoutX(4);
        Label scolDate = new Label(scolarite.getDateDebut()+"-"+scolarite.getDateFin());
        scolDate.setFont(prefFont);
        scolDate.setLayoutX(4);
        scolDate.setLayoutY(21);
        
        VBox.setMargin(anchor, new Insets(0, 0, 10, 0));
        anchor.getChildren().addAll(scolName, scolDate);
        
        return anchor;
    }
    
    private AnchorPane empItem(Emploi emploi)
    {
        Font prefFont = new Font("System", 12);
        AnchorPane anchor = new AnchorPane();
        anchor.setPrefSize(200, 200);                
        
        Label empName = new Label(emploi.getContenu());
        empName.setFont(prefFont);
        empName.setLayoutX(4);
        Label empDate = new Label(emploi.getDateDebut()+"-"+emploi.getDateFin());
        empDate.setFont(prefFont);
        empDate.setLayoutX(4);
        empDate.setLayoutY(21);
        
        VBox.setMargin(anchor, new Insets(0, 0, 10, 0));
        anchor.getChildren().addAll(empName, empDate);
        
        return anchor;
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
