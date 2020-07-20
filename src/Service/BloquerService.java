/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.User;
import IService.IBloquerService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Achrafoun
 */
public class BloquerService implements IBloquerService {
    private Connection con = DataSource.getInstance().getConnection();
    private Statement ste;

    public BloquerService() {
        try {
            ste = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(SignalerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void bloquerUser(User u) {
        try {
            String req = "update user set enabled=0 where id=? ";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, u.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BloquerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void debloquerUser(User u) {
        try {
            String req = "update user set enabled=1 where id=? ";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, u.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BloquerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
