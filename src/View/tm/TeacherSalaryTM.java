package View.tm;

public class TeacherSalaryTM {
    private String teacherID;
    private String teacherName;
    private String description;
    private String subject;
    private String fee;
    private String totalFee;
    private String deductions;
    private String netSalary;
    private String condition;

    public TeacherSalaryTM(String teacherID, String teacherName, String description, String subject, String fee, String totalFee, String deductions, String netSalary, String condition) {
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.description = description;
        this.subject = subject;
        this.fee = fee;
        this.totalFee = totalFee;
        this.deductions = deductions;
        this.netSalary = netSalary;
        this.condition = condition;
    }

    public TeacherSalaryTM() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getDeductions() {
        return deductions;
    }

    public void setDeductions(String deductions) {
        this.deductions = deductions;
    }

    public String getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(String netSalary) {
        this.netSalary = netSalary;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "TeacherSalaryTM{" +
                "teacherID='" + teacherID + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", description='" + description + '\'' +
                ", subject='" + subject + '\'' +
                ", fee='" + fee + '\'' +
                ", totalFee='" + totalFee + '\'' +
                ", deductions='" + deductions + '\'' +
                ", netSalary='" + netSalary + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
