/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.AvisEspace;
import IService.IAvisEspaceService;
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
public class AvisEspaceService implements IAvisEspaceService {

    private Connection con = DataSource.getInstance().getConnection();

    @Override
    public AvisEspace ajouterRating(AvisEspace e) {
        try {

            String query = "INSERT INTO `avis_espace`(`nbrating`, `rating`, `idEspace`, `idUser`) VALUES(?,?,?,?)";
            PreparedStatement prs = con.prepareStatement(query);

            prs.setInt(1, e.getNbrating());

            prs.setInt(2, e.getRating());
            prs.setInt(3, e.getIdEspace());
            prs.setInt(4, e.getIdUser());

            int id = prs.executeUpdate();
            e.setId(id);

            return e;

        } catch (SQLException ex) {
            Logger.getLogger(AvisEspace.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
       @Override
    public List<AvisEspace> getRating(int id) {
        try {
            String query = "select * from avis_espace where idEspace=? ";
            Statement ste = con.createStatement();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            List<AvisEspace> ratings = new ArrayList<>();
            while (rs.next()) {
                ratings.add(new AvisEspace(rs.getInt("id"),rs.getInt("nbrating"), rs.getInt("rating"),rs.getInt("idEspace"),rs.getInt("idUser")));
                
            }
            return ratings;
        } catch (SQLException ex) {
            Logger.getLogger(AvisEspace.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
