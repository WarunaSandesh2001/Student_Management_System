package Controller;

import BusinessLogics.AttendanceController;
import BusinessLogics.StudentController;
import BusinessLogics.SubjectController;
import BusinessLogics.TeacherController;
import Model.Attendance;
import Model.Student;
import View.tm.StudentDetailTM;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

public class StudentAttendanceFormController {
    public AnchorPane attendanceFormContext;
    public TextField txtSearchStudent;
    public JFXComboBox<String> cmbSubject;
    public JFXComboBox<String> cmbTeacher;
    public TableView tblStudentDetails;
    public TableColumn colStudentID;
    public TableColumn colName;
    public TableColumn colNIC;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colExamYear;
    public JFXDatePicker dtPicker;
    public Button btnMark;
    public TableView tblAttendance;
    public TableColumn colAID;
    public TableColumn colASName;
    public TableColumn colAExamYear;
    public TableColumn colAttendance;
    public Label lblTotalStudents;
    public Label lblAttendCount;
    public TextField txtSearchAttendance;

    ArrayList<String> subjectNames = new ArrayList<>();
    ArrayList<String> teacherNames = new ArrayList<>();
    ArrayList<StudentDetailTM> students = new ArrayList<>();
    ArrayList<Student> studentDetails = new ArrayList<>();
    ArrayList<StudentDetailTM> temp = new ArrayList<>();
    ArrayList<Attendance> attendance = new ArrayList<>();
    int selectedRowInStudentTable = -1;
    int selectRowToDelete = -1;
    int attendCount = 0;
    int studentsCount = 0;

    public void initialize(){
        lblAttendCount.setText(String.valueOf(attendCount));
        dtPicker.setValue(LocalDate.now());
        cmbTeacher.setDisable(true);
        btnMark.setDisable(true);
        //System.out.println(dtPicker.getTypeSelector());

        try {
            studentDetails = new StudentController().getAllStudentDetails();
            subjectNames = new SubjectController().getSubjectNames();
            cmbSubject.getItems().setAll(subjectNames);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbSubject.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cmbTeacher.getItems().clear();
            cmbTeacher.setDisable(false);
            btnMark.setDisable(true);
            
            try {
                teacherNames = new TeacherController().getTeacherNames(cmbSubject.getSelectionModel().getSelectedItem());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            cmbTeacher.getItems().setAll(teacherNames);
        });

        cmbTeacher.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            btnMark.setDisable(false);
            try {
                attendance=new AttendanceController().getAttendanceSheet(cmbSubject.getSelectionModel().getSelectedItem(),cmbTeacher.getSelectionModel().getSelectedItem(),/*"09/23/2021"*/String.valueOf(dtPicker.getValue()));
                attendCount = attendance.size();
                lblAttendCount.setText(String.valueOf(attendCount));
                studentsCount = new StudentController().getStudentCount(cmbSubject.getSelectionModel().getSelectedItem(),cmbTeacher.getSelectionModel().getSelectedItem());
                lblTotalStudents.setText(String.valueOf(studentsCount));
                setDataToAttendanceTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        setDataToStudentDetailTable();

        tblStudentDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRowInStudentTable = (int) newValue;
        });

