package BusinessLogics;

import Interface.UserInterface;
import Model.OwnerDetail;
import Model.Subject;
import Model.User;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserController implements UserInterface {

    @Override
    public boolean setInfo(User u) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `login detail` VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,u.getFirstName());
        stm.setObject(2,u.getLastName());
        stm.setObject(3,u.getAddress());
        stm.setObject(4,u.getContact());
        stm.setObject(5,u.getEmail());
        stm.setObject(6,u.getNic());
        stm.setObject(7,u.getUserName());
        stm.setObject(8,u.getPassword());
        stm.setObject(9,u.getUserType());

        return stm.executeUpdate()>0;
    }


    @Override
    public ArrayList<User> getUserDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `login detail`");
        ResultSet rst = stm.executeQuery();
        ArrayList<User> users = new ArrayList<>();
        while (rst.next()) {
            User u = new User(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9));
            users.add(u);
        }

        return users;
    }

    @Override
    public boolean setInfoToOwnerTable(OwnerDetail u) throws SQLException, ClassNotFoundException{
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `OwnerDetails` VALUES(?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,u.getName());
        stm.setObject(2,u.getContact());

        return stm.executeUpdate()>0;
    }

    @Override
    public OwnerDetail getOwnerDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `OwnerDetails`");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            return new OwnerDetail(rst.getString(1),rst.getString(2));
        }

        return null;
    }

    @Override
    public boolean updateOwnerDetails(OwnerDetail s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `OwnerDetails` SET ownerName=?, Contact=?");
        stm.setObject(1,s.getName());
        stm.setObject(2,s.getContact());
        return stm.executeUpdate()>0;
    }

    @Override
    public ArrayList<String> getUserTypes() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `login detail`");
        ResultSet rst = stm.executeQuery();
        ArrayList<String> users = new ArrayList<>();
        while (rst.next()) {
            users.add(rst.getString(9));
        }

        return users;
    }

    @Override
    public String searchUser(String uName, String uPassword) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `login detail` WHERE userName='"+uName+"'  && password='"+uPassword+"'");
        ResultSet rst = stm.executeQuery();
        boolean b = rst.next();

        if (b==true) {
            return rst.getString(9);
        }
        if(b==false){
            return null;
        }
        return null;
    }

}
