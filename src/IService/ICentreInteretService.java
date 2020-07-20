/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.CentreInteret;
import Entity.User;
import java.util.List;

/**
 *
 * @author Achrafoun
 */
public interface ICentreInteretService {
    
    public List<CentreInteret> getFilmsByUser(User u);
    
    public List<CentreInteret> getSeriesByUser(User u);
    
    public List<CentreInteret> getArtistsByUser(User u);
    
    public List<CentreInteret> getLivresByUser(User u);
    
    public void ajouterCentreInteret(CentreInteret c,User u);
    
    public void modifierCentreInteret(CentreInteret c);
    
    public void supprimerCentreInteret(CentreInteret c);
}
