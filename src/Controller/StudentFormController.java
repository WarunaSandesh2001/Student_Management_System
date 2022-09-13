package Controller;

import BusinessLogics.*;
import Model.Employee;
import Model.Student;
import Model.StudentDetail;
import Model.Teacher;
import View.tm.EmployeeDetailsTM;
import View.tm.StudentDetailTM;
import View.tm.StudentSelectedSubjectsTM;
import View.tm.SubjectTM;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.omg.PortableInterceptor.INACTIVE;
import util.ValidationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class StudentFormController {
    public AnchorPane studentFormContext;
    public Label lblStudentID;
    public TextField txtFullName;
    public TextField txtAddress;
    public TextField txtParentName;
    public TextField txtContact;
    public TextField txtNIC;
    public TextField txtEmail;
    public ComboBox<Integer> cmbExamYear;
    public Label lblRegisterFees;
    public CheckBox chboxClearPayments;
    public ComboBox<String> cmbSelectSubject;
    public TableView tblSubject;
    public TableColumn colSubjectName;
    public TableColumn colTeacherName;
    public ToggleGroup toglGender;
    public JFXComboBox<String> cmbSelectTeacher;
    public TableView tblStudentDetails;
    public TableColumn colStudentID;
    public TableColumn colName;
    public TableColumn colNIC;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colExamYear;
    public TableColumn colSubject;
    public TextField txtTempFullName;
    public TextField txtTempEmail;
    public TextField txtTempNIC;
    public TextField txtTempContact;
    public TextField txtTempAddress;
    public ComboBox<Integer> cmbTempExamYear;
    public JFXTextField txtSearchStudent;
    public Button btnRegister;
    public Button btnUpdate;

    ArrayList<StudentSelectedSubjectsTM> selectedSubjects = new ArrayList<>();
    ArrayList<StudentDetailTM> students = new ArrayList<>();
    ArrayList<Student> studentDetails = new ArrayList<>();
    ArrayList<String> subjectNames = new ArrayList<>();
    ArrayList<String> teacherNames = new ArrayList<>();
    ArrayList<Integer> examYears = new ArrayList<>();
    ArrayList<StudentDetailTM> temp = new ArrayList<>();
    int selectedRow =-1;
    int selectedRowInStudentTable=-1;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> map1 = new LinkedHashMap();
    Pattern fullNamePattern = Pattern.compile("^[A-z.[ ]]{2,}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}[V,v]$");
    Pattern addressPattern = Pattern.compile("^[#./0-9a-zA-Z,-[ ]]{5,}$");
    Pattern emailPattern = Pattern.compile("^[a-z0-9]{3,}(@)[a-z]{3,}(.)[a-z]{2,3}$");
    Pattern parentNamePattern = Pattern.compile("^[A-z.[ ]]{2,}$");
    Pattern contactPattern = Pattern.compile("^(07)[0-9][0-9]{7}$");

    Pattern tempFullNamePattern = Pattern.compile("^[A-z.[ ]]{2,}$");
    Pattern tempNicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}[V,v]$");
    Pattern tempAddressPattern = Pattern.compile("^[#./0-9a-zA-Z,-[ ]]{5,}$");
    Pattern tempEmailPattern = Pattern.compile("^[a-z0-9]{3,}(@)[a-z]{3,}(.)[a-z]{2,3}$");
    Pattern tempContactPattern = Pattern.compile("^(07)[0-9][0-9]{7}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        cmbExamYear.getItems().clear();
        cmbTempExamYear.getItems().clear();
        lblStudentID.setText(new StudentController().setStudentIDs());
        studentDetails = new StudentController().getAllStudentDetails();

        cmbSelectTeacher.setDisable(true);

        subjectNames = new SubjectController().getSubjectNames();
        cmbSelectSubject.getItems().setAll(subjectNames);

        cmbSelectSubject.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cmbSelectTeacher.getItems().clear();
            cmbSelectTeacher.setDisable(false);
            try {

                teacherNames = new TeacherController().getTeacherNames(cmbSelectSubject.getSelectionModel().getSelectedItem());

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            cmbSelectTeacher.getItems().setAll(teacherNames);
        });

        tblSubject.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRow = (int) newValue;
        });

        tblStudentDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRowInStudentTable = (int) newValue;
        });

        setDataToStudentDetailTable();
        setExamYears();

        txtSearchStudent.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

        btnRegister.setDisable(true);
        btnUpdate.setDisable(true);
        storeValidations1();
    }
    private void storeValidations1() {
        map.put(txtFullName, fullNamePattern);
        map.put(txtNIC, nicPattern);
        map.put(txtAddress, addressPattern);
        map.put(txtEmail, emailPattern);
        map.put(txtParentName, parentNamePattern);
        map.put(txtContact, contactPattern);

        map1.put(txtTempFullName, tempFullNamePattern);
        map1.put(txtTempNIC, tempNicPattern);
        map1.put(txtTempAddress, tempAddressPattern);
        map1.put(txtTempEmail, tempEmailPattern);
        map1.put(txtTempContact, tempContactPattern);
    }


    public void textFieldsForAdd_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateForNormalTextFields(map,btnRegister);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    public void textFieldsForUpdate_key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateForNormalTextFields(map1,btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    public void searchStudentOnAction(ActionEvent actionEvent) {
        search(txtSearchStudent.getText());
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

    private void setExamYears(){
        examYears.clear();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year = formatter.format(date);
        int firstYear = Integer.parseInt(year);
        int secondYear = firstYear+1;
        int thirdYear = firstYear+2;
        int fourthYear = firstYear+3;
        examYears.add(firstYear);
        examYears.add(secondYear);
        examYears.add(thirdYear);
        examYears.add(fourthYear);

        cmbExamYear.getItems().setAll(examYears);
        cmbTempExamYear.getItems().setAll(examYears);
    }


    public void addSubjectOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            if (!cmbSelectSubject.getSelectionModel().isEmpty() && !cmbSelectTeacher.getSelectionModel().isEmpty()) {
                StudentSelectedSubjectsTM s = new StudentSelectedSubjectsTM(cmbSelectSubject.getSelectionModel().getSelectedItem(), cmbSelectTeacher.getSelectionModel().getSelectedItem());
                int count = -1;
                for (StudentSelectedSubjectsTM n : selectedSubjects) {
                    if (n.getSubject().equals(s.getSubject()) && n.getTeacher().equals(s.getTeacher())) {
                        count = 0;
                    } else {
                    }
                }

                if (count == -1) {
                    selectedSubjects.add(s);
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Already Selected..!").show();
                }

                setDataToSubjectTable();
            } else {
                new Alert(Alert.AlertType.WARNING, "Select Subject and Teacher..And try again..!").show();
            }
        }catch (Exception e){}
    }

    private void setDataToSubjectTable(){
        colSubjectName.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colTeacherName.setCellValueFactory(new PropertyValueFactory<>("teacher"));

        tblSubject.setItems(FXCollections.observableArrayList(selectedSubjects));
    }

    public void removeSubjectOnAction(ActionEvent actionEvent) {
        if(selectedRow == -1){
            new Alert(Alert.AlertType.WARNING,"Please select a row and try again..!").show();
        }else{
            selectedSubjects.remove(selectedRow);
            setDataToSubjectTable();
        }
    }

    public void RegisterOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            ArrayList<StudentDetail> stuDetails = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();
            String dateAndTime = formatter.format(date);
            RadioButton selectedRadioButton = (RadioButton) toglGender.getSelectedToggle();
            boolean b = chboxClearPayments.isSelected();
            if (!txtFullName.getText().isEmpty() && !txtAddress.getText().isEmpty() && !txtParentName.getText().isEmpty() && !txtContact.getText().isEmpty() && !txtNIC.getText().isEmpty() && !txtEmail.getText().isEmpty() && !cmbExamYear.getSelectionModel().isEmpty()  && !selectedSubjects.isEmpty()) {

                Student s1 = new Student(
                        lblStudentID.getText(), txtFullName.getText(), txtAddress.getText(), txtNIC.getText(), txtEmail.getText(),
                        selectedRadioButton.getText(), txtContact.getText(), txtParentName.getText(),
                        String.valueOf(cmbExamYear.getSelectionModel().getSelectedItem()), lblRegisterFees.getText(), dateAndTime
                );
                for (StudentSelectedSubjectsTM s : selectedSubjects) {
                    String name = s.getSubject();
                    String tID = new TeacherController().getTeacherID(name);
                    StudentDetail studentDetail = new StudentDetail(lblStudentID.getText(), txtFullName.getText(), String.valueOf(cmbExamYear.getSelectionModel().getSelectedItem()), tID, s.getTeacher(),s.getSubject());
                    stuDetails.add(studentDetail);
                }
                new StudentController().setInfoToStudent(s1 , stuDetails);
                ObservableList<SubjectTM> subjects = tblSubject.getItems();


                String studentId = lblStudentID.getText();
                String studentName = txtFullName.getText();
                String studentNIC = txtNIC.getText();
                String studentAddress = txtAddress.getText();
                String studentEmail = txtEmail.getText();
                String studentParentName = txtParentName.getText();
                String studentContact = txtContact.getText();
                String studentExamYear = String.valueOf(cmbExamYear.getSelectionModel().getSelectedItem());
                String studentGender = selectedRadioButton.getText();
                String registerFee = lblRegisterFees.getText();

                HashMap map3 = new HashMap();
                map3.put("id",studentId);
                map3.put("name",studentName);
                map3.put("nic",studentNIC);
                map3.put("address",studentAddress);
                map3.put("email",studentEmail);
                map3.put("parentName",studentParentName);
                map3.put("contact",studentContact);
                map3.put("examYear",studentExamYear);
                map3.put("gender",studentGender);
                map3.put("rFee",registerFee);
                try {
                    JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/Reports/RegisterReport.jrxml"));
                    JasperReport compileReport = JasperCompileManager.compileReport(design);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map3,new JRBeanArrayDataSource(subjects.toArray()));
                    JasperViewer.viewReport(jasperPrint,false);
                } catch (JRException e) {
                    e.printStackTrace();
                }

                examYears.clear();
                selectedSubjects.clear();
                setDataToSubjectTable();
                txtFullName.clear();
                txtEmail.clear();
                txtNIC.clear();
                txtContact.clear();
                txtParentName.clear();
                txtAddress.clear();
                cmbSelectTeacher.getItems().clear();
                cmbSelectSubject.getItems().clear();
                cmbExamYear.getItems().clear();
                toglGender.getSelectedToggle().setSelected(false);
                chboxClearPayments.fire();
                temp.clear();
                initialize();
            } else {
                new Alert(Alert.AlertType.WARNING, "fill all fields and try again..!").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.WARNING, "Something went wrong..!").show();
        }
    }

    public void setDataToStudentDetailTable() throws SQLException, ClassNotFoundException {

        for(Student s : studentDetails){
            ArrayList<String> subjects = new ArrayList<>();
            String tempSubjects = "";
            subjects = new StudentController().getSubjectsFromSID(s.getStudentID());
            for(String temp : subjects){
                tempSubjects = temp+"/"+tempSubjects;
            }
            StudentDetailTM s1 = new StudentDetailTM(s.getStudentID(),s.getFullName(),s.getNic(),s.getAddress(),s.getEmail(),s.getContact(),s.getExamYear(),tempSubjects);
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
        colSubject.setCellValueFactory(new PropertyValueFactory<>("studentSubjects"));

        tblStudentDetails.setItems(FXCollections.observableArrayList(temp));

    }

    public void setDataToFieldsOnAction(MouseEvent mouseEvent) {
        try {
            StudentDetailTM s = temp.get(selectedRowInStudentTable);

            txtTempFullName.setText(s.getStudentName());
            txtTempAddress.setText(s.getStudentAddress());
            txtTempContact.setText(s.getStudentContact());
            txtTempEmail.setText(s.getStudentEmail());
            txtTempNIC.setText(s.getStudentNIC());
            cmbTempExamYear.setValue(Integer.parseInt(s.getStudentExamYear()));
        }catch (Exception e){}
    }



    public void UpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            if (!txtTempFullName.getText().equals("") && !txtTempNIC.getText().equals("") && !txtTempAddress.getText().equals("") && !txtTempEmail.getText().equals("") && !txtTempContact.getText().equals("") && !cmbTempExamYear.getSelectionModel().getSelectedItem().equals("null")) {
                if (selectedRowInStudentTable == -1) {
                    new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
                } else {
                    StudentDetailTM c = temp.get(selectedRowInStudentTable);
                    String sID = c.getStudentID();

                    Student s = new StudentController().getStudent(sID);

                    Student updatedStudent = new Student(sID, txtTempFullName.getText(), txtTempAddress.getText(), txtTempNIC.getText(), txtTempEmail.getText(), s.getGender(), txtTempContact.getText(), s.getParentName(), String.valueOf(cmbTempExamYear.getSelectionModel().getSelectedItem()), s.getRegFee(), s.getRegDate());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Update Message");
                    alert.setHeaderText("Message of update details of Students..");
                    alert.setContentText("Do you want to Update this student.?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get() == null || option.get() == ButtonType.CANCEL) {


                    } else if (option.get() == ButtonType.OK) {
                        if (new StudentController().updateStudent(updatedStudent, sID)) {
                            txtTempFullName.clear();
                            txtTempAddress.clear();
                            txtTempEmail.clear();
                            txtTempNIC.clear();
                            txtTempContact.clear();
                            students.clear();
                            temp.clear();
                            cmbTempExamYear.getItems().clear();
                            cmbExamYear.getItems().clear();
                            txtSearchStudent.clear();
                            initialize();
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Try again..!").show();
                        }
                    }
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Fill all fields and Try again..!").show();
            }
        }catch (Exception e){}
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            if (selectedRowInStudentTable == -1) {
                new Alert(Alert.AlertType.WARNING, "Please Select a row..").show();
            } else {
                StudentDetailTM s = temp.get(selectedRowInStudentTable);

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Delete Message");
                alert.setHeaderText("Message of delete details of Students..");
                alert.setContentText("Do you want to delete this Student ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get() == null || option.get() == ButtonType.CANCEL) {


                } else if (option.get() == ButtonType.OK) {
                    if (new StudentController().deleteStudent(s) && new StudentController().deleteStudentFromStudentDetailTable(s.getStudentID()) && new ResultController().deleteStudentResults(s.getStudentID())) {
                        txtTempFullName.clear();
                        txtTempAddress.clear();
                        txtTempEmail.clear();
                        txtTempNIC.clear();
                        txtTempContact.clear();
                        students.clear();
                        temp.clear();
                        txtSearchStudent.clear();
                        tblStudentDetails.setItems(FXCollections.observableArrayList(temp));
                        initialize();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try again..!").show();
                    }
                }
            }
        }catch (Exception e){}
    }

    public void btnUpdateDisableOn(MouseEvent mouseEvent) {
        btnUpdate.setDisable(false);
    }

}
