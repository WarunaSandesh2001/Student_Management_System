package Model;

public class Student {
    private String studentID;
    private String fullName;
    private String address;
    private String nic;
    private String email;
    private String gender;
    private String contact;
    private String parentName;
    private String examYear;
    private String regFee;
    private String regDate;

    public Student(String studentID, String fullName, String address, String nic, String email, String gender, String contact, String parentName, String examYear, String regFee, String regDate) {
        this.studentID = studentID;
        this.fullName = fullName;
        this.address = address;
        this.nic = nic;
        this.email = email;
        this.gender = gender;
        this.contact = contact;
        this.parentName = parentName;
        this.examYear = examYear;
        this.regFee = regFee;
        this.regDate = regDate;
    }

    public Student() {
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getRegFee() {
        return regFee;
    }

    public void setRegFee(String regFee) {
        this.regFee = regFee;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID='" + studentID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", nic='" + nic + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", contact='" + contact + '\'' +
                ", parentName='" + parentName + '\'' +
                ", examYear='" + examYear + '\'' +
                ", regFee='" + regFee + '\'' +
                ", regDate='" + regDate + '\'' +
                '}';
    }
}


