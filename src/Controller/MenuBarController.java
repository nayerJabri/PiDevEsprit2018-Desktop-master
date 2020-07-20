/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.Controller;
import Entity.Notification;
import IService.INotificationService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToolbar;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author hero
 */
public class MenuBarController extends Controller implements Initializable {

    private final INotificationService notificationService = this.getService().getNotificationService();
    private int lastNotificationId = Integer.MAX_VALUE;
    private Timer timer;
    private List<Notification> notifications;
    private int messageNotifCount;
    private int demandeNotifCount;
    private int acceptNotifCount;
    @FXML
    private Label messageNotifLabel;
    @FXML
    private Label demandeNotifLabel;
    @FXML
    private Label acceptNotifLabel;
    @FXML
    private ImageView Maparea;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTimer();
        Controller.getNotificationController().refreshUnseenNotifications(notificationService.getUnseenNotifications(this.getUser()));
    }

    private void refreshCounts()
    {
        notifications = notificationService.getUnseenNotifications(this.getUser());
        if(!notifications.isEmpty())
        {
            if(notifications.get(0).getId() > lastNotificationId)
            {
                Controller.getNotificationController().refreshUnseenNotifications(notifications);
            }
            lastNotificationId = notifications.get(0).getId();
        }
        else
        {
            lastNotificationId = 0;
        }
        messageNotifCount = 0;
        demandeNotifCount = 0;
        acceptNotifCount = 0;
        for(Notification notification:notifications)
        {
            switch(notification.getSubject())
            {
                case"Accept":acceptNotifCount++;Notifications.create().text("Il y'a des nouveaux notifications  !!!").position(Pos.CENTER).hideAfter(Duration.seconds(3)).showConfirm();break;
                case"Demande":demandeNotifCount++;Notifications.create().text("Il y'a des nouveaux notifications  !!!").position(Pos.CENTER).hideAfter(Duration.seconds(3)).showConfirm();break;
                case"Message":messageNotifCount++;break;
            }
        }
        messageNotifLabel.setText(String.valueOf(messageNotifCount));
        demandeNotifLabel.setText(String.valueOf(demandeNotifCount));
        acceptNotifLabel.setText(String.valueOf(acceptNotifCount));
        System.gc();
    }
    
    private void initTimer()
    {
        this.timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    refreshCounts();
                });
            }
        };
        timer.schedule(timerTask,0,5000);
    }

    @FXML
    private void demandeNotification(MouseEvent event) {
        Controller.getNotificationController().showWindow("Demande");
    }

    @FXML
    private void messageNotification(MouseEvent event) {
        Controller.getNotificationController().showWindow("Message");
    }

    @FXML
    private void acceptNotification(MouseEvent event) {
        Controller.getNotificationController().showWindow("Accept");
    }

    @FXML
    private void switchMap(MouseEvent event) throws IOException {
        Stage stage = new Stage();
          Parent root = FXMLLoader.load(getClass().getResource("../View/Maparea.fxml")); 
        Scene scene = new Scene(root,1100,700);     
        stage.setScene(scene);
        stage.show();
    }
    
}
