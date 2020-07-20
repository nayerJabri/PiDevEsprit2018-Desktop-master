/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;

import Entity.Espace;
import Entity.PhotoEspace;
import java.util.List;

/**
 *
 * @author hero
 */
public interface IEspaceService {
    public Espace getEspaceById(int id);
    public List<Espace> getEspaceConfirmer();
    public List<Espace> getEspaceNonConfirmer();
    public void removeEspace(int id);
    public void confirmeEspace(int id);
    public void send_mail(Espace espace);
    public Espace ajoutEspace(Espace e);
    public Espace lastEspaces();
    public List<Espace> findByDistance(double latitude, double longitude, double distance);
    public void send_mailconf(Espace espace);
}
