package Interface;

import Model.Employee;
import Model.EmployeeSalary;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeInterface {
    public String setEmployeeIDS() throws SQLException, ClassNotFoundException;
    public boolean setInfo(Employee u) throws SQLException, ClassNotFoundException;
    public ArrayList<Employee> getAllEmployeeDetails() throws SQLException, ClassNotFoundException;
    public boolean updateEmployee(Employee e) throws SQLException, ClassNotFoundException;
    public boolean deleteEmployee(Employee e) throws SQLException, ClassNotFoundException;
    //public static ArrayList<Employee> searchEmployee(String value) throws SQLException, ClassNotFoundException;
    public EmployeeSalary getSalaryDetails(String eID) throws SQLException, ClassNotFoundException;
    public String getEmployeeSalaryDetails(String eID, String month) throws SQLException, ClassNotFoundException;
}
