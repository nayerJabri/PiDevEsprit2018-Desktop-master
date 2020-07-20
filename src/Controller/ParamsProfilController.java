/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import APIs.ProfilOccupationAPI;
import APIs.ProfilPaysAPI;
import APIs.UploadAPI;
import Core.Controller;
import Entity.Album;
import Entity.User;
import IService.IUserService;
import Service.UserService;
import Utility.Religion;
import Utility.Sexe;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Achrafoun
 */
public class ParamsProfilController extends Controller implements Initializable {

    FileChooser saveFileChooser = new FileChooser();    
    File saveFile;
    File srcFile, destFile;
    
    private IUserService userService = this.getService().getUserService();
    
    private static User connectedUser;
    public static User getConnectedUser() {
        return connectedUser;
    }

    public static void setConnectedUser(User connectedUser) {
        ParamsProfilController.connectedUser = connectedUser;
    }
    @FXML
    private Label nomp;
    @FXML
    private ImageView photop;
    @FXML
    private Button journalButton;
    @FXML
    private Button aproposButton;
    @FXML
    private Button albumButton;
    @FXML
    private Button paramsProfil;
    @FXML
    private Button paramsCentre;
    @FXML
    private Button paramsEduc;
    @FXML
    private Button paramsEmp;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private DatePicker date_naiss;
    @FXML
    private TextField tel;
    @FXML
    private ComboBox<String> region;
    @FXML
    private ComboBox<String> ville;
    @FXML
    private ComboBox<String> pays;
    @FXML
    private TextField lieu_naiss;
    @FXML
    private ComboBox<Religion> religion;
    @FXML
    private ComboBox<String> occupation;
    @FXML
    private ComboBox<Sexe> sexe;
    @FXML
    private TextArea description;
    @FXML
    private TextField facebook;
    @FXML
    private TextField instagram;
    @FXML
    private TextField twitter;
    @FXML
    private ImageView img_profil;
    @FXML
    private Button enregistrer;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomp.setText(connectedUser.getNom()+" "+connectedUser.getPrenom());
        //-------------        
        photop.setImage(new Image(getClass().getResource("../Images/"+connectedUser.getImage()).toExternalForm()));
        //-------------
        Sexe h = new Sexe("Homme", "M");
        Sexe f = new Sexe("Femme", "F");
        ObservableList<Sexe> sl = FXCollections.observableArrayList(h,f);
        sexe.setItems(sl);
        //------
        Religion i = new Religion("Islam", "I");
        Religion c = new Religion("Christianisme", "C");
        Religion j = new Religion("Judaïsme", "J");
        Religion b = new Religion("Bouddhisme", "B");
        Religion hi = new Religion("Hindouisme", "H");
        ObservableList<Religion> rl = FXCollections.observableArrayList(i,c,j,b,hi);
        religion.setItems(rl);
        //------
        ProfilOccupationAPI occApi = ProfilOccupationAPI.getInstance();
        ObservableList<String> ol = FXCollections.observableArrayList(occApi.fetchResult());
        occupation.setItems(ol);
        //------------
        ProfilPaysAPI paysApi = ProfilPaysAPI.getInstance();
        
