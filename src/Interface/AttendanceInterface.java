package Interface;

import Model.Attendance;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AttendanceInterface {
    public boolean saveAttendance(Attendance u) throws SQLException, ClassNotFoundException;
    public ArrayList<Attendance> getAttendanceSheet(String subName, String tName, String date) throws SQLException, ClassNotFoundException;
    public boolean deleteAttendance(Attendance s) throws SQLException, ClassNotFoundException;

}
