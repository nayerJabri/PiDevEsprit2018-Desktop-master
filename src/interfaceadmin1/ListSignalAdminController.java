/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceadmin1;

import Controller.ParamsEducController;
import Core.Controller;
import static Core.Controller.holderPane;
import Entity.Message;
import Entity.Signaler;
import Entity.User;
import IService.IBloquerService;
import IService.IMessageService;
import IService.ISignalerService;
import IService.IUserService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Achrafoun
 */
public class ListSignalAdminController extends Controller implements Initializable {
    private IUserService userService = this.getService().getUserService();
    private IMessageService messageService = this.getService().getMessageService();
    private ISignalerService signalerService = this.getService().getSignalerService();
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
    private Label nbSig;
    @FXML
    private Button affPub;
    @FXML
    private Button block;
    @FXML
    private Button affCauses;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        HashMap<User,List<Signaler>> map = signalerService.getSignaledUsers();
        vboxRow.getChildren().clear();
        for(User user:map.keySet())
        {
            vboxRow.getChildren().add(hRowItem(user,map.get(user).size()));
        }
        
    }
    
    public HBox hRowItem(User u,int size){
        Font prefFont = new Font("System", 12);
        HBox hbox = new HBox();
        hbox.setPrefSize(1097, 38);
        hbox.setStyle("-fx-border-color: black;");
        
        Label usernameL = new Label(u.getUsername());
        usernameL.setFont(prefFont);
        HBox.setMargin(usernameL, new Insets(10, 0, 0, 5));
        
        Label nomL = new Label(u.getNom());
        nomL.setFont(prefFont);
        HBox.setMargin(nomL, new Insets(10, 0, 0, 100));
        
        Label prenomL = new Label(u.getPrenom());
        prenomL.setFont(prefFont);
        HBox.setMargin(prenomL, new Insets(10, 0, 0, 85));
        
        Label telL = new Label(u.getTel());
        telL.setFont(prefFont);
        HBox.setMargin(telL, new Insets(10, 0, 0, 85));
        
        Label mailL = new Label(u.getEmail());
        mailL.setFont(prefFont);
        HBox.setMargin(mailL, new Insets(10, 0, 0, 50));
        
        Label countl = new Label(String.valueOf(size));
        countl.setFont(prefFont);
        HBox.setMargin(countl, new Insets(10, 0, 0, 60));
        
        Button affCause = new Button("Afficher Causes");
        affCause.setMnemonicParsing(false);
        HBox.setMargin(affCause, new Insets(7, 0, 0, 70));
        affCause.setId(u.getId().toString());
        affCause.setOnAction(this::afficherCausesAction);
        
        Button affMsg = new Button("Afficher Msgs");
        affMsg.setMnemonicParsing(false);
        HBox.setMargin(affMsg, new Insets(7, 0, 0, 70));
        affMsg.setId(u.getId().toString());
        affMsg.setOnAction(this::afficherPubAction);
        
        Button bloquer = new Button("Block");
        bloquer.setMnemonicParsing(false);
        HBox.setMargin(bloquer, new Insets(7, 0, 0, 70));
        bloquer.setId(u.getId().toString());
        bloquer.setOnAction(this::bloquerAction);
        
        hbox.getChildren().addAll(usernameL,nomL,prenomL,telL,mailL,countl,affCause,affMsg,bloquer);
        
        
        return hbox;
    }

    @FXML
    private void afficherPubAction(ActionEvent event) {
        Button x = (Button) event.getSource();
        User u = new User();
        u.setId(Integer.parseInt(x.getId()));
        //------------------
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Messages envoyés !");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setResizable(true);
        
        List<Message> lst = messageService.getMessageSenderByUser(u.getId());
        ObservableList<Message> MsgList = FXCollections.observableArrayList();
        MsgList.addAll(lst);

        ListView<Message> listViewOfMessages = new ListView<>(MsgList);
        listViewOfMessages.setCellFactory(param -> new ListCell<Message>() {
            @Override
            protected void updateItem(Message item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getText()== null) {
                    setText(null);
                } else {
                    setText(item.getText());
                }
            }
        });
        
        alert.getDialogPane().setContent(listViewOfMessages);

        alert.showAndWait();
    }

    @FXML
    private void bloquerAction(ActionEvent event) {
        Button x = (Button) event.getSource();
        User u = new User();
        u.setId(Integer.parseInt(x.getId()));
        bloquerService.bloquerUser(u);
        //-------
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View_Admin/ListSignalAdmin.fxml"));
            try {
                holderPane.getChildren().clear();
                holderPane.getChildren().add(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(ListBlockAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void afficherCausesAction(ActionEvent event) {
        Button x = (Button) event.getSource();
        User u = new User();
        u.setId(Integer.parseInt(x.getId()));
        //------------------
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Causes de Signal !");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setResizable(true);
        
        List<Signaler> lst = signalerService.getAllSignalsByUser(u);
        ObservableList<Signaler> SigList = FXCollections.observableArrayList();
        SigList.addAll(lst);

        ListView<Signaler> listViewOfSignals = new ListView<>(SigList);
        listViewOfSignals.setCellFactory(param -> new ListCell<Signaler>() {
            @Override
            protected void updateItem(Signaler item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getCause()== null) {
                    setText(null);
                } else {
                    setText(item.getCause());
                }
            }
        });
        
        alert.getDialogPane().setContent(listViewOfSignals);

        alert.showAndWait();
    }
    
}
