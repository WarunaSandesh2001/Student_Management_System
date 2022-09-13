package Controller;

import BusinessLogics.EmployeeController;
import BusinessLogics.SubjectController;
import Model.Employee;
import Model.Subject;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import util.ValidationUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class AdminSubjectFormController {
    public AnchorPane AdminSubjectContext;
    public JFXTextField txtSubjectName;
    public JFXTextField txtSubjectDescription;
    public Label lblSubjectID;
    public TextField txtTempSubjectName;
    public TextField txtTempDescription;
    public TableView tblSubjectDetails;
    public TableColumn colSubjectID;
    public TableColumn colSubjectName;
    public TableColumn colDescription;
    public Button btnAdd;
    public Button btnUpdate;

    ArrayList<Subject> subjectDetails = new ArrayList<>();
    int selectedRow;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    Pattern subjectNamePattern = Pattern.compile("^[A-z[ ]]{2,15}$");
    Pattern descriptionPattern = Pattern.compile("^[A-z./[ ]]{2,}$");

    LinkedHashMap<TextField, Pattern> map1 = new LinkedHashMap();
    Pattern tempSubjectNamePattern = Pattern.compile("^[A-z[ ]]{2,15}$");
    Pattern tempDescriptionPattern = Pattern.compile("^[A-z./[ ]]{2,}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        lblSubjectID.setText(new SubjectController().setSubjectIDs());
        subjectDetails = new SubjectController().getAllSubjectDetails();
        setDataToTable();
        btnAdd.setDisable(true);
        btnUpdate.setDisable(true);

        tblSubjectDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRow = (int) newValue;
        });
        storeValidations1();
        storeValidations2();
    }

    private void storeValidations1() {
        map.put(txtSubjectName, subjectNamePattern);
        map.put(txtSubjectDescription, descriptionPattern);
    }

    private void storeValidations2() {
        map1.put(txtTempSubjectName, tempSubjectNamePattern);
        map1.put(txtTempDescription, tempDescriptionPattern);
    }

    public void jfxTextFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateForJFXTextFields(map, btnAdd);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof JFXTextField) {
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateForNormalTextFields(map1, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    private void setDataToTable(){
        colSubjectID.setCellValueFactory(new PropertyValueFactory<>("subjectID"));
        colSubjectName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("subjectDescription"));

        tblSubjectDetails.setItems(FXCollections.observableArrayList(subjectDetails));
    }

    public void setDataOnAction(MouseEvent mouseEvent) {
        Subject e = subjectDetails.get(selectedRow);

        txtTempSubjectName.setText(e.getSubjectName());
        txtTempDescription.setText(e.getSubjectDescription());
    }

    public void AddSubjectOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!txtSubjectName.getText().equals("") && !txtSubjectDescription.getText().equals("")){
            Subject subject = new Subject(lblSubjectID.getText(),txtSubjectName.getText(),txtSubjectDescription.getText());

            if(new SubjectController().setInfo(subject)){
                new Alert(Alert.AlertType.CONFIRMATION, "Subject added Successfully").show();
                txtSubjectName.clear();
                txtSubjectDescription.clear();
                subjectDetails.clear();
                initialize();
            }
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!txtTempSubjectName.getText().equals("") && !txtTempDescription.getText().equals("")){
            if (selectedRow == -1) {
                new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
            } else {
                Subject c = subjectDetails.get(selectedRow);
                String subID = c.getSubjectID();

                Subject s = new Subject(subID,txtTempSubjectName.getText(),txtTempDescription.getText());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Update Message");
                alert.setHeaderText("Message of update details of Subjects..");
                alert.setContentText("Do you want to Update ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get() == null || option.get() == ButtonType.CANCEL) {


                } else if (option.get() == ButtonType.OK) {
                    if (new SubjectController().updateSubjects(s)) {
                        txtTempSubjectName.clear();
                        txtTempDescription.clear();
                        subjectDetails.clear();
                        initialize();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try again..!").show();
                    }
                }
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fill all fields and Try again").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (selectedRow == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row..").show();
        } else {
            Subject s = subjectDetails.get(selectedRow);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Message");
            alert.setHeaderText("Message of delete details of Subjects..");
            alert.setContentText("Do you want to delete this subject ?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null || option.get() == ButtonType.CANCEL) {


            } else if (option.get() == ButtonType.OK) {
                if(new SubjectController().deleteSubject(s)){
                    txtTempSubjectName.clear();
                    txtTempDescription.clear();
                    subjectDetails.clear();
                    initialize();
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try again..!").show();
                }
            }
        }
    }



}
