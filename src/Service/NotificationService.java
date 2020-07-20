/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.Notifiable;
import Entity.Notification;
import Entity.User;
import IService.INotificationService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hero
 */
public class NotificationService implements INotificationService {
    private Connection con = DataSource.getInstance().getConnection();
    
    @Override
    public void insertNotification(Notifiable notifiable, Notification notification) {
        Calendar c = Calendar.getInstance();
        Timestamp ts = new Timestamp(c.getTimeInMillis());
        String query = "select * from notifiable where identifier = ?";
        ResultSet keyResult;
        PreparedStatement ps;
        int notifiableId=0;
        int notificationId=0;
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, notifiable.getIdentifier());
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                notifiableId = rs.getInt("id");
            }
            else
            {
                query = "INSERT INTO notifiable (identifier) VALUES(?)";
                ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, notifiable.getIdentifier());
                ps.executeUpdate();
                keyResult = ps.getGeneratedKeys();
                if (keyResult.next()) {
                    notifiableId = keyResult.getInt(1);
                }
            }
            
            query = "INSERT INTO notification (date,subject,message,link) VALUES(?,?,?,?)";
            ps = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, ts);
            ps.setString(2, notification.getSubject());
            ps.setString(3, notification.getMessage());
            ps.setString(4, notification.getLink());
            ps.executeUpdate();
            keyResult = ps.getGeneratedKeys();
            if (keyResult.next()) {
                notificationId = keyResult.getInt(1);
            }
            
            query="INSERT INTO `notifiable_notification` (`notification_id`,`notifiable_entity_id`,seen) VALUES(?,?,0)";
            ps = con.prepareStatement(query);
            ps.setInt(1, notificationId);
            ps.setInt(2, notifiableId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Notification> getNotifications(User user,int seen,int lastId) {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM `notification` n "
                + "join `notifiable_notification` nn on nn.notification_id = n.id "
                + "join `notifiable` no on no.id = nn.notifiable_entity_id where (no.identifier = ? AND nn.seen = ? and n.id > ?) order by n.date DESC";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setInt(2, seen);
            ps.setInt(3, lastId);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Notification notification = Notification.createNotification(rs);
                notifications.add(notification);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notifications;
    }

    @Override
    public List<Notification> getSeenNotifications(User user) {
        return getNotifications(user, 1,0);
    }

    @Override
    public List<Notification> getUnseenNotifications(User user) {
        return getNotifications(user, 0,0);
    }

    @Override
    public boolean deleteNotification(Notification notification) {
        String query = "DELETE FROM notification WHERE (message = ? AND link = ?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, notification.getMessage());
            ps.setString(2, notification.getLink());
            return(ps.executeUpdate()>0);
        } catch (SQLException ex) {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public int makeNotificationsAsSeen(User user, List<Notification> notifications) {
        String query = "UPDATE `notifiable_notification` SET seen = 1 WHERE `notification_id` = ?";
        int c = 0;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            notifications.forEach((Notification notification)-> {
                try {
                    ps.setInt(1, notification.getId());
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(NotificationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    @Override
    public List<Notification> getUnseenNotifications(User user, int lastNotificationId) {
        return getNotifications(user, 0,lastNotificationId);
    }
    
}
