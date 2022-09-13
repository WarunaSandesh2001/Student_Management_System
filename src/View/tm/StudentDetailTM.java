package View.tm;

public class StudentDetailTM {
    private String studentID;
    private String studentName;
    private String studentNIC;
    private String studentAddress;
    private String studentEmail;
    private String studentContact;
    private String studentExamYear;
    private String studentSubjects;

    public StudentDetailTM(String studentID, String studentName, String studentNIC, String studentAddress, String studentEmail, String studentContact, String studentExamYear, String studentSubjects) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentNIC = studentNIC;
        this.studentAddress = studentAddress;
        this.studentEmail = studentEmail;
        this.studentContact = studentContact;
        this.studentExamYear = studentExamYear;
        this.studentSubjects = studentSubjects;
    }

    public StudentDetailTM(String studentID, String studentName, String studentNIC, String studentAddress, String studentEmail, String studentContact, String studentExamYear) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentNIC = studentNIC;
        this.studentAddress = studentAddress;
        this.studentEmail = studentEmail;
        this.studentContact = studentContact;
        this.studentExamYear = studentExamYear;
    }

    public StudentDetailTM() {
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

    public String getStudentNIC() {
        return studentNIC;
    }

    public void setStudentNIC(String studentNIC) {
        this.studentNIC = studentNIC;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentContact() {
        return studentContact;
    }

    public void setStudentContact(String studentContact) {
        this.studentContact = studentContact;
    }

    public String getStudentExamYear() {
        return studentExamYear;
    }

    public void setStudentExamYear(String studentExamYear) {
        this.studentExamYear = studentExamYear;
    }

    public String getStudentSubjects() {
        return studentSubjects;
    }

    public void setStudentSubjects(String studentSubjects) {
        this.studentSubjects = studentSubjects;
    }

    @Override
    public String toString() {
        return "StudentDetailTM{" +
                "studentID='" + studentID + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentNIC='" + studentNIC + '\'' +
                ", studentAddress='" + studentAddress + '\'' +
                ", studentEmail='" + studentEmail + '\'' +
                ", studentContact='" + studentContact + '\'' +
                ", studentExamYear='" + studentExamYear + '\'' +
                ", studentSubjects='" + studentSubjects + '\'' +
                '}';
    }
}


