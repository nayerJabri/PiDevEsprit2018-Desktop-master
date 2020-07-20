/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.Controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.directions.DirectionsWaypoint;
import com.lynden.gmapsfx.service.directions.TravelModes;
import com.lynden.gmapsfx.service.elevation.ElevationResult;
import com.lynden.gmapsfx.service.elevation.ElevationServiceCallback;
import com.lynden.gmapsfx.service.elevation.ElevationStatus;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;
import javafx.event.EventHandler;
import com.lynden.gmapsfx.javascript.event.EventHandlers;
import com.lynden.gmapsfx.javascript.event.GFXEventHandler;
import Entity.Espace;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import Entity.PhotoEspace;
import IService.IEspaceService;
import IService.IPhotoEspaceService;
import Service.EspaceService;
import com.jfoenix.controls.JFXButton;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import static interfaceadmin1.UIController.rootP;
import static java.awt.SystemColor.info;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import static javax.swing.Spring.height;
import static javax.swing.Spring.width;
import netscape.javascript.JSObject;

/**
 * FXML Controller class
 *
 * @author Nayer Ben Jaber
 */
public class Espace_frontController extends Controller implements Initializable, MapComponentInitializedListener,
        ElevationServiceCallback, GeocodingServiceCallback, DirectionsServiceCallback {

    protected GoogleMapView mapComponent;
    protected GoogleMap map;
    protected DirectionsPane directions;

    private Button btnZoomIn;
    private Button btnZoomOut;
    private Label lblZoom;
    private Label lblCenter;
    private Label lblClick;
    private ComboBox<MapTypeIdEnum> mapTypeCombo;

    private MarkerOptions markerOptions2;
    private Marker myMarker2;
    private Button btnHideMarker;
    private Button btnDeleteMarker;
    FileChooser saveFileChooser = new FileChooser();
    File saveFile;
    File srcFile, destFile;
    private final IEspaceService espaceService = new EspaceService();
    private final IPhotoEspaceService photoService = this.getService().getPhotoEspaceService();
    @FXML
    private AnchorPane holderPane;
    @FXML
    private TextField titre;
    @FXML
    private TextArea description;
    @FXML
    private TextField email;
    @FXML
    private JFXButton photo;
    @FXML
    private TextField adresse;
    @FXML
    private JFXButton ajouter;
    @FXML
    private JFXButton photo1;
    @FXML
    private JFXButton photo11;
    @FXML
    private JFXButton photo12;
    private GeocodingService geocodingService;
    @FXML
    private JFXButton photo13;

    private StringProperty address = new SimpleStringProperty();
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Pane p;
    @FXML
    private JFXButton GetMap;
    @FXML
    private TextField lat;
    @FXML
    private TextField longi;
    @FXML
    private Label info;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {

        
             
        Espace e = new Espace(titre.getText(), description.getText(), email.getText(), adresse.getText(), srcFile.getName(), 0, this.getUser().getId(), Float.valueOf(longi.getText()), Float.valueOf(lat.getText()));
        
        PhotoEspace p = new PhotoEspace(srcFile.getName(), srcFile.getName(), srcFile.getName(), srcFile.getName(), 0);
             espaceService.ajoutEspace(e);
        photoService.ajoutPhoto(p);
        AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("../View/InfoEspacefront.fxml")));

        holderPane.getChildren().clear();
        holderPane.getChildren().setAll(parentContent);
         }
    

    @FXML
    private void UploadImage(ActionEvent event) {

        File file = saveFileChooser.showOpenDialog(null);
        if (file != null) {
            srcFile = file;
            if (srcFile != null) {
                try {
                    String p = System.getProperty("user.dir") + "/src/images/" + srcFile.getName();
                    copyFile(srcFile, new File(p));
                } catch (IOException ex) {
                    Logger.getLogger(Espace_frontController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        try (
                FileChannel in = new FileInputStream(sourceFile).getChannel();
                FileChannel out = new FileOutputStream(destFile).getChannel();) {

            out.transferFrom(in, 0, in.size());
        }
    }

    @FXML
    private void GetMap(ActionEvent event) {

        System.out.println("Java version: " + System.getProperty("java.home"));
        mapComponent = new GoogleMapView(Locale.getDefault().getLanguage(), null);
        mapComponent.addMapInializedListener(this);
        mapComponent.setPrefWidth(400);
        mapComponent.setPrefHeight(300);
        BorderPane bp = new BorderPane();
        ToolBar tb = new ToolBar();

        btnZoomIn = new Button("Zoom In");
        btnZoomIn.setOnAction(e -> {
            map.zoomProperty().set(map.getZoom() + 1);
        });
        btnZoomIn.setDisable(true);

        btnZoomOut = new Button("Zoom Out");
        btnZoomOut.setOnAction(e -> {
            map.zoomProperty().set(map.getZoom() - 1);
        });
        btnZoomOut.setDisable(true);

        lblZoom = new Label();
        lblCenter = new Label();
        lblClick = new Label();

        mapTypeCombo = new ComboBox<>();
        mapTypeCombo.setOnAction(e -> {
            map.setMapType(mapTypeCombo.getSelectionModel().getSelectedItem());
        });
        mapTypeCombo.setDisable(true);

        Button btnType = new Button("Map type");
        btnType.setOnAction(e -> {
            map.setMapType(MapTypeIdEnum.ROADMAP);
        });

        btnHideMarker = new Button("");
        btnHideMarker.setOnAction(e -> {
        });

        btnDeleteMarker = new Button("");
        btnDeleteMarker.setOnAction(e -> {
        });

        AnchorPane.getChildren().addAll(btnZoomIn, btnZoomOut, mapTypeCombo,
                new Label("Zoom: "), lblZoom,
                new Label("Center: "), lblCenter,
                new Label("Click: "), lblClick,
                btnHideMarker, btnDeleteMarker);

        AnchorPane.getChildren().add(mapComponent);
    }

    DirectionsRenderer renderer;

    @Override
    public void mapInitialized() {
        LatLong center = new LatLong(500.606189, -500.335842);
        mapComponent.addMapReadyListener(() -> {
            // This call will fail unless the map is completely ready.
            checkCenter(center);
        });
        mapComponent.setPrefWidth(400);
        mapComponent.setPrefHeight(300);
        MapOptions options = new MapOptions();
        options.center(center)
                .mapMarker(true)
                .zoom(1)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.ROADMAP);

        //[{\"featureType\":\"landscape\",\"stylers\":[{\"saturation\":-100},{\"lightness\":65},{\"visibility\":\"on\"}]},{\"featureType\":\"poi\",\"stylers\":[{\"saturation\":-100},{\"lightness\":51},{\"visibility\":\"simplified\"}]},{\"featureType\":\"road.highway\",\"stylers\":[{\"saturation\":-100},{\"visibility\":\"simplified\"}]},{\"featureType\":\"road.arterial\",\"stylers\":[{\"saturation\":-100},{\"lightness\":30},{\"visibility\":\"on\"}]},{\"featureType\":\"road.local\",\"stylers\":[{\"saturation\":-100},{\"lightness\":40},{\"visibility\":\"on\"}]},{\"featureType\":\"transit\",\"stylers\":[{\"saturation\":-100},{\"visibility\":\"simplified\"}]},{\"featureType\":\"administrative.province\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"water\",\"elementType\":\"labels\",\"stylers\":[{\"visibility\":\"on\"},{\"lightness\":-25},{\"saturation\":-100}]},{\"featureType\":\"water\",\"elementType\":\"geometry\",\"stylers\":[{\"hue\":\"#ffff00\"},{\"lightness\":-25},{\"saturation\":-97}]}]
        map = mapComponent.createMap(options, false);
        directions = mapComponent.getDirec();

        map.setHeading(123.2);

        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {

            LatLong ll = new LatLong((JSObject) obj.getMember("latLng"));
            map.clearMarkers();
            Marker marker = new Marker(new MarkerOptions().position(ll).title(adresse.getText()).animation(Animation.DROP));
           
            map.addMarker(marker);
            
            System.out.println("LatLong: lat: " + ll.getLatitude() + " lng: " + ll.getLongitude());
            lblClick.setText(ll.toString());
            lat.setText(String.valueOf(ll.getLatitude()));
            longi.setText(String.valueOf(ll.getLongitude()));

        });

        btnZoomIn.setDisable(false);
        btnZoomOut.setDisable(false);
        mapTypeCombo.setDisable(false);
        mapTypeCombo.getItems().addAll(MapTypeIdEnum.ALL);

        GeocodingService gs = new GeocodingService();

        DirectionsService ds = new DirectionsService();
        renderer = new DirectionsRenderer(true, map, directions);
        String z = adresse.getText();
        DirectionsWaypoint[] dw = new DirectionsWaypoint[1];
        dw[0] = new DirectionsWaypoint(z);
        System.out.println("location testing " + dw[0].getJSObject());

        //dw[1] = new DirectionsWaypoint("Juiz de Fora - MG");
        DirectionsRequest dr = new DirectionsRequest(
                z,
                z,
                TravelModes.DRIVING,
                dw);
        ds.getRoute(dr, this, renderer);

    }

    private void hideMarker() {
//		System.out.println("deleteMarker");

        boolean visible = myMarker2.getVisible();

        //System.out.println("Marker was visible? " + visible);
        myMarker2.setVisible(!visible);

//				markerOptions2.visible(Boolean.FALSE);
//				myMarker2.setOptions(markerOptions2);
//		System.out.println("deleteMarker - made invisible?");
    }

    private void deleteMarker() {
        //System.out.println("Marker was removed?");
        map.removeMarker(myMarker2);
    }

    private void checkCenter(LatLong center) {
//        System.out.println("Testing fromLatLngToPoint using: " + center);
//        Point2D p = map.fromLatLngToPoint(center);
//        System.out.println("Testing fromLatLngToPoint result: " + p);
//        System.out.println("Testing fromLatLngToPoint expected: " + mapComponent.getWidth()/2 + ", " + mapComponent.getHeight()/2);
    }

    public void elevationsReceived(ElevationResult[] ers, ElevationStatus es) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void geocodedResultsReceived(GeocodingResult[] grs, GeocoderStatus gs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void directionsReceived(DirectionsResult dr, DirectionStatus ds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void switchEspacefront(ActionEvent event) throws IOException {
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(("../View/InfoEspacefront.fxml")));
        AnchorPane parentContent = fxmlloader.load();
        holderPane.getChildren().clear();
        holderPane.getChildren().add(parentContent);
    }
    
}
