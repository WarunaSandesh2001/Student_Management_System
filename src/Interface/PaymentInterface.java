package Interface;

import Model.EmployeeSalary;
import Model.Payment;
import Model.StudentDetail;
import Model.TeacherSalary;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentInterface {

    public String getFeesFromTeacherName(String studentID,String subName,String payMonth) throws SQLException, ClassNotFoundException ;
    public boolean savePaymentInfo(Payment u) throws SQLException, ClassNotFoundException ;
    public boolean findPaidOrNot(String studentID,String payMonth) throws SQLException, ClassNotFoundException ;
    public boolean findPaidOrNotBySubject(String studentID,String payMonth,String s) throws SQLException, ClassNotFoundException ;
    public Double getTotalFeeOfTeacher(String tName,String subName,String payMonth) throws SQLException, ClassNotFoundException ;
    public boolean saveTeacherSalary(TeacherSalary u) throws SQLException, ClassNotFoundException ;
    public boolean saveEmployeeSalary(EmployeeSalary u) throws SQLException, ClassNotFoundException  ;
    public boolean PaidOrNotTeacher(String teacherID,String payMonth) throws SQLException, ClassNotFoundException ;
    public boolean PaidOrNotEmployee(String employeeID,String payMonth) throws SQLException, ClassNotFoundException;

}
