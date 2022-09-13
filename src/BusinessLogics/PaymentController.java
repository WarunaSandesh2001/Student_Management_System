package BusinessLogics;

import Interface.PaymentInterface;
import Model.Employee;
import Model.EmployeeSalary;
import Model.Payment;
import Model.TeacherSalary;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentController implements PaymentInterface{

    @Override
    public String getFeesFromTeacherName(String studentID,String subName,String payMonth) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Payment` WHERE sID='"+studentID+"' AND subject='"+subName+"' AND paymentMonth='"+payMonth+"'");
        ResultSet rst = stm.executeQuery();
        boolean b = rst.next();
        if(b == true){
            return "Paid";
        }
        return "Not Paid";
    }

    @Override
    public boolean savePaymentInfo(Payment u) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `Payment` VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,u.getStudentID());
        stm.setObject(2,u.getCardNumber());
        stm.setObject(3,u.getSubject());
        stm.setObject(4,u.getTeacher());
        stm.setObject(5,u.getPaymentMonth());
        stm.setObject(6,u.getAmount());
        stm.setObject(7,u.getpDate());
        stm.setObject(8,u.getpTime());

        return stm.executeUpdate()>0;
    }

    @Override
    public boolean findPaidOrNot(String studentID,String payMonth) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Payment` WHERE sID='"+studentID+"' AND paymentMonth='"+payMonth+"'");
        ResultSet rst = stm.executeQuery();
        boolean b = rst.next();
        if(b == true){

            return true;
        }

        return false;
    }

    @Override
    public boolean findPaidOrNotBySubject(String studentID,String payMonth,String s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Payment` WHERE sID='"+studentID+"' AND paymentMonth='"+payMonth+"' AND subject='"+s+"'");
        ResultSet rst = stm.executeQuery();
        boolean b = rst.next();
        if(b == true){

            return true;
        }

        return false;
    }

    @Override
    public Double getTotalFeeOfTeacher(String tName,String subName,String payMonth) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Payment` WHERE teacher='"+tName+"' AND subject='"+subName+"' AND paymentMonth='"+payMonth+"'");
        ResultSet rst = stm.executeQuery();


        double total = 0.00;
        while (rst.next()){
            total= total+rst.getDouble(6);
        }
        return total;
    }

    @Override
    public boolean saveTeacherSalary(TeacherSalary u) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `teacher salary` VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,u.getTeacherID());
        stm.setObject(2,u.getTeacherName());
        stm.setObject(3,u.getTotalFee());
        stm.setObject(4,Double.valueOf(u.getPercentage()));
        stm.setObject(5,Double.valueOf(u.getAllowances()));
        stm.setObject(6,Double.valueOf(u.getDeductions()));
        stm.setObject(7,Double.valueOf(u.getNetSalary()));
        stm.setObject(8,u.gettMonth());
        stm.setObject(9,u.gettDate());
        stm.setObject(10,u.gettTime());

        return stm.executeUpdate()>0;
    }

    @Override
    public boolean saveEmployeeSalary(EmployeeSalary u) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `Employee salary` VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,u.getEmployeeID());
        stm.setObject(2,u.getEmployeeName());
        stm.setObject(3,u.getNic());
        stm.setObject(4,Integer.valueOf(u.getWorkingDays()));
        stm.setObject(5,Double.valueOf(u.getSalaryPerDay()));
        stm.setObject(6,Double.valueOf(u.getAllowances()));
        stm.setObject(7,Double.valueOf(u.getDeductions()));
        stm.setObject(8,Double.valueOf(u.getNetSalary()));
        stm.setObject(9,u.geteMonth());
        stm.setObject(10,u.geteDate());
        stm.setObject(11,u.geteTime());

        return stm.executeUpdate()>0;
    }

    @Override
    public boolean PaidOrNotTeacher(String teacherID,String payMonth) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `teacher salary` WHERE TeacherId='"+teacherID+"' AND TMonth='"+payMonth+"'");
        ResultSet rst = stm.executeQuery();
        boolean b = rst.next();
        if(b == true){
            return true;
        }
        return false;
    }

    @Override
    public boolean PaidOrNotEmployee(String employeeID,String payMonth) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Employee salary` WHERE EmployeeId='"+employeeID+"' AND EMonth='"+payMonth+"'");
        ResultSet rst = stm.executeQuery();
        boolean b = rst.next();
        if(b == true){
            return true;
        }
        return false;
    }
}
