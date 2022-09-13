package Controller;

import BusinessLogics.PaymentController;
import BusinessLogics.StudentController;
import BusinessLogics.TeacherController;
import Model.Payment;
import Model.Student;
import View.tm.PaymentInfoTM;
import View.tm.StudentDetailTM;
import View.tm.StudentMonthlyPaymentsDetailTM;
import View.tm.SubjectTM;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class StudentPaymentsFormController {

    public AnchorPane StudentPaymentsFormContext;
    public JFXTextField txtSearchStudent;
    public TableView tblStudentDetails;
    public TableColumn colStudentID;
    public TableColumn colStudentName;
    public TableColumn colNIC;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colExamYear;
    public Label lblTotalFee;
    public JFXCheckBox chBoxPaidSuccessfully;
    public TableView tblSubjectDetails;
    public TableColumn colSubjectName;
    public TableColumn colTeacherName;
    public TableColumn colSubjectFee;
    public ComboBox<String> cmbPaymentMonth;
    public Label lblLastPaidMonth;
    public TextField txtStudentID;
    public ComboBox<String> cmbSubject;
    public JFXRadioButton rdBTNPayForAllSubjects;
    public JFXTextField txtCardNumber;
    public TableColumn colPaymentOK;
    public Button btnPay;

    ArrayList<Student> students = new ArrayList<>();
    ArrayList<StudentMonthlyPaymentsDetailTM> studentPaymentInfo = new ArrayList<>();
    ArrayList<SubjectTM> subjects = new ArrayList<>();
    //ArrayList<StudentMonthlyPaymentsDetailTM> temp = new ArrayList<>();
    int selectedRow = -1;
    Double total = 0.00;
    String teacherName = null;

    public void initialize() throws SQLException, ClassNotFoundException {
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        Date date = new Date();
        String month = formatter.format(date);
        cmbPaymentMonth.setValue(checkMonth(month));
        cmbSubject.setDisable(true);
        btnPay.setDisable(true);

        students = new StudentController().getAllStudentDetails();
        for(Student s : students){
            studentPaymentInfo.add(new StudentMonthlyPaymentsDetailTM(s.getStudentID(),s.getFullName(),s.getNic(),s.getAddress(),s.getEmail(),s.getContact(),s.getExamYear()));
        }
        setDataToStudentDetailsTable();
        cmbPaymentMonth.getItems().addAll("January","February","March","April","May","June","July","August","September","October","November","December");


        txtSearchStudent.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

        tblStudentDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRow = (int) newValue;
            lblTotalFee.setText("0.00 /=");
            rdBTNPayForAllSubjects.setSelected(false);
        });

        cmbPaymentMonth.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<PaymentInfoTM> temp = new ArrayList<>();
            setDataToPaymentTable(temp);
            selectedRow=-1;

        });

        cmbSubject.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            for(SubjectTM s : subjects){
                if(s.getSubject().equals(cmbSubject.getSelectionModel().getSelectedItem())){
                    try {
                        teacherName = s.getTeacher();
                        String fee = new TeacherController().getFeesFromTeacherName(s.getTeacher());
                        lblTotalFee.setText(fee+" /=");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

        });


    }

    public void cardNumberOnAction(KeyEvent keyEvent) {
        btnPay.setDisable(true);
        String regEx = "^(C-)[0-9]{2,}$";
        String text = txtCardNumber.getText();
        Pattern compile = Pattern.compile(regEx);
        boolean matches = compile.matcher(text).matches();

        if(matches){
            txtCardNumber.setStyle("-fx-text-fill: white");
        }else {
            txtCardNumber.setStyle("-fx-text-fill: darkred");
        }

        if(keyEvent.getCode()== KeyCode.ENTER){
            if(matches){
                btnPay.setDisable(false);
            }
        }
    }

    private String checkMonth(String month){
        if(month.equals("01")){
            return "January";
        }else if(month.equals("02")){
            return "February";
        }else if(month.equals("03")){
            return "March";
        }else if(month.equals("04")){
            return "April";
        }else if(month.equals("05")){
            return "May";
        }else if(month.equals("06")){
            return "June";
        }else if(month.equals("07")){
            return "July";
        }else if(month.equals("08")){
            return "August";
        }else if(month.equals("09")){
            return "September";
        }else if(month.equals("10")){
            return "October";
        }else if(month.equals("11")){
            return "November";
        }else if(month.equals("12")){
            return "December";
        }
        return null;
    }

    public void setSubjectsOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if(selectedRow==-1){

        }else{
            cmbSubject.setDisable(false);
            cmbSubject.getItems().clear();
            ArrayList<String> fees = new ArrayList<>();
            ArrayList<PaymentInfoTM> tableInfo = new ArrayList<>();

            StudentMonthlyPaymentsDetailTM s = studentPaymentInfo.get(selectedRow);
            txtStudentID.setText(s.getStudentID());

            subjects = new StudentController().getSubjectDetailsFromSID(s.getStudentID());

            for(SubjectTM tempsubject : subjects){
                String f = new TeacherController().getFeesFromTeacherName(tempsubject.getTeacher());
                fees.add(f);
            }

            int count=0;
            for(SubjectTM n : subjects) {
                String condition = new PaymentController().getFeesFromTeacherName(s.getStudentID(),n.getSubject(),cmbPaymentMonth.getSelectionModel().getSelectedItem());

                PaymentInfoTM paymentInFo = new PaymentInfoTM(n.getSubject(),n.getTeacher(),String.valueOf(fees.get(count)),condition);
                count=count+1;
                tableInfo.add(paymentInFo);
            }
            setDataToPaymentTable(tableInfo);


            ArrayList<String> paymentMonths = new StudentController().getSubjectsFromSID(s.getStudentID());
            cmbSubject.getItems().setAll(paymentMonths);
        }
    }

    private void setDataToPaymentTable(ArrayList<PaymentInfoTM> tableInfo){
        colSubjectName.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colTeacherName.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        colSubjectFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colPaymentOK.setCellValueFactory(new PropertyValueFactory<>("condition"));

        tblSubjectDetails.setItems(FXCollections.observableArrayList(tableInfo));
    }

    public void searchStudentOnAction(ActionEvent actionEvent) {
        search(txtSearchStudent.getText());
    }

    private void search(String value){
        try {
            ArrayList<Student> studentTemp = new ArrayList<>();

            studentPaymentInfo.clear();
            studentTemp = StudentController.searchStudent(value);
            for(Student s : studentTemp){
                studentPaymentInfo.add(new StudentMonthlyPaymentsDetailTM(s.getStudentID(),s.getFullName(),s.getNic(),s.getAddress(),s.getEmail(),s.getContact(),s.getExamYear()));
            }

            tblStudentDetails.getItems().setAll(studentPaymentInfo);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setDataToStudentDetailsTable(){

        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colExamYear.setCellValueFactory(new PropertyValueFactory<>("examYear"));

        tblStudentDetails.setItems(FXCollections.observableArrayList(studentPaymentInfo));

    }


    public void calculateTotalFee(ActionEvent actionEvent) {
        boolean b = rdBTNPayForAllSubjects.isSelected();

        if(b == true){
            cmbSubject.setDisable(true);
            cmbSubject.getSelectionModel().clearSelection();
            total = 0.00;
            for(SubjectTM s : subjects){
                    try {
                        String fee = new TeacherController().getFeesFromTeacherName(s.getTeacher());
                        total = total + Double.parseDouble(fee);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
            }
            lblTotalFee.setText(total+" /=");

        }else{
            cmbSubject.setDisable(false);
            lblTotalFee.setText("0.00 /=");
        }
    }

    public void payOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String pDate = formatter.format(date);
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
        Date time = new Date();
        String pTime = formatter1.format(time);

        boolean b = rdBTNPayForAllSubjects.isSelected();

        if(!txtStudentID.getText().equals("") && (!cmbSubject.getSelectionModel().isEmpty() || b==true) && !txtCardNumber.getText().equals("")){
            if(b==true){
                if((new PaymentController().findPaidOrNot(txtStudentID.getText(),cmbPaymentMonth.getSelectionModel().getSelectedItem())==false)) {
                    for (SubjectTM s : subjects) {
                        Double fee = Double.parseDouble(new TeacherController().getFeesFromTeacherName(s.getTeacher()));
                        Payment payment = new Payment(txtStudentID.getText(), txtCardNumber.getText(), s.getSubject(), s.getTeacher(), cmbPaymentMonth.getSelectionModel().getSelectedItem(), fee, pDate, pTime);
                        if (new PaymentController().savePaymentInfo(payment)) {

                        } else {
                            new Alert(Alert.AlertType.WARNING, "Something went wrong..!").show();
                        }
                    }
                    txtCardNumber.clear();
                    txtStudentID.clear();
                    chBoxPaidSuccessfully.setSelected(false);
                    studentPaymentInfo.clear();
                    tblSubjectDetails.getItems().clear();
                    initialize();
                    new Alert(Alert.AlertType.CONFIRMATION, "Payment success!").show();
                }else{
                    new Alert(Alert.AlertType.CONFIRMATION,"Already Paid..!").show();
                }
            }else{
                if((new PaymentController().findPaidOrNotBySubject(txtStudentID.getText(),cmbPaymentMonth.getSelectionModel().getSelectedItem(),cmbSubject.getSelectionModel().getSelectedItem())==false)) {
                    Double fee = Double.parseDouble(new TeacherController().getFeesFromTeacherName(teacherName));

                    Payment payment = new Payment(txtStudentID.getText(), txtCardNumber.getText(), cmbSubject.getSelectionModel().getSelectedItem(), teacherName, cmbPaymentMonth.getSelectionModel().getSelectedItem(), fee, pDate, pTime);
                    if (new PaymentController().savePaymentInfo(payment)) {
                        txtCardNumber.clear();
                        txtStudentID.clear();
                        chBoxPaidSuccessfully.setSelected(false);
                        studentPaymentInfo.clear();
                        tblSubjectDetails.getItems().clear();
                        initialize();
                        new Alert(Alert.AlertType.CONFIRMATION, "Payment success!").show();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Something went wrong..!").show();
                    }
                }else{
                    new Alert(Alert.AlertType.CONFIRMATION,"Already Paid..!").show();
                }
            }

        }else {
            new Alert(Alert.AlertType.WARNING,"Please fill all fields and try again..!").show();
        }
    }

    public void chBoxPaidSuccessfullyOnAction(ActionEvent actionEvent) {
        if(chBoxPaidSuccessfully.isSelected()){
            btnPay.setDisable(false);
        }else{
            btnPay.setDisable(true);
        }
    }


}
