package BusinessLogics;

import Interface.ResultInterface;
import Model.Employee;
import Model.Result;
import Model.Teacher;
import View.tm.ResultsTM;
import View.tm.StudentDetailTM;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultController implements ResultInterface {

    @Override
    public boolean setInfo(Result u) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `Evaluation Result` VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,u.getStudentID());
        stm.setObject(2,u.getStudentName());
        stm.setObject(3,u.getSubjectName());
        stm.setObject(4,u.getIssuedDate());
        stm.setObject(5,u.getResult());
        stm.setObject(6,u.getExamName());
        stm.setObject(7,u.getExamMonth());
        stm.setObject(8,u.getMarks());

        return stm.executeUpdate()>0;
    }

    @Override
    public boolean searchResults(String sID,String month,String subject,String examName) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `evaluation result` WHERE StudentId='"+sID+"' AND examMonth='"+month+"' AND SubjectName='"+subject+"' AND examName='"+examName+"'");
        ResultSet rst = stm.executeQuery();
        boolean b = rst.next();
        if(b == true){
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<String> getExamNames() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `evaluation result`");
        ResultSet rst = stm.executeQuery();
        ArrayList<String> examNames = new ArrayList<>();
        while (rst.next()) {
            examNames.add(new String(
                    rst.getString(6)
            ));
        }
        return examNames;
    }

    @Override
    public boolean deleteStudentResults(String sID) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `evaluation result` WHERE StudentId='"+sID+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public ArrayList<ResultsTM> getAllStudentResultDetails(String month, String examName) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `evaluation result` WHERE examMonth='"+month+"' AND examName='"+examName+"'");
        ResultSet rst = stm.executeQuery();
        ArrayList<ResultsTM> results = new ArrayList<>();
        while (rst.next()) {
            results.add(new ResultsTM(
                    rst.getString(1),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(8)
            ));
        }
        return results;
    }

    @Override
    public Result getStudentResultByIDOrExamName(String id ,String eName,String month) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `evaluation result` WHERE StudentId='"+id+"' AND examName='"+eName+"' AND examMonth='"+month+"'");
        ResultSet rst = stm.executeQuery();
        boolean b = rst.next();
            if (b==true) {
               Result r =  new Result(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getString(7),
                        rst.getString(8)
                );
                System.out.println(r.toString());
               return r;
            }
        return null;
    }

    @Override
    public boolean deleteResultsFromTable(String sID,String month, String eName) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `evaluation result` WHERE StudentId='"+sID+"' AND examName='"+eName+"' AND examMonth='"+month+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }

}
