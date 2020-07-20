/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.Loisir;
import Entity.User;
import IService.ILoisirService;
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
public class LoisirService implements ILoisirService {
    private Connection con = DataSource.getInstance().getConnection();

    @Override
    public List<Loisir> getAllLoisirsByUser(User u) {
        String req = "SELECT * FROM loisir where idUser=?";
        ResultSet rs= null;
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, u.getId());
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Loisir> l = new ArrayList<>();
        try {
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("idUser"));
                l.add(new Loisir(rs.getInt("id"), rs.getString("contenu"), user));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoisirService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    @Override
    public void ajouterLoisir(Loisir l, int u) {
        try {
            String req = "INSERT INTO loisir (contenu, idUser) VALUES (?,?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, l.getContenu());
            pre.setInt(2, u);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LoisirService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifierLoisir(Loisir l) {
        try {
            String req = "update loisir set contenu=? where idUser=? ";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, l.getContenu());
            pre.setInt(4, l.getIdUser().getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LoisirService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimerLoisir(Loisir l) {
        try {
            String req = "delete from loisir where id=? ";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, l.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LoisirService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
