package Model;

public class Attendance {
    private String studentID;
    private String studentName;
    private String attendance;
    private String subjectName;
    private String teacherName;
    private String examYear;
    private String attendanceTime;
    private String attendanceDate;

    public Attendance(String studentID, String studentName, String attendance, String subjectName, String teacherName, String examYear, String attendanceTime, String attendanceDate) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.attendance = attendance;
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.examYear = examYear;
        this.attendanceTime = attendanceTime;
        this.attendanceDate = attendanceDate;
    }

    public Attendance(String studentID, String studentName, String attendance, String examYear) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.examYear = examYear;
        this.attendance = attendance;
    }

    public Attendance() {
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getExamYear() {
        return examYear;
    }

    public void setExamYear(String examYear) {
        this.examYear = examYear;
    }

    public String getAttendanceTime() {
        return attendanceTime;
    }

    public void setAttendanceTime(String attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "studentID='" + studentID + '\'' +
                ", studentName='" + studentName + '\'' +
                ", attendance='" + attendance + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", examYear='" + examYear + '\'' +
                ", attendanceTime='" + attendanceTime + '\'' +
                ", attendanceDate='" + attendanceDate + '\'' +
                '}';
    }
}
