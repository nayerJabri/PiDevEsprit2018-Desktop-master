/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;



import Controller.beblio;
import Core.DataSource;
import Core.Controller;
import Entity.sujet;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ASUS
 */
public class serviceSujet {
     PreparedStatement pst;
    ResultSet rs;
    Connection cnx= DataSource.getInstance().getConnection();
    public Statement ste;
    java.util.Date date;
    
     public serviceSujet() {
        try {
            ste = cnx.createStatement();
                    } catch (SQLException ex) {
            Logger.getLogger(serviceSujet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
      public void AjouterSujet(sujet s , int idc) throws SQLException, IOException{
          System.out.println("hhhhcvxcvhhhh======>"+Controller.getUserId());
          
           String req1= "SELECT * from categorie where id=?";
        PreparedStatement prs1= cnx.prepareStatement(req1);
        prs1.setInt(1, idc);
        ResultSet rs = prs1.executeQuery();
        rs.first();
        int x = rs.getInt("nombrePost");
        
         String req2= "update categorie set nombrePost=? WHERE (id =?) ";
        PreparedStatement prs2= cnx.prepareStatement(req2);
        
        prs2.setInt(1, x+1);
        prs2.setInt(2, idc);
        prs2.executeUpdate();
        
 
                  String req= "INSERT INTO post  (titre,contenu,datePublication,image,IdCategorie,IdUser) VALUES (?,?,?,?,?,?)";
        PreparedStatement prs= cnx.prepareStatement(req);
        prs.setString(1, s.getTitre());
        prs.setString(2, s.getContenu());

         java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        prs.setDate(3, sqlDate);
        prs.setString(4, s.getImage());
         prs.setObject(5, idc);
         prs.setObject(6, Controller.getUserId());

        int executeUpdate = prs.executeUpdate();
       

        System.out.println(" Ajoutée");
       
    }
     
      public void SupprimerSujet(int ids , int idc) throws SQLException{
      
           String req1= "SELECT * from categorie where id=?";
        PreparedStatement prs1= cnx.prepareStatement(req1);
        prs1.setInt(1, idc);
        ResultSet rs = prs1.executeQuery();
        rs.first();
        int x = rs.getInt("nombrePost");
        
         String req2= "update categorie set nombrePost=? WHERE (id =?) ";
        PreparedStatement prs2= cnx.prepareStatement(req2);
        
        prs2.setInt(1, x-1);
        prs2.setInt(2, idc);
        prs2.executeUpdate();
          
          
       String req = "DELETE from  post  WHERE id =?";
          PreparedStatement pre = cnx.prepareStatement(req);
            pre.setInt(1, ids);
            System.out.println(" sujet supprimé ");
            pre.executeUpdate();
      
      
      }
      
      
      
         public ResultSet Listfavoris(Integer id) throws SQLException {
     
          String req=  "Select * from jaime where IdUser=? ";
          PreparedStatement prs= cnx.prepareStatement(req);
          prs.setInt(1, id);
          rs= prs.executeQuery();
          return rs;
    }
      
               public ResultSet donnes_favoris(Integer id) throws SQLException {
     
          String req=  "Select * from post where id=? ";
          PreparedStatement prs= cnx.prepareStatement(req);
          prs.setInt(1, id);
          rs= prs.executeQuery();
          return rs;
    }
               
               
          public ResultSet Listsujet(Integer id) throws SQLException {
     
          String req=  "Select * from post where IdCategorie=? ";
          PreparedStatement prs= cnx.prepareStatement(req);
          prs.setInt(1, id);
          rs= prs.executeQuery();
          return rs;
    }
               
       public void Ajouterfavoris(Integer iduser , Integer idpost) throws SQLException, IOException{
       
          
                  String req= "INSERT INTO jaime  (IdUser,IdPost) VALUES (?,?)";
        PreparedStatement prs= cnx.prepareStatement(req);
        prs.setInt(1, iduser);
        prs.setInt(2, idpost);

        prs.executeUpdate();
        System.out.println(" Ajoutée");
       
    }
          
                public ResultSet ConditionButton(Integer iduser , Integer idpost) throws SQLException {
     
          String req=  "Select * from jaime where IdUser=? and IdPost=? ";
          PreparedStatement prs= cnx.prepareStatement(req);
          prs.setInt(1, iduser);
          prs.setInt(2, idpost);
          
          return prs.executeQuery();
    }  
                
                
                
       public void Supprimerfavoris(Integer id) throws SQLException, IOException{
       
          
                  String req= "DELETE from jaime where id=?";
        PreparedStatement prs= cnx.prepareStatement(req);
        prs.setInt(1, id);
        prs.executeUpdate();

       
    }

    public void ModifierSujet(sujet V, Integer idc) throws SQLException {
       
        
           System.out.println("hhhhcvxcvhhhh======>"+Controller.getUserId());
          
                  String req= "update post set titre=? , contenu=? , datePublication=? , image=? , IdCategorie=? , IdUser=? where id=?";
        PreparedStatement prs= cnx.prepareStatement(req);
        prs.setString(1, V.getTitre());
        prs.setString(2, V.getContenu());

         java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        prs.setDate(3, sqlDate);
        prs.setString(4, V.getImage());
         prs.setObject(5, idc);
         prs.setObject(6, Controller.getUserId());
         prs.setObject(7, beblio.getIds());
  


        prs.executeUpdate();
        
        
    }

    public ResultSet Listsujetrecherche(int idc, String recherche) throws SQLException {
         String req=  "Select * from post where IdCategorie=? and titre=? ";
          PreparedStatement prs= cnx.prepareStatement(req);
          prs.setInt(1, idc);
          prs.setString(2, recherche);
          rs= prs.executeQuery();
          return rs;
    }
                
                
}
