package View.tm;

public class EmployeeDetailsTM {
    private String employeeID;
    private String employeeName;
    private String address;
    private String email;
    private String nic;
    private String contact;
    private String jobRole;

    public EmployeeDetailsTM(String employeeID, String employeeName, String address, String email, String nic, String contact, String jobRole) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.address = address;
        this.email = email;
        this.nic = nic;
        this.contact = contact;
        this.jobRole = jobRole;
    }

    public EmployeeDetailsTM() {
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

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    @Override
    public String toString() {
        return "EmployeeDetailsTM{" +
                "employeeID='" + employeeID + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", nic='" + nic + '\'' +
                ", contact='" + contact + '\'' +
                ", jobRole='" + jobRole + '\'' +
                '}';
    }
}
