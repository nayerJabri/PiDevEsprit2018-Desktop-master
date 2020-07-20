/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.Loisir;
import Entity.User;
import java.util.List;

/**
 *
 * @author Achrafoun
 */
public interface ILoisirService {
    
    public List<Loisir> getAllLoisirsByUser(User u);
    
    public void ajouterLoisir(Loisir l,int u);
    
    public void modifierLoisir(Loisir l);
    
    public void supprimerLoisir(Loisir l);
    
}
