package Model;

public class StudentDetail {
    private String studentID;
    private String studentName;
    private String examYear;
    private String teacherID;
    private String teacherName;
    private String subName;

    public StudentDetail(String studentID, String studentName, String examYear, String teacherID, String teacherName, String subName) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.examYear = examYear;
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.subName = subName;
    }

    public StudentDetail() {
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

    public String getExamYear() {
        return examYear;
    }

    public void setExamYear(String examYear) {
        this.examYear = examYear;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    @Override
    public String toString() {
        return "StudentDetail{" +
                "studentID='" + studentID + '\'' +
                ", studentName='" + studentName + '\'' +
                ", examYear='" + examYear + '\'' +
                ", teacherID='" + teacherID + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", subName='" + subName + '\'' +
                '}';
    }
}
