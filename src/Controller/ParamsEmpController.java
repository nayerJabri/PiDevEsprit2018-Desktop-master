/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.Controller;
import Entity.Emploi;
import Entity.User;
import IService.IEmploiService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.text.Font;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Achrafoun
 */
public class ParamsEmpController extends Controller implements Initializable {
    
    private IEmploiService emploiService = this.getService().getEmploiService();

    private static User connectedUser;
    public static User getConnectedUser() {
        return connectedUser;
    }

    public static void setConnectedUser(User connectedUser) {
        ParamsEmpController.connectedUser = connectedUser;
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
    private VBox vboxEmploi;
    @FXML
    private Button ajouterEmploi;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomp.setText(connectedUser.getNom()+" "+connectedUser.getPrenom()); 
        photop.setImage(new Image(getClass().getResource("../Images/"+connectedUser.getImage()).toExternalForm()));
        //------------------------
        List<Emploi> scolarites = emploiService.getAllEmploisByUser(connectedUser);
        vboxEmploi.getChildren().clear();
        for(Emploi s :scolarites)
        {
            vboxEmploi.getChildren().add(emploiItem(s));
        }
    }
    
    private HBox emploiItem(Emploi emploi)
    {
        Font prefFont = new Font("System", 12);
        HBox hbox = new HBox();
        hbox.setPrefSize(739, 100);
        hbox.setStyle("-fx-border-color: black;");
        
        AnchorPane anchor = new AnchorPane();
        anchor.setPrefSize(351, 100);        
                
        ImageView deleteImage = new ImageView(getClass().getResource("../Images/delete.png").toExternalForm());
        deleteImage.setId(emploi.getId().toString());
        deleteImage.setOnMouseClicked(this::supprimerEmploi);
        deleteImage.setFitWidth(24);
        deleteImage.setFitHeight(24);
        deleteImage.setPickOnBounds(true);
        deleteImage.setPreserveRatio(true);
        deleteImage.setLayoutX(296);
        deleteImage.setLayoutY(63);
        
        ImageView editImage = new ImageView(getClass().getResource("../Images/edit1.png").toExternalForm());
        editImage.setId(emploi.getId().toString());
        editImage.setOnMouseClicked(this::modifierEmploi);
        editImage.setFitWidth(24);
        editImage.setFitHeight(24);
        editImage.setPickOnBounds(true);
        editImage.setPreserveRatio(true);
        editImage.setLayoutX(296);
        editImage.setLayoutY(22);
        
        Label l1 = new Label("Titre de l'emploi");
        l1.setFont(prefFont);
        l1.setUnderline(true);
        l1.setLayoutX(10);
        l1.setLayoutY(5);
        
        Label l2 = new Label("Année de début");
        l2.setFont(prefFont);
        l2.setUnderline(true);
        l2.setLayoutX(10);
        l2.setLayoutY(48);
        
        Label l3 = new Label("Année de fin");
        l3.setFont(prefFont);
        l3.setUnderline(true);
        l3.setLayoutX(112);
        l3.setLayoutY(48);
        
        Label empName = new Label(emploi.getContenu());
        empName.setFont(prefFont);
        empName.setLayoutX(10);
        empName.setLayoutY(25);
        
        Label empDD = new Label(emploi.getDateDebut());
        empDD.setFont(prefFont);
        empDD.setLayoutX(10);
        empDD.setLayoutY(69);
        
        Label empDF = new Label(emploi.getDateFin());
        empDF.setFont(prefFont);
        empDF.setLayoutX(112);
        empDF.setLayoutY(69);
        
        anchor.getChildren().addAll(l1,l2,l3,empName,empDD,empDF,editImage,deleteImage);        
        hbox.getChildren().addAll(anchor);       
        return hbox;
    }

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
        ParamsCentreController.setConnectedUser(connectedUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsCentre.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            ParamsCentreController paramsCentreController = loader.getController();
            paramsCentreController.setConnectedUser(connectedUser);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    }

    @FXML
    private void ajouterEmploiAction(ActionEvent event) {
        Emploi s = new Emploi();
        s.setIdUser(connectedUser);
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
        grid.setHgap(30);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField contenu = new TextField();
        contenu.setPrefSize(50, 10);
        TextField dd = new TextField();
        contenu.setPrefSize(30, 10);
        TextField df = new TextField();
        contenu.setPrefSize(30, 10);

        grid.add(new Label("Emploi: "), 0, 0);
        grid.add(contenu, 1, 0);
        
        grid.add(new Label("Année de début: "), 0, 1);
        grid.add(dd, 1, 1);
        
        grid.add(new Label("Année de fin: "), 0, 2);
        grid.add(df, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){       
            //System.out.println(contenuModifie.getText());
            s.setContenu(contenu.getText());
            s.setDateDebut(dd.getText());
            s.setDateFin(df.getText());
            emploiService.ajouterEmploi(s,connectedUser);
            //----
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsEmp.fxml"));
            try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(ParamsEmpController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void supprimerEmploi(Event event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        //alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Voulez vous supprimer cet element ?");
        alert.setHeaderText(null);
        alert.setGraphic(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ImageView x = (ImageView) event.getSource();
            Emploi s = new Emploi();
            s.setId(Integer.parseInt(x.getId()));
            emploiService.supprimerEmploi(s);
            //---
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsEmp.fxml"));
            try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(ParamsEmpController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
    
    @FXML
    private void modifierEmploi(Event event) {
        ImageView x = (ImageView) event.getSource();
        Emploi s = new Emploi();
        s = emploiService.getEmploiById(Integer.parseInt(x.getId()));                
        //------------------
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Modifier emploi");
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

        TextField contenuModifie = new TextField(s.getContenu());
        TextField ddModifie = new TextField(s.getDateDebut());
        TextField dfModifie = new TextField(s.getDateFin());

        grid.add(new Label("Emploi: "), 0, 0);
        grid.add(contenuModifie, 1, 0);
        
        grid.add(new Label("Année de début: "), 0, 1);
        grid.add(ddModifie, 1, 1);
        
        grid.add(new Label("Année de fin: "), 0, 2);
        grid.add(dfModifie, 1, 2);

        dialog.getDialogPane().setContent(grid);
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){       
            s.setContenu(contenuModifie.getText());
            s.setDateDebut(ddModifie.getText());
            s.setDateFin(dfModifie.getText());
            emploiService.modifierEmploi(s);
            //----
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsEmp.fxml"));
            try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(ParamsEducController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
