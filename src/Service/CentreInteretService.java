/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.CentreInteret;
import Entity.User;
import IService.ICentreInteretService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hero
 */
public class CentreInteretService implements ICentreInteretService {
    private Connection con = DataSource.getInstance().getConnection();
    private Statement ste;

    @Override
    public List<CentreInteret> getFilmsByUser(User u) {
        String req = "SELECT * FROM centre_interet where idUser=? and type='film'";
        ResultSet rs= null;
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, u.getId());
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<CentreInteret> ci = new ArrayList<>();
        try {
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("idUser"));
                ci.add(new CentreInteret(rs.getInt("id"), rs.getString("type"), rs.getString("contenu"), user));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ci;
    }

    @Override
    public List<CentreInteret> getSeriesByUser(User u) {
        String req = "SELECT * FROM centre_interet where idUser=? and type='serie'";
        ResultSet rs= null;
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, u.getId());
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<CentreInteret> ci = new ArrayList<>();
        try {
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("idUser"));
                ci.add(new CentreInteret(rs.getInt("id"), rs.getString("type"), rs.getString("contenu"), user));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ci;
    }

    @Override
    public List<CentreInteret> getArtistsByUser(User u) {
        String req = "SELECT * FROM centre_interet where idUser=? and type='artist'";
        ResultSet rs= null;
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, u.getId());
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<CentreInteret> ci = new ArrayList<>();
        try {
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("idUser"));
                ci.add(new CentreInteret(rs.getInt("id"), rs.getString("type"), rs.getString("contenu"), user));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ci;
    }

    @Override
    public List<CentreInteret> getLivresByUser(User u) {
        String req = "SELECT * FROM centre_interet where idUser=? and type='livre'";
        ResultSet rs= null;
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, u.getId());
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<CentreInteret> ci = new ArrayList<>();
        try {
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("idUser"));
                ci.add(new CentreInteret(rs.getInt("id"), rs.getString("type"), rs.getString("contenu"), user));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ci;
    }

    @Override
    public void ajouterCentreInteret(CentreInteret c, User u) {
        try {
            String req = "INSERT INTO centre_interet (type, contenu, idUser) VALUES (?,?,?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, c.getType());
            pre.setString(2, c.getContenu());
            pre.setInt(3, u.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CentreInteretService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifierCentreInteret(CentreInteret c) {
        try {
            String req = "update centre_interet set type=?, contenu=? where idUser=? ";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, c.getType());
            pre.setString(2, c.getContenu());
            pre.setInt(3, c.getIdUser().getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CentreInteretService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimerCentreInteret(CentreInteret c) {
        try {
            String req = "delete from centre_interet where id=? ";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, c.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CentreInteretService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
