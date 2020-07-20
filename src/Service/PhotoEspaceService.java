/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.Espace;
import Entity.PhotoEspace;
import IService.IPhotoEspaceService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hero
 */
public class PhotoEspaceService implements IPhotoEspaceService {
    private Connection con = DataSource.getInstance().getConnection();
               @Override
    public PhotoEspace ajoutPhoto(PhotoEspace e) {
        try {
            EspaceService es = new EspaceService();
            Espace k=es.lastEspaces();
            String query = "INSERT INTO `photo_espace`(`photo1`, `photo2`, `photo3`, `photo4`, `idEspace`) VALUES(?,?,?,?,?)";
            PreparedStatement prs = con.prepareStatement(query);
            
            prs.setString(1, e.getPhoto1());
            
            prs.setString(2, e.getPhoto2());
            prs.setString(3, e.getPhoto3());
            prs.setString(4, e.getPhoto4());
            prs.setInt(5, k.getId());
            
            int id = prs.executeUpdate();
            e.setId(id);
            return e;
        } catch (SQLException ex) {
            Logger.getLogger(PhotoEspaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
        @Override
    public PhotoEspace getPhotoById(int id) {
                try {
            String query = "select * from photo_espace where idEspace = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Espace espace;
            while(rs.next())
            {
                PhotoEspace photo = new PhotoEspace(rs.getString("photo1"), rs.getString("photo2"),rs.getString("photo3"), rs.getString("photo4"), rs.getInt("idEspace"));
                return photo;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EspaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
