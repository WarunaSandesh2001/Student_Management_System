package Interface;

import Model.EmployeeSalary;
import Model.Payment;
import Model.Subject;
import Model.TeacherSalary;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SubjectInterface {

    public String setSubjectIDs() throws SQLException, ClassNotFoundException;
    public boolean setInfo(Subject u) throws SQLException, ClassNotFoundException ;
    public ArrayList<Subject> getAllSubjectDetails() throws SQLException, ClassNotFoundException ;
    public String getSubjectNameByDescription(String desc) throws SQLException, ClassNotFoundException ;
    public ArrayList<String> getSubjectNames() throws SQLException, ClassNotFoundException  ;
    public boolean updateSubjects(Subject s) throws SQLException, ClassNotFoundException;
    public boolean deleteSubject(Subject s) throws SQLException, ClassNotFoundException ;
    public int getAmountOfSubjects() throws SQLException, ClassNotFoundException ;
    public ArrayList<String> getSubjectNamesByTeacherName(String tName) throws SQLException, ClassNotFoundException ;

}
