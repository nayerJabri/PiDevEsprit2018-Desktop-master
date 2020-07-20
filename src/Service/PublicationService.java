/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.Publication;
import Entity.User;
import IService.IPublicationService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Achrafoun
 */
public class PublicationService implements IPublicationService{
    private Connection con = DataSource.getInstance().getConnection();

    @Override
    public List<Publication> getPublicationByUser(User u) {
        String req = "SELECT * FROM publication where idUser=? order by datePublication desc";
        ResultSet rs= null;
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, u.getId());
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Publication> p = new ArrayList<>();
        try {
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("idUser"));
                p.add(new Publication(rs.getInt("id"), rs.getString("contenu"), rs.getTimestamp("datePublication"), user));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PublicationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    @Override
    public void ajouterPublication(Publication p) {
        Calendar c = Calendar.getInstance();
        Timestamp ts = new Timestamp(c.getTimeInMillis());
        try {
            String req = "INSERT INTO publication (contenu, datePublication, idUser) VALUES (?,?,?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, p.getContenu());
            
            pre.setTimestamp(2, ts);
            
            pre.setInt(3, p.getIdUser().getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PublicationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifierPublication(Publication p) {
        Calendar c = Calendar.getInstance();
        Timestamp ts = new Timestamp(c.getTimeInMillis());
        try {
            String req = "update publication set contenu=?, datePublication=? where id=? ";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, p.getContenu());
            
            pre.setTimestamp(2, ts);
            
            pre.setInt(3, p.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PublicationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimerPublication(Publication p) {
        try {
            String req = "delete from publication where id=? ";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, p.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PublicationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Publication getPublicationById(int id) {
        String req = "SELECT * FROM publication where id=?";
        ResultSet rs= null;
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, id);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(PublicationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        Publication p = new Publication();
        try {
            while (rs.next()){
                User user = new User();
                p.setId(rs.getInt("id"));
                p.setContenu(rs.getString("contenu"));
                p.setDatePublication(rs.getTimestamp("datePublication"));
                p.setIdUser(user);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(PublicationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
}
