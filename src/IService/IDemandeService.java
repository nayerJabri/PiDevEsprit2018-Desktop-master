/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.Demande;
import Entity.User;
import java.util.List;

/**
 *
 * @author hero
 */
public interface IDemandeService {
    
    public Demande insertDemande(Demande demande);
    public boolean deleteDemande(Demande demande);
    public boolean checkDemande(User cUser,User oUser);
    public List<Demande> getDemandesByUser(User user);
}
