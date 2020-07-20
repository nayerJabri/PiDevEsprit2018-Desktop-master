/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.Liste_categrorieController.p;
import Core.DataSource;
import Entity.Category;
import Entity.User;
import Entity.sujet;
import Service.UserService;
import Service.serviceCategory;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class Liste_utilisateurController implements Initializable {

    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> username;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableColumn<User, String> nom;
    @FXML
    private TableColumn<User, String> prenom;
    @FXML
    private TableColumn<User, String> reole;
Connection cnx= DataSource.getInstance().getConnection();
    public Statement ste;
    public ResultSet rs;
    private Label label;
    public static User p;
    public int i ;
    private ObservableList<User> list;
    @FXML
    private ImageView image;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         list = FXCollections.observableArrayList();

       
            ResultSet rs = null;  
        try {
            rs = cnx.createStatement().executeQuery("select * FROM user");
        } catch (SQLException ex) {
            Logger.getLogger(Liste_utilisateurController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (rs.next()) {
                
                
                
                
                list.add(new User(rs.getString("username"),rs.getString("email"),rs.getString("roles"),rs.getString("nom"),rs.getString("prenom")));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Liste_utilisateurController.class.getName()).log(Level.SEVERE, null, ex);
        }
             
       
   
     
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        reole.setCellValueFactory(new PropertyValueFactory<>("roles"));
 
        table.setItems(null);
        table.setItems(list);
        table.setEditable(true);

        
         username.setCellFactory(TextFieldTableCell.forTableColumn());
        email.setCellFactory(TextFieldTableCell.forTableColumn());
         nom.setCellFactory(TextFieldTableCell.forTableColumn());
         prenom.setCellFactory(TextFieldTableCell.forTableColumn());
         reole.setCellFactory(TextFieldTableCell.forTableColumn());
         
         
         table.setRowFactory(e->{
         
         TableRow<User> row = new TableRow<>();
         
         row.setOnMouseClicked(f->{
            
         
          File ff = new File(System.getProperty("user.dir")+"/src/Images/avatar.jpg");
           
            Image img = new Image(ff.toURI().toString());
            image.setImage(img);
        
         
         
         
         });
         
         
         
         
         
             return row;
         
         });
         
         
        
    }    

    @FXML
    private void supprimer(ActionEvent event) throws SQLException {
        
            UserService spa= new UserService();   
          spa.deleteData(event, list, table);  
        
        
        
    }
    
    
    
    
}
