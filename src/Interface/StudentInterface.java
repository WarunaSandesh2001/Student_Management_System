package Interface;

import Model.StudentDetail;
import View.tm.StudentDetailTM;
import View.tm.SubjectTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentInterface {
    public String setStudentIDs() throws SQLException, ClassNotFoundException;
    public boolean setInfoToStudentDetail(ArrayList<StudentDetail> studentDetail) throws SQLException, ClassNotFoundException;
    public boolean setInfoToStudent(Model.Student u , ArrayList<StudentDetail> s) throws SQLException, ClassNotFoundException;
    public ArrayList<Model.Student> getAllStudentDetails() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getSubjectsFromSID(String sID) throws SQLException, ClassNotFoundException;
    public Model.Student getStudent(String studentID) throws SQLException, ClassNotFoundException;
    public boolean updateStudent(Model.Student s, String studentID) throws SQLException, ClassNotFoundException;
    public boolean updateStudentDetailsTable(ArrayList<StudentDetail> s,String stID) throws SQLException, ClassNotFoundException;
    public boolean deleteStudent(StudentDetailTM e) throws SQLException, ClassNotFoundException;
    //public ArrayList<Model.Student> searchStudent(String value) throws SQLException, ClassNotFoundException ;
    public boolean deleteStudentFromStudentDetailTable(String sID) throws SQLException, ClassNotFoundException;
    public ArrayList<SubjectTM> getSubjectDetailsFromSID(String sID) throws SQLException, ClassNotFoundException ;
    public int getStudentCount(String subName,String tName) throws SQLException, ClassNotFoundException ;
    public int getStudent(String sID,String tName,String subName) throws SQLException, ClassNotFoundException ;
    public boolean getRegisterOrNot(String sID,String tName,String subName) throws SQLException, ClassNotFoundException  ;
    public int getAmountOfStudent() throws SQLException, ClassNotFoundException  ;
    public String getExamYearBySID(String sID) throws SQLException, ClassNotFoundException  ;


}
