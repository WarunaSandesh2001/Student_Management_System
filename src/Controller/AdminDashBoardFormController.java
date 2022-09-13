package Controller;

import BusinessLogics.EmployeeController;
import BusinessLogics.StudentController;
import BusinessLogics.SubjectController;
import BusinessLogics.TeacherController;
import Model.Employee;
import Model.Student;
import Model.Subject;
import Model.Teacher;
import View.tm.StudentInformationTM;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class AdminDashBoardFormController {
    public AnchorPane AdminDashBoardFormContext;
    public AnchorPane AdminDashBoardForm1Context;
    public TableView tblStudentDetails;
    public TableColumn colStudentID;
    public TableColumn colStudentName;
    public TableColumn colStudentAddress;
    public TableColumn colStudentNIC;
    public TableColumn colStudentEmail;
    public TableColumn colStudentGender;
    public TableColumn colStudentContact;
    public TableColumn colStudentParentName;
    public TableColumn colStudentExamYear;
    public TableColumn colStudentRegisterDate;
    public TableColumn colStudentSubjects;
    public Label lblTime;
    public Label lblDate;
    public JFXTextField txtSearchStudent;
    public TableColumn colExamYear;
    public TableColumn colParentName;
    public TableColumn colRegDate;
    public TableColumn colSubjects;
    public TableView tblTeacher;
    public TableColumn colTeacherID;
    public TableColumn colTeacherName;
    public TableColumn colTeacherEmail;
    public TableColumn colTeacherContact;
    public TableColumn colTeacherAddress;
    public TableColumn colTeacherNIC;
    public TableColumn colTeacherDescription;
    public TableColumn colTeacherSubjectName;
    public TableColumn colFee;
    public JFXTextField txtSearchTeacher;
    public TableView tblSubject;
    public TableColumn colSubjectID;
    public TableColumn colSubjectName;
    public TableColumn colSubjectDescription;
    public JFXTextField txtSearchSubject;
    public TableView tblEmployeeTable;
    public TableColumn colEmployeeID;
    public TableColumn colEmployeeFName;
    public TableColumn colEmployeeLName;
    public TableColumn colEmployeeAddress;
    public TableColumn colEmployeeNIC;
    public TableColumn colEmployeeEmail;
    public TableColumn colEmployeeContact;
    public TableColumn colJobRole;
    public JFXTextField txtSearchEmployee;
    public Button btnInformation;
    public Button btnEmployee;
    public Button btnSystemReports;
    public Button btnSetting;
    public Button btnTeacher;
    public Button btnSubjects;
    public Button btnSalary;
    public Button btnGetEmployeeDetails;
    public Button btnSubjectDetails;
    public Button btnGetTeacherDetails;
    public Button btnGetStudentDetails;
    Stage stage = null;

    ArrayList<Student> students = new ArrayList<>();
    ArrayList<Teacher> teachers = new ArrayList<>();
    ArrayList<Subject> subjects = new ArrayList<>();
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<StudentInformationTM> tblTemp = new ArrayList();

    public void initialize(){
        btnInformation.setMouseTransparent(true);
        btnInformation.setStyle("-fx-background-color : red;");
        btnInformation.setStyle("-fx-text-fill: red;");
        btnInformation.setStyle("-fx-base: #c41010;");
        btnInformation.applyCss();
        loadDateAndTime();

        try {

            students = new StudentController().getAllStudentDetails();
            teachers = new TeacherController().getAllTeacherDetails();
            subjects = new SubjectController().getAllSubjectDetails();
            employees = new EmployeeController().getAllEmployeeDetails();
            setDataToStudents();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setDataToTeacher();
        setDataToSubject();
        setDataToEmployee();

        txtSearchStudent.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchStudent(newValue);
            }
        });

        txtSearchTeacher.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchTeacher(newValue);
            }
        });

        txtSearchSubject.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchSubject(newValue);
            }
        });

        txtSearchEmployee.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                searchEmployee(newValue);
            }
        });
    }

    private void setStyles(){
        btnInformation.setStyle("-fx-background-color : white;");
        btnInformation.setStyle("-fx-text-fill: black;");
        btnInformation.setStyle("-fx-base: white;");
        btnInformation.setStyle("-fx-background-radius: 15;");

        btnEmployee.setStyle("-fx-background-color : white;");
        btnEmployee.setStyle("-fx-text-fill: black;");
        btnEmployee.setStyle("-fx-base: white;");
        btnEmployee.setStyle("-fx-background-radius: 15;");

        btnTeacher.setStyle("-fx-background-color : white;");
        btnTeacher.setStyle("-fx-text-fill: black;");
        btnTeacher.setStyle("-fx-base: white;");
        btnTeacher.setStyle("-fx-background-radius: 15;");

        btnSubjects.setStyle("-fx-background-color : white;");
        btnSubjects.setStyle("-fx-text-fill: black;");
        btnSubjects.setStyle("-fx-base: white;");
        btnSubjects.setStyle("-fx-background-radius: 15;");

        btnSetting.setStyle("-fx-background-color : white;");
        btnSetting.setStyle("-fx-text-fill: black;");
        btnSetting.setStyle("-fx-base: white;");
        btnSetting.setStyle("-fx-background-radius: 15;");

        btnSystemReports.setStyle("-fx-background-color : white;");
        btnSystemReports.setStyle("-fx-text-fill: black;");
        btnSystemReports.setStyle("-fx-base: white;");
        btnSystemReports.setStyle("-fx-background-radius: 15;");

        btnSalary.setStyle("-fx-background-color : white;");
        btnSalary.setStyle("-fx-text-fill: black;");
        btnSalary.setStyle("-fx-base: white;");
        btnSalary.setStyle("-fx-background-radius: 15;");

        btnInformation.setMouseTransparent(false);
        btnEmployee.setMouseTransparent(false);
        btnTeacher.setMouseTransparent(false);
        btnSubjects.setMouseTransparent(false);
        btnSetting.setMouseTransparent(false);
        btnSystemReports.setMouseTransparent(false);
        btnSalary.setMouseTransparent(false);
    }

    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void minimizeProgram2(MouseEvent mouseEvent) {
        stage = (Stage) AdminDashBoardFormContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void closeTheProgram2(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void closeTheProgram1(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void minimizeProgram1(MouseEvent mouseEvent) {
        stage = (Stage) AdminDashBoardFormContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/MainLoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminDashBoardFormContext.getChildren().clear();
        AdminDashBoardFormContext.getChildren().add(load);
    }

    public void openTeacherForm(ActionEvent actionEvent) throws IOException {
        setStyles();
        btnTeacher.setMouseTransparent(true);
        btnTeacher.setStyle("-fx-background-color : blue;");
        btnTeacher.setStyle("-fx-text-fill: red;");
        btnTeacher.setStyle("-fx-base: #c41010;");
        URL resource = getClass().getResource("../View/TeacherForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminDashBoardForm1Context.getChildren().clear();
        AdminDashBoardForm1Context.getChildren().add(load);
    }

    public void openAdminInformationOnAction(ActionEvent actionEvent) throws IOException {
        setStyles();
        btnInformation.setMouseTransparent(true);
        btnInformation.setStyle("-fx-background-color : blue;");
        btnInformation.setStyle("-fx-text-fill: red;");
        btnInformation.setStyle("-fx-base: #c41010;");
        URL resource = getClass().getResource("../View/AdminInformationForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminDashBoardForm1Context.getChildren().clear();
        AdminDashBoardForm1Context.getChildren().add(load);
    }

    public void openSubjectFormOnAction(ActionEvent actionEvent) throws IOException {
        setStyles();
        btnSubjects.setMouseTransparent(true);
        btnSubjects.setStyle("-fx-background-color : blue;");
        btnSubjects.setStyle("-fx-text-fill: red;");
        btnSubjects.setStyle("-fx-base: #c41010;");
        URL resource = getClass().getResource("../View/AdminSubjectForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminDashBoardForm1Context.getChildren().clear();
        AdminDashBoardForm1Context.getChildren().add(load);
    }

    public void openEmployeeFormOnAction(ActionEvent actionEvent) throws IOException {
        setStyles();
        btnEmployee.setMouseTransparent(true);
        btnEmployee.setStyle("-fx-background-color : blue;");
        btnEmployee.setStyle("-fx-text-fill: red;");
        btnEmployee.setStyle("-fx-base: #c41010;");
        URL resource = getClass().getResource("../View/AdminEmployeeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminDashBoardForm1Context.getChildren().clear();
        AdminDashBoardForm1Context.getChildren().add(load);
    }

    public void openSettingFormOnAction(ActionEvent actionEvent) throws IOException {
        setStyles();
        btnSetting.setMouseTransparent(true);
        btnSetting.setStyle("-fx-background-color : blue;");
        btnSetting.setStyle("-fx-text-fill: red;");
        btnSetting.setStyle("-fx-base: #c41010;");
        URL resource = getClass().getResource("../View/SettingForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminDashBoardForm1Context.getChildren().clear();
        AdminDashBoardForm1Context.getChildren().add(load);
    }

    public void openSalaryFormOnAction(ActionEvent actionEvent) throws IOException {
        setStyles();
        btnSalary.setMouseTransparent(true);
        btnSalary.setStyle("-fx-background-color : blue;");
        btnSalary.setStyle("-fx-text-fill: red;");
        btnSalary.setStyle("-fx-base: #c41010;");
        URL resource = getClass().getResource("../View/SalaryManagementForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminDashBoardForm1Context.getChildren().clear();
        AdminDashBoardForm1Context.getChildren().add(load);
    }

    public void openSystemReportFormOnAction(ActionEvent actionEvent) throws IOException {
        setStyles();
        btnSystemReports.setMouseTransparent(true);
        btnSystemReports.setStyle("-fx-background-color : blue;");
        btnSystemReports.setStyle("-fx-text-fill: red;");
        btnSystemReports.setStyle("-fx-base: #c41010;");
        URL resource = getClass().getResource("../View/SystemReportForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminDashBoardForm1Context.getChildren().clear();
        AdminDashBoardForm1Context.getChildren().add(load);
    }

    public void setDataToEmployeeTable(ActionEvent actionEvent) {
        searchEmployee(txtSearchEmployee.getText());
    }

    public void setDataToSubjectTable(ActionEvent actionEvent) {
        searchSubject(txtSearchSubject.getText());
    }

    public void setDataToTeacherTable(ActionEvent actionEvent) {
        searchTeacher(txtSearchTeacher.getText());
    }

    public void setDataToStudentDetailTable(ActionEvent actionEvent) {
        searchStudent(txtSearchStudent.getText());
    }

    private void searchEmployee(String value){
        try {
            employees.clear();
            employees = EmployeeController.searchEmployee(value);


            tblEmployeeTable.getItems().setAll(employees);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void searchSubject(String value){
        try {
            subjects.clear();
            subjects = SubjectController.searchSubject(value);


            tblSubject.getItems().setAll(subjects);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void searchTeacher(String value){
        try {
            teachers.clear();
            teachers = TeacherController.searchTeacher(value);

            tblTeacher.getItems().setAll(teachers);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void searchStudent(String value){
        try {
            ArrayList<Student> student = new ArrayList<>();
            ArrayList<StudentInformationTM> temp = new ArrayList<>();
            temp.clear();
            student = StudentController.searchStudent(value);
            for(Student s : student){
                for(StudentInformationTM q : tblTemp){
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

    private void setDataToStudents() throws SQLException, ClassNotFoundException {

        for(Student s : students){
            ArrayList<String> subjects = new ArrayList<>();
            String tempSubjects = "";
            subjects = new StudentController().getSubjectsFromSID(s.getStudentID());
            for(String temp : subjects){
                tempSubjects = temp+"/"+tempSubjects;
            }

            StudentInformationTM s1 = new StudentInformationTM(s.getStudentID(),s.getFullName(),s.getAddress(),s.getNic(),s.getEmail(),s.getGender(),s.getContact(),s.getParentName(),s.getExamYear(),s.getRegDate(),tempSubjects);
            tblTemp.add(s1);
        }

        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colStudentAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colStudentNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colStudentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStudentGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colStudentContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colParentName.setCellValueFactory(new PropertyValueFactory<>("parentName"));
        colExamYear.setCellValueFactory(new PropertyValueFactory<>("examYear"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));
        colSubjects.setCellValueFactory(new PropertyValueFactory<>("subjects"));

        tblStudentDetails.setItems(FXCollections.observableArrayList(tblTemp));
    }

    private void setDataToTeacher() {

        colTeacherID.setCellValueFactory(new PropertyValueFactory<>("TeacherId"));
        colTeacherName.setCellValueFactory(new PropertyValueFactory<>("TeacherName"));
        colTeacherEmail.setCellValueFactory(new PropertyValueFactory<>("teacherEmail"));
        colTeacherContact.setCellValueFactory(new PropertyValueFactory<>("teacherContact"));
        colTeacherAddress.setCellValueFactory(new PropertyValueFactory<>("teacherAddress"));
        colTeacherNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colTeacherDescription.setCellValueFactory(new PropertyValueFactory<>("teacherDescription"));
        colTeacherSubjectName.setCellValueFactory(new PropertyValueFactory<>("subName"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        tblTeacher.setItems(FXCollections.observableArrayList(teachers));
    }

    private void setDataToSubject() {

        colSubjectID.setCellValueFactory(new PropertyValueFactory<>("subjectID"));
        colSubjectName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        colSubjectDescription.setCellValueFactory(new PropertyValueFactory<>("subjectDescription"));

        tblSubject.setItems(FXCollections.observableArrayList(subjects));
    }

    private void setDataToEmployee() {

        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        colEmployeeFName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colEmployeeLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmployeeNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmployeeEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmployeeContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));

        tblEmployeeTable.setItems(FXCollections.observableArrayList(employees));
    }


    public void generateEmployeeDetailsReport(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/Reports/Employee_Details_Report.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateSubjectDetailsReport(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/Reports/Subject_Details.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateTeacherDetailsReport(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/Reports/Teacher_Details.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateStudentDetailsReport(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/Reports/Student_Details.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
