package Controller;

import BusinessLogics.ScheduleController;
import BusinessLogics.SubjectController;
import BusinessLogics.TeacherController;
import Model.Schedule;
import View.tm.ScheduleTM;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import util.ValidationUtil;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ScheduleFormController {
    public AnchorPane scheduleFormContext;
    public TableView tblScheduleDetails;
    public TableColumn colTeacherName;
    public TableColumn colSubject;
    public TableColumn colHallNo;
    public TableColumn colDescription;
    public TableColumn colDate;
    public TableColumn colStartTime;
    public TableColumn colEndTime;
    public TableColumn colClass;
    public JFXDatePicker datePForAdd;
    public ComboBox<String> cmbTeacherNameForAdd;
    public TextField txtTeacherID;
    public ComboBox<Integer> cmbExamYear;
    public JFXTimePicker timePStartTime;
    public JFXTimePicker timePEndTime;
    public JFXTextField txtDescription;
    public JFXDatePicker datePForView;
    public ComboBox<String> cmbTeacherNameForView;
    public JFXTextField txtHallNo;
    public ComboBox<String> cmbSubject;
    public Button btnAddToSchedule;


    ArrayList<Integer> examYears = new ArrayList<>();
    ArrayList<String> subjectNames = new ArrayList<>();
    ArrayList<String> teacherNames = new ArrayList<>();
    ArrayList<ScheduleTM> temp = new ArrayList<>();
    int selectedRow= -1;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    Pattern descriptionPatter = Pattern.compile("^[A-z._-[ ][0-9]]{2,}$");
    Pattern hallNumberPattern = Pattern.compile("^(H-)[0-9]{1,2}$");

    public void initialize() {
        setExamYears();
        cmbSubject.setDisable(true);
        timePEndTime.setEditable(false);
        timePStartTime.setEditable(false);
        datePForAdd.setEditable(false);
        datePForView.setEditable(false);
        datePForView.setValue(LocalDate.now());
        btnAddToSchedule.setDisable(true);
        try {
            setDataToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            teacherNames = new TeacherController().getTeacherNames();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbTeacherNameForAdd.getItems().addAll(teacherNames);
        cmbTeacherNameForView.getItems().addAll(teacherNames);

        cmbTeacherNameForAdd.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cmbSubject.getItems().clear();
            cmbSubject.setDisable(false);
            try {

                subjectNames = new SubjectController().getSubjectNamesByTeacherName(cmbTeacherNameForAdd.getSelectionModel().getSelectedItem());
                if((int)newValue !=-1){txtTeacherID.setText(new TeacherController().getTeacherID(teacherNames.get((int)newValue)));}else{}


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            cmbSubject.getItems().setAll(subjectNames);
        });

        cmbTeacherNameForView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<Schedule> schedules = new ArrayList<>();
            try {
                schedules= new ScheduleController().getDetailsByTeacher(cmbTeacherNameForView.getSelectionModel().getSelectedItem(),String.valueOf(datePForView.getValue()));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            temp.clear();
            if(schedules!=null) {
                for (Schedule s : schedules) {
                    ScheduleTM scheduleTM = new ScheduleTM(s.getTeacherName(), s.getSubjectName(), s.getHallNumber(), s.getDescription(), s.getsDate(), String.valueOf(s.getStartTime()), String.valueOf(s.getEndTime()), s.getExamYear());
                    temp.add(scheduleTM);
                }
                tblScheduleDetails.setItems(FXCollections.observableArrayList(temp));
            }else {tblScheduleDetails.setItems(FXCollections.observableArrayList(temp));}
        });

        tblScheduleDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRow = (int) newValue;
        });
     storeValidations();
    }

    public void jfxTextFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateForJFXTextFields(map, btnAddToSchedule);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof JFXTextField) {
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    private void storeValidations() {
        map.put(txtDescription, descriptionPatter);
        map.put(txtHallNo, hallNumberPattern);
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
    }

    private int searchSchedule(Schedule s) throws SQLException, ClassNotFoundException {
        ArrayList<Schedule> schedules = new ArrayList<>();
        schedules = new ScheduleController().getAllDetails();
        int count = -1;
        for(Schedule t : schedules){
            if(t.getStartTime().equals(s.getStartTime()) && t.getEndTime().equals(s.getEndTime()) && t.getsDate().equals(s.getsDate()) && t.getExamYear().equals(s.getExamYear())){
                count=0;
                return count;
            }
        }
        return count;
    }

    public void AddToScheduleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!cmbSubject.getSelectionModel().isEmpty() && !cmbExamYear.getSelectionModel().isEmpty() && !cmbTeacherNameForAdd.getSelectionModel().isEmpty() && !txtHallNo.getText().isEmpty() && !txtDescription.getText().isEmpty() && timePStartTime.getValue()!=(null) && timePEndTime.getValue()!=(null) && datePForAdd.getValue()!=(null)){
            Schedule s = new Schedule(txtTeacherID.getText(),cmbTeacherNameForAdd.getSelectionModel().getSelectedItem(),cmbSubject.getSelectionModel().getSelectedItem(),String.valueOf(cmbExamYear.getSelectionModel().getSelectedItem()),timePStartTime.getValue(),timePEndTime.getValue(),String.valueOf(datePForAdd.getValue()),txtDescription.getText(),txtHallNo.getText());
            if(searchSchedule(s)==-1) {
                if (new ScheduleController().setInfo(s)) {
                    cmbSubject.setDisable(true);
                    txtTeacherID.clear();
                    txtDescription.clear();
                    txtHallNo.clear();
                    timePEndTime.getEditor().clear();
                    timePStartTime.getEditor().clear();
                    datePForAdd.getEditor().clear();
                    cmbTeacherNameForAdd.getSelectionModel().clearSelection();
                    cmbExamYear.getSelectionModel().clearSelection();
                    cmbSubject.getSelectionModel().clearSelection();
                    setDataToTable();
                    new Alert(Alert.AlertType.CONFIRMATION, "Schedule Updated Successfully...!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something went Wrong....!").show();
                }
            }else {
                new Alert(Alert.AlertType.WARNING,"Already this students have a class in this time..").show();
            }
        }
    }

    private void setDataToTable() throws SQLException, ClassNotFoundException {
        ArrayList<Schedule> schedules = new ArrayList<>();
        schedules= new ScheduleController().getDetails(String.valueOf(datePForView.getValue()));
        temp.clear();
        for(Schedule s : schedules){
            ScheduleTM scheduleTM = new ScheduleTM(s.getTeacherName(),s.getSubjectName(),s.getHallNumber(),s.getDescription(),s.getsDate(),String.valueOf(s.getStartTime()),String.valueOf(s.getEndTime()),s.getExamYear());
            temp.add(scheduleTM);
        }

        colTeacherName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colHallNo.setCellValueFactory(new PropertyValueFactory<>("hallNo"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colClass.setCellValueFactory(new PropertyValueFactory<>("SClass"));

        tblScheduleDetails.setItems(FXCollections.observableArrayList(temp));
    }

    public void clearOnAction(ActionEvent actionEvent) {
        try {
            cmbSubject.setDisable(true);
            txtTeacherID.clear();
            txtDescription.clear();
            txtHallNo.clear();
            timePEndTime.getEditor().clear();
            timePStartTime.getEditor().clear();
            datePForAdd.getEditor().clear();
            cmbTeacherNameForAdd.getSelectionModel().clearSelection();
            cmbExamYear.getSelectionModel().clearSelection();
            cmbSubject.getSelectionModel().clearSelection();
        }catch (Exception e){}

    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(selectedRow!=-1){
            ScheduleTM s = temp.get(selectedRow);
            if(new ScheduleController().deleteSchedule(s)){
                temp.remove(selectedRow);
                tblScheduleDetails.setItems(FXCollections.observableArrayList(temp));
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted Successfully..!").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try again....!").show();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Please select row and try again...!").show();
        }
    }

    public void sortDataOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<Schedule> schedules = new ArrayList<>();
        if(!cmbTeacherNameForView.getSelectionModel().isEmpty()) {
            schedules = new ScheduleController().getDetailsByTeacher(cmbTeacherNameForView.getSelectionModel().getSelectedItem(),String.valueOf(datePForView.getValue()));
            temp.clear();
            for (Schedule s : schedules) {
                ScheduleTM scheduleTM = new ScheduleTM(s.getTeacherName(), s.getSubjectName(), s.getHallNumber(), s.getDescription(), s.getsDate(), String.valueOf(s.getStartTime()), String.valueOf(s.getEndTime()), s.getExamYear());
                temp.add(scheduleTM);
            }
            tblScheduleDetails.setItems(FXCollections.observableArrayList(temp));
        }else{
            schedules= new ScheduleController().getDetails(String.valueOf(datePForView.getValue()));
            temp.clear();
            for(Schedule s : schedules){
                ScheduleTM scheduleTM = new ScheduleTM(s.getTeacherName(),s.getSubjectName(),s.getHallNumber(),s.getDescription(),s.getsDate(),String.valueOf(s.getStartTime()),String.valueOf(s.getEndTime()),s.getExamYear());
                temp.add(scheduleTM);
            }
            tblScheduleDetails.setItems(FXCollections.observableArrayList(temp));
        }
    }

    public void refreshOnAction(ActionEvent actionEvent) {
        cmbSubject.setDisable(true);
        txtTeacherID.clear();
        txtDescription.clear();
        txtHallNo.clear();
        timePEndTime.getEditor().clear();
        timePStartTime.getEditor().clear();
        datePForAdd.getEditor().clear();
        cmbTeacherNameForAdd.getSelectionModel().clearSelection();
        cmbExamYear.getSelectionModel().clearSelection();
        cmbSubject.getSelectionModel().clearSelection();
        cmbTeacherNameForView.getSelectionModel().clearSelection();
        initialize();
    }


}
