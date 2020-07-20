/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.Publication;
import Entity.User;
import java.util.List;

/**
 *
 * @author Achrafoun
 */
public interface IPublicationService {
    
    public List<Publication> getPublicationByUser(User u);
    
    public Publication getPublicationById(int id);
    
    public void ajouterPublication(Publication p);
    
    public void modifierPublication(Publication p);
    
    public void supprimerPublication(Publication p);
}
