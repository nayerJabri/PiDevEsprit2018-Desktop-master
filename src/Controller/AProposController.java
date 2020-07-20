/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.Controller;
import Entity.CentreInteret;
import Entity.Loisir;
import Entity.Scolarite;
import Entity.Emploi;
import Entity.User;
import IService.ICentreInteretService;
import IService.IEmploiService;
import IService.ILoisirService;
import IService.IScolariteService;
import IService.IUserService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Achrafoun
 */
public class AProposController extends Controller implements Initializable {

    private static User aProposUser;
    private ICentreInteretService centreInteretService = this.getService().getCentreInteretService();
    private ILoisirService loisirService = this.getService().getLoisirService();
    private IScolariteService scolariteService = this.getService().getScolariteService();
    private IEmploiService emploiService = this.getService().getEmploiService();
    
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

    public static User getaProposUser() {
        return aProposUser;
    }

    public static void setaProposUser(User aProposUser) {
        AProposController.aProposUser = aProposUser;
    }
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
    private Button paramsProfil;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomp.setText(aProposUser.getNom()+" "+aProposUser.getPrenom());
        //--------
        descriptionLabel.setText(aProposUser.getApropos());
        //-------------        
        photop.setImage(new Image(getClass().getResource("../Images/"+aProposUser.getImage()).toExternalForm()));
        //-------------
        dnLabel.setText(aProposUser.getDateNaissance().toString());
        //-------------
        if(aProposUser.getGenre().equals("M"))
            sexeLabel.setText("Homme");
        else
            sexeLabel.setText("Femme");
        //-------------
        vitEnLabel.setText(aProposUser.getVille());
        //-------------
        lnLabel.setText(aProposUser.getPlaceNaiss());        
        //-------------
        derConLabel.setText(aProposUser.getLastLogin().toString());
        //-------------
        emploiLabel.setText(aProposUser.getOccupation());
        //-------------
        emailLabel.setText(aProposUser.getEmail());
        //-------------
        telLabel.setText(aProposUser.getTel());
        //-------------
        if(aProposUser.getReligion().equals("I"))
            religionLabel.setText("Islam");
        if(aProposUser.getReligion().equals("C"))
            religionLabel.setText("Christianisme");
        if(aProposUser.getReligion().equals("J"))
            religionLabel.setText("Judaisme");
        if(aProposUser.getReligion().equals("B"))
            religionLabel.setText("Budhisme");
        if(aProposUser.getReligion().equals("H"))
            religionLabel.setText("Hindouisme");
        
        //-------------
        String livres="";
        List<CentreInteret> listLivres = centreInteretService.getLivresByUser(aProposUser);
        for(CentreInteret c:listLivres)
        {
            livres+=c.getContenu()+",";
        }
        livresLabel.setText(livres);
        //-------------
        String artists="";
        List<CentreInteret> listArtists = centreInteretService.getArtistsByUser(aProposUser);
        for(CentreInteret c:listArtists)
        {
            artists+=c.getContenu()+",";
        }
        artistsLabel.setText(artists);
        //-------------
        String films="";
        List<CentreInteret> listFilms = centreInteretService.getFilmsByUser(aProposUser);
        for(CentreInteret c:listFilms)
        {
            films+=c.getContenu()+",";
        }
        filmsLabel.setText(films);
        //-------------
        String series="";
        List<CentreInteret> listSeries = centreInteretService.getSeriesByUser(aProposUser);
        for(CentreInteret c:listSeries)
        {
            series+=c.getContenu()+",";
        }
        seriesLabel.setText(series);
        //-------------
        String loisirs="";
        List<Loisir> listLoisirs = loisirService.getAllLoisirsByUser(aProposUser);
        for(Loisir l:listLoisirs)
        {
            loisirs+=l.getContenu()+",";
        }
        loisirsLabel.setText(loisirs);
        //-------------
        List<Scolarite> scolarites = scolariteService.getAllScolariteByUser(aProposUser);
        vboxScol.getChildren().clear();
        for(Scolarite s :scolarites)
        {
            vboxScol.getChildren().add(scolItem(s));
        }
        //-------------
        List<Emploi> emplois = emploiService.getAllEmploisByUser(aProposUser);
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
    private void aProposAction(ActionEvent event) {
    }

    @FXML
    private void albumAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/album.fxml"));
        try {
            AlbumController albumController = loader.getController();
            albumController.setConnectedUser(aProposUser);
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void journalAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Journal.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            ProfilController profilController = loader.getController();
            profilController.setConnectedUser(aProposUser);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void paramsProfilAction(ActionEvent event) {
        ParamsProfilController.setConnectedUser(aProposUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsProfil.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            ParamsProfilController paramsProfilController = loader.getController();
            paramsProfilController.setConnectedUser(aProposUser);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