        tblAttendance.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectRowToDelete = (int) newValue;
        });

        txtSearchStudent.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

        txtSearchAttendance.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchAttendanceOfTable(newValue);
            }
        });
    }

    public void searchAttendanceOnAction(ActionEvent actionEvent) {
        searchAttendanceOfTable(txtSearchAttendance.getText());
    }

    private void searchAttendanceOfTable(String text) {
        try {
            ArrayList<Attendance> student = new ArrayList<>();
            student = AttendanceController.searchStudent(text);
            tblAttendance.getItems().setAll(student);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void search(String value){
        try {
            ArrayList<Student> student = new ArrayList<>();
            temp.clear();
            student = StudentController.searchStudent(value);
            for(Student s : student){
                for(StudentDetailTM q : students){
                    if(s.getStudentID().equals(q.getStudentID())){
                        temp.add(q);
                    }
                }
            }
            tblStudentDetails.getItems().setAll(temp);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setDataToStudentDetailTable(){

        for(Student s : studentDetails){
            StudentDetailTM s1 = new StudentDetailTM(s.getStudentID(),s.getFullName(),s.getNic(),s.getAddress(),s.getEmail(),s.getContact(),s.getExamYear());
            students.add(s1);
            temp.add(s1);
        }

        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("studentNIC"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("studentAddress"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("studentEmail"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("studentContact"));
        colExamYear.setCellValueFactory(new PropertyValueFactory<>("studentExamYear"));

        tblStudentDetails.setItems(FXCollections.observableArrayList(temp));

    }

    public void searchStudentOnAction(ActionEvent actionEvent) {
        search(txtSearchStudent.getText());
    }

    public void checkAttendanceInfo_OnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!cmbSubject.getSelectionModel().isEmpty() && !cmbTeacher.getSelectionModel().isEmpty()) {
            attendance = new AttendanceController().getAttendanceSheet(cmbSubject.getSelectionModel().getSelectedItem(), cmbTeacher.getSelectionModel().getSelectedItem(),/*"09/23/2021"*/String.valueOf(dtPicker.getValue()));
            attendCount = attendance.size();
            lblAttendCount.setText(String.valueOf(attendCount));
            studentsCount = new StudentController().getStudentCount(cmbSubject.getSelectionModel().getSelectedItem(),cmbTeacher.getSelectionModel().getSelectedItem());
            lblTotalStudents.setText(String.valueOf(studentsCount));
            setDataToAttendanceTable();
        }
    }

    private void setDataToAttendanceTable(){
        colAID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colASName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colAExamYear.setCellValueFactory(new PropertyValueFactory<>("examYear"));
        colAttendance.setCellValueFactory(new PropertyValueFactory<>("attendance"));

        tblAttendance.setItems(FXCollections.observableArrayList(attendance));
    }

    private int searchAttendance(StudentDetailTM t){
        for (Attendance e : attendance) {
            if(e.getStudentID().equals(t.getStudentID())){
                return 0;
            }
        }
        return -1;
    }

    public void markAttendanceOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        attendance.clear();
        attendance=new AttendanceController().getAttendanceSheet(cmbSubject.getSelectionModel().getSelectedItem(),cmbTeacher.getSelectionModel().getSelectedItem(),String.valueOf(dtPicker.getValue()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        String time = simpleDateFormat.format(new Date());
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());

        if(!cmbTeacher.getSelectionModel().getSelectedItem().isEmpty() && !cmbSubject.getSelectionModel().getSelectedItem().isEmpty()){
             if(selectedRowInStudentTable==-1){
                 new Alert(Alert.AlertType.WARNING,"Please select a student and try again..!").show();
             }else{
                 StudentDetailTM t = temp.get(selectedRowInStudentTable);
                 int count = searchAttendance(t);

                 if(!attendance.isEmpty()) {

                     if (count==0) {
                         new Alert(Alert.AlertType.CONFIRMATION, "Already marked..!").show();
                     } else {
                         if (new StudentController().getRegisterOrNot(t.getStudentID(), cmbTeacher.getSelectionModel().getSelectedItem(), cmbSubject.getSelectionModel().getSelectedItem())) {
                             Attendance a = new Attendance(t.getStudentID(), t.getStudentName(), "Present", cmbSubject.getSelectionModel().getSelectedItem(), cmbTeacher.getSelectionModel().getSelectedItem(), t.getStudentExamYear(), time, date);
                             if (new AttendanceController().saveAttendance(a)) {
                                 attendCount++;
                                 lblAttendCount.setText(String.valueOf(attendCount));
                                 attendance.add(a);
                                 tblAttendance.setItems(FXCollections.observableArrayList(attendance));
                             } else {
                                 new Alert(Alert.AlertType.WARNING, "Error..!").show();
                             }
                        }else {
                             new Alert(Alert.AlertType.WARNING,"This student is not registered for this class..!").show();
                         }
                     }
                 }else{
                     if (new StudentController().getRegisterOrNot(t.getStudentID(), cmbTeacher.getSelectionModel().getSelectedItem(), cmbSubject.getSelectionModel().getSelectedItem())) {
                         Attendance a = new Attendance(t.getStudentID(), t.getStudentName(), "Present", cmbSubject.getSelectionModel().getSelectedItem(), cmbTeacher.getSelectionModel().getSelectedItem(), t.getStudentExamYear(), time, date);
                         if (new AttendanceController().saveAttendance(a)) {
                             attendCount++;
                             lblAttendCount.setText(String.valueOf(attendCount));
                             attendance.add(a);
                             tblAttendance.setItems(FXCollections.observableArrayList(attendance));
                         } else {
                             new Alert(Alert.AlertType.WARNING, "Error..!").show();
                         }
                     }else {
                         new Alert(Alert.AlertType.WARNING,"This student is not registered for this class..!").show();
                     }
                 }
             }
        }else {}
    }

    public void deleteAttendanceOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(selectRowToDelete!=-1) {
            Attendance a = attendance.get(selectRowToDelete);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete Message");
            alert.setHeaderText("Message of Delete attendance of Students..");
            alert.setContentText("Do you want to Delete this attendance.?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null || option.get() == ButtonType.CANCEL) {


            } else if (option.get() == ButtonType.OK) {
                if (new AttendanceController().deleteAttendance(a)) {
                    attendCount++;
                    lblAttendCount.setText(String.valueOf(attendCount));
                    attendance.remove(selectRowToDelete);
                    tblAttendance.setItems(FXCollections.observableArrayList(attendance));
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try again..!").show();
                }
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Select a row and try again").show();
        }
    }


}
