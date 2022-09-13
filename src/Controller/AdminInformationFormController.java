package Controller;

import BusinessLogics.EmployeeController;
import BusinessLogics.StudentController;
import BusinessLogics.SubjectController;
import BusinessLogics.TeacherController;
import Model.Employee;
import Model.Student;
import Model.Subject;
import Model.Teacher;
import View.tm.StudentDetailTM;
import View.tm.StudentInformationTM;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminInformationFormController {
    public AnchorPane AdminInformationContext;
    public JFXTextField txtSearchStudent;
    public TableView tblStudentDetails;
    public TableColumn colStudentID;
    public TableColumn colStudentName;
    public TableColumn colStudentAddress;
    public TableColumn colStudentNIC;
    public TableColumn colStudentEmail;
    public TableColumn colStudentGender;
    public TableColumn colStudentContact;
    public TableColumn colParentName;
    public TableColumn colExamYear;
    public TableColumn colRegDate;
    public TableColumn colSubjects;
    public TableView tblTeacher;
    public JFXTextField txtSearchTeacher;
    public TableColumn colTeacherID;
    public TableColumn colTeacherName;
    public TableColumn colTeacherEmail;
    public TableColumn colTeacherContact;
    public TableColumn colTeacherAddress;
    public TableColumn colTeacherNIC;
    public TableColumn colTeacherDescription;
    public TableColumn colTeacherSubjectName;
    public TableColumn colFee;
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
    public Button btnGetEmployeeDetails;
    public Button btnSubjectDetails;
    public Button btnGetStudentDetails;

    ArrayList<Student> students = new ArrayList<>();
    ArrayList<Teacher> teachers = new ArrayList<>();
    ArrayList<Subject> subjects = new ArrayList<>();
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<StudentInformationTM> tblTemp = new ArrayList();

    public void initialize() {
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
        setDataToStudent();

    }

    private void setDataToStudent(){
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
