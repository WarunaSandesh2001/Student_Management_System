package Interface;

import Model.Schedule;
import View.tm.ScheduleTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ScheduleInterface {
    public boolean setInfo(Schedule u) throws SQLException, ClassNotFoundException;
    public ArrayList<Schedule> getDetails(String date) throws SQLException, ClassNotFoundException;
    public ArrayList<Schedule> getDetailsByTeacher(String teacherName , String date) throws SQLException, ClassNotFoundException;
    public ArrayList<Schedule> getAllDetails() throws SQLException, ClassNotFoundException;
    public boolean deleteSchedule(ScheduleTM t) throws SQLException, ClassNotFoundException;
}
