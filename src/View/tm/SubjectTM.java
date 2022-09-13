package View.tm;

public class SubjectTM {
    private String subject;
    private String teacher;

    public SubjectTM(String subject, String teacher) {
        this.subject = subject;
        this.teacher = teacher;
    }

    public SubjectTM() {
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
        return "SubjectTM{" +
                "subject='" + subject + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
