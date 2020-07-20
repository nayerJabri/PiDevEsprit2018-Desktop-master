/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Core.DataSource;
import Entity.Relation;
import Entity.User;
import IService.IRelationService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hero
 */
public class RelationService implements IRelationService{
    private final Connection con = DataSource.getInstance().getConnection();

    @Override
    public List<Relation> fetchMembers(User user) {
        List<Relation> relations = new ArrayList<>();
        ResultSet resultUser;
        try {
            String query = "select * from relation r where(r.acceptor = ? or r.requester = ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setInt(2, user.getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                ps = con.prepareStatement("select * from user where id = ?");
                ps.setInt(1, rs.getInt("requester"));
                resultUser = ps.executeQuery();
                resultUser.next();
                User requester = User.createUser(resultUser);
                ps.setInt(1, rs.getInt("acceptor"));
                resultUser = ps.executeQuery();
                resultUser.next();
                User acceptor = User.createUser(resultUser);
                Relation relation = new Relation(new Date(), requester, acceptor);
                relations.add(relation);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RelationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return relations;
    }

    @Override
    public boolean insertRelation(User acceptor, User requester) {
        String query = "INSERT INTO relation (requester,acceptor,dateRelation) VALUES(?,?,?)";
        Calendar c = Calendar.getInstance();
        Timestamp ts = new Timestamp(c.getTimeInMillis());
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, requester.getId());
            ps.setInt(2, acceptor.getId());
            ps.setTimestamp(3, ts);
            return(ps.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(RelationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean checkRelation(User cUser, User oUser) {
        String query="SELECT * FROM relation where (requester in (?,?) AND acceptor in (?,?))";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cUser.getId());
            ps.setInt(2, oUser.getId());
            ps.setInt(3, cUser.getId());
            ps.setInt(4, oUser.getId());
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                rs.close();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RelationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
