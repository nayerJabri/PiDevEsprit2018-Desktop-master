/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Controller.SujetController;
import Core.DataSource;
import Entity.Category;
import Entity.User;
import IService.IUserService;
import interfaceadmin1.UIController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;

/**
 *
 * @author hero
 */
public class UserService implements IUserService {
    private Connection con = DataSource.getInstance().getConnection();
    private Statement ste;

    public UserService() {
        try {
            ste = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    @Override
    public List<User> searchResult(int id, Date datemin, Date datemax, String gender, List<String> occupation, List<String> religion, List<String> pays, 
            List<String> ville, List<String> region, List<String> films, List<String> series, List<String> livres, List<String> musiques) {
        List<User> users = new ArrayList<>();
        List<String> interets = new ArrayList<>();
        boolean empty = true;
        Map<Integer,String> argsMap = new HashMap<>();
        int counter = 3;
        try {
            String query = "select DISTINCT * from user u left join `centre_interet` i on u.id = i.IdUser "
                    + "where (`date_naissance` BETWEEN  ? AND ? ) and (u.id != ?) ";
            if(!"".equals(gender))
            {
                query += "and ( genre in (?) ) ";
                argsMap.put(++counter,gender);
            }
            if(!occupation.isEmpty())
            {
                query+="and (u.occupation in ("+statementArgs(occupation) +")) ";
                for(String s : occupation)
                    argsMap.put(++counter, s);
            }
            if(!religion.isEmpty())
            {
                query+="and (u.religion in ("+statementArgs(religion) +")) ";
                for(String s : religion)
                    argsMap.put(++counter, s);
            }
            if(!pays.isEmpty())
            {
                query+="and (u.pays in ("+statementArgs(pays) +")) ";
                for(String s : pays)
                    argsMap.put(++counter, s);
            }
            if(!ville.isEmpty())
            {
                query+="and (u.religion in ("+statementArgs(ville) +")) ";
                for(String s : ville)
                    argsMap.put(++counter, s);
            }
            if(!region.isEmpty())
            {
                query+="and (region in ("+statementArgs(region) +")) ";
                for(String s : region)
                    argsMap.put(++counter, s);
            }
            if(!films.isEmpty())
            {
                interets.addAll(films);
                empty = false;
            }
            if(!series.isEmpty())
            {
                interets.addAll(series);
                empty = false;
            }
            if(!livres.isEmpty())
            {
                interets.addAll(livres);
                empty = false;
            }
            if(!musiques.isEmpty())
            {
                interets.addAll(musiques);
                empty = false;
            }
            if(!empty)
            {
                query+="and (i.contenu in ("+statementArgs(interets) +")) ";
                for(String s : interets)
                    argsMap.put(++counter, s);
            }
            query +="group by nom,prenom,username";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setDate(1, datemax);
            ps.setDate(2, datemin);
            ps.setInt(3, id);
            for(Map.Entry<Integer,String> entry:argsMap.entrySet())
            {
                ps.setString(entry.getKey(), entry.getValue());
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                users.add(User.createUser(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }
    
    private String statementArgs(List<String> args)
    {
        String t="";
        for(int i=0;i<args.size();i++)
        {
          t+=",?";
        }
        t=t.replaceFirst(",","");
        return t;
    }

    @Override
    public List<User> getAllUsers() {
        String req = "SELECT * FROM user where roles NOT LIKE '%ROLE_SUPER_ADMIN%'";
        List<User> users = new ArrayList<>();
        try {
            
            ResultSet rs = ste.executeQuery(req);            
            while (rs.next()){
                users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getBoolean("enabled"), rs.getString("salt"), rs.getDate("last_Login"), rs.getString("roles"), rs.getString("nom"), rs.getString("prenom"), rs.getDate("date_Naissance"), rs.getString("genre"), rs.getString("pays"), rs.getString("region"), rs.getString("ville"), rs.getString("tel"), rs.getString("place_Naiss"), rs.getString("religion"), rs.getString("apropos"), rs.getString("facebook"), rs.getString("twitter"), rs.getString("instagram"), rs.getString("image"), rs.getDate("updated_At"), rs.getString("occupation")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return users;
    }

    @Override
    public User getUserById(int id) {
        try {
            String req = "select * from user where id=?";
            User u = null;
            PreparedStatement ps = con.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getBoolean("enabled"), rs.getString("salt"), rs.getDate("last_Login"), rs.getString("roles"), rs.getString("nom"), rs.getString("prenom"), rs.getDate("date_Naissance"), rs.getString("genre"), rs.getString("pays"), rs.getString("region"), rs.getString("ville"), rs.getString("tel"), rs.getString("place_Naiss"), rs.getString("religion"), rs.getString("apropos"), rs.getString("facebook"), rs.getString("twitter"), rs.getString("instagram"), rs.getString("image"), rs.getDate("updated_At"), rs.getString("occupation"));
            }
            return u;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int countUsers() {
        String req = "SELECT count(*) as cu FROM user where roles not like '%ROLE_SUPER_ADMIN%'";
        ResultSet rs= null;
        try {
            rs = ste.executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        int cu = 0;
        if(rs != null){
            try {
                while (rs.next()){
                    cu = rs.getInt("cu");
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cu;
    }

    @Override
    public int countActifUsers() {
        String req = "SELECT COUNT(*) as cu FROM user u WHERE DATE_ADD(u.last_login, INTERVAL 7 DAY)>= now() and roles not like '%ROLE_SUPER_ADMIN%'";
        ResultSet rs= null;
        try {
            rs = ste.executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        int cu = 0;        
            try {
                while (rs.next()){
                    cu = rs.getInt("cu");
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }        
        return cu;
    }

    @Override
    public int countInactifUsers() {
        String req = "SELECT COUNT(*) as cu FROM user u WHERE DATE_ADD(u.last_login, INTERVAL 7 DAY)< now() and roles not like '%ROLE_SUPER_ADMIN%'";
        ResultSet rs= null;
        try {
            rs = ste.executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        int cu = 0;        
            try {
                while (rs.next()){
                    cu = rs.getInt("cu");
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }        
        return cu;
    }

    @Override
    public List<User> getSuggestionUsers(User u) {
        
        try {
            String req = "SELECT * FROM user where salt like ? and id!=? and roles NOT LIKE '%ROLE_SUPER_ADMIN%'";
            PreparedStatement ps = con.prepareStatement(req);
            ps.setString(1, u.getSalt());
            ps.setInt(2, u.getId());            
            List<User> users = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getBoolean("enabled"), rs.getString("salt"), rs.getDate("last_Login"), rs.getString("roles"), rs.getString("nom"), rs.getString("prenom"), rs.getDate("date_Naissance"), rs.getString("genre"), rs.getString("pays"), rs.getString("region"), rs.getString("ville"), rs.getString("tel"), rs.getString("place_Naiss"), rs.getString("religion"), rs.getString("apropos"), rs.getString("facebook"), rs.getString("twitter"), rs.getString("instagram"), rs.getString("image"), rs.getDate("updated_At"), rs.getString("occupation")));
            }                        
            return users;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<User> getBlockedUsers() {
        String req = "SELECT * FROM user where enabled != 1 and roles not like '%ROLE_SUPER_ADMIN%'";
        ResultSet rs= null;
        try {
            rs = ste.executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<User> users = new ArrayList<>();
        try {
            while (rs.next()){
                users.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getBoolean("enabled"), rs.getString("salt"), rs.getDate("last_Login"), rs.getString("roles"), rs.getString("nom"), rs.getString("prenom"), rs.getDate("date_Naissance"), rs.getString("genre"), rs.getString("pays"), rs.getString("region"), rs.getString("ville"), rs.getString("tel"), rs.getString("place_Naiss"), rs.getString("religion"), rs.getString("apropos"), rs.getString("facebook"), rs.getString("twitter"), rs.getString("instagram"), rs.getString("image"), rs.getDate("updated_At"), rs.getString("occupation")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    @Override
    public void modifierUserPhoto(User u) {
        try {
            String req = "update user set image=?, updated_at=? where id=? ";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, u.getImage());
            java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            pre.setTimestamp(2, date);
            pre.setInt(3, u.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifierUser(User u) {
        try {
            String req = "update user set nom=?, prenom=?, date_naissance=?, genre=?, pays=?, region=?, ville=?, tel=?, place_naiss=?, religion=?, apropos=?, facebook=?, twitter=?, instagram=?, occupation=? where id=? ";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, u.getNom());
            pre.setString(2, u.getPrenom());
            pre.setDate(3, (Date) u.getDateNaissance());
            pre.setString(4, u.getGenre());
            pre.setString(5, u.getPays());
            pre.setString(6, u.getRegion());
            pre.setString(7, u.getVille());
            pre.setString(8, u.getTel());
            pre.setString(9, u.getPlaceNaiss());
            pre.setString(10, u.getReligion());
            pre.setString(11, u.getApropos());
            pre.setString(12, u.getFacebook());
            pre.setString(13, u.getTwitter());
            pre.setString(14, u.getInstagram());
            pre.setString(15, u.getOccupation());
            
            pre.setInt(16, u.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public ResultSet getAllUsersRS() {
        String req = "SELECT * FROM user where roles NOT LIKE '%ROLE_SUPER_ADMIN%'";
        ResultSet rs = null;
        try {
            
            rs = ste.executeQuery(req);                        
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }

    @Override
    public int getByFacebook() {
        String req = "SELECT count(*) as cu FROM user u where u.facebook not like '' and u.roles NOT LIKE '%ROLE_SUPER_ADMIN%' ";
        ResultSet rs= null;
        try {
            rs = ste.executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        int cu = 0;
        try {
            while (rs.next()){
                cu = rs.getInt("cu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cu;
    }

    @Override
    public int getByTwitter() {
        String req = "SELECT count(*) as cu FROM user u where u.twitter not like '' and u.roles NOT LIKE '%ROLE_SUPER_ADMIN%' ";
        ResultSet rs= null;
        try {
            rs = ste.executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        int cu = 0;
        try {
            while (rs.next()){
                cu = rs.getInt("cu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cu;
    }

    @Override
    public int getByInstagram() {
        String req = "SELECT count(*) as cu FROM user u where u.instagram not like '' and u.roles NOT LIKE '%ROLE_SUPER_ADMIN%' ";
        ResultSet rs= null;
        try {
            rs = ste.executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        int cu = 0;
        try {
            while (rs.next()){
                cu = rs.getInt("cu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cu;
    }


    @Override
    public void updateLoginPlace(User u) {
        try {
            String req = "update user set salt=? where id=? ";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, u.getSalt());
            pre.setInt(2, u.getId());            
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    public void deleteData(ActionEvent event,ObservableList<User>list,TableView<User> table) throws SQLException {
        
           list=FXCollections.observableArrayList();
        User panier = table.getSelectionModel().getSelectedItem();
        String a = panier.getEmail();
     
        String queryy ="DELETE FROM user WHERE email=?";

        
        PreparedStatement prss= con.prepareStatement(queryy);
        prss.setString(1, a);
        prss.executeUpdate();  
        table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
        table.getSelectionModel().select(null);
             
       
    }

    
    
}
