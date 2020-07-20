/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class beblio {
    
    public static Integer ids ,id_user;
    public static String contenus;
    public static Integer idc;
    public static String recherche;

    public static String getRecherche() {
        return recherche;
    }

    public static void setRecherche(String recherche) {
        beblio.recherche = recherche;
    }
    
    
    
     public void setIds(int ids , String contenus , int id_user) throws SQLException, IOException {
        this.ids = ids;
        this.contenus = contenus;
        this.id_user = id_user;
    }
     
     
     

    public static Integer getIds() {
        return ids;
    }

    public static void setIds(Integer ids) {
        beblio.ids = ids;
    }

    public static Integer getId_user() {
        return id_user;
    }

    public static void setId_user(Integer id_user) {
        beblio.id_user = id_user;
    }

    public static String getContenus() {
        return contenus;
    }

    public static void setContenus(String contenus) {
        beblio.contenus = contenus;
    }

    public static Integer getIdc() {
        return idc;
    }

    public static void setIdc(Integer idc) {
        beblio.idc = idc;
    }

    public beblio() {
    }
    
    

    
}
