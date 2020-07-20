/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Espace;
import Service.EspaceService;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.sun.prism.PhongMaterial.MapType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * FXML Controller class
 *
 * @author hechem
 */
public class MapareaController implements Initializable, MapComponentInitializedListener {

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    @FXML
    private Slider slider;

    private EspaceService Eservice = new EspaceService();
    private List<Marker> markers = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        slider.setMin(0);
        slider.setMax(700);
        slider.setValue(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        TextField addresse = new TextField();
        addresse.setPromptText("entrez votre addresse");
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {

                if (markers != null) {
                    for (Marker marker : markers) {
                        map.removeMarker(marker);
                    }
                }

                List<Espace> espaces = Eservice.findByDistance(36.849109, 10.181531600000001, (double) new_val);
                markers = new ArrayList<Marker>();
   
                for (int i = 0; i < espaces.size(); i++) {
                       Espace esp = Eservice.getEspaceById(espaces.get(i).getId());
                    LatLong position = new LatLong(esp.getLat(), esp.getLongi());

                    Marker marker = new Marker(new MarkerOptions().position(position).title(esp.getTitre()).animation(Animation.DROP));
                    map.addMarker(marker);
                    markers.add(marker);

                    InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                    String adresse = "<h2 style='text-align: center'>" + esp.getTitre() ;
                    infoWindowOptions.content(adresse);
                    InfoWindow infowindow = new InfoWindow(infoWindowOptions);
                    infowindow.open(map, marker);

                }

            }

        });

        mapView.addMapInializedListener(this);
        

    }

    @Override
    public void mapInitialized() {

        LatLong ghazela = new LatLong(36.898392, 10.189732);

        MapOptions mapOptions = new MapOptions();
        LatLong position = new LatLong(36.849109, 10.181531600000001);

        mapOptions.center(position)
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions);
        position = null;

        //Add markers to the map
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(ghazela);

        Marker esprit = new Marker(markerOptions1);
        map.addMarker(esprit);

        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content("<h2>on est là</h2>");
        InfoWindow infowindow = new InfoWindow(infoWindowOptions);
        infowindow.open(map, esprit);

    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344 * 1000;

        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
