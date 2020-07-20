/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceadmin1;

import Core.Controller;
import Entity.User;
import IService.IUserService;
import Utility.Sexe;
import java.net.URL;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.table.TableFilter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Achrafoun
 */
public class ListUtilAdminController extends Controller implements Initializable {
    
    private IUserService userService = this.getService().getUserService();
    private ObservableList<User> masterData = FXCollections.observableArrayList();
    

    @FXML
    private AnchorPane holderPane;
    private TextField filterField;
    @FXML
    private TableColumn<User, String> nom;
    @FXML
    private TableColumn<User, String> prenom;
    @FXML
    private TableColumn<User, Date> dn;
    @FXML
    private TableColumn<User, String> sexe;
    @FXML
    private TableColumn<User, String> tel;
    @FXML
    private TableColumn<User, String> occ;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> ville;
    @FXML
    private TextField filterNom;
    @FXML
    private ComboBox<Sexe> filterGender;
    @FXML
    private TextField filterVille;
    @FXML
    private Button export;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Sexe h = new Sexe("Homme", "M");
        Sexe f = new Sexe("Femme", "F");
        ObservableList<Sexe> sl = FXCollections.observableArrayList(h,f);
        filterGender.setItems(sl);
        
        TableFilter filter = new TableFilter(userTable);
        List<User> users = userService.getAllUsers();
        masterData.addAll(users);
        nom.setCellValueFactory(new PropertyValueFactory<User, String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<User, String>("prenom"));
        dn.setCellValueFactory(new PropertyValueFactory<User, Date>("dateNaissance"));
        
        sexe.setCellValueFactory(new PropertyValueFactory<User, String>("genre"));
        ville.setCellValueFactory(new PropertyValueFactory<User, String>("ville"));
        tel.setCellValueFactory(new PropertyValueFactory<User, String>("tel"));
        occ.setCellValueFactory(new PropertyValueFactory<User, String>("occupation"));
        //userTable.getItems().setAll(masterData);
        
        FilteredList<User> filteredData = new FilteredList<>(masterData, p -> true);
               
        
        filterNom.textProperty().addListener((obsVal, oldValue, newValue) -> {
            filteredData.setPredicate(user -> user.getNom().toLowerCase().contains(filterNom.getText().toLowerCase())                
               && user.getVille().toLowerCase().contains(filterVille.getText().toLowerCase())
               //&& user.getGenre().contains(filterGender.getSelectionModel().getSelectedItem().getValue())
            );
        });
        filterGender.valueProperty().addListener((obsVal, oldValue, newValue) -> {
            filteredData.setPredicate(user -> user.getNom().toLowerCase().contains(filterNom.getText().toLowerCase()) 
               && user.getGenre().contains(filterGender.getSelectionModel().getSelectedItem().getValue())
               && user.getVille().toLowerCase().contains(filterVille.getText().toLowerCase())
            );
        });
        filterVille.textProperty().addListener((obsVal, oldValue, newValue) -> {
            filteredData.setPredicate(user -> user.getNom().toLowerCase().contains(filterNom.getText().toLowerCase()) 
               //&& user.getGenre().contains(filterGender.getSelectionModel().getSelectedItem().getValue())
               && user.getVille().toLowerCase().contains(filterVille.getText().toLowerCase())
            );
        });
        
        /*filterNom.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (user.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (user.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });                
        filterGender.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.getValue().isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.getValue();

                if (user.getGenre().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });*/
        
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(userTable.comparatorProperty());
        
        userTable.setItems(sortedData);
    }    

    @FXML
    private void exportAction(ActionEvent event) throws SQLException {
        FileChooser chooser = new FileChooser();
        String fileAsString = "";
        File file = chooser.showSaveDialog(null);
        if (file != null) {
            fileAsString = file.toString();
        }
        //-------
        try {
            /* Define the SQL query */
            ResultSet query_set = userService.getAllUsersRS();
            /* Step-2: Initialize PDF documents - logical objects */
            Document my_pdf_report = new Document();
            PdfWriter.getInstance(my_pdf_report, new FileOutputStream(fileAsString+".pdf"));
            my_pdf_report.open();            
            //we have four columns in our table
            PdfPTable my_report_table = new PdfPTable(4);
            //create a cell object
            PdfPCell table_cell;

            while (query_set.next()) {                
                            String dept_id = query_set.getString("username");
                            table_cell=new PdfPCell(new Phrase(dept_id));
                            my_report_table.addCell(table_cell);
                            String dept_name=query_set.getString("email");
                            table_cell=new PdfPCell(new Phrase(dept_name));
                            my_report_table.addCell(table_cell);
                            String manager_id=query_set.getString("nom");
                            table_cell=new PdfPCell(new Phrase(manager_id));
                            my_report_table.addCell(table_cell);
                            String location_id=query_set.getString("prenom");
                            table_cell=new PdfPCell(new Phrase(location_id));
                            my_report_table.addCell(table_cell);
                            }
            /* Attach report table to PDF */
            my_pdf_report.add(my_report_table);                       
            my_pdf_report.close();

            /* Close all DB related objects */
            query_set.close();              

        } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        } catch (DocumentException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();        
        }    
    }
    
}
