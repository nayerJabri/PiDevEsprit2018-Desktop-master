/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ASUS
 */
public class serviceCommentaire {
    
      PreparedStatement pst;
    ResultSet rs;
    Connection cnx= DataSource.getInstance().getConnection();
    public Statement ste;
    java.util.Date date;
    
    
      public ResultSet CommentaireByPost(Integer id) throws SQLException {
     
          String req= "Select * from commentaire_forum where IdPost=? ";
          PreparedStatement prs= cnx.prepareStatement(req);
          prs.setInt(1, id);
          rs= prs.executeQuery();
          return rs;
    }
    
     public void SupprimerCommentaire(int ids) throws SQLException{
      
       String req = "DELETE from  commentaire_forum  WHERE id =?";
          PreparedStatement pre = cnx.prepareStatement(req);
            pre.setInt(1, ids);
            System.out.println(" sujet supprimé ");
            pre.executeUpdate();
      
      
      }
     
       public void AjouterCommentaire(String contenu , Integer iduser , Integer idpost) throws SQLException, IOException{
         
          
                  String req= "INSERT INTO commentaire_forum (contenu,IdUser,IdPost) VALUES (?,?,?) ";
        PreparedStatement prs= cnx.prepareStatement(req);
        prs.setString(1, contenu);
        prs.setInt(2, iduser);
        prs.setInt(3, idpost);

        prs.executeUpdate();
       

        System.out.println("Commentaire Ajoutée");
       
    }
     
    
}
