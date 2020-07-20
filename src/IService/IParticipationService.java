/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.Evenement;
import Entity.User;

/**
 *
 * @author hero
 */
public interface IParticipationService {
    public void increment(Evenement ev,User user);
    public void decrement(Evenement ev, User user);
    public boolean checkParticipation(Evenement evenement,User user);
}
