/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.AvisEvenement;
import Entity.Evenement;
import IService.IAvisEvenementService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author hero
 */
public class AvisEvenementService implements IAvisEvenementService {
    private Connection con = DataSource.getInstance().getConnection();
    
    @Override
    public AvisEvenement insertAvis(AvisEvenement a)  {
       
           
        try {
            String query = "INSERT INTO `avis_evenement`(`contenu`,`idEvenement`) VALUES(?,?)";
            
            PreparedStatement prs = con.prepareStatement(query);
            
            prs.setString(1, a.getContenu());
            prs.setInt(2, a.getIdEvenement());
            
            int id = prs.executeUpdate();
            a.setId(id);
            return a;
        } catch (SQLException ex) {
            Logger.getLogger(AvisEvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
       
        

    }
    public ObservableList<AvisEvenement> getAvis() {
        try {
            String query = "select * from avis_evenement";

            Statement ste = con.createStatement();
            ResultSet rs = ste.executeQuery(query);
            ObservableList<AvisEvenement> avis_evenements = FXCollections.observableArrayList();
            while (rs.next()) {
                avis_evenements.add(new AvisEvenement(rs.getString("contenu"), rs.getInt("IdEvenement")));
            }
            return avis_evenements;
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ObservableList<AvisEvenement> getAvis(int idEvenement) {
        try {
            String query = "select * from avis_evenement where IdEvenement ="+idEvenement;

            Statement ste = con.createStatement();
            ResultSet rs = ste.executeQuery(query);
            ObservableList<AvisEvenement> avis_evenements = FXCollections.observableArrayList();
            while (rs.next()) {
                avis_evenements.add(new AvisEvenement(rs.getString("contenu"), rs.getInt("IdEvenement")));
            }
            return avis_evenements;
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
