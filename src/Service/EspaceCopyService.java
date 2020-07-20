/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.EspaceCopy;
import IService.IEspaceCopyService;
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
public class EspaceCopyService implements IEspaceCopyService {

    private Connection con = DataSource.getInstance().getConnection();

    @Override
    public List<EspaceCopy> getAll() {
        try {
            String query = "select * from espace_copy";
            Statement ste = con.createStatement();
            ResultSet rs = ste.executeQuery(query);
            List<EspaceCopy> espacescopy = new ArrayList<>();
            while (rs.next()) {
                espacescopy.add(new EspaceCopy(rs.getInt("id"), rs.getString("titre"), rs.getString("description"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("id_esp")));

            }
            return espacescopy;
        } catch (SQLException ex) {
            Logger.getLogger(EspaceCopyService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public EspaceCopy ajoutEspaceCopy(EspaceCopy e) {
        try {

            String query = "INSERT INTO `espace_copy`(`titre`, `description`, `nom`, `prenom`, `id_esp`) VALUES(?,?,?,?,?)";
            PreparedStatement prs = con.prepareStatement(query);
            prs.setString(1, e.getTitre());
            prs.setString(2, e.getDescription());
            prs.setString(3, e.getNom());
            prs.setString(4, e.getPrenom());
            prs.setInt(5, e.getId_esp());
            int id = prs.executeUpdate();
            e.setId(id);
            return e;

        } catch (SQLException ex) {
            Logger.getLogger(EspaceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
        public EspaceCopy confirmerEspaceModif(EspaceCopy e) {
        try {

            String query = "Update espace set titre=?, description=?  where id=? ";
            PreparedStatement prs = con.prepareStatement(query);
            prs.setString(1, e.getTitre());
            prs.setString(2, e.getDescription());
            prs.setInt(3, e.getId_esp());
            int id = prs.executeUpdate();
            e.setId(id);
            return e;

        } catch (SQLException ex) {
            Logger.getLogger(EspaceCopyService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
            @Override
    public void removeEspaceCopy(int id) {
        try {
            String query = "delete from espace_copy where id =?";
             PreparedStatement pstmt = con.prepareStatement(query);
 
            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(EspaceCopyService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
