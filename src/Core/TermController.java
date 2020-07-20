/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class TermController implements Initializable {

    @FXML
    private JFXTextArea txt;
    @FXML
    private JFXCheckBox terms;
    @FXML
    private JFXButton signupbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          try {
            FileReader reader = new FileReader("src\\term.txt");
            
            BufferedReader buffer = new BufferedReader(reader);
            String s;
            while((s=buffer.readLine())!=null)
            {
            txt.appendText(s + "\n");
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TermController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TermController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void signUp(ActionEvent event) {
        
            if (!terms.isSelected()){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Information Dialog");
      alert.setHeaderText(null);
      alert.setContentText("You must accept terms of use before signing up");
      alert.show();
        }
        else {
            Node node =(Node)event.getSource();
                Stage stage = (Stage)node.getScene().getWindow();
            stage.close();
        }
        
    }
    
}
