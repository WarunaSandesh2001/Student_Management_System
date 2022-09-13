package Interface;

import Model.Subject;
import Model.Teacher;
import Model.TeacherSalary;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TeacherInterface {

    public String setTeacherIDs() throws SQLException, ClassNotFoundException;
    public boolean setInfo(Teacher u) throws SQLException, ClassNotFoundException ;
    public ArrayList<Teacher> getAllTeacherDetails() throws SQLException, ClassNotFoundException ;
    public boolean updateTeacher(Teacher t) throws SQLException, ClassNotFoundException ;
    public boolean deleteTeacher(Teacher t) throws SQLException, ClassNotFoundException  ;
    public Teacher getTeacherBySubject(String subject) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getTeacherNames(String subName) throws SQLException, ClassNotFoundException ;
    public String getTeacherID(String name) throws SQLException, ClassNotFoundException ;
    public ArrayList<String> getSubjectsFromTeacherID(String tID) throws SQLException, ClassNotFoundException ;
    public String getFeesFromTeacherName(String tName) throws SQLException, ClassNotFoundException ;
    public ArrayList<Teacher> getDetails() throws SQLException, ClassNotFoundException  ;
    public int getAmountOfTeachers() throws SQLException, ClassNotFoundException  ;
    public ArrayList<String> getTeacherNames() throws SQLException, ClassNotFoundException ;
    public String getTeacherSalaryDetails(String tID, String month) throws SQLException, ClassNotFoundException ;
    public TeacherSalary getSalaryDetails(String tID) throws SQLException, ClassNotFoundException ;

}
