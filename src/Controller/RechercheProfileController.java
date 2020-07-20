/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import APIs.*;
import Core.Controller;
import Entity.Demande;
import Entity.User;
import IService.IDemandeService;
import IService.IRelationService;
import IService.IUserService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.RangeSlider;

/**
 * FXML Controller class
 *
 * @author hero
 */
public class RechercheProfileController extends Controller implements Initializable {
    
    private final IUserService userService = this.getService().getUserService();
    private final IDemandeService demandeService = this.getService().getDemandeService();
    private final IRelationService relationService = this.getService().getRelationService();
    private RangeSlider hSlider;
    Font prefFont;
    
    @FXML
    private VBox resultList;
    @FXML
    private ComboBox<?> filmComboBox;
    @FXML
    private ComboBox<String> genreComboBox;
    @FXML
    private ComboBox<String> occupationComboBox;
    @FXML
    private ComboBox<String> religionComboBox;
    @FXML
    private ComboBox<String> paysComboBox;
    @FXML
    private ComboBox<?> serieComboBox;
    @FXML
    private ComboBox<?> livreComboBox;
    @FXML
    private ComboBox<?> musiqueComboBox;
    @FXML
    private HBox group1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefFont = new Font("System Bold", 12);
        initializeAutoCompleteAPI();
        initializeFinalComboBox();
        rangeSliderCreator(group1);
    }
    
    private void initializeFinalComboBox()
    {  
        genreComboBox.getItems().add("");
        genreComboBox.getItems().add("Male");
        genreComboBox.getItems().add("Femelle");
        genreComboBox.selectionModelProperty().get().selectFirst();
        religionComboBox.getItems().add("");
        religionComboBox.getItems().add("Islam");
        religionComboBox.getItems().add("Christianisme");
        religionComboBox.getItems().add("Buddhism");
        religionComboBox.selectionModelProperty().get().selectFirst();
    }
    
    private void initializeAutoCompleteAPI()
    {
        new AutoCompleteAPI(filmComboBox,new TheMovieDBAPI(TheMovieDBAPI.Movie));
        new AutoCompleteAPI(serieComboBox,new TheMovieDBAPI(TheMovieDBAPI.Serie));
        new AutoCompleteAPI(livreComboBox,BookAPI.getInstance());
        new AutoCompleteAPI(musiqueComboBox,ArtistAPI.getInstance());
        new AutoCompleteAPI(occupationComboBox, OccupationAPI.getInstance());
        new AutoCompleteAPI(paysComboBox, PaysAPI.getInstance());
    }
    
    private void rangeSliderCreator(HBox hBox)
    {
        hSlider = new RangeSlider(18, 70, 18, 70);
        hSlider.setPrefHeight(30);
        hSlider.setOrientation(Orientation.HORIZONTAL);
        hSlider.setShowTickMarks(true);
        hSlider.setShowTickLabels(true);
        hSlider.setBlockIncrement(1);
        hSlider.setMajorTickUnit(4);
        hSlider.setMinorTickCount(10);
        TextField sliderValue = new TextField("18;70");
        sliderValue.setEditable(false);
        sliderValue.setPrefSize(50, 30);
        sliderValue.setFont(prefFont);
        hSlider.addEventHandler(EventType.ROOT, new EventHandler<Event>(){
            @Override
            public void handle(Event event) {
                if(hSlider.lowValueChangingProperty().get() || hSlider.highValueChangingProperty().get())
                {
                    sliderValue.setText(hSlider.lowValueProperty().intValue()+";"+hSlider.highValueProperty().intValue());
                }
            }
            
        });
        hBox.getChildren().add(hSlider);
        hBox.getChildren().add(sliderValue);
        HBox.setMargin(sliderValue, new Insets(0, 0, 0, 15));
    }
    
    private HBox searchResultItem(User user)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(new java.util.Date());
        int currentYear = c.get(Calendar.YEAR);
        c.setTime(user.getDateNaissance());
        int userBirthYear = c.get(Calendar.YEAR);
        HBox hBox = new HBox();
        hBox.setPrefSize(920, 60);
        ImageView userImage = new ImageView(getClass().getResource("../Images/"+user.getImage()).toExternalForm());
        userImage.setFitWidth(60);
        userImage.setFitHeight(60);
        userImage.setPickOnBounds(true);
        userImage.setPreserveRatio(true);
        userImage.setClip(new Circle(30, 30, 30));
        Label userName = new Label(user.getPrenom()+" "+user.getNom());
        userName.setPrefSize(200, 60);
        userName.setFont(prefFont);
        Label userAge = new Label("Age: "+(currentYear-userBirthYear));
        userAge.setPrefSize(70, 60);
        userAge.setFont(prefFont);
        Label userLocation = new Label(user.getVille()+", "+user.getPays());
        userLocation.setPrefSize(260, 60);
        userLocation.setFont(prefFont);
        Button profileButton = new Button("Ouvrir profile");
        profileButton.setMnemonicParsing(false);
        profileButton.setPrefSize(100, 60);
        profileButton.setFont(prefFont);
        profileButton.setId(user.getId().toString());
        profileButton.setOnAction(this::profileAction);
        ObservableList<Node> childs = hBox.getChildren();
        childs.add(userImage);
        childs.add(userName);
        childs.add(userAge);
        childs.add(userLocation);
        childs.add(profileButton);
        if(!((relationService.checkRelation(this.getUser(), user))||(demandeService.checkDemande(this.getUser(), user))))
        {
            Button demandeButton = new Button("Demande d'intéraction");
            demandeButton.setMnemonicParsing(false);
            demandeButton.setPrefSize(170, 60);
            demandeButton.setFont(prefFont);
            demandeButton.setId(user.getId().toString());
            demandeButton.setOnAction(this::demandeInteraction);
            childs.add(demandeButton);
            HBox.setMargin(demandeButton, new Insets(0, 0, 0, 10));
        }
        HBox.setMargin(userImage, new Insets(0, 0, 0, 10));
        HBox.setMargin(userName, new Insets(0, 0, 0, 10));
        HBox.setMargin(userLocation, new Insets(0, 0, 0, 20));
        HBox.setMargin(profileButton, new Insets(0, 0, 0, 60));
        return hBox;
    }

    @FXML
    private void demandeInteraction(ActionEvent event) {
        Calendar c = Calendar.getInstance();
        Button demandeButton = (Button)event.getSource();
        int acceptorId = Integer.parseInt(demandeButton.getId());
        User acceptor = new User();
        acceptor.setId(acceptorId);
        Demande demande = new Demande(new java.sql.Timestamp(c.getTimeInMillis()), this.getUser(),acceptor);
        demandeService.insertDemande(demande);
        NotificationApi.createDemandeNotification(demande);
        demandeButton.setVisible(false);
        Notifications.create().text("Votre demande a été envoyé").position(Pos.CENTER).hideAfter(Duration.seconds(3)).showConfirm();
    }
    
    @FXML
    private void profileAction(ActionEvent event)
    {
        Button button = (Button) event.getSource();
        User user = userService.getUserById(Integer.valueOf(button.getId()));
        AutreJournalController.setAutreUser(user);
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource(("../View/autreJournal.fxml")));
        Controller.holderPane.getChildren().clear();
        try {
            Controller.holderPane.getChildren().add(fxmlLoader.load());
        } catch (IOException ex) {
            Logger.getLogger(RechercheProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String genderValue()
    {
        switch(genreComboBox.selectionModelProperty().get().getSelectedItem())
        {
            case"":return "";
            case"Male":return "M";
        }
        return "F";
    }
    
    private List<String> religionValue()
    {
        List<String> list = new ArrayList<>();
        switch(religionComboBox.selectionModelProperty().get().getSelectedItem())
        {
            case"Islam":list.add("I");
            case"Christianisme":list.add("C");
            case"Buddhism":list.add("B");
        }
        return list;
    }
    
    @FXML
    private void search(ActionEvent event) {
        HBox hBox;
        resultList.getChildren().clear();
        List<String> filmList = comboBoxItemsFill(filmComboBox);
        List<String> serieList = comboBoxItemsFill(serieComboBox);
        List<String> artistList = comboBoxItemsFill(musiqueComboBox);
        List<String> bookList = comboBoxItemsFill(livreComboBox);
        List<String> occupationList = comboBoxItemsFill(occupationComboBox);
        List<String> paysList = comboBoxItemsFill(paysComboBox);
        for(int i=0;i<paysList.size();i++)
            paysList.set(i, paysList.get(i).substring(paysList.get(i).length()-2));
        String genre = genderValue();
        List<String> religionList = religionValue();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -hSlider.highValueProperty().intValue());
        Date dmax = new Date(c.getTimeInMillis());
        c.setTime(new java.util.Date());
        c.add(Calendar.YEAR, -hSlider.lowValueProperty().intValue());
        Date dmin = new Date(c.getTimeInMillis());
        List<User> users = userService.searchResult(this.getUser().getId(), dmin, dmax, genre, occupationList, religionList, 
                paysList, new ArrayList<>(),new ArrayList<>(),filmList ,serieList, bookList, artistList);
        for(User user:users)
        {
            hBox = searchResultItem(user);
            VBox.setMargin(hBox, new Insets(10, 0, 0, 0));
            resultList.getChildren().add(hBox);
        }
    }
    
    private List<String> comboBoxItemsFill(ComboBox<?> comboBox)
    {
        List<String> list = new ArrayList<>();
        if(!comboBox.selectionModelProperty().getValue().isEmpty())
            list.add(comboBox.selectionModelProperty().getValue().getSelectedItem().toString());
        return list;
    }
    
}
