/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.Evenement;
import Entity.User;
import IService.IParticipationService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hero
 */
public class ParticipationService implements IParticipationService {
    private Connection con = DataSource.getInstance().getConnection();
//     {
//         try {
//             String req="UPDATE `event` SET `participants`=? WHERE `id`=?";
//             PreparedStatement prs = con.prepareStatement(req);
//             prs.setInt(1,(ev.getNbreParticipants())+1);
//             prs.setInt(2,ev.getId());
//             prs.executeUpdate();
//             System.out.println("services.ServiceEvents.increment()");  
//  
//         } catch (SQLException e) {
//             System.out.println("404");
//         }
//         
//     }
    @Override
       public   void increment(Evenement ev,User user)
     {
         try {
             String req="UPDATE `Evenement` SET `nbplaces`=? WHERE `id`=?";
             PreparedStatement prs = con.prepareStatement(req);
             prs.setInt(1,(ev.getNbplaces())+1);
             prs.setInt(2,ev.getId());
             prs.executeUpdate();
             req = "INSERT INTO participation (IdEvenement,IdUser) VALUES (?,?)";
             prs = con.prepareStatement(req);
             prs.setInt(1, ev.getId());
             prs.setInt(2, user.getId());
             prs.executeUpdate();
         } catch (SQLException e) {
             System.out.println("404");
         }
         
     }
    @Override
         public   void decrement(Evenement ev,User user)
     {
         try {
             String req="UPDATE `Evenement` SET `nbplaces`=? WHERE `id`=?";
             PreparedStatement prs = con.prepareStatement(req);
             prs.setInt(1,(ev.getNbplaces())-1);
             prs.setInt(2,ev.getId());
             prs.executeUpdate();
             req = "DELETE FROM participation WHERE IdEvenement = ? and IdUser=?";
             prs = con.prepareStatement(req);
             prs.setInt(1, ev.getId());
             prs.setInt(2, user.getId());
             prs.executeUpdate();
  
         } catch (SQLException e) {
             System.out.println("404");
         }
         
     }

    @Override
    public boolean checkParticipation(Evenement evenement, User user) {
        String query = "select * from participation where IdEvenement = ? and IdUser = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, evenement.getId());
            ps.setInt(2, user.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return true;
        } catch (SQLException ex) {
            Logger.getLogger(ParticipationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
