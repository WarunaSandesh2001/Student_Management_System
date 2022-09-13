package View.tm;

public class StudentInformationTM {
    private String studentID;
    private String studentName;
    private String address;
    private String nic;
    private String email;
    private String gender;
    private String contact;
    private String parentName;
    private String examYear;
    private String regDate;
    private String subjects;

    public StudentInformationTM(String studentID, String studentName, String address, String nic, String email, String gender, String contact, String parentName, String examYear, String regDate, String subjects) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.address = address;
        this.nic = nic;
        this.email = email;
        this.gender = gender;
        this.contact = contact;
        this.parentName = parentName;
        this.examYear = examYear;
        this.regDate = regDate;
        this.subjects = subjects;
    }

    public StudentInformationTM() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getExamYear() {
        return examYear;
    }

    public void setExamYear(String examYear) {
        this.examYear = examYear;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "StudentInformationTM{" +
                "studentID='" + studentID + '\'' +
                ", studentName='" + studentName + '\'' +
                ", address='" + address + '\'' +
                ", nic='" + nic + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", contact='" + contact + '\'' +
                ", parentName='" + parentName + '\'' +
                ", examYear='" + examYear + '\'' +
                ", regDate='" + regDate + '\'' +
                ", subjects='" + subjects + '\'' +
                '}';
    }
}

