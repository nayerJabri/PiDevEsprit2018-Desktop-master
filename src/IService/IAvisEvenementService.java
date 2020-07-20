/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.AvisEvenement;
import javafx.collections.ObservableList;

/**
 *
 * @author hero
 */
public interface IAvisEvenementService {
    public AvisEvenement insertAvis(AvisEvenement a);
    public ObservableList<AvisEvenement> getAvis();
    public ObservableList<AvisEvenement> getAvis(int idEvenement);
}
