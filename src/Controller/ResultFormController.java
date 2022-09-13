package Controller;

import BusinessLogics.ResultController;
import BusinessLogics.StudentController;
import BusinessLogics.TeacherController;
import Model.Student;
import Model.Subject;
import Model.Result;
import View.tm.StudentDetailTM;
import View.tm.SubjectTM;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.ValidationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.regex.Pattern;

public class ResultFormController {
    public AnchorPane studentResultFormContext;
    public JFXTextField txtSearchStudent;
    public TableView tblStudentDetails;
    public TableColumn colStudentID;
    public TableColumn colStudentName;
    public TableColumn colNIC;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colExamYear;
    public ComboBox<String> cmbExamMonth;
    public ComboBox<String> cmbSubject;
    public JFXTextField txtMarks;
    public Label lblGrade;
    public Label lblStudentID;
    public JFXTextField txtExamName;
    public ComboBox<String> cmbExamNames;
    public Button btnSave;

    ArrayList<Student> studentDetails = new ArrayList<>();
    ArrayList<StudentDetailTM> students = new ArrayList<>();
    ArrayList<StudentDetailTM> temp = new ArrayList<>();
    ArrayList<String> examNames = new ArrayList<>();
    int selectedRow = -1;
    StudentDetailTM s;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    Pattern marksPattern = Pattern.compile("^([0-9]{1,2}|100)$");
    Pattern examNamePattern = Pattern.compile("^[A-z._-[ ][0-9]]{2,}$");

    public void initialize(){

        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        Date date = new Date();
        String month = formatter.format(date);
        cmbExamMonth.setValue(checkMonth(month));
        btnSave.setDisable(true);

        try {

            studentDetails = new StudentController().getAllStudentDetails();
            examNames = new ResultController().getExamNames();
            setExamNames();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbExamMonth.getItems().addAll("January","February","March","April","May","June","July","August","September","October","November","December");

        setDataToStudentDetailTable();

        txtSearchStudent.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

        tblStudentDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRow = (int) newValue;
            if(selectedRow == -1){

            }else {
                s = temp.get(selectedRow);
                lblStudentID.setText(s.getStudentID());
                try {
                    ArrayList<String> sub = new StudentController().getSubjectsFromSID(s.getStudentID());
                    cmbSubject.getItems().setAll(sub);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtMarks, marksPattern);
        map.put(txtExamName, examNamePattern);
    }

    public void textfield_ReleaseOnAction(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateForJFXTextFields(map, btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof JFXTextField) {
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    private void checkGrade(int marks){
        if(marks >= 75 && marks<=100){
            lblGrade.setText("A");
            lblGrade.setStyle("-fx-text-fill: green");
        }else if(marks >= 65 && marks<=75){
            lblGrade.setText("B");
            lblGrade.setStyle("-fx-text-fill: darkgreen");
        }else if(marks >= 55 && marks<=65){
            lblGrade.setText("C");
            lblGrade.setStyle("-fx-text-fill: darkblue");
        }else if(marks >= 35 && marks<=55){
            lblGrade.setText("S");
            lblGrade.setStyle("-fx-text-fill: darkgoldenrod");
        }else if(marks >= 0 && marks<=35){
            lblGrade.setText("F");
            lblGrade.setStyle("-fx-text-fill: darkred");
        }else{
            lblGrade.setText(" ");
            new Alert(Alert.AlertType.WARNING,"Incorrect marks..!").show();
        }
    }

    private void setExamNames() throws SQLException, ClassNotFoundException {
        examNames = new ResultController().getExamNames();
        System.out.println(examNames.toString());
        ArrayList<String> tempArray = new ArrayList<>();
        for(String e : examNames){
            int count=-1;
            if(tempArray.isEmpty()){tempArray.add(e);}
            else {
                for (int i = 0; i < tempArray.size(); i++) {
                    if (e.equals(tempArray.get(i))) {
                        count = 0;
                    }
                }
                if(count==-1){
                    tempArray.add(e);
                }
            }
        }
        cmbExamNames.getItems().addAll(tempArray);
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
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
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

    private boolean searchStudentMarks(String sID,String examName) throws SQLException, ClassNotFoundException {
        boolean b = new ResultController().searchResults(sID,cmbExamMonth.getSelectionModel().getSelectedItem(),cmbSubject.getSelectionModel().getSelectedItem(),examName);
        return b;
    }


    public void saveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());
        if(selectedRow!=-1 && !cmbExamMonth.getSelectionModel().isEmpty() && !cmbSubject.getSelectionModel().isEmpty() && !txtMarks.getText().equals("") && (!txtExamName.getText().equals("") || !cmbExamNames.getSelectionModel().isEmpty())){
            if(!txtExamName.getText().equals("")) {
                Result result = new Result(lblStudentID.getText(), s.getStudentName(), cmbSubject.getSelectionModel().getSelectedItem(), date, lblGrade.getText(),txtExamName.getText(),cmbExamMonth.getSelectionModel().getSelectedItem(),txtMarks.getText());

                if (searchStudentMarks(lblStudentID.getText(),txtExamName.getText())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Results already added..!").show();
                }else {
                    if (new ResultController().setInfo(result)) {
                        setExamNames();
                        txtMarks.clear();
                        cmbSubject.getItems().clear();
                        lblGrade.setText(" ");
                        lblStudentID.setText(" ");
                        new Alert(Alert.AlertType.CONFIRMATION, "Result saved successfully..!").show();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Error..!").show();
                    }
                }

            }else if(!cmbExamNames.getSelectionModel().isEmpty()) {
                Result result = new Result(lblStudentID.getText(), s.getStudentName(), cmbSubject.getSelectionModel().getSelectedItem(), date, lblGrade.getText(),cmbExamNames.getSelectionModel().getSelectedItem(),cmbExamMonth.getSelectionModel().getSelectedItem(),txtMarks.getText());

                if (searchStudentMarks(lblStudentID.getText(),cmbExamNames.getSelectionModel().getSelectedItem())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Results already added..!").show();
                }else {
                    if (new ResultController().setInfo(result)) {
                        setExamNames();
                        txtMarks.clear();
                        cmbSubject.getItems().clear();
                        lblGrade.setText(" ");
                        lblStudentID.setText(" ");
                        new Alert(Alert.AlertType.CONFIRMATION, "Result saved successfully..!").show();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Error..!").show();
                    }
                }

            }
        }
    }

    public void setGradeOnAction(ActionEvent actionEvent) {
        if(!txtMarks.getText().equals("")) {
            checkGrade(Integer.parseInt(txtMarks.getText()));
        }else {lblGrade.setText(" ");}
    }

    public void openResultReportForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ResultReportsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.initStyle(StageStyle.UNDECORATED);
        stage1.show();
    }


}
