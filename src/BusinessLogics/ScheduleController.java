package BusinessLogics;

import Interface.ScheduleInterface;
import Model.Schedule;
import Model.Teacher;
import View.tm.ScheduleTM;
import db.DbConnection;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

public class ScheduleController implements ScheduleInterface {

    @Override
    public boolean setInfo(Schedule u) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO `Schedule` VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,u.getTeacherID());
        stm.setObject(2,u.getTeacherName());
        stm.setObject(3,u.getSubjectName());
        stm.setObject(4,u.getExamYear());
        stm.setObject(5,u.getStartTime());
        stm.setObject(6,u.getEndTime());
        stm.setObject(7,u.getsDate());
        stm.setObject(8,u.getDescription());
        stm.setObject(9,u.getHallNumber());

        return stm.executeUpdate()>0;
    }

    @Override
    public ArrayList<Schedule> getDetails(String date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Schedule` WHERE Sdate='"+date+"'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Schedule> schedules = new ArrayList<>();
        while (rst.next()) {
            schedules.add(new Schedule(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    LocalTime.parse(rst.getString(5)),
                    LocalTime.parse(rst.getString(6)),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            ));
        }
        return schedules;
    }

    @Override
    public ArrayList<Schedule> getDetailsByTeacher(String teacherName , String date) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Schedule` WHERE TeacherName='"+teacherName+"' AND Sdate='"+date+"'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Schedule> schedules = new ArrayList<>();
            while (rst.next()) {
                schedules.add(new Schedule(rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        LocalTime.parse(rst.getString(5)),
                        LocalTime.parse(rst.getString(6)),
                        rst.getString(7),
                        rst.getString(8),
                        rst.getString(9)
                ));
            }
        return schedules;
    }

    @Override
    public ArrayList<Schedule> getAllDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Schedule`");
        ResultSet rst = stm.executeQuery();
        ArrayList<Schedule> schedules = new ArrayList<>();
        while (rst.next()) {
            schedules.add(new Schedule(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    LocalTime.parse(rst.getString(5)),
                    LocalTime.parse(rst.getString(6)),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            ));
        }
        return schedules;
    }

    @Override
    public boolean deleteSchedule(ScheduleTM t) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Schedule` WHERE TeacherName='"+t.getTeacherName()+"' && SubjectName='"+t.getSubject()+"' && examYear='"+t.getSClass()+"' && Sdate='"+t.getDate()+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
}
