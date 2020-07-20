/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.Emploi;
import Entity.User;
import java.util.List;

/**
 *
 * @author Achrafoun
 */
public interface IEmploiService {
    
    public List<Emploi> getAllEmploisByUser(User u);
    
    public Emploi getEmploiById(int e);
    
    public void ajouterEmploi(Emploi e, User u);
    
    public void modifierEmploi(Emploi e);
    
    public void supprimerEmploi(Emploi e);
}
