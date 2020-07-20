/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.Relation;
import Entity.User;
import java.util.List;

/**
 *
 * @author hero
 */
public interface IRelationService {
    public List<Relation> fetchMembers(User user);
    public boolean checkRelation(User cUser,User oUser);
    public boolean insertRelation(User acceptor,User requester);
}
