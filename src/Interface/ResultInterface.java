package Interface;

import Model.Result;
import View.tm.ResultsTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ResultInterface {
    public boolean setInfo(Result u) throws SQLException, ClassNotFoundException;
    public boolean searchResults(String sID,String month,String subject,String examName) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getExamNames() throws SQLException, ClassNotFoundException;
    public boolean deleteStudentResults(String sID) throws SQLException, ClassNotFoundException;
    public ArrayList<ResultsTM> getAllStudentResultDetails(String month, String examName) throws SQLException, ClassNotFoundException;
    public Result getStudentResultByIDOrExamName(String id ,String eName,String month) throws SQLException, ClassNotFoundException;
    public boolean deleteResultsFromTable(String sID,String month, String eName) throws SQLException, ClassNotFoundException;
}
