/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.Notifiable;
import Entity.Notification;
import Entity.User;
import java.util.List;

/**
 *
 * @author hero
 */
public interface INotificationService {
    public void insertNotification(Notifiable notifiable,Notification notification);
    public List<Notification> getUnseenNotifications(User user);
    public List<Notification> getUnseenNotifications(User user,int lastNotificationId);
    public List<Notification> getSeenNotifications(User user);
    public boolean deleteNotification(Notification notification);
    public int makeNotificationsAsSeen(User user,List<Notification> notifications);
}
