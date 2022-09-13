package Model;

public class Subject {
    private String subjectID;
    private String subjectName;
    private String subjectDescription;

    public Subject(String subjectID, String subjectName, String subjectDescription) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
    }

    public Subject() {
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectID='" + subjectID + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", subjectDescription='" + subjectDescription + '\'' +
                '}';
    }
}
