/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.Album;
import Entity.User;
import IService.IAlbumService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Achrafoun
 */
public class AlbumService implements IAlbumService{
    private Connection con = DataSource.getInstance().getConnection();

    @Override
    public List<Album> getPhotosByUser(User u) {
        String req = "SELECT * FROM album where idUser=? order by datePublication desc";
        ResultSet rs= null;
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, u.getId());
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Album> a = new ArrayList<>();
        try {
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("idUser"));
                a.add(new Album(rs.getInt("id"), rs.getString("url"), rs.getDate("datePublication"), user));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    @Override
    public void ajouterPhoto(Album a) {
              
        try {
            String req = "INSERT INTO album (url, datePublication,idUser) VALUES (?,?,?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, a.getUrl());
            java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            pre.setTimestamp(2, date);
            pre.setInt(3, a.getIdUser().getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AlbumService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimerPhoto(Album a) {
        try {
            String req = "delete from album where id=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, a.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AlbumService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Album> getLastPhotosByUser(User u) {
        String req = "SELECT * FROM album where idUser=? order by datePublication desc limit 9";
        ResultSet rs= null;
        try {
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, u.getId());
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Album> p = new ArrayList<>();
        try {
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("idUser"));
                p.add(new Album(rs.getInt("id"), rs.getString("url"), rs.getDate("datePublication"), user));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
}
