package View.tm;

public class StudentMonthlyPaymentsDetailTM {
    private String studentID;
    private String name;
    private String nic;
    private String address;
    private String email;
    private String contact;
    private String examYear;

    public StudentMonthlyPaymentsDetailTM(String studentID, String name, String nic, String address, String email, String contact, String examYear) {
        this.studentID = studentID;
        this.name = name;
        this.nic = nic;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.examYear = examYear;
    }

    public StudentMonthlyPaymentsDetailTM() {
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getExamYear() {
        return examYear;
    }

    public void setExamYear(String examYear) {
        this.examYear = examYear;
    }

    @Override
    public String toString() {
        return "StudentMonthlyPaymentsDetailTM{" +
                "studentID='" + studentID + '\'' +
                ", name='" + name + '\'' +
                ", nic='" + nic + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", examYear='" + examYear + '\'' +
                '}';
    }
}
