package View.tm;

public class EmployeeSalaryTM {
    private String employeeID;
    private String employeeName;
    private String jobRole;
    private String workingDays;
    private String salaryPerDay;
    private String allowances;
    private String deductions;
    private String netSalary;
    private String condition;

    public EmployeeSalaryTM(String employeeID, String employeeName, String jobRole, String workingDays, String salaryPerDay, String allowances, String deductions, String netSalary, String condition) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.jobRole = jobRole;
        this.workingDays = workingDays;
        this.salaryPerDay = salaryPerDay;
        this.allowances = allowances;
        this.deductions = deductions;
        this.netSalary = netSalary;
        this.condition = condition;
    }

    public EmployeeSalaryTM() {
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

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "EmployeeSalaryTM{" +
                "employeeID='" + employeeID + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", jobRole='" + jobRole + '\'' +
                ", workingDays='" + workingDays + '\'' +
                ", salaryPerDay='" + salaryPerDay + '\'' +
                ", allowances='" + allowances + '\'' +
                ", deductions='" + deductions + '\'' +
                ", netSalary='" + netSalary + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
