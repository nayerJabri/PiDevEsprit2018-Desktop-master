/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import APIs.BubbleSpec;
import APIs.BubbledLabel;
import APIs.ChatListener;
import APIs.NotificationApi;
import Core.Controller;
import Entity.Message;
import Entity.User;
import IService.IMessageService;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author hero
 */
public class ChatController extends Controller implements Initializable{

    private final IMessageService messageService = this.getService().getMessageService();
    private User chatUser;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ListView chatPane;
    @FXML
    private TextArea messageBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageBox.addEventFilter(KeyEvent.KEY_PRESSED, ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                try {
                    sendButtonAction();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ke.consume();
            }
        });
        
    }
    
    public void loadMessages()
    {
        messageService.fetchMessage(this.getUser(), chatUser).forEach((message) -> {
            chatPane.getItems().add(chatItem(message));
            NotificationApi.deleteMessageNotification(message);
            Controller.getNotificationController().deleteMessageNotification(message);
        });
        Controller.getNotificationController().refreshMessageNotifications();
        chatPane.scrollTo(chatPane.getItems().size()-1);
    }
    
    private HBox chatItem(Message msg)
    {
        BubbledLabel bl6 = new BubbledLabel();
        Image image = new Image(getClass().getClassLoader().getResource("images/"+msg.getSender().getImage()).toString());
        HBox hBox = new HBox();
        ImageView profileImage = new ImageView(image);
        profileImage.setFitHeight(32);
        profileImage.setFitWidth(32);
        profileImage.setClip(new Circle(16, 16, 16));
        bl6.setText(msg.getText());
        Label dateLabel = new Label(this.getLongDateFormat(msg.getDate()));
        dateLabel.setPrefSize(100, 32);
        if (msg.getReceiver().getId().intValue() == this.getUser().getId().intValue())
        {
            bl6.setBackground(new Background(new BackgroundFill(Color.ORANGE,null, null)));
            bl6.setBubbleSpec(BubbleSpec.FACE_LEFT_CENTER);
            hBox.getChildren().addAll(profileImage, bl6,dateLabel);
        }
        else
        {
            bl6.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,null, null)));
            bl6.setBubbleSpec(BubbleSpec.FACE_RIGHT_CENTER);
            hBox.setMaxWidth(chatPane.getPrefWidth() - 20);
            hBox.setAlignment(Pos.TOP_RIGHT);
            hBox.getChildren().addAll(dateLabel,bl6,profileImage);
        }
        return hBox;
    }
    
    public synchronized void addToChat(Message msg) {
        Task<HBox> receivedMessages = new Task<HBox>() {
            @Override
            public HBox call() throws Exception {
                System.out.println("received");
                Image image = new Image(getClass().getClassLoader().getResource("images/"+msg.getSender().getImage()).toString());
                ImageView profileImage = new ImageView(image);
                profileImage.setFitHeight(32);
                profileImage.setFitWidth(32);
                profileImage.setClip(new Circle(16, 16, 16));
                BubbledLabel bl6 = new BubbledLabel();
                bl6.setText(msg.getText());
                bl6.setBackground(new Background(new BackgroundFill(Color.ORANGE,null, null)));
                HBox x = new HBox();
                bl6.setBubbleSpec(BubbleSpec.FACE_LEFT_CENTER);
                Label dateLabel = new Label(getLongDateFormat(msg.getDate()));
                dateLabel.setPrefSize(100, 32);
                x.getChildren().addAll(profileImage, bl6,dateLabel);                
                return x;
            }
        };

        receivedMessages.setOnSucceeded(event -> {
            ObservableList list =  chatPane.getItems();
            list.add(receivedMessages.getValue());
            chatPane.scrollTo(list.size()-1);
        });

        Task<HBox> sendedMessages = new Task<HBox>() {
            @Override
            public HBox call() throws Exception {
                Image image = new Image(getClass().getClassLoader().getResource("images/"+msg.getSender().getImage()).toString());
                ImageView profileImage = new ImageView(image);
                profileImage.setFitHeight(32);
                profileImage.setFitWidth(32);
                profileImage.setClip(new Circle(16, 16, 16));
                BubbledLabel bl6 = new BubbledLabel();
                bl6.setText(msg.getText());
                bl6.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,null, null)));
                HBox x = new HBox();
                x.setMaxWidth(chatPane.getPrefWidth() - 20);
                x.setAlignment(Pos.TOP_RIGHT);
                bl6.setBubbleSpec(BubbleSpec.FACE_RIGHT_CENTER);
                Label dateLabel = new Label(getLongDateFormat(msg.getDate()));
                dateLabel.setPrefSize(100, 32);
                x.getChildren().addAll(dateLabel,bl6, profileImage);                
                return x;
            }
        };
        sendedMessages.setOnSucceeded(event -> {
            ObservableList list =  chatPane.getItems();
            list.add(sendedMessages.getValue());
            chatPane.scrollTo(list.size()-1);
        });

        if (this.getUser().getId().intValue() == msg.getReceiver().getId().intValue()) {
            Thread t2 = new Thread(receivedMessages);
            t2.setDaemon(true);
            t2.start();
        } else {
            Thread t = new Thread(sendedMessages);
            t.setDaemon(true);
            t.start();
        }
    }
    
    public void sendButtonAction() throws IOException {
        String text = messageBox.getText();
        if (!messageBox.getText().isEmpty()) {
            Message message = new Message();
            message.setSender(this.getUser());
            message.setReceiver(chatUser);
            message.setText(text);
            message.setDate(new Date());
            addToChat(message);
            ChatListener.send(message);
            messageBox.clear();
        }
    }

    public User getChatUser() {
        return chatUser;
    }

    public void setChatUser(User chatUser) {
        this.chatUser = chatUser;
    }
    
}
