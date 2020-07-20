/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.Scolarite;
import Entity.User;
import java.util.List;

/**
 *
 * @author Achrafoun
 */
public interface IScolariteService {
    
    public List<Scolarite> getAllScolariteByUser(User u);
    
    public Scolarite getScolariteById(int s);
    
    public void ajouterScolarite(Scolarite e, User u);
    
    public void modifierScolarite(Scolarite e);
    
    public void supprimerScolarite(Scolarite e);
}
