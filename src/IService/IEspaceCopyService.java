/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IService;
import Entity.Espace;
import Entity.EspaceCopy;
import java.util.List;

/**
 *
 * @author hero
 */
public interface IEspaceCopyService {
        public List<EspaceCopy> getAll();
        public EspaceCopy ajoutEspaceCopy(EspaceCopy e);
        public  EspaceCopy confirmerEspaceModif(EspaceCopy e);
        public void removeEspaceCopy(int id);
}
