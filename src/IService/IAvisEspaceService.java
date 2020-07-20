/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.AvisEspace;
import java.util.List;

/**
 *
 * @author hero
 */
public interface IAvisEspaceService {
     AvisEspace ajouterRating(AvisEspace e);
     List<AvisEspace> getRating(int id);
}
