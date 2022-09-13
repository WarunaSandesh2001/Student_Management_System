package Model;

public class Teacher {
    private String TeacherId;
    private String TeacherName;
    private String teacherEmail;
    private String teacherContact;
    private String teacherAddress;
    private String nic;
    private String teacherDescription;
    private String subName;
    private String fee;

    public Teacher(String teacherId, String teacherName, String teacherEmail, String teacherContact, String teacherAddress, String nic, String teacherDescription, String subName, String fee) {
        TeacherId = teacherId;
        TeacherName = teacherName;
        this.teacherEmail = teacherEmail;
        this.teacherContact = teacherContact;
        this.teacherAddress = teacherAddress;
        this.nic = nic;
        this.teacherDescription = teacherDescription;
        this.subName = subName;
        this.fee = fee;
    }

    public Teacher(String teacherName, String subName) {
        TeacherName = teacherName;
        this.subName = subName;
    }

    public Teacher() {
    }

    public String getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(String teacherId) {
        TeacherId = teacherId;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherContact() {
        return teacherContact;
    }

    public void setTeacherContact(String teacherContact) {
        this.teacherContact = teacherContact;
    }

    public String getTeacherAddress() {
        return teacherAddress;
    }

    public void setTeacherAddress(String teacherAddress) {
        this.teacherAddress = teacherAddress;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getTeacherDescription() {
        return teacherDescription;
    }

    public void setTeacherDescription(String teacherDescription) {
        this.teacherDescription = teacherDescription;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "TeacherId='" + TeacherId + '\'' +
                ", TeacherName='" + TeacherName + '\'' +
                ", teacherEmail='" + teacherEmail + '\'' +
                ", teacherContact='" + teacherContact + '\'' +
                ", teacherAddress='" + teacherAddress + '\'' +
                ", nic='" + nic + '\'' +
                ", teacherDescription='" + teacherDescription + '\'' +
                ", subName='" + subName + '\'' +
                ", fee='" + fee + '\'' +
                '}';
    }
}
