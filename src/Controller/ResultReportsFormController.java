package Controller;

import BusinessLogics.ResultController;
import BusinessLogics.StudentController;
import BusinessLogics.TeacherController;
import Model.Result;
import View.tm.ResultsTM;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

public class ResultReportsFormController {

    public AnchorPane resultReportsContext;
    public AnchorPane closeBTN;
    public JFXTextField txtSearchStudent;
    public ComboBox<String> cmbEvaluationMonth;
    public ComboBox<String> cmbExamNames;
    public TableView tblStudentResults;
    public TableColumn colStudentID;
    public TableColumn colSubject;
    public TableColumn colIssuedDate;
    public TableColumn colGrade;
    public TableColumn colMarks;
    public JFXCheckBox chBoxShowAllMarks;
    public Label lblExamYear;
    public Label lblExamYear1;

    double x=0;
    double y=0;
    ArrayList<String> examNames = new ArrayList<>();
    ArrayList<ResultsTM> temp = new ArrayList<>();
    int selectedRowInStudentResultTable = -1;

    public void initialize(){
        String date = new SimpleDateFormat("MM", Locale.ENGLISH).format(new Date());
        cmbEvaluationMonth.setValue(checkMonth(date));
        cmbEvaluationMonth.getItems().addAll("January","February","March","April","May","June","July","August","September","October","November","December");

        try {

            setExamNames();
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbEvaluationMonth.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(chBoxShowAllMarks.isSelected()) {
                try {
                    sortResults();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    txtSearchStudent.clear();
                    SearchStudent();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        cmbExamNames.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            chBoxShowAllMarks.setDisable(false);
            if(chBoxShowAllMarks.isSelected()) {
                try {
                    sortResults();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    SearchStudent();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        tblStudentResults.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRowInStudentResultTable = (int) newValue;
        });

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

    public void dragged(MouseEvent mouseEvent) {
        try {
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        }catch (Exception e){}
    }

    public void pressed(MouseEvent mouseEvent) {
        x=mouseEvent.getSceneX();
        y=mouseEvent.getSceneY();
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
        cmbExamNames.setValue(tempArray.get(0));
    }

    public void closeTheProgram2(MouseEvent mouseEvent) {
        Stage stage = (Stage) resultReportsContext.getScene().getWindow();
        stage.close();
    }

    public void closeTheProgram1(MouseEvent mouseEvent) {
        Stage stage = (Stage) resultReportsContext.getScene().getWindow();
        stage.close();
    }
    public void closeBTNMouseEntered(MouseEvent mouseEvent) {
        closeBTN.setStyle("-fx-background-color: #e74c3c");
    }

    public void closeBTNMouseEntered1(MouseEvent mouseEvent) {
        closeBTN.setStyle("-fx-background-color: #e74c3c");
    }

    public void closeBTNMouseExited(MouseEvent mouseEvent) {
        closeBTN.setStyle("-fx-background-color: #2f5a7f");
    }

    public void closeBTNMouseExited1(MouseEvent mouseEvent) {
        closeBTN.setStyle("-fx-background-color: #2f5a7f");
    }

    public void searchStudentMarksOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        SearchStudent();
    }

    public void SearchStudent() throws SQLException, ClassNotFoundException {
        temp.clear();
        selectedRowInStudentResultTable=-1;
        if(!cmbExamNames.getSelectionModel().isEmpty()) {
            if(!txtSearchStudent.getText().equals("")) {
                Result r = new ResultController().getStudentResultByIDOrExamName(txtSearchStudent.getText(), cmbExamNames.getSelectionModel().getSelectedItem(), cmbEvaluationMonth.getSelectionModel().getSelectedItem());
                if (r != null) {
                    String examYear = new StudentController().getExamYearBySID(r.getStudentID());
                    lblExamYear.setText(examYear);
                    ResultsTM r1 = new ResultsTM(r.getStudentID(), r.getSubjectName(), r.getIssuedDate(), r.getResult(), r.getMarks());
                    temp.add(r1);
                    colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
                    colSubject.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
                    colIssuedDate.setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
                    colGrade.setCellValueFactory(new PropertyValueFactory<>("result"));
                    colMarks.setCellValueFactory(new PropertyValueFactory<>("marks"));

                    tblStudentResults.setItems(FXCollections.observableArrayList(temp));
                } else {
                    temp.clear();
                    tblStudentResults.setItems(FXCollections.observableArrayList(temp));
                    new Alert(Alert.AlertType.WARNING, "Student is not found..!").show();
                }
            }else {temp.clear();
                tblStudentResults.setItems(FXCollections.observableArrayList(temp));}
        }else {
            new Alert(Alert.AlertType.WARNING,"Please select Exam Name and try again..!").show();
        }
    }

    public void sortAllMarksOnAction(ActionEvent actionEvent) {
        selectedRowInStudentResultTable=-1;
        try {
            sortResults();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sortResults() throws SQLException, ClassNotFoundException {
        if(chBoxShowAllMarks.isSelected()){
            temp = new ResultController().getAllStudentResultDetails(cmbEvaluationMonth.getSelectionModel().getSelectedItem(),cmbExamNames.getSelectionModel().getSelectedItem());
            lblExamYear.setText(" ");
            lblExamYear1.setVisible(false);
        }else {
            txtSearchStudent.clear();
            temp.clear();
            lblExamYear.setText(" ");
            lblExamYear1.setVisible(true);
        }

        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        colIssuedDate.setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("result"));
        colMarks.setCellValueFactory(new PropertyValueFactory<>("marks"));

        tblStudentResults.setItems(FXCollections.observableArrayList(temp));

    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {


            if (selectedRowInStudentResultTable == -1) {
                new Alert(Alert.AlertType.WARNING, "please select a student and try again..!").show();
            } else {
                ResultsTM r = temp.get(selectedRowInStudentResultTable);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Delete Message");
                alert.setHeaderText("Message of delete Result details of Students..");
                alert.setContentText("Do you want to delete this Results ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get() == null || option.get() == ButtonType.CANCEL) {


                } else if (option.get() == ButtonType.OK) {
                    if (new ResultController().deleteResultsFromTable(r.getStudentID(), cmbEvaluationMonth.getSelectionModel().getSelectedItem(), cmbExamNames.getSelectionModel().getSelectedItem())) {
                        temp.clear();
                        txtSearchStudent.clear();
                        tblStudentResults.setItems(FXCollections.observableArrayList(temp));
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try again..!").show();
                    }
                }
            }
        }catch (Exception e){}
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) resultReportsContext.getScene().getWindow();
        stage.close();
    }
}
