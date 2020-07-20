/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.Controller;
import Core.DataSource;
import Entity.Category;
import Entity.sujet;
import Service.serviceCategory;
import Service.serviceSujet;
import interfaceadmin1.UIController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.util.Collections.list;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class Liste_categrorieController implements Initializable {

    private TableColumn<Category, Integer> id;
    @FXML
    private TableColumn<Category, String> libelle;
    @FXML
    private TableColumn<Category, String> description;
    @FXML
    private TableColumn<Category, String> date;
    @FXML
    private Button ajouter;
    @FXML
    private TableView<Category> table;
    
    Connection cnx= DataSource.getInstance().getConnection();
    public Statement ste;
    public ResultSet rs;
    private Label label;
    public static Category p;
    public int i ;
    
     private ObservableList<Category> list;
    @FXML
    private Button delete;
    @FXML
    private TableView<sujet> table1;
    @FXML
    private TableColumn<sujet, String> titre;
    @FXML
    private TableColumn<sujet, String> contenu;
    @FXML
    private TableColumn<sujet, String> date1;
     private ObservableList<sujet> list1;
    @FXML
    private Button consulter;
    @FXML
    private ImageView image;
     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         File ff = new File(System.getProperty("user.dir")+"/src/Images/avatar.jpg");
           
            Image img = new Image(ff.toURI().toString());
            image.setImage(img);
        
         System.out.println("======> + ====>"+Controller.getUserId());
          serviceCategory sv = new serviceCategory();
          list = FXCollections.observableArrayList();

        try {  
            ResultSet rs= cnx.createStatement().executeQuery("select * FROM categorie");
             while (rs.next()) {
                 
                list.add(new Category(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) { 
            Logger.getLogger(Liste_categrorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(list);
        libelle.setCellValueFactory(new PropertyValueFactory<>("Libelle"));
        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        date.setCellValueFactory(new PropertyValueFactory<>("date_ajout"));
 
        table.setItems(null);
        table.setItems(list);
        table.setEditable(true);

        
        libelle.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        date.setCellFactory(TextFieldTableCell.forTableColumn());
        
        
          
        

    }    

    @FXML
    private void ajouter(ActionEvent event) {
        
        
           Parent root = null;
                 try {
                     root = FXMLLoader.load(getClass().getResource("../View/Admin_categorie.fxml"));
                     UIController.setNode(root);
                 } catch (IOException ex) {
                     Logger.getLogger(SujetController.class.getName()).log(Level.SEVERE, null, ex);
                 }  
        
    }
    @FXML
    private void update_libelle(TableColumn.CellEditEvent<Category,String> event) throws SQLException {
        
          serviceCategory spa= new serviceCategory();
          spa.update_libelle(event, list, table);   
    }
    @FXML
    private void udpate_description(TableColumn.CellEditEvent<Category,String> event) throws SQLException {
        serviceCategory spa= new serviceCategory();
          spa.update_description(event, list, table); 
        
    }

    @FXML
    private void deleteData(ActionEvent event) throws SQLException {
                 serviceCategory spa= new serviceCategory();
          spa.deleteData(event, list, table);    
    }

    @FXML
    private void consulter(ActionEvent event) throws SQLException {
         p = (Category) table.getSelectionModel().getSelectedItem();
          i=p.getId_category();
          
          list1 = FXCollections.observableArrayList();

        try {  
            serviceSujet ss = new serviceSujet();
            
            ResultSet rs = ss.Listsujet(i);
            
             while (rs.next()) {
                 
                list1.add(new sujet(rs.getString("titre"),rs.getString("contenu"),rs.getString("datePublication"),rs.getString("image")));
                       


            }
        } catch (SQLException ex) { 
            Logger.getLogger(Liste_categrorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(list1);
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        contenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        date1.setCellValueFactory(new PropertyValueFactory<>("datePublication"));
 
        table1.setItems(null);
        table1.setItems(list1);
        table1.setEditable(true);

        
        titre.setCellFactory(TextFieldTableCell.forTableColumn());
        contenu.setCellFactory(TextFieldTableCell.forTableColumn());
        date1.setCellFactory(TextFieldTableCell.forTableColumn());
        
        
         p = (Category) table.getSelectionModel().getSelectedItem();
          i=p.getId_category();
          
           String req=  "Select * from categorie where id=? ";
          PreparedStatement prs = null;
        try {
            prs = cnx.prepareStatement(req);
        } catch (SQLException ex) {
            Logger.getLogger(Liste_categrorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            prs.setInt(1, i);
        } catch (SQLException ex) {
            Logger.getLogger(Liste_categrorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rs= prs.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Liste_categrorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
          rs.first();
          File ff = new File(System.getProperty("user.dir")+"/src/Images/" + rs.getString("image"));
           
            Image img = new Image(ff.toURI().toString());
            image.setImage(img);
      
    }

   

   

 

 
}
