package View.tm;

public class PaymentInfoTM {
    private String subject;
    private String teacher;
    private String fee;
    private String condition;

    public PaymentInfoTM(String subject, String teacher, String fee, String condition) {
        this.subject = subject;
        this.teacher = teacher;
        this.fee = fee;
        this.condition = condition;
    }

    public PaymentInfoTM() {
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

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "PaymentInfoTM{" +
                "subject='" + subject + '\'' +
                ", teacher='" + teacher + '\'' +
                ", fee='" + fee + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
