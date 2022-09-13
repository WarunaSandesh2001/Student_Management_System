package Model;

public class EmployeeSalary {
    private String employeeID;
    private String employeeName;
    private String nic;
    private String workingDays;
    private String salaryPerDay;
    private String allowances;
    private String deductions;
    private String netSalary;
    private String eMonth;
    private String eDate;
    private String eTime;

    public EmployeeSalary(String employeeID, String employeeName, String nic, String workingDays, String salaryPerDay, String allowances, String deductions, String netSalary, String eMonth, String eDate, String eTime) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.nic = nic;
        this.workingDays = workingDays;
        this.salaryPerDay = salaryPerDay;
        this.allowances = allowances;
        this.deductions = deductions;
        this.netSalary = netSalary;
        this.eMonth = eMonth;
        this.eDate = eDate;
        this.eTime = eTime;
    }

    public EmployeeSalary() {
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(String workingDays) {
        this.workingDays = workingDays;
    }

    public String getSalaryPerDay() {
        return salaryPerDay;
    }

    public void setSalaryPerDay(String salaryPerDay) {
        this.salaryPerDay = salaryPerDay;
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

    public String geteMonth() {
        return eMonth;
    }

    public void seteMonth(String eMonth) {
        this.eMonth = eMonth;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    @Override
    public String toString() {
        return "EmployeeSalary{" +
                "employeeID='" + employeeID + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", nic='" + nic + '\'' +
                ", workingDays='" + workingDays + '\'' +
                ", salaryPerDay='" + salaryPerDay + '\'' +
                ", allowances='" + allowances + '\'' +
                ", deductions='" + deductions + '\'' +
                ", netSalary='" + netSalary + '\'' +
                ", eMonth='" + eMonth + '\'' +
                ", eDate='" + eDate + '\'' +
                ", eTime='" + eTime + '\'' +
                '}';
    }
}
