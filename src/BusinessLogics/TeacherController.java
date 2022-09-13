package BusinessLogics;

import Interface.TeacherInterface;
import Model.*;
import View.tm.SubjectTM;
import View.tm.TeacherSalaryTM;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherController implements TeacherInterface {

    @Override
    public String setTeacherIDs() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT TeacherId FROM `Teacher` ORDER BY TeacherId DESC LIMIT 1").executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "T-00"+tempId;
            }else if(tempId<=99){
                return "T-0"+tempId;
            }else{
                return "T-"+tempId;
            }
        }else{
            return "T-001";
        }
    }

    @Override
    public boolean setInfo(Teacher u) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `Teacher` VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,u.getTeacherId());
        stm.setObject(2,u.getTeacherName());
        stm.setObject(3,u.getTeacherEmail());
        stm.setObject(4,u.getTeacherContact());
        stm.setObject(5,u.getTeacherAddress());
        stm.setObject(6,u.getNic());
        stm.setObject(7,u.getTeacherDescription());
        stm.setObject(8,u.getSubName());
        stm.setObject(9,u.getFee());

        return stm.executeUpdate()>0;
    }

    @Override
    public ArrayList<Teacher> getAllTeacherDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher`");
        ResultSet rst = stm.executeQuery();
        ArrayList<Teacher> teachers = new ArrayList<>();
        while (rst.next()) {
            teachers.add(new Teacher(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            ));
        }
        return teachers;
    }

    @Override
    public boolean updateTeacher(Teacher t) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `Teacher` SET TeacherId=?, TeacherName=?, teacherEmail=?, teacherContact=?, teacherAddress=?, nic=?, teacherDescription=?, subName=?, fee=?  WHERE TeacherId='"+t.getTeacherId()+"'");
        stm.setObject(1,t.getTeacherId());
        stm.setObject(2,t.getTeacherName());
        stm.setObject(3,t.getTeacherEmail());
        stm.setObject(4,t.getTeacherContact());
        stm.setObject(5,t.getTeacherAddress());
        stm.setObject(6,t.getNic());
        stm.setObject(7,t.getTeacherDescription());
        stm.setObject(8,t.getSubName());
        stm.setObject(9,t.getFee());
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean deleteTeacher(Teacher t) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Teacher` WHERE TeacherId='"+t.getTeacherId()+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Teacher getTeacherBySubject(String subject) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher` WHERE subName='"+subject+"'");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            return new Teacher(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
                    );
        }
        return null;
    }

    @Override
    public ArrayList<String> getTeacherNames(String subName) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher` WHERE subName='"+subName+"'");
        ResultSet rst = stm.executeQuery();
        ArrayList<String> teacherName = new ArrayList<>();
        while (rst.next()) {
            teacherName.add(rst.getString(2));
        }
        return teacherName;
    }

    @Override
    public String getTeacherID(String name) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher` WHERE subName=?");
        stm.setObject(1,name);
        ResultSet rst = stm.executeQuery();
        String tID = null;
        while (rst.next()) {
            tID = rst.getString(1);
        }
        return tID;
    }

    @Override
    public ArrayList<String> getSubjectsFromTeacherID(String tID) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher` WHEERE TeacherId='"+tID+"'");
        ResultSet rst = stm.executeQuery();
        ArrayList<String> subjectNames = new ArrayList<>();
        while (rst.next()) {
            subjectNames.add(rst.getString(1));
        }
        return subjectNames;
    }

    @Override
    public String getFeesFromTeacherName(String tName) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher` WHERE TeacherName='"+tName+"'");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            return rst.getString(9);
        }

        return null;
    }


    public static ArrayList<Teacher> searchTeacher(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher` WHERE TeacherId LIKE '%"+value+"%' || TeacherName LIKE '%"+value+"%' || teacherAddress LIKE '%"+value+"%' || nic LIKE '%"+value+"%' || subName LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();


        ArrayList<Teacher> teachers = new ArrayList<>();

        while (rst.next()) {
            teachers.add(new Teacher(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            ));
        }

        return teachers;
    }

    @Override
    public ArrayList<Teacher> getDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher`");
        ResultSet rst = stm.executeQuery();
        ArrayList<Teacher> teachers = new ArrayList<>();
        while (rst.next()) {
            teachers.add(new Teacher(
                    rst.getString(2),
                    rst.getString(8)
            ));
        }
        return teachers;
    }

    @Override
    public int getAmountOfTeachers() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher`");
        ResultSet rst = stm.executeQuery();
        int count=0;
        while (rst.next()) {
            count++;
        }
        return count;
    }

    @Override
    public ArrayList<String> getTeacherNames() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher`");
        ResultSet rst = stm.executeQuery();
        ArrayList<String> teacherName = new ArrayList<>();
        while (rst.next()) {
            teacherName.add(rst.getString(2));
        }
        return teacherName;
    }

    @Override
    public String getTeacherSalaryDetails(String tID, String month) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `teacher salary` WHERE TMonth='"+month+"' && TeacherId='"+tID+"'");
        ResultSet rst = stm.executeQuery();
        boolean b = rst.next();
        if(b == true){
            return "Paid";
        }
        return "Not Paid";
    }

    @Override
    public TeacherSalary getSalaryDetails(String tID) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `teacher salary` WHERE TeacherId='"+tID+"'");
        ResultSet rst = stm.executeQuery();
        while (rst.next()){
            return new TeacherSalary(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9),
                    rst.getString(10)
            );
        }
        return null;
    }

}
