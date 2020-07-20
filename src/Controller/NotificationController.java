/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import APIs.ChatListener;
import APIs.NotificationApi;
import Core.Controller;
import Entity.Demande;
import Entity.Message;
import Entity.Notification;
import Entity.User;
import IService.IDemandeService;
import IService.IMessageService;
import IService.INotificationService;
import IService.IRelationService;
import IService.IUserService;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author hero
 */
public class NotificationController extends Controller implements Initializable {

    private final INotificationService notificationService = this.getService().getNotificationService();
    private final IUserService userService = this.getService().getUserService();
    private final IMessageService messageService = this.getService().getMessageService();
    private final IDemandeService demandeService = this.getService().getDemandeService();
    private final IRelationService relationService = this.getService().getRelationService();
    private List<Notification> seenDemandeNotifications;
    private List<Notification> unseenDemandeNotifications;
    private List<Notification> seenMessageNotifications;
    private List<Notification> unseenMessageNotifications;
    private List<Notification> seenAcceptNotifications;
    private List<Notification> unseenAcceptNotifications;
    private VBox seenDemandeNotificationsVBox;
    private VBox unseenDemandeNotificationsVBox;
    private VBox seenAcceptNotificationsVBox;
    private VBox unseenAcceptNotificationsVBox;
    private VBox seenMessageNotificationsVBox;
    private VBox unseenMessageNotificationsVBox;
    private Font prefFont;
    private String openedWindow;
    private String openedTab;
    private Stage stage;
    @FXML
    private ScrollPane scrollContainer;
    @FXML
    private JFXButton makeAsSeenButton;
    @FXML
    private JFXButton unseenButton;
    @FXML
    private JFXButton seenButton;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prefFont = new Font("System Bold", 12);
        openedTab = "Unseen";
        initLists();
        fillLists();
        initVBoxItems();
    }
    
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }
    
    public void showWindow(String window)
    {
        openedWindow = window;
        openedTab = "Unseen";
        switch(window)
        {
            case"Demande":scrollContainer.setContent(unseenDemandeNotificationsVBox);break;
            case"Message":scrollContainer.setContent(unseenMessageNotificationsVBox);break;
            case"Accept":scrollContainer.setContent(unseenAcceptNotificationsVBox);break;
        }
        if(!stage.isShowing())
            stage.show();
        stage.toFront();
    }
    
    private void initLists()
    {
        seenAcceptNotifications = new ArrayList<>();
        seenDemandeNotifications = new ArrayList<>();
        seenMessageNotifications = new ArrayList<>();
        unseenAcceptNotifications = new ArrayList<>();
        unseenDemandeNotifications = new ArrayList<>();
        unseenMessageNotifications = new ArrayList<>();
    }
    
    private void fillLists()
    {        
        for(Notification notification:notificationService.getSeenNotifications(this.getUser()))
        {
            switch(notification.getSubject())
            {
                case"Accept":seenAcceptNotifications.add(notification);break;
                case"Demande":seenDemandeNotifications.add(notification);break;
                case"Message":seenMessageNotifications.add(notification);break;
            }
        }
    }
    
    public void deleteMessageNotification(Message message)
    {
        Iterator i = seenMessageNotifications.iterator();
        while(i.hasNext())
            if(((Notification)i.next()).getMessage().equals(message.getId().toString()))
            {
                i.remove();
                return;
            }
        
        i = unseenMessageNotifications.iterator();
        while(i.hasNext())
            if(((Notification)i.next()).getMessage().equals(message.getId().toString()))
            {
                i.remove();
                return;
            }
    }
    
    public void refreshMessageNotifications()
    {
        seenMessageNotificationsVBox = messageNotificationsListItem(seenMessageNotifications);
        unseenMessageNotificationsVBox = messageNotificationsListItem(unseenMessageNotifications);
        if(openedWindow == null) return ;
        if(!openedWindow.equals("Message")) return ;
        switch(openedTab)
        {
            case"Seen":scrollContainer.setContent(seenMessageNotificationsVBox);break;
            case"Unseen":scrollContainer.setContent(unseenMessageNotificationsVBox);break;
        }
    }   
    
    public void refreshUnseenNotifications(List<Notification> notificationList)
    {
        unseenDemandeNotifications.clear();
        unseenMessageNotifications.clear();
        unseenAcceptNotifications.clear();
        for(Notification notification:notificationList)
        {
            switch(notification.getSubject())
            {
                case"Accept":unseenAcceptNotifications.add(notification);break;
                case"Demande":unseenDemandeNotifications.add(notification);break;
                case"Message":unseenMessageNotifications.add(notification);break;
            }
        }
        for(Notification notification :unseenMessageNotifications)
        {
            Message message = messageService.getMessageById(Integer.valueOf(notification.getMessage()));
            ChatController controller =  ChatListener.getChatListcontrollers().get(message.getSender());
            if(controller != null)
                controller.addToChat(message);
        }
        
        initVBoxItems();
        switch(openedTab)
        {
            case"Seen":seenButton.fire();
            case"Unseen":unseenButton.fire();
        }
        System.gc();
    }
    
    private void initVBoxItems()
    {
        seenDemandeNotificationsVBox = demandeNotificationsListItem(seenDemandeNotifications);
        unseenDemandeNotificationsVBox = demandeNotificationsListItem(unseenDemandeNotifications);
        seenAcceptNotificationsVBox = acceptNotificationsListItem(seenAcceptNotifications);
        unseenAcceptNotificationsVBox = acceptNotificationsListItem(unseenAcceptNotifications);
        seenMessageNotificationsVBox = messageNotificationsListItem(seenMessageNotifications);
        unseenMessageNotificationsVBox = messageNotificationsListItem(unseenMessageNotifications);
    }
    
    private VBox demandeNotificationsListItem(List<Notification> notifications)
    {
        VBox vBox = new VBox();
        vBox.setPrefSize(485, 350);
        HBox.setMargin(vBox, new Insets(0, 0, 0, 10));
        notifications.forEach((notification) -> {
            User user = userService.getUserById(Integer.parseInt(notification.getLink()));
            vBox.getChildren().add(demandeNotificationItem(user, notification));
        });
        return vBox;
    }
    
    private VBox acceptNotificationsListItem(List<Notification> notifications)
    {
        VBox vBox = new VBox();
        vBox.setPrefSize(485, 350);
        HBox.setMargin(vBox, new Insets(0, 0, 0, 10));
        notifications.forEach((notification) -> {
            vBox.getChildren().add(acceptNotificationItem(notification));
        });
        return vBox; 
    }
    
    private VBox messageNotificationsListItem(List<Notification> notifications)
    {
        VBox vBox = new VBox();
        vBox.setPrefSize(485, 350);
        HBox.setMargin(vBox, new Insets(0, 0, 0, 10));
        notifications.forEach((notification) -> {
            Message message = messageService.getMessageById(Integer.valueOf(notification.getMessage()));
            vBox.getChildren().add(messageNotificationItem(message));
        });
        return vBox;
    }
    
    private HBox messageNotificationItem(Message message)
    {
        HBox hBoxParent = new HBox();
        hBoxParent.setPrefSize(485, 60);
        ImageView userImage = new ImageView(getClass().getResource("../Images/"+message.getSender().getImage()).toExternalForm());
        userImage.setFitWidth(60);
        userImage.setFitHeight(60);
        userImage.setPickOnBounds(true);
        userImage.setPreserveRatio(true);
        userImage.setClip(new Circle(30, 30, 30));
        VBox vBox = new VBox();
        vBox.setPrefSize(430, 60);
        HBox hBoxChild = new HBox();
        hBoxChild.setPrefSize(430, 30);
        Label notificationUser = new Label(message.getSender().getPrenom()+" "+message.getSender().getNom());
        notificationUser.setPrefSize(330, 30);
        notificationUser.setFont(prefFont);
        Label notificationDate = new Label(this.getLongDateFormat(message.getDate()));
        notificationDate.setPrefSize(100, 30);
        hBoxChild.getChildren().add(notificationUser);
        hBoxChild.getChildren().add(notificationDate);
        Label messageText = new Label(message.getText());
        messageText.setPrefSize(430, 30);
        vBox.getChildren().add(hBoxChild);
        vBox.getChildren().add(messageText);
        hBoxParent.getChildren().add(userImage);
        hBoxParent.getChildren().add(vBox);
        HBox.setMargin(notificationUser, new Insets(0, 0, 0, 5));
        VBox.setMargin(messageText, new Insets(0, 0, 0, 5));
        VBox.setMargin(hBoxParent, new Insets(5, 0, 0, 0));
        return hBoxParent;
    }
    
    private HBox demandeNotificationItem(User user,Notification notification)
    {
        HBox hBox = new HBox();
        hBox.setPrefSize(485, 60);
        ImageView userImage = new ImageView(getClass().getResource("../Images/"+user.getImage()).toExternalForm());
        userImage.setFitWidth(60);
        userImage.setFitHeight(60);
        userImage.setPickOnBounds(true);
        userImage.setPreserveRatio(true);
        userImage.setClip(new Circle(30, 30, 30));
        VBox vBox = new VBox();
        vBox.setPrefSize(120, 60);
        Label notificationUser = new Label(user.getPrenom()+" "+user.getNom());
        notificationUser.setPrefSize(120, 30);
        notificationUser.setFont(prefFont);
        Label notificationDate = new Label(this.getLongDateFormat(notification.getDate()));
        notificationDate.setPrefSize(120, 30);
        notificationDate.setFont(prefFont);
        vBox.getChildren().add(notificationUser);
        vBox.getChildren().add(notificationDate);
        Label notificationText = new Label("a envoyé une demande");
        notificationText.setPrefSize(140, 60);
        Button acceptButton = new Button("Accepter");
        acceptButton.setMnemonicParsing(false);
        acceptButton.setPrefSize(70, 60);
        acceptButton.setFont(prefFont);
        acceptButton.setId(user.getId().toString());
        acceptButton.setOnAction(this::acceptDemande);
        Button rejectButton = new Button("Rejeter");
        rejectButton.setMnemonicParsing(false);
        rejectButton.setPrefSize(70, 60);
        rejectButton.setFont(prefFont);
        rejectButton.setId(user.getId().toString());
        rejectButton.setOnAction(this::rejectDemande);
        ObservableList<Node> childs = hBox.getChildren();
        childs.add(userImage);
        childs.add(vBox);
        childs.add(notificationText);
        childs.add(acceptButton);
        childs.add(rejectButton);
        HBox.setMargin(vBox, new Insets(0, 0, 0, 5));
        HBox.setMargin(acceptButton, new Insets(0, 0, 0, 5));
        HBox.setMargin(rejectButton, new Insets(0, 0, 0, 5));
        VBox.setMargin(hBox, new Insets(5, 0, 0, 0));
        return hBox;
    }
    
    private HBox acceptNotificationItem(Notification notification)
    {
        HBox hBox = new HBox();
        hBox.setPrefSize(485, 60);
        ImageView userImage = new ImageView(getClass().getResource("../Images/"+notification.getLink()).toExternalForm());
        userImage.setFitWidth(60);
        userImage.setFitHeight(60);
        userImage.setPickOnBounds(true);
        userImage.setPreserveRatio(true);
        userImage.setClip(new Circle(30, 30, 30));
        Label notificationUser = new Label(notification.getMessage());
        notificationUser.setPrefSize(120, 60);
        notificationUser.setFont(prefFont);
        Label notificationText = new Label("a accepté votre demande");
        Label notificationDate = new Label(this.getLongDateFormat(notification.getDate()));
        notificationText.setPrefSize(140, 60);
        notificationDate.setPrefSize(100, 60);
        ObservableList<Node> childs = hBox.getChildren();
        childs.add(userImage);
        childs.add(notificationUser);
        childs.add(notificationText);
        childs.add(notificationDate);
        HBox.setMargin(notificationUser, new Insets(0, 0, 0, 5));
        VBox.setMargin(hBox, new Insets(5, 0, 0, 0));
        return hBox;
    }
    
    private void deleteDemande(int requesterId)
    {
        Iterator iterator;
        Notification notification;
        iterator = unseenDemandeNotifications.iterator();
        while(iterator.hasNext())
        {
            notification = (Notification)iterator.next();
            if(notification.getLink().equals(String.valueOf(requesterId)))
            {
                notificationService.deleteNotification(notification);
                iterator.remove();
            }
        }
        iterator = seenDemandeNotifications.iterator();
        while(iterator.hasNext())
        {
            notification = (Notification)iterator.next();
            if(notification.getLink().equals(String.valueOf(requesterId)))
            {
                notificationService.deleteNotification(notification);
                iterator.remove();
            }
        }
        seenDemandeNotificationsVBox = demandeNotificationsListItem(seenDemandeNotifications);
        unseenDemandeNotificationsVBox = demandeNotificationsListItem(unseenDemandeNotifications);
        switch(openedTab)
        {
            case"Seen":scrollContainer.setContent(seenDemandeNotificationsVBox);break;
            case"Unseen":scrollContainer.setContent(unseenDemandeNotificationsVBox);break;
        }
        User requester = new User();
        requester.setId(requesterId);
        Demande demande = new Demande();
        demande.setAcceptor(this.getUser());
        demande.setRequester(requester);
        demandeService.deleteDemande(demande);
        System.gc();
    }
    
    @FXML
    private void acceptDemande(ActionEvent event) 
    {   
        Button demandeButton = (Button)event.getSource();
        int requesterId = Integer.parseInt(demandeButton.getId());
        deleteDemande(requesterId);
        User requester = userService.getUserById(requesterId);
        NotificationApi.createAcceptNotification(this.getUser(),requester);
        relationService.insertRelation(this.getUser(),requester);
        demandeButton.setVisible(false);
        Notifications.create().text("Félicitation tu as un nouveau amis !!!").position(Pos.CENTER).hideAfter(Duration.seconds(3)).showConfirm();
    }
    
    @FXML
    private void rejectDemande(ActionEvent event) {
        Button demandeButton = (Button)event.getSource();
        int requesterId = Integer.parseInt(demandeButton.getId());
        deleteDemande(requesterId);
        demandeButton.setVisible(false);
        Notifications.create().text("Cette demande a été supprimé").position(Pos.CENTER).hideAfter(Duration.seconds(3)).showConfirm();
    }

    @FXML
    private void unseenAction(ActionEvent event) {
        if("Unseen".equals(openedTab))
            return;
        openedTab = "Unseen";
        switch(openedWindow)
        {
            case"Demande":scrollContainer.setContent(unseenDemandeNotificationsVBox);break;
            case"Message":scrollContainer.setContent(unseenMessageNotificationsVBox);break;
            case"Accept":scrollContainer.setContent(unseenAcceptNotificationsVBox);break;
        }
    }

    @FXML
    private void seenAction(ActionEvent event) {
        if("Seen".equals(openedTab))
            return;
        openedTab = "Seen";
        switch(openedWindow)
        {
            case"Demande":scrollContainer.setContent(seenDemandeNotificationsVBox);break;
            case"Message":scrollContainer.setContent(seenMessageNotificationsVBox);break;
            case"Accept":scrollContainer.setContent(seenAcceptNotificationsVBox);break;
        }
    }

    @FXML
    private void makeAsSeenAction(ActionEvent event) {
        switch(openedWindow)
        {
            case"Demande":{
                notificationService.makeNotificationsAsSeen(this.getUser(), unseenDemandeNotifications);
                seenDemandeNotifications.addAll(unseenDemandeNotifications);
                unseenDemandeNotifications.clear();
                unseenDemandeNotificationsVBox = demandeNotificationsListItem(unseenDemandeNotifications);
                seenDemandeNotificationsVBox = demandeNotificationsListItem(seenDemandeNotifications);
                scrollContainer.setContent(seenDemandeNotificationsVBox);
                break;
            }
            case"Message":{
                notificationService.makeNotificationsAsSeen(this.getUser(), unseenMessageNotifications);
                seenMessageNotifications.addAll(unseenMessageNotifications);
                unseenMessageNotifications.clear();
                unseenMessageNotificationsVBox = messageNotificationsListItem(unseenMessageNotifications);
                seenMessageNotificationsVBox = messageNotificationsListItem(seenMessageNotifications);
                scrollContainer.setContent(seenMessageNotificationsVBox);
                break;
            }
            case"Accept":{
                notificationService.makeNotificationsAsSeen(this.getUser(), unseenAcceptNotifications);
                seenAcceptNotifications.addAll(unseenAcceptNotifications);
                unseenAcceptNotifications.clear();
                unseenAcceptNotificationsVBox = acceptNotificationsListItem(unseenAcceptNotifications);
                seenAcceptNotificationsVBox = acceptNotificationsListItem(seenAcceptNotifications);
                scrollContainer.setContent(seenAcceptNotificationsVBox);
                break;
            }
        }
        openedTab = "Seen";
        System.gc();
    }
}
