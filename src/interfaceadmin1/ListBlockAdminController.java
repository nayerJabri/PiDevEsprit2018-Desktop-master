/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceadmin1;

import Core.Controller;
import Entity.User;
import IService.IBloquerService;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Achrafoun
 */
public class ListBlockAdminController extends Controller implements Initializable {
    private IUserService userService = this.getService().getUserService();
    private IBloquerService bloquerService = this.getService().getBloquerService();
            
    @FXML
    private AnchorPane holderPane;
    @FXML
    private VBox vboxRow;
    @FXML
    private Label username;
    @FXML
    private Label nom;
    @FXML
    private Label prenom;
    @FXML
    private Label tel;
    @FXML
    private Label mail;
    @FXML
    private Button deblock;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<User> usersBloqued = userService.getBlockedUsers();
        vboxRow.getChildren().clear();
        for(User u :usersBloqued)
        {
            vboxRow.getChildren().add(hRowItem(u));
        }
    } 
    
    public HBox hRowItem(User u){
        Font prefFont = new Font("System", 12);
        HBox hbox = new HBox();
        hbox.setPrefSize(1097, 38);
        hbox.setStyle("-fx-border-color: black;");
        
        Label usernameL = new Label(u.getUsername());
        usernameL.setFont(prefFont);
        HBox.setMargin(usernameL, new Insets(10, 0, 0, 5));
        
        Label nomL = new Label(u.getNom());
        nomL.setFont(prefFont);
        HBox.setMargin(nomL, new Insets(10, 0, 0, 85));
        
        Label prenomL = new Label(u.getPrenom());
        prenomL.setFont(prefFont);
        HBox.setMargin(prenomL, new Insets(10, 0, 0, 85));
        
        Label telL = new Label(u.getTel());
        telL.setFont(prefFont);
        HBox.setMargin(telL, new Insets(10, 0, 0, 85));
        
        Label mailL = new Label(u.getEmail());
        mailL.setFont(prefFont);
        HBox.setMargin(mailL, new Insets(10, 0, 0, 50));
        
        Button deb = new Button("Débloquer");
        deb.setMnemonicParsing(false);
        HBox.setMargin(deb, new Insets(7, 0, 0, 70));
        deb.setId(u.getId().toString());
        deb.setOnAction(this::debloquerAction);
        
        hbox.getChildren().addAll(usernameL,nomL,prenomL,telL,mailL,deb);
                        
        return hbox;
    }

    @FXML
    private void debloquerAction(ActionEvent event) {
        Button x = (Button) event.getSource();
        User u = new User();
        u.setId(Integer.parseInt(x.getId()));
        bloquerService.debloquerUser(u);
        //-------
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View_Admin/ListBlockAdmin.fxml"));      
            try {
                holderPane.getChildren().clear();
                holderPane.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(ListBlockAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
}
