/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import APIs.UploadAPI;
import Core.Controller;
import static Core.Controller.holderPane;
import Entity.Album;
import Entity.User;
import IService.IAlbumService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * FXML Controller class
 *
 * @author Achrafoun
 */
public class AlbumController extends Controller implements Initializable {
    
    private static User connectedUser;
    private IAlbumService albumService = this.getService().getAlbumService();

    public static User getConnectedUser() {
        return connectedUser;
    }

    public static void setConnectedUser(User connectedUser) {
        AlbumController.connectedUser = connectedUser;
    }
    
    FileChooser saveFileChooser = new FileChooser();    
    File saveFile;
    File srcFile, destFile;
    
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
    private ScrollPane scrollPanGrid;
    @FXML
    private GridPane album;
    @FXML
    private Button ajouterPhoto;
    @FXML
    private Button paramsProfil;
    
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomp.setText(connectedUser.getNom()+" "+connectedUser.getPrenom());
        //-------------        
        photop.setImage(new Image(getClass().getResource("../Images/"+connectedUser.getImage()).toExternalForm()));
        //-------------
        List<Album> all_photos = albumService.getPhotosByUser(connectedUser);
        /*int x = all_photos.size();
        double nb_ligne_v1 = x/4;
        int nb_ligne=0;
        
        if((nb_ligne_v1-(int)nb_ligne_v1)!=0)
            //System.out.println("decimal value is there");
            nb_ligne = ((int) x/4)+1;
        else
            //System.out.println("decimal value is not there");
            nb_ligne = (int)x/4;
        */
        int c=0;
        int l=0;
        for(Album photo:all_photos)
        {
            ImageView img = new ImageView(getClass().getResource("../Images/"+photo.getUrl()).toExternalForm());
            img.setFitWidth(228);
            img.setFitHeight(155);
            img.setPickOnBounds(true);
            img.setPreserveRatio(true);
            img.setId(photo.getId().toString());
            img.setOnMouseClicked(this::deleteAction);
            album.add(img, c++, l);
            if(c==4)
            {
                c=0;
                l++;
            }
        }
        //------------
        
        
    }
    
    @FXML
    private void deleteAction(Event event)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        //alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Voulez vous supprimer cette photo ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ImageView x = (ImageView) event.getSource();
        Album a = new Album();
        a.setId(Integer.parseInt(x.getId()));
        albumService.supprimerPhoto(a);
        //-------
        try {
            //---
            sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AlbumController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //---
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/album.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
        //-------------
        
    }
    
    
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
    }

    @FXML
    private void ajouterPhotoAction(ActionEvent event) throws IOException {
        File file = saveFileChooser.showOpenDialog(null);
        String nameF = "";
        try {
            //-------
            nameF = UploadAPI.upload(file);
        } catch (Exception ex) {
            Logger.getLogger(AlbumController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Album new_al = new Album(nameF,connectedUser);
        albumService.ajouterPhoto(new_al);
        //-------
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
                        //p = "src/images/"+nameF+".png";
                        copyFile(srcFile, new File(p));
                        Album new_al = new Album(nameF+".png",connectedUser);
                        albumService.ajouterPhoto(new_al);
                    }
                    if(s[s.length-1].equals("jpg")){
                        String p = System.getProperty("user.dir")+"/src/images/"+nameF+".jpg";
                        copyFile(srcFile, new File(p));
                        Album new_al = new Album(nameF+".jpg", connectedUser);
                        albumService.ajouterPhoto(new_al);
                    }
                    if(s[s.length-1].equals("jpeg")){
                        String p = System.getProperty("user.dir")+"/src/images/"+nameF+".jpeg";
                        copyFile(srcFile, new File(p));
                        Album new_al = new Album(nameF+".jpeg", connectedUser);
                        albumService.ajouterPhoto(new_al);
                    }
                    if(s[s.length-1].equals("gif")){
                        String p = System.getProperty("user.dir")+"/src/images/"+nameF+".gif";
                        copyFile(srcFile, new File(p));
                        Album new_al = new Album(nameF+".gif", connectedUser);
                        albumService.ajouterPhoto(new_al);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/album.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void paramsProfilAction(ActionEvent event) {
        ParamsProfilController.setConnectedUser(connectedUser);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/paramsProfil.fxml"));
        try {
            holderPane.getChildren().clear();
            holderPane.getChildren().add(loader.load());
            ParamsProfilController paramsProfilController = loader.getController();
            paramsProfilController.setConnectedUser(connectedUser);
        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
