/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import APIs.ChatListener;
import Core.Controller;
import Entity.Relation;
import Entity.User;
import IService.IRelationService;
import IService.IUserService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author hero
 */
public class FriendListController extends Controller implements Initializable {

    private IRelationService relationService = this.getService().getRelationService();
    private IUserService userService = this.getService().getUserService();
    private List<User> friendList;
    private Timer timer;
    @FXML
    private VBox usersList;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        friendList = new ArrayList<>();
        initTimer();
    }    

    private void initTimer()
    {
        this.timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    refresh(relationService.fetchMembers(getUser()));
                });
            }
        };
        timer.schedule(timerTask,0,5000);
    }
    
    private void refresh(List<Relation> relations)
    {
        usersList.getChildren().clear();
        relations.forEach((relation) -> {
            if(!Objects.equals(relation.getAcceptor().getId(), getUser().getId()))
            {
                usersList.getChildren().add(userItem(relation.getAcceptor()));
                friendList.add(relation.getAcceptor());
            }
            else
            {
                usersList.getChildren().add(userItem(relation.getRequester()));
                friendList.add(relation.getRequester());
            }
        });
        System.gc();
    }
    
    @FXML
    private void openChatWindow(MouseEvent event) {
        ImageView image = (ImageView) event.getSource();
        User user = friendList.stream().filter((u)->{return ((User)u).getId() == Integer.parseInt(image.getId());}).findFirst().get();
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ChatWindow.fxml"));
                Parent root = loader.load();
                ChatController chatController = loader.getController();
                chatController.setChatUser(user);
                chatController.loadMessages();
                ChatListener.addController(chatController);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle(user.getNom()+" "+user.getPrenom());
                stage.setScene(scene);
                stage.setOnCloseRequest((WindowEvent windowsEvent) -> {
                ChatListener.removeController(chatController);
                });
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FriendListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void openProfile(MouseEvent event) {
        Label button = (Label) event.getSource();
        User user = userService.getUserById(Integer.valueOf(button.getId()));
        AutreJournalController.setAutreUser(user);
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource(("../View/autreJournal.fxml")));
        Controller.holderPane.getChildren().clear();
        try {
            Controller.holderPane.getChildren().add(fxmlLoader.load());
        } catch (IOException ex) {
            Logger.getLogger(RechercheProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private HBox userItem(User user)
    {
        Font prefFont = new Font("System Bold", 12);
        HBox hBox = new HBox();
        hBox.setId(user.getId().toString());
        hBox.setPrefSize(150,60);
        ImageView userImage = new ImageView(getClass().getResource("../Images/"+user.getImage()).toExternalForm());
        userImage.setId(user.getId().toString());
        userImage.setFitWidth(60);
        userImage.setFitHeight(60);
        userImage.setPickOnBounds(true);
        userImage.setPreserveRatio(true);
        userImage.setClip(new Circle(30, 30, 30));
        userImage.setOnMousePressed(this::openChatWindow);
        Label userName = new Label(user.getPrenom()+" "+user.getNom());
        userName.setId(user.getId().toString());
        userName.setPrefSize(200, 60);
        userName.setFont(prefFont);
        userName.setOnMousePressed(this::openProfile);
        HBox.setMargin(userName, new Insets(0, 0, 0, 5));
        HBox.setMargin(userImage, new Insets(5, 0, 0, 5));
        ObservableList<Node> childs = hBox.getChildren();
        childs.add(userImage);
        childs.add(userName);
        return hBox;
    }
    
}
