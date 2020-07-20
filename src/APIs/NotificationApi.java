/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APIs;

import Entity.Demande;
import Entity.Message;
import Entity.Notifiable;
import Entity.Notification;
import Entity.User;
import IService.INotificationService;
import Service.NotificationService;
import java.util.Date;

/**
 *
 * @author hero
 */
public class NotificationApi {
    private final static INotificationService notificationService = new NotificationService();
    
    public static void createMessageNotification(Message message)
    {
        Notifiable notifiable = new Notifiable();
        notifiable.setIdentifier(message.getReceiver().getId());
        Notification notification = new Notification();
        notification.setDate(new Date());
        notification.setSubject("Message");
        notification.setMessage(message.getId().toString());
        notification.setLink(message.getSender().getId().toString());
        notificationService.insertNotification(notifiable, notification);
    }
    
    public static void createDemandeNotification(Demande demande)
    {
        Notifiable notifiable = new Notifiable();
        notifiable.setIdentifier(demande.getAcceptor().getId());
        Notification notification = new Notification();
        notification.setDate(new Date());
        notification.setSubject("Demande");
        notification.setMessage(demande.getAcceptor().getId().toString());
        notification.setLink(demande.getRequester().getId().toString());
        notificationService.insertNotification(notifiable, notification);
    }
    
    public static void createAcceptNotification(User cUser,User oUser)
    {
        Notifiable notifiable = new Notifiable();
        notifiable.setIdentifier(oUser.getId());
        Notification notification = new Notification();
        notification.setDate(new Date());
        notification.setSubject("Accept");
        notification.setMessage(cUser.getNom()+" "+cUser.getPrenom());
        notification.setLink(cUser.getImage());
        notificationService.insertNotification(notifiable, notification);     
    }
    
    public static void deleteMessageNotification(Message message)
    {
        Notification notification = new Notification();
        notification.setMessage(message.getId().toString());
        notification.setLink(message.getSender().getId().toString());
        notificationService.deleteNotification(notification);
    }
}
