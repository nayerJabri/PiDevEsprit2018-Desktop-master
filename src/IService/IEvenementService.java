/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.Evenement;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author hero
 */
public interface IEvenementService {
    public Evenement getEvenementById(int id);
    public Evenement insertEvenement(Evenement e);
    public ObservableList<Evenement> getAll();
    public ObservableList<Evenement> getEv();
    public boolean deleteEvenement(int ide);
    public void updateEvenement(Evenement e, int id);
//    public int statistique();

   
}
