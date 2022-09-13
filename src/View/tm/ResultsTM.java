package View.tm;

public class ResultsTM {
    private String studentID;
    private String subjectName;
    private String issuedDate;
    private String result;
    private String marks;

    public ResultsTM(String studentID, String subjectName, String issuedDate, String result, String marks) {
        this.studentID = studentID;
        this.subjectName = subjectName;
        this.issuedDate = issuedDate;
        this.result = result;
        this.marks = marks;
    }

    public ResultsTM() {
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
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

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "ResultsTM{" +
                "studentID='" + studentID + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", issuedDate='" + issuedDate + '\'' +
                ", result='" + result + '\'' +
                ", marks='" + marks + '\'' +
                '}';
    }
}
