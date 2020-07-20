/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.Evenement;
import IService.IEvenementService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author hero
 */
public class EvenementService implements IEvenementService {

    private Connection con = DataSource.getInstance().getConnection();

    @Override
    public Evenement getEvenementById(int id) {
        try {
            String query = "select * from evenement where id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Evenement evenement;
            while (rs.next()) {
                evenement = new Evenement(rs.getInt("id"), rs.getString("image_eve"), rs.getInt("nbplaces"), rs.getDate("dateEvenement"), rs.getString("titre"), rs.getString("description"), rs.getString("titreCordination"));
                return evenement;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Evenement insertEvenement(Evenement e) {
        try {
           
            String query = "INSERT INTO `evenement`(`image_eve`, `nbplaces`, `dateEvenement`, `titre`, `description`, `titreCordination`) VALUES(?,?,?,?,?,?)";
            PreparedStatement prs = con.prepareStatement(query);
            
            prs.setString(1, e.getImageEve());
            
            prs.setInt(2, 0);
            prs.setDate(3, e.getDateEvenement());
            prs.setString(4, e.getTitre());
            prs.setString(5, e.getDescription());
            prs.setString(6, e.getTitreCordination());
            
            int id = prs.executeUpdate();
            e.setId(id);
            return e;
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ObservableList<Evenement> getAll() {

        try {
            String query = "select * from evenement";
            Statement ste = con.createStatement();
            ResultSet rs = ste.executeQuery(query);
            ObservableList<Evenement> evenements = FXCollections.observableArrayList();
            while (rs.next()) {
                evenements.add(new Evenement(rs.getInt("id"), rs.getInt("nbplaces"), rs.getDate("dateEvenement"),
                            rs.getString("titre"), rs.getString("description"), rs.getString("titreCordination")));

            }
            return evenements;
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
   
    public ObservableList<Evenement> getEv() {
        try {
            String query = "select * from evenement WHERE dateEvenement > NOW()";

            Statement ste = con.createStatement();
            ResultSet rs = ste.executeQuery(query);
            ObservableList<Evenement> evenements = FXCollections.observableArrayList();
            while (rs.next()) {
                evenements.add(new Evenement(rs.getInt("id"), rs.getString("image_eve") , rs.getInt("nbplaces"), rs.getDate("dateEvenement"), rs.getString("titre"), rs.getString("description"), rs.getString("titreCordination")));
            }
            System.out.println(evenements.size());
            return evenements;
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean deleteEvenement(int ide) {
        try {
            Connection con = DataSource.getInstance().getConnection();
            String query = "DELETE from evenement where id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, ide);
            ps.executeUpdate();
            System.out.println("Suppression avec Succés !");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    
    public void updateEvenement(Evenement e, int id) {

        try {
            String query ="Update evenement set titre = ?,titreCordination=?,description=?,dateEvenement=? where id = "+id;
            PreparedStatement prs = con.prepareStatement(query);
            
            prs.setString(1,e.getTitre());
            prs.setString(2,e.getTitreCordination());
            prs.setString(3,e.getDescription());
            prs.setDate(4,(Date) e.getDateEvenement());
            prs.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
//        public void ModifierFormation(Formation f,int id) throws SQLException{
//            String req ="UPDATE Formation SET nomFor = ?,place=?,idTypeFor=?,description=?,dateFor=? where idFor = "+idFor;
//            PreparedStatement ps = con.prepareStatement(req);
//            ps.setString(1,f.getNomFor());
//            ps.setString(2,f.getPlace());
//            ps.setInt(3,f.getIdTypeFor().getIdTypeFor());
//            ps.setString(4,f.getDescriptionFor());
//            ps.setDate(5,(Date) f.getDateFor());
//            
//            ps.executeUpdate();
//    }


}
