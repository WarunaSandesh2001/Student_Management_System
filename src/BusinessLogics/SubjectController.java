package BusinessLogics;

import Interface.SubjectInterface;
import Model.Employee;
import Model.Subject;
import Model.Teacher;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectController implements SubjectInterface {

    @Override
    public String setSubjectIDs() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT subId FROM `Subject` ORDER BY subId DESC LIMIT 1").executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "Sub-00"+tempId;
            }else if(tempId<=99){
                return "Sub-0"+tempId;
            }else{
                return "Sub-"+tempId;
            }

        }else{
            return "Sub-001";
        }
    }

    @Override
    public boolean setInfo(Subject u) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `Subject` VALUES(?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,u.getSubjectID());
        stm.setObject(2,u.getSubjectName());
        stm.setObject(3,u.getSubjectDescription());

        return stm.executeUpdate()>0;
    }

    @Override
    public ArrayList<Subject> getAllSubjectDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Subject`");
        ResultSet rst = stm.executeQuery();
        ArrayList<Subject> subjects = new ArrayList<>();
        while (rst.next()) {
            subjects.add(new Subject(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
        }
        return subjects;
    }

    @Override
    public String getSubjectNameByDescription(String desc) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Subject` WHERE subjectDescription='"+desc+"'");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            return rst.getString(2);
        }
        return null;
    }



    @Override
    public ArrayList<String> getSubjectNames() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Subject`");
        ResultSet rst = stm.executeQuery();
        ArrayList<String> subjectNames = new ArrayList<>();
        while (rst.next()) {
            subjectNames.add(rst.getString(2));
        }
        return subjectNames;
    }

    @Override
    public boolean updateSubjects(Subject s) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `Subject` SET subId=?, subjectName=?, subjectDescription=?   WHERE subId='"+s.getSubjectID()+"'");
        stm.setObject(1,s.getSubjectID());
        stm.setObject(2,s.getSubjectName());
        stm.setObject(3,s.getSubjectDescription());
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean deleteSubject(Subject s) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Subject` WHERE subId='"+s.getSubjectID()+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public static ArrayList<Subject> searchSubject(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Subject` WHERE subId LIKE '%"+value+"%' || subjectName LIKE '%"+value+"%' || subjectDescription LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();


        ArrayList<Subject> subjects = new ArrayList<>();

        while (rst.next()) {
            subjects.add(new Subject(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
        }

        return subjects;
    }

    @Override
    public int getAmountOfSubjects() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Subject`");
        ResultSet rst = stm.executeQuery();
        int count=0;
        while (rst.next()) {
             count++;
        }
        return count;
    }

    @Override
    public ArrayList<String> getSubjectNamesByTeacherName(String tName) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Teacher` WHERE TeacherName='"+tName+"' ");
        ResultSet rst = stm.executeQuery();
        ArrayList<String> subjectNames = new ArrayList<>();
        while (rst.next()) {
            subjectNames.add(rst.getString(8));
        }
        return subjectNames;
    }

}
