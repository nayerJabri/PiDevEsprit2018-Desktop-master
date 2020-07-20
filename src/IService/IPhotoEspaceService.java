/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.PhotoEspace;

/**
 *
 * @author hero
 */
public interface IPhotoEspaceService {
    public PhotoEspace ajoutPhoto(PhotoEspace e);
     public PhotoEspace getPhotoById(int id);
}
