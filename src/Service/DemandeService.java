/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.Demande;
import Entity.User;
import IService.IDemandeService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hero
 */
public class DemandeService implements IDemandeService{
    private Connection con = DataSource.getInstance().getConnection();

    @Override
    public Demande insertDemande(Demande demande) {
        try {
            String query  = "insert into `demande` (`requester`,`acceptor`,`dateDemande`) Values (?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,demande.getRequester().getId());
            ps.setInt(2,demande.getAcceptor().getId());
            ps.setTimestamp(3, (Timestamp) demande.getDateDemande());
            int id = ps.executeUpdate();
            demande.setId(id);
            return demande;
        } catch (SQLException ex) {
            Logger.getLogger(DemandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean deleteDemande(Demande demande) {
        try {
            String query  = "delete from `demande` where (requester = ? AND acceptor = ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,demande.getRequester().getId());
            ps.setInt(2, demande.getAcceptor().getId());
            return ps.executeUpdate() > 0 ;
        } catch (SQLException ex) {
            Logger.getLogger(DemandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Demande> getDemandesByUser(User user) {
        try {
            List<Demande> demandes = new ArrayList<>();
            String query = "select * from demande where acceptor = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,user.getId());
            ResultSet rs = ps.executeQuery();
            query = "select * from user where id = ?";
            ps = con.prepareStatement(query);
            while(rs.next())
            {
                User requester = new User();
                ps.setInt(1, rs.getInt("requester"));
                ResultSet rsu =  ps.executeQuery();
                while(rsu.next())
                {
                    requester.setId(rsu.getInt("id"));
                    requester.setUsername(rsu.getString("username"));
                    requester.setNom(rsu.getString("nom"));
                    requester.setPrenom(rsu.getString("prenom"));
                    requester.setGenre(rsu.getString("genre"));
                    requester.setPays(rsu.getString("pays"));
                    requester.setDateNaissance(rsu.getDate("date_naissance"));
                    requester.setOccupation(rsu.getString("occupation"));
                }
                Demande demande = new Demande(rs.getInt("id"), rs.getDate("dateDemande"), requester, user);
                demandes.add(demande);
            }
            return demandes;
        } catch (SQLException ex) {
            Logger.getLogger(DemandeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean checkDemande(User cUser, User oUser) {
        String query="SELECT * FROM demande where (requester in (?,?) AND acceptor in (?,?))";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cUser.getId());
            ps.setInt(2, oUser.getId());
            ps.setInt(3, cUser.getId());
            ps.setInt(4, oUser.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                rs.close();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RelationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
