/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.Controller;
import Core.DataSource;
import Entity.Evenement;
import IService.IEvenementService;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author DJAZIA
 */
public class BarchartEventController extends Controller implements Initializable {

    @FXML
    private BarChart<?, ?> participationChart;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis x;
    
    private final IEvenementService ess = this.getService().getEvenementService();
    private Connection con = DataSource.getInstance().getConnection();

                
    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {

        x.setLabel("titre");
        y.setLabel("nbplaces");
        XYChart.Series set1 = new XYChart.Series<>();
        ObservableList ok = FXCollections.observableArrayList();
        List<Evenement> ev = ess.getEv();
        int[] ee = new int[12];
        for (Evenement e : ev) {
            int rami = e.getNbplaces();
            set1.getData().add(new XYChart.Data<>(e.getTitre(), e.getNbplaces()));
        }
            participationChart.getData().addAll(set1);   
}  
}
