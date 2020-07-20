
package Service;

import Core.DataSource;
import Entity.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;

public class serviceCategory {
    
    PreparedStatement pst;
    ResultSet rs;
    Connection cnx= DataSource.getInstance().getConnection();
    public Statement ste;

    public serviceCategory() {
        try {
            ste = cnx.createStatement();
                    } catch (SQLException ex) {
            Logger.getLogger(serviceCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void AjouterCat(Category c) throws SQLException{
        String req= "INSERT INTO categorie  (libelle,description,date_ajout,image) VALUES (?,?,?,?)";
        PreparedStatement prs= cnx.prepareStatement(req);
        prs.setString(1, c.getLibelle());
        prs.setString(2, c.getDescription());
        prs.setString(3, c.getDate_ajout());
        prs.setString(4, c.getImage());
        
        int executeUpdate = prs.executeUpdate();
        System.out.println(" Ajoutée");
    }
    
    public void supprimer(int idcat) throws SQLException {
        Scanner sc = new Scanner(System.in);
       
           String req = "DELETE from  categorie  WHERE id =?";
          PreparedStatement pre = cnx.prepareStatement(req);
            pre.setInt(1, idcat);
            System.out.println(" categorie supprimee ");
            pre.executeUpdate();
    }
    
    public ResultSet ConsulterTT() throws SQLException {
        
       String requete= "select * from categorie";
       rs = ste.executeQuery(requete);
       return rs;
     
    }
    
    public void update(int id) throws SQLException{

    
    
      Scanner sc = new Scanner(System.in);
        
        String a,b,e,c;
        
        
          System.out.println("le libelle");
        a= sc.next();
        System.out.println("la description");
         b= sc.next();
         System.out.println("la date d'ajout");
         c= sc.next();
         System.out.println("l'url image");
         e= sc.next();
        
      
     String req = "UPDATE `categorie` SET `libelle`=?,`description`=?,`date_ajout`=?,`image`=?  WHERE id=?";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setString(1,a);
        pre.setString(2,b);
        pre.setString(3, c);
        pre.setString(4, e);
         pre.setInt(5, id);
       
       
        pre.executeUpdate();
      
        System.out.println("Categorie Modifie avec Succees");
    
}
    
    public List<Category>ConsulterCategory(List<Category>list) throws SQLException {
        
        
        String req = "SELECT * from categorie";
        ResultSet res; 
        res = ste.executeQuery(req);
    while (res.next()) {
                Category p = new Category(res.getInt("id"),
                        res.getString("libelle"), 
                        res.getString("description"),
                        res.getString("date_ajout"));
                list.add(p);


            }

        
        return list;

        }
    
    
 public void deleteData(ActionEvent event,ObservableList<Category>data,TableView<Category> tablepanier) throws SQLException {
        
        data=FXCollections.observableArrayList();
        Category panier = tablepanier.getSelectionModel().getSelectedItem();
        int a = panier.getId_category();
        System.out.println(a);
        
        
        String queryy ="DELETE FROM categorie WHERE id=?";

        
        PreparedStatement prss= cnx.prepareStatement(queryy);
        prss.setInt(1, a);
        prss.executeUpdate();  
        tablepanier.getItems().removeAll(tablepanier.getSelectionModel().getSelectedItem());
        tablepanier.getSelectionModel().select(null);
        
      
               
    }

    public void update_libelle(TableColumn.CellEditEvent edit,ObservableList<Category>list,TableView<Category> table) throws SQLException {
         list=FXCollections.observableArrayList();
        Category p1= table.getSelectionModel().getSelectedItem();
        p1.setLibelle(edit.getNewValue().toString());
        
        String query ="update categorie set libelle=? WHERE (id =?) ";
        
        PreparedStatement prs= cnx.prepareStatement(query);
        prs.setString(1,(edit.getNewValue().toString()));
         prs.setInt(2,table.getSelectionModel().getSelectedItem().getId_category());
        prs.executeUpdate();
        table.getSelectionModel().select(null);
    }

    public void update_description(TableColumn.CellEditEvent edit,ObservableList<Category>list,TableView<Category> table) throws SQLException {
        
            list=FXCollections.observableArrayList();
        Category p1= table.getSelectionModel().getSelectedItem();
        p1.setLibelle(edit.getNewValue().toString());
        
        String query ="update categorie set description=? WHERE (id =?) ";
        
        PreparedStatement prs= cnx.prepareStatement(query);
        prs.setString(1,(edit.getNewValue().toString()));
         prs.setInt(2,table.getSelectionModel().getSelectedItem().getId_category());
        prs.executeUpdate();
        table.getSelectionModel().select(null);
       
    }
    
    
}

