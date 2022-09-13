package BusinessLogics;

import Interface.StudentInterface;
import Model.Employee;
import Model.Student;
import Model.StudentDetail;
import View.tm.StudentDetailTM;
import View.tm.SubjectTM;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentController implements StudentInterface {

    @Override
    public String setStudentIDs() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT sID FROM `Student` ORDER BY sID DESC LIMIT 1").executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "S-00"+tempId;
            }else if(tempId<=99){
                return "S-0"+tempId;
            }else{
                return "S-"+tempId;
            }

        }else{
            return "S-001";
        }
    }

    @Override
    public boolean setInfoToStudentDetail(ArrayList<StudentDetail> studentDetail) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `Student Detail` VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        for (StudentDetail s : studentDetail) {
            stm.setObject(1, s.getStudentID());
            stm.setObject(2, s.getStudentName());
            stm.setObject(3, s.getExamYear());
            stm.setObject(4, s.getTeacherID());
            stm.setObject(5, s.getTeacherName());
            stm.setObject(6, s.getSubName());
            stm.executeUpdate();
        }
        return true;

    }

    @Override
    public boolean setInfoToStudent(Student u , ArrayList<StudentDetail> s) throws SQLException, ClassNotFoundException {
        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            String query = "INSERT INTO `Student` VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = con.prepareStatement(query);
            stm.setObject(1, u.getStudentID());
            stm.setObject(2, u.getFullName());
            stm.setObject(3, u.getAddress());
            stm.setObject(4, u.getNic());
            stm.setObject(5, u.getEmail());
            stm.setObject(6, u.getGender());
            stm.setObject(7, u.getContact());
            stm.setObject(8, u.getParentName());
            stm.setObject(9, u.getExamYear());
            stm.setObject(10, u.getRegFee());
            stm.setObject(11, u.getRegDate());


            if(stm.executeUpdate()>0){
                if(setInfoToStudentDetail(s)){
                    con.commit();
                    return true;
                }else {
                    con.rollback();
                    return false;
                }
            }else {
                con.rollback();
                return false;
            }

       }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
       }
       finally {
            try {
                con.setAutoCommit(true);
            }catch (SQLException e){
                e.printStackTrace();
            }

       }
        return false;
    }

    @Override
    public ArrayList<Student> getAllStudentDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student`");
        ResultSet rst = stm.executeQuery();
        ArrayList<Student> students = new ArrayList<>();
        while (rst.next()) {
            students.add(new Student(
                    rst.getString(1),
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
            ));
        }
        return students;
    }

    @Override
    public ArrayList<String> getSubjectsFromSID(String sID) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student Detail` WHERE StudentId='"+sID+"'");
        ResultSet rst = stm.executeQuery();
        ArrayList<String> subjects = new ArrayList<>();
        while (rst.next()) {
            subjects.add(rst.getString(6));
        }
        return subjects;
    }

    @Override
    public Student getStudent(String studentID) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student` WHERE sID='"+studentID+"'");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            return new Student(
                    rst.getString(1),
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
    public boolean updateStudent(Student s,String studentID) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `Student` SET sID=?, fullName=?, address=?, nic=?, email=?, gender=?, contact=?, parentName=?, Examyear=?, RegistrationFee=?, regDate=?  WHERE sID='"+studentID+"'");
        stm.setObject(1,s.getStudentID());
        stm.setObject(2,s.getFullName());
        stm.setObject(3,s.getAddress());
        stm.setObject(4,s.getNic());
        stm.setObject(5,s.getEmail());
        stm.setObject(6,s.getGender());
        stm.setObject(7,s.getContact());
        stm.setObject(8,s.getParentName());
        stm.setObject(9,s.getExamYear());
        stm.setObject(10,s.getRegFee());
        stm.setObject(11,s.getRegDate());
        return stm.executeUpdate()>0;
    }

    @Override
    public boolean updateStudentDetailsTable(ArrayList<StudentDetail> s,String stID) throws SQLException, ClassNotFoundException {
        setInfoToStudentDetail(s);
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Student Detail` WHERE StudentId='"+stID+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean deleteStudent(StudentDetailTM e) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Student` WHERE sID='"+e.getStudentID()+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    public static ArrayList<Student> searchStudent(String value) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student` WHERE sID LIKE '%"+value+"%' || fullName LIKE '%"+value+"%' || address LIKE '%"+value+"%' || nic LIKE '%"+value+"%' || Examyear LIKE '%"+value+"%'");
        ResultSet rst = stm.executeQuery();


        ArrayList<Student> students = new ArrayList<>();

        while (rst.next()) {
            students.add(new Student(
                    rst.getString(1),
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
            ));
        }

        return students;
    }

    @Override
    public boolean deleteStudentFromStudentDetailTable(String sID) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Student Detail` WHERE StudentId='"+sID+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public ArrayList<SubjectTM> getSubjectDetailsFromSID(String sID) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student Detail` WHERE StudentId='"+sID+"'");
        ResultSet rst = stm.executeQuery();
        ArrayList<SubjectTM> subjects = new ArrayList<>();
        while (rst.next()) {
            subjects.add(new SubjectTM(rst.getString(6),rst.getString(5)));
        }
        return subjects;
    }

    @Override
    public int getStudentCount(String subName,String tName) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student Detail` WHERE TeacherName='"+tName+"' AND subName='"+subName+"'");
        ResultSet rst = stm.executeQuery();
        int count = 0;
        while (rst.next()) {
            count++;
        }
        return count;
    }

    @Override
    public int getStudent(String sID,String tName,String subName) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student Detail` WHERE StudentId='"+sID+"' AND TeacherName='"+tName+"' AND subName='"+subName+"'");
        ResultSet rst = stm.executeQuery();
        int count = 0;
        while (rst.next()) {
            count++;
        }
        return count;
    }

    @Override
    public boolean getRegisterOrNot(String sID,String tName,String subName) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student Detail` WHERE StudentId='"+sID+"' AND TeacherName='"+tName+"' AND subName='"+subName+"'");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            return true;
        }
        return false;
    }

    @Override
    public int getAmountOfStudent() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Student");
        ResultSet rst = stm.executeQuery();
        int count = 0;
        while (rst.next()) {
            count++;
        }
        return count;
    }

    @Override
    public String getExamYearBySID(String sID) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Student Detail` WHERE StudentId='"+sID+"'");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
             return rst.getString(3);
        }
        return null;
    }
}
