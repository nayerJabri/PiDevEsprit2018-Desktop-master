/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.Scolarite;
import Entity.User;
import IService.IScolariteService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hero
 */
public class ScolariteService implements IScolariteService {
    private Connection con = DataSource.getInstance().getConnection();

    @Override
    public List<Scolarite> getAllScolariteByUser(User u) {
        String req = "SELECT * FROM scolarite where idUser=? order by dateDebut asc";
        ResultSet rs= null;
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, u.getId());
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ScolariteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Scolarite> s = new ArrayList<>();
        try {
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("idUser"));
                s.add(new Scolarite(rs.getInt("id"), rs.getString("contenu"), rs.getString("dateDebut"), rs.getString("dateFin"), user));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScolariteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    @Override
    public void ajouterScolarite(Scolarite s, User u) {
        try {
            String req = "INSERT INTO scolarite (contenu, dateDebut, dateFin, idUser) VALUES (?,?,?,?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, s.getContenu());
            pre.setString(2, s.getDateDebut());
            pre.setString(3, s.getDateFin());
            pre.setInt(4, u.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ScolariteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifierScolarite(Scolarite s) {
        try {
            String req = "update scolarite set contenu=?, dateDebut=?, dateFin=? where id=? ";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, s.getContenu());
            pre.setString(2, s.getDateDebut());
            pre.setString(3, s.getDateFin());
            pre.setInt(4, s.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ScolariteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimerScolarite(Scolarite s) {
        try {
            String req = "delete from scolarite where id=? ";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, s.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ScolariteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Scolarite getScolariteById(int s) {
        String req = "SELECT * FROM scolarite where id=? ";
        ResultSet rs= null;
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, s);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ScolariteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scolarite sc = new Scolarite();
        try {
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("idUser"));
                sc.setId(rs.getInt("id"));
                sc.setContenu(rs.getString("contenu"));
                sc.setDateDebut(rs.getString("dateDebut"));
                sc.setDateFin(rs.getString("dateFin"));
                sc.setIdUser(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScolariteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sc;
    }
}
