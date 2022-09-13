package Interface;

import Model.OwnerDetail;
import Model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserInterface {
    public boolean setInfo(User u) throws SQLException, ClassNotFoundException;
    public ArrayList<User> getUserDetails() throws SQLException, ClassNotFoundException ;
    public boolean setInfoToOwnerTable(OwnerDetail u) throws SQLException, ClassNotFoundException;
    public OwnerDetail getOwnerDetails() throws SQLException, ClassNotFoundException;
    public boolean updateOwnerDetails(OwnerDetail s) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getUserTypes() throws SQLException, ClassNotFoundException;
    public String searchUser(String uName, String uPassword) throws SQLException, ClassNotFoundException;
}
