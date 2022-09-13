package Model;

public class TeacherSalary {
    private String teacherID;
    private String teacherName;
    private String totalFee;
    private String percentage;
    private String allowances;
    private String deductions;
    private String netSalary;
    private String tMonth;
    private String tDate;
    private String tTime;

    public TeacherSalary(String teacherID, String teacherName, String totalFee, String percentage, String allowances, String deductions, String netSalary, String tMonth, String tDate, String tTime) {
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.totalFee = totalFee;
        this.percentage = percentage;
        this.allowances = allowances;
        this.deductions = deductions;
        this.netSalary = netSalary;
        this.tMonth = tMonth;
        this.tDate = tDate;
        this.tTime = tTime;
    }

    public TeacherSalary() {
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

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getAllowances() {
        return allowances;
    }

    public void setAllowances(String allowances) {
        this.allowances = allowances;
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

    public String gettMonth() {
        return tMonth;
    }

    public void settMonth(String tMonth) {
        this.tMonth = tMonth;
    }

    public String gettDate() {
        return tDate;
    }

    public void settDate(String tDate) {
        this.tDate = tDate;
    }

    public String gettTime() {
        return tTime;
    }

    public void settTime(String tTime) {
        this.tTime = tTime;
    }

    @Override
    public String toString() {
        return "TeacherSalary{" +
                "teacherID='" + teacherID + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", totalFee='" + totalFee + '\'' +
                ", percentage='" + percentage + '\'' +
                ", allowances='" + allowances + '\'' +
                ", deductions='" + deductions + '\'' +
                ", netSalary='" + netSalary + '\'' +
                ", tMonth='" + tMonth + '\'' +
                ", tDate='" + tDate + '\'' +
                ", tTime='" + tTime + '\'' +
                '}';
    }
}
