package Model;

import java.time.LocalTime;

public class Schedule {
    private String teacherID;
    private String teacherName;
    private String subjectName;
    private String examYear;
    private LocalTime startTime;
    private LocalTime endTime;
    private String sDate;
    private String description;
    private String hallNumber;

    public Schedule(String teacherID, String teacherName, String subjectName, String examYear, LocalTime startTime, LocalTime endTime, String sDate, String description, String hallNumber) {
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.subjectName = subjectName;
        this.examYear = examYear;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sDate = sDate;
        this.description = description;
        this.hallNumber = hallNumber;
    }

    public Schedule() {
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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getExamYear() {
        return examYear;
    }

    public void setExamYear(String examYear) {
        this.examYear = examYear;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(String hallNumber) {
        this.hallNumber = hallNumber;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "teacherID='" + teacherID + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", examYear='" + examYear + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", sDate='" + sDate + '\'' +
                ", description='" + description + '\'' +
                ", hallNumber='" + hallNumber + '\'' +
                '}';
    }
}
