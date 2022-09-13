package BusinessLogics;

import Interface.EmployeeInterface;
import Model.*;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController implements EmployeeInterface {

    @Override
    public String setEmployeeIDS() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT EmployeeId FROM `Employee detail` ORDER BY EmployeeId DESC LIMIT 1").executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "E-00"+tempId;
            }else if(tempId<=99){
                return "E-0"+tempId;
            }else{
                return "E-"+tempId;
            }

        }else{
            return "E-001";
        }
    }

    @Override
    public boolean setInfo(Employee u) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `Employee detail` VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,u.getEmployeeID());
        stm.setObject(2,u.getFirstName());
        stm.setObject(3,u.getLastName());
        stm.setObject(4,u.getAddress());
        stm.setObject(5,u.getNic());
        stm.setObject(6,u.getEmail());
        stm.setObject(7,u.getContact());
        stm.setObject(8,u.getJobRole());

        return stm.executeUpdate()>0;
    }

    @Override
    public ArrayList<Employee> getAllEmployeeDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Employee detail`");
        ResultSet rst = stm.executeQuery();
        ArrayList<Employee> employees = new ArrayList<>();
        while (rst.next()) {
            employees.add(new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            ));
        }
        return employees;
    }


    @Override
    public boolean updateEmployee(Employee e) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `Employee detail` SET EmployeeId=?, fName=?, lName=?, address=?, nic=?, email=?, contact=?, JobRole=?  WHERE EmployeeId='"+e.getEmployeeID()+"'");
        stm.setObject(1,e.getEmployeeID());
        stm.setObject(2,e.getFirstName());
        stm.setObject(3,e.getLastName());
        stm.setObject(4,e.getAddress());
        stm.setObject(5,e.getNic());
        stm.setObject(6,e.getEmail());
        stm.setObject(7,e.getContact());
        stm.setObject(8,e.getJobRole());
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean deleteEmployee(Employee e) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Employee detail` WHERE EmployeeId='"+e.getEmployeeID()+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public static ArrayList<Employee> searchEmployee(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Employee detail` WHERE fName LIKE '%"+value+"%' || lName LIKE '%"+value+"%' || address LIKE '%"+value+"%' || nic LIKE '%"+value+"%' || JobRole LIKE '%"+value+"%' || EmployeeId LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();


        ArrayList<Employee> employees = new ArrayList<>();

        while (rst.next()) {
            employees.add(new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)

            ));
        }

        return employees;
    }

    @Override
   public EmployeeSalary getSalaryDetails(String eID) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Employee salary` WHERE EmployeeId='"+eID+"'");
        ResultSet rst = stm.executeQuery();
        while (rst.next()){
            return new EmployeeSalary(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9),
                    rst.getString(10),
                    rst.getString(11)
            );
        }
        return null;
    }

    @Override
    public String getEmployeeSalaryDetails(String eID, String month) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Employee salary` WHERE EMonth='"+month+"' && EmployeeId='"+eID+"'");
        ResultSet rst = stm.executeQuery();
        boolean b = rst.next();
        if(b == true){
            return "Paid";
        }
        return "Not Paid";
    }

}
