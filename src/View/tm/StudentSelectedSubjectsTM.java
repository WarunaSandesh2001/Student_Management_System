package View.tm;

public class StudentSelectedSubjectsTM {
    private String subject;
    private String teacher;

    public StudentSelectedSubjectsTM(String subject, String teacher) {
        this.subject = subject;
        this.teacher = teacher;
    }

    public StudentSelectedSubjectsTM() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "StudentSelectedSubjectsTM{" +
                "subject='" + subject + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
