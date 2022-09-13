package Model;

import java.sql.Time;
import java.util.Date;
import java.util.Timer;

public class Payment {
    private String studentID;
    private String cardNumber;
    private String subject;
    private String teacher;
    private String paymentMonth;
    private Double amount;
    private String pDate;
    private String pTime;

    public Payment(String studentID, String cardNumber, String subject, String teacher, String paymentMonth, Double amount, String pDate, String pTime) {
        this.studentID = studentID;
        this.cardNumber = cardNumber;
        this.subject = subject;
        this.teacher = teacher;
        this.paymentMonth = paymentMonth;
        this.amount = amount;
        this.pDate = pDate;
        this.pTime = pTime;
    }

    public Payment() {
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public String getPaymentMonth() {
        return paymentMonth;
    }

    public void setPaymentMonth(String paymentMonth) {
        this.paymentMonth = paymentMonth;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "studentID='" + studentID + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", subject='" + subject + '\'' +
                ", teacher='" + teacher + '\'' +
                ", paymentMonth='" + paymentMonth + '\'' +
                ", amount=" + amount +
                ", pDate='" + pDate + '\'' +
                ", pTime='" + pTime + '\'' +
                '}';
    }
}
