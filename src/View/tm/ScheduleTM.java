package View.tm;

public class ScheduleTM {
    private String teacherName;
    private String subject;
    private String hallNo;
    private String description;
    private String date;
    private String startTime;
    private String endTime;
    private String SClass;

    public ScheduleTM(String teacherName, String subject, String hallNo, String description, String date, String startTime, String endTime, String SClass) {
        this.teacherName = teacherName;
        this.subject = subject;
        this.hallNo = hallNo;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.SClass = SClass;
    }

    public ScheduleTM() {
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHallNo() {
        return hallNo;
    }

    public void setHallNo(String hallNo) {
        this.hallNo = hallNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSClass() {
        return SClass;
    }

    public void setSClass(String SClass) {
        this.SClass = SClass;
    }

    @Override
    public String toString() {
        return "ScheduleTM{" +
                "teacherName='" + teacherName + '\'' +
                ", subject='" + subject + '\'' +
                ", hallNo='" + hallNo + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", SClass='" + SClass + '\'' +
                '}';
    }
}
