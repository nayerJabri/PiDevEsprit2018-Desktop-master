/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import IService.*;
import Service.*;

/**
 *
 * @author hero
 */
public class Service {
    private static Service service ;
    private IUserService userService;
    private ICentreInteretService centreInteretService;
    private IDemandeService demandeService;
    private IEmploiService emploiService;
    private ILoisirService loisirService;
    private IMessageService messageService;
    private IRelationService relationService;
    private IScolariteService scolariteService;
    private ISignalerService signalerService;
    private IEvenementService evenementService;
    private IAvisEvenementService avisEvenementService;
    private IParticipationService participationService;
    private IEspaceService espaceService;
    private IEspaceCopyService espaceCopyService;
    private IAvisEspaceService avisEspaceService;
    private IPhotoEspaceService photoEspaceService;
    private IPublicationService publicationService;
    private IAlbumService albumService;
    private IBloquerService bloquerService; 
    private INotificationService notificationService;
    private Service()
    {
        
    }
    public static Service getInstance()
    {
        if(service == null)
            service = new Service();
        return service;
    }

    public IUserService getUserService()
    {
        if(userService == null)
            userService = new UserService();
        return userService;
    }
    
    public ICentreInteretService getCentreInteretService()
    {
        if(centreInteretService == null)
            centreInteretService = new CentreInteretService();
        return centreInteretService;
    }
    
    public IDemandeService getDemandeService()
    {
        if(demandeService == null)
            demandeService = new DemandeService();
        return demandeService;
    }
    
    public IEmploiService getEmploiService()
    {
        if(emploiService == null)
            emploiService = new EmploiService();
        return emploiService;
    }
    
    public ILoisirService getLoisirService()
    {
        if(loisirService == null)
            loisirService = new LoisirService();
        return loisirService;
    }
    
    public IMessageService getMessageService()
    {
        if(messageService == null)
            messageService = new MessageService();
        return messageService;
    }
    
    public IRelationService getRelationService()
    {
        if(relationService == null)
            relationService = new RelationService();
        return relationService;
    }
    
    public IScolariteService getScolariteService()
    {
        if(scolariteService == null)
            scolariteService = new ScolariteService();
        return scolariteService;
    }
    
    public ISignalerService getSignalerService()
    {
        if(signalerService == null)
            signalerService = new SignalerService();
        return signalerService;
    }
    
    public IEvenementService getEvenementService()
    {
        if(evenementService == null)
            evenementService = new EvenementService();
        return evenementService;
    }
    
    public IParticipationService getParticipationService()
    {
        if(participationService == null)
            participationService = new ParticipationService();
        return participationService;
    }
    
    public IAvisEvenementService getAvisEvenementService()
    {
        if(avisEvenementService == null)
            avisEvenementService = new AvisEvenementService();
        return avisEvenementService;
    }
    
    public IEspaceService getEspaceService()
    {
        if(espaceService == null)
            espaceService = new EspaceService();
        return espaceService;
    }
    
    public IEspaceCopyService getEspaceCopyService()
    {
        if(espaceCopyService == null)
            espaceCopyService = new EspaceCopyService();
        return espaceCopyService;
    }
    
    public IAvisEspaceService getAvisEspaceService()
    {
        if(avisEspaceService == null)
            avisEspaceService = new AvisEspaceService();
        return avisEspaceService;
    }
    
    public IPhotoEspaceService getPhotoEspaceService()
    {
        if(photoEspaceService == null)
            photoEspaceService = new PhotoEspaceService();
        return photoEspaceService;
    }
    
    public IPublicationService getPublicationService()
    {
        if(publicationService == null)
            publicationService = new PublicationService();
        return publicationService;
    }
    
    public IAlbumService getAlbumService()
    {
        if(albumService == null)
            albumService = new AlbumService();
        return albumService;
    }
       
    public IBloquerService getBloquerService()
    {
        if(bloquerService == null)
            bloquerService = new BloquerService();
        return bloquerService;
    }
    
    public INotificationService getNotificationService()
    {
        if(notificationService == null)
            notificationService = new NotificationService();
        return notificationService;
    }
}
