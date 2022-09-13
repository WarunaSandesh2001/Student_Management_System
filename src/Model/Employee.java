package Model;

public class Employee {
    private String employeeID;
    private String firstName;
    private String lastName;
    private String address;
    private String nic;
    private String email;
    private String contact;
    private String jobRole;

    public Employee(String employeeID, String firstName, String lastName, String address, String nic, String email, String contact, String jobRole) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.nic = nic;
        this.email = email;
        this.contact = contact;
        this.jobRole = jobRole;
    }

    public Employee() {
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        return "Employee{" +
                "employeeID='" + employeeID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", nic='" + nic + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", jobRole='" + jobRole + '\'' +
                '}';
    }
}
