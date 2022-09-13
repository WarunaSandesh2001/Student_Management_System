package BusinessLogics;

import Interface.AttendanceInterface;
import Model.*;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceController implements AttendanceInterface {

    @Override
    public boolean saveAttendance(Attendance u) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `student attendance` VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,u.getStudentID());
        stm.setObject(2,u.getStudentName());
        stm.setObject(3,u.getAttendance());
        stm.setObject(4,u.getSubjectName());
        stm.setObject(5,u.getTeacherName());
        stm.setObject(6,u.getExamYear());
        stm.setObject(7,u.getAttendanceTime());
        stm.setObject(8,u.getAttendanceDate());


        return stm.executeUpdate()>0;
    }

    @Override
    public ArrayList<Attendance> getAttendanceSheet(String subName,String tName,String date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `student attendance` WHERE SubjectName=? AND teacherName=? AND ADate=?");
        stm.setObject(1,subName);
        stm.setObject(2,tName);
        stm.setObject(3,date);
        ResultSet rst = stm.executeQuery();
        ArrayList<Attendance> attendances = new ArrayList<>();
        while (rst.next()) {
            attendances.add(new Attendance(
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
        return attendances;
    }

    @Override
    public boolean deleteAttendance(Attendance s) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Student Attendance` WHERE StudentId='"+s.getStudentID()+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public static ArrayList<Attendance> searchStudent(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student Attendance` WHERE StudentId LIKE '%"+value+"%' || StudentName LIKE '%"+value+"%' || examYear LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();


        ArrayList<Attendance> students = new ArrayList<>();

        while (rst.next()) {
            students.add(new Attendance(
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

        return students;
    }
}
