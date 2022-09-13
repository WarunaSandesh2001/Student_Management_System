package Controller;

import BusinessLogics.StudentController;
import BusinessLogics.SubjectController;
import BusinessLogics.TeacherController;
import Model.Student;
import Model.StudentDetail;
import View.tm.StudentDetailTM;
import View.tm.StudentSelectedSubjectsTM;
import View.tm.SubjectTM;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserSubjectFormController {
    public AnchorPane SubjectFormContext;
    public TableView tblStudentDetails;
    public TableColumn colStudentID;
    public TableColumn colName;
    public TableColumn colNIC;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colExamYear;
    public TableColumn colSubjects;
    public ComboBox<String> cmbSelectSubject;
    public ComboBox<String> cmbSelectTeacher;
    public TableColumn colSubject;
    public TableColumn colTeacher;
    public TableView tblSubjectDetails;
    public TextField txtSearchStudent;

    ArrayList<StudentDetailTM> student = new ArrayList<>();
    ArrayList<Student> studentDetails = new ArrayList<>();
    ArrayList<String> subjectNames = new ArrayList<>();
    ArrayList<String> teacherNames = new ArrayList<>();
    ArrayList<SubjectTM> subjects = new ArrayList<>();
    int selectedRow=-1;

    public void initialize() throws SQLException, ClassNotFoundException {
        studentDetails = new StudentController().getAllStudentDetails();
        setDataToStudentDetailTable();

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

        tblStudentDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRow = (int) newValue;
        });

        txtSearchStudent.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

    }

    private void search(String value){
        try {
            ArrayList<Student> studentTemp = new ArrayList<>();
            ArrayList<StudentDetailTM> temp = new ArrayList<>();
            temp.clear();
            studentTemp = StudentController.searchStudent(value);
            for(Student s : studentTemp){
                for(StudentDetailTM q : student){
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

    public void searchStudentOnAction(ActionEvent actionEvent) {
        search(txtSearchStudent.getText());
    }


    public void setSubjectsToTableOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if(selectedRow == -1){

        }else{

            StudentDetailTM s = student.get(selectedRow);
            String sID = s.getStudentID();



            subjects = new StudentController().getSubjectDetailsFromSID(sID);


            colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
            colTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));

            tblSubjectDetails.setItems(FXCollections.observableArrayList(subjects));
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
            student.add(s1);
        }

        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("studentNIC"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("studentAddress"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("studentEmail"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("studentContact"));
        colExamYear.setCellValueFactory(new PropertyValueFactory<>("studentExamYear"));
        colSubjects.setCellValueFactory(new PropertyValueFactory<>("studentSubjects"));

        tblStudentDetails.setItems(FXCollections.observableArrayList(student));

    }


    public void addSubjectOnAction(ActionEvent actionEvent) {
        if(!cmbSelectSubject.getSelectionModel().isEmpty() && !cmbSelectTeacher.getSelectionModel().isEmpty()) {
            SubjectTM s = new SubjectTM(cmbSelectSubject.getSelectionModel().getSelectedItem(), cmbSelectTeacher.getSelectionModel().getSelectedItem());
            int count = -1;
            for(SubjectTM n : subjects ){
                if(n.getSubject().equals(s.getSubject()) && n.getTeacher().equals(s.getTeacher())){
                    count = 0;
                }else{}
            }

            if(count==-1){
                subjects.add(s);
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Already Selected..!").show();
            }

            colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
            colTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));

            tblSubjectDetails.setItems(FXCollections.observableArrayList(subjects));

        }else{
            new Alert(Alert.AlertType.WARNING,"Select Subject and Teacher..And try again..!").show();
        }
    }

    public void deleteSubjectOnAction(ActionEvent actionEvent) {
        if(selectedRow == -1){
            new Alert(Alert.AlertType.WARNING,"Please select a row and try again..!").show();
        }else{
            subjects.remove(selectedRow);
            colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
            colTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));

            tblSubjectDetails.setItems(FXCollections.observableArrayList(subjects));

        }
    }

    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<StudentDetail> stuDetails = new ArrayList<>();
        StudentDetailTM temp = student.get(selectedRow);
        String sID = temp.getStudentID();
        for (SubjectTM s : subjects) {
            String name = s.getSubject();


            String tID = new TeacherController().getTeacherID(name);
            System.out.println(tID);
            StudentDetail studentDetail = new StudentDetail(sID, temp.getStudentName(), temp.getStudentExamYear(), tID, s.getTeacher(),s.getSubject());
            stuDetails.add(studentDetail);
            System.out.println(stuDetails.toString());
        }
        if( new StudentController().deleteStudentFromStudentDetailTable(sID)){
            new StudentController().setInfoToStudentDetail(stuDetails);
            cmbSelectTeacher.getItems().clear();
            cmbSelectSubject.getItems().clear();
            student.clear();
            subjects.clear();
            colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
            colTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));

            tblSubjectDetails.setItems(FXCollections.observableArrayList(subjects));
            initialize();
            new Alert(Alert.AlertType.CONFIRMATION,"Student Subject Details Updated Successfully..!").show();
        }else {}
    }


}