        ObservableList<String> pl = FXCollections.observableArrayList(paysApi.fetchResult());
        pays.setItems(pl);
        String codPays = connectedUser.getPays();
        String namePays = paysApi.getPaysName(codPays);
        pays.getSelectionModel().select(namePays+"-"+codPays);
        pays.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String newChoosedItem) {
                String newPays = newChoosedItem.split("-")[newChoosedItem.split("-").length-1];
                ObservableList<String> vl = FXCollections.observableArrayList(paysApi.getVilleByPays(newPays));
                ville.setItems(vl);                
            }    
        });
        //------------
        ObservableList<String> vl = FXCollections.observableArrayList(paysApi.getVilleByPays(codPays));
        ville.setItems(vl);
        ville.getSelectionModel().select(connectedUser.getVille());
        ville.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String newChoosedItem) {
                String comparePaysCB = pays.getSelectionModel().getSelectedItem().split("-")[pays.getSelectionModel().getSelectedItem().split("-").length-1];
                if((newChoosedItem != null)&&(comparePaysCB.equals(connectedUser.getPays())==true)){
                    String ville_u = newChoosedItem.replaceAll(" ", "%20");
                    ObservableList<String> regl = FXCollections.observableArrayList(paysApi.getRegionByPaysVille(codPays, ville_u));
                    region.setItems(regl);
                }
                if((newChoosedItem != null)&&(comparePaysCB.equals(connectedUser.getPays())==false)){
                    String newVille = ville.getSelectionModel().getSelectedItem();
                    newVille=newVille.replaceAll(" ", "%20");
                    ObservableList<String> regl = FXCollections.observableArrayList(paysApi.getRegionByPaysVille(comparePaysCB, newVille));
                    region.setItems(regl);
                }
                
            }    
        });
        //------------
        String ville_u = connectedUser.getVille().replaceAll(" ", "%20");
        ObservableList<String> regl = FXCollections.observableArrayList(paysApi.getRegionByPaysVille(codPays, ville_u));
        region.setItems(regl);
        String selectedRegion = paysApi.getOneRegion(codPays, ville_u, connectedUser.getRegion());
        region.getSelectionModel().select(selectedRegion);
        //------------
        nom.setText(connectedUser.getNom());
        prenom.setText(connectedUser.getPrenom());
        email.setText(connectedUser.getEmail());
        email.setEditable(false);
        //----
        java.sql.Date sqlDate = (java.sql.Date) connectedUser.getDateNaissance();
        date_naiss.setValue(sqlDate.toLocalDate());
        //----
        tel.setText(connectedUser.getTel());
        //----
        if(connectedUser.getGenre().equals("M"))
            sexe.getSelectionModel().select(0);
        else
            sexe.getSelectionModel().select(1);
        //----
        lieu_naiss.setText(connectedUser.getPlaceNaiss());
        occupation.getSelectionModel().select(connectedUser.getOccupation());
        description.setText(connectedUser.getApropos());
        facebook.setText(connectedUser.getFacebook());
        instagram.setText(connectedUser.getInstagram());
        twitter.setText(connectedUser.getTwitter());
        //-----
        switch (connectedUser.getReligion()) {
            case "I":  religion.getSelectionModel().select(0);
                     break;
            case "C":  religion.getSelectionModel().select(1);
                     break;
            case "J":  religion.getSelectionModel().select(2);
                     break;
            case "B":  religion.getSelectionModel().select(3);
                     break;
            case "H":  religion.getSelectionModel().select(4);
                     break;            
        }
        //-----
        img_profil.setImage(new Image(getClass().getResource("../Images/"+connectedUser.getImage()).toExternalForm()));
        img_profil.setId(connectedUser.getId().toString());
        img_profil.setOnMouseClicked(this::updatePhotoProfilAction);
    }

    @FXML
    private void journalAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Journal.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            ProfilController profilController = loader.getController();
            profilController.setConnectedUser(connectedUser);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void aProposAction(ActionEvent event) {
        AProposController.setaProposUser(connectedUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/aPropos.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            AProposController aproposController = loader.getController();
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void albumAction(ActionEvent event) {
        AlbumController.setConnectedUser(connectedUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/album.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            AlbumController albumController = loader.getController();
            albumController.setConnectedUser(connectedUser);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void paramsProfilAction(ActionEvent event) {
    }

    @FXML
    private void paramsCentreAction(ActionEvent event) {
        ParamsCentreController.setConnectedUser(connectedUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsCentre.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            ParamsCentreController paramsCentreController = loader.getController();
            paramsCentreController.setConnectedUser(connectedUser);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void paramsEducAction(ActionEvent event) {
        ParamsEducController.setConnectedUser(connectedUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsEduc.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            ParamsEducController paramsEducController = loader.getController();
            paramsEducController.setConnectedUser(connectedUser);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void paramsEmpAction(ActionEvent event) {
        ParamsEmpController.setConnectedUser(connectedUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsEmp.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            ParamsEmpController paramsEmpController = loader.getController();
            paramsEmpController.setConnectedUser(connectedUser);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //----------------

    
    protected String genString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
    
    private void updatePhotoProfilAction(Event event)
    {
        File file = saveFileChooser.showOpenDialog(null);
        String nameF = "";
        try {
            //-------
            nameF = UploadAPI.upload(file);
        } catch (Exception ex) {
            Logger.getLogger(AlbumController.class.getName()).log(Level.SEVERE, null, ex);
        }
        User up_user = new User();
        up_user= connectedUser;
        up_user.setImage(nameF);
        userService.modifierUserPhoto(up_user);
                        
        /*if (file != null) {                
            srcFile = file;
            //labelSaveFile.setText(destFile.getName());
            if (srcFile != null) {
                try {
                    //String[] s = srcFile.getAbsolutePath().substring(srcFile.getAbsolutePath().lastIndexOf("\\")+1).split(".");
                    //System.out.println(s);
                    //String aaaa = srcFile.getAbsolutePath().substring(srcFile.getAbsolutePath().lastIndexOf("\\")+1).split(".")[1];
                    String x = srcFile.getName();
                    String[] s = x.split("\\.(?=[^\\.]+$)");
                    //----
                    if(s[s.length-1].equals("png")){
                        String p = System.getProperty("user.dir")+"/src/images/"+nameF+".png";
                        copyFile(srcFile, new File(p));
                        User up_user = new User();
                        up_user= connectedUser;
                        //ImageView im = (ImageView) event.getSource();
                        //up_user.setId(Integer.parseInt(im.getId()));
                        up_user.setImage(nameF+".png");
                        userService.modifierUser(up_user);
                    }
                    if(s[s.length-1].equals("jpg")){
                        String p = System.getProperty("user.dir")+"/src/images/"+nameF+".jpg";
                        copyFile(srcFile, new File(p));
                        User up_user = new User();
                        up_user= connectedUser;
                        //ImageView im = (ImageView) event.getSource();
                        //up_user.setId(Integer.parseInt(im.getId()));
                        up_user.setImage(nameF+".jpg");
                        userService.modifierUser(up_user);
                    }
                    if(s[s.length-1].equals("jpeg")){
                        String p = System.getProperty("user.dir")+"/src/images/"+nameF+".jpeg";
                        copyFile(srcFile, new File(p));
                        User up_user = new User();
                        up_user= connectedUser;
                        //ImageView im = (ImageView) event.getSource();
                        //up_user.setId(Integer.parseInt(im.getId()));
                        up_user.setImage(nameF+".jpeg");
                        userService.modifierUser(up_user);
                    }
                    if(s[s.length-1].equals("gif")){
                        String p = System.getProperty("user.dir")+"/src/images/"+nameF+".gif";
                        copyFile(srcFile, new File(p));
                        User up_user = new User();
                        up_user= connectedUser;
                        //ImageView im = (ImageView) event.getSource();
                        //up_user.setId(Integer.parseInt(im.getId()));
                        up_user.setImage(nameF+".gif");
                        userService.modifierUser(up_user);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(AlbumController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }*/
        try {
            //---
            sleep(8000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AlbumController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //---
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsProfil.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(ParamsProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    @FXML
    private void enregistrerParamsProfil(ActionEvent event) {
        User u = new User();
        u = connectedUser;
        u.setNom(nom.getText());
        u.setPrenom(prenom.getText());
        
        LocalDate x = date_naiss.getValue();
        java.sql.Date dat = java.sql.Date.valueOf(x);
        u.setDateNaissance(dat);
        
        u.setGenre(sexe.getSelectionModel().getSelectedItem().getValue());
        //-------
        String paysVal="";
        String[] s = pays.getSelectionModel().getSelectedItem().split("-");
        paysVal = s[s.length-1];
        u.setPays(paysVal);
                
        u.setVille(ville.getSelectionModel().getSelectedItem());
        
        String regionVal="";
        s = region.getSelectionModel().getSelectedItem().split("-");
        regionVal = s[0];
        u.setRegion(regionVal);
        //-------
        u.setTel(tel.getText());
        u.setPlaceNaiss(lieu_naiss.getText());
        u.setReligion(religion.getSelectionModel().getSelectedItem().getValue());
        u.setApropos(description.getText());
        u.setFacebook(facebook.getText());
        u.setTwitter(twitter.getText());
        u.setInstagram(instagram.getText());
        u.setOccupation(occupation.getSelectionModel().getSelectedItem());
        //--------
        userService.modifierUser(u);
        //---
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsProfil.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(ParamsProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
