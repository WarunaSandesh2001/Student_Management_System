package Model;

public class Result {
    private String studentID;
    private String studentName;
    private String subjectName;
    private String issuedDate;
    private String result;
    private String examName;
    private String examMonth;
    private String marks;

    public Result(String studentID, String studentName, String subjectName, String issuedDate, String result, String examName, String examMonth, String marks) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.subjectName = subjectName;
        this.issuedDate = issuedDate;
        this.result = result;
        this.examName = examName;
        this.examMonth = examMonth;
        this.marks = marks;
    }

    public Result() {
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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamMonth() {
        return examMonth;
    }

    public void setExamMonth(String examMonth) {
        this.examMonth = examMonth;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Result{" +
                "studentID='" + studentID + '\'' +
                ", studentName='" + studentName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", issuedDate='" + issuedDate + '\'' +
                ", result='" + result + '\'' +
                ", examName='" + examName + '\'' +
                ", examMonth='" + examMonth + '\'' +
                ", marks='" + marks + '\'' +
                '}';
    }
}
