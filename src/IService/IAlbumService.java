/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.Album;
import Entity.User;
import java.util.List;

/**
 *
 * @author Achrafoun
 */
public interface IAlbumService {
    
    public List<Album> getPhotosByUser(User u);
    
    public List<Album> getLastPhotosByUser(User u);
    
    public void ajouterPhoto(Album a);
    
    public void supprimerPhoto(Album a);
}
