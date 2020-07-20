/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceadmin1;

import Core.Controller;
import Entity.User;
import IService.IUserService;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Achrafoun
 */
public class StatistiqueAdminController extends Controller implements Initializable {
    private IUserService userService = this.getService().getUserService();
    private ObservableList<String> rsx = FXCollections.observableArrayList();

    @FXML
    private AnchorPane holderPane;
    @FXML
    private Label total;
    @FXML
    private Label actifs;
    @FXML
    private Label inactifs;
    @FXML
    private BarChart<String, Integer> rsxchart;
    @FXML
    private PieChart activChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int nbActif = userService.countActifUsers();
        int nbInactif = userService.countInactifUsers();
        int nbTotal = userService.countUsers();
        total.setText(String.valueOf(nbTotal));
        actifs.setText(String.valueOf(nbActif));
        inactifs.setText(String.valueOf(nbInactif));
        //----BAR CHART
        rsx.add("Facebook");
        rsx.add("Twitter");
        rsx.add("Instagram");
        
        int nbFacebook = userService.getByFacebook();
        int nbTwitter = userService.getByTwitter();
        int nbInstagram = userService.getByInstagram();
        
        //xAxis.setCategories(rsx);
        
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(rsx.get(0), nbFacebook));
        series.getData().add(new XYChart.Data<>(rsx.get(1), nbTwitter));
        series.getData().add(new XYChart.Data<>(rsx.get(2), nbInstagram));
        rsxchart.getData().add(series);
        //----PIE CHART
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
            new PieChart.Data("Actifs", nbActif), 
            new PieChart.Data("Inactifs", nbInactif)
            );
        activChart.setData(pieChartData);
        activChart.setTitle("Activité");
        activChart.setClockwise(true);
        activChart.setLabelLineLength(50);
        activChart.setLabelsVisible(true);
        activChart.setStartAngle(180);
        
    }
    
}
