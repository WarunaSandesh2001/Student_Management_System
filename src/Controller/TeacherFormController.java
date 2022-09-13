package Controller;

import BusinessLogics.EmployeeController;
import BusinessLogics.SubjectController;
import BusinessLogics.TeacherController;
import Model.Employee;
import Model.Teacher;
import com.jfoenix.controls.JFXComboBox;
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

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class TeacherFormController {
    public AnchorPane TeacherFormContext;
    public JFXComboBox<String> cmbSelectSubject;
    public JFXTextField txtFullName;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;
    public JFXTextField txtNIC;
    public JFXTextField txtEmail;
    public JFXTextField txtDescription;
    public Label lblTeacherID;
    public TableView tblTeacherDetails;
    public TableColumn colFullName;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colNIC;
    public TableColumn colDescription;
    public TableColumn colSubject;
    public TableColumn colID;
    public TextField txtTempFullName;
    public TextField txtTempAddress;
    public TextField txtTempEmail;
    public TextField txtTempContact;
    public TextField txtTempNIC;
    public TextField txtTempDescription;
    public JFXComboBox<String> cmgTempSubject;
    public TableColumn colFee;
    public JFXTextField txtFee;
    public TextField txtTempFee;
    public Button btnUpdate;
    public Button btnAdd;

    ArrayList<Teacher> teacherDetails = new ArrayList<>();
    ArrayList<String> subNames = new ArrayList<>();
    int selectedRow;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> map1 = new LinkedHashMap();
    Pattern fullNamePattern = Pattern.compile("^[A-z.[ ]]{2,}$");
    Pattern addressPattern = Pattern.compile("^[#./0-9a-zA-Z,-[ ]]{5,}$");
    Pattern contactPattern = Pattern.compile("^(07)[0-9][0-9]{7}$");
    Pattern emailPattern = Pattern.compile("^[a-z0-9]{3,}(@)[a-z]{3,}(.)[a-z]{2,3}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}[V,v]$");
    Pattern descriptionPattern = Pattern.compile("^[A-z.,-[ ]]{2,}$");
    Pattern feePattern = Pattern.compile("^[0-9.]{1,}$");

    Pattern tempFullNamePattern = Pattern.compile("^[A-z.[ ]]{2,}$");
    Pattern tempAddressPattern = Pattern.compile("^[#./0-9a-zA-Z,-[ ]]{5,}$");
    Pattern tempContactPattern = Pattern.compile("^(07)[0-9][0-9]{7}$");
    Pattern tempEmailPattern = Pattern.compile("^[a-z0-9]{3,}(@)[a-z]{3,}(.)[a-z]{2,3}$");
    Pattern tempNicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}[V,v]$");
    Pattern tempDescriptionPattern = Pattern.compile("^[A-z.,-[ ]]{2,}$");
    Pattern tempFeePattern = Pattern.compile("^[0-9.]{1,}$");

    public void initialize() throws SQLException, ClassNotFoundException {

        btnAdd.setDisable(true);
        btnUpdate.setDisable(true);
        subNames = new SubjectController().getSubjectNames();
        teacherDetails = new TeacherController().getAllTeacherDetails();
        cmbSelectSubject.getItems().setAll(subNames);
        lblTeacherID.setText(new TeacherController().setTeacherIDs());
        setDataToTable();

        tblTeacherDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRow = (int) newValue;
        });

        storeValidations();
        storeValidationsNormal();

    }

    private void storeValidations() {
        map.put(txtFullName, fullNamePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtEmail, emailPattern);
        map.put(txtContact, contactPattern);
        map.put(txtNIC, nicPattern);
        map.put(txtDescription, descriptionPattern);
        map.put(txtFee, feePattern);

    }

    private void storeValidationsNormal() {
        map1.put(txtTempFullName, tempFullNamePattern);
        map1.put(txtTempContact, tempContactPattern);
        map1.put(txtTempFee, tempFeePattern);
        map1.put(txtTempAddress, tempAddressPattern);
        map1.put(txtTempNIC, tempNicPattern);
        map1.put(txtTempEmail, tempEmailPattern);
        map1.put(txtTempDescription, tempDescriptionPattern);

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

    private void setDataToTable(){
        colID.setCellValueFactory(new PropertyValueFactory<>("TeacherId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("TeacherName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("teacherEmail"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("teacherContact"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("teacherAddress"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("teacherDescription"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subName"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        tblTeacherDetails.setItems(FXCollections.observableArrayList(teacherDetails));
    }

    public void setDataOnAction(MouseEvent mouseEvent) {
        Teacher teacher = teacherDetails.get(selectedRow);

        txtTempFullName.setText(teacher.getTeacherName());
        txtTempAddress.setText(teacher.getTeacherAddress());
        txtTempEmail.setText(teacher.getTeacherEmail());
        txtTempContact.setText(teacher.getTeacherContact());
        txtTempNIC.setText(teacher.getNic());
        txtTempFee.setText(teacher.getFee());
        txtTempDescription.setText(teacher.getTeacherDescription());
        cmgTempSubject.getItems().setAll(subNames);
        cmgTempSubject.setValue(teacher.getSubName());

    }

    public void AddNewTeacherOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!txtFullName.getText().equals("") && !txtAddress.getText().equals("") && !txtContact.getText().equals("") && !txtDescription.getText().equals("") && !txtEmail.getText().equals("") && !txtNIC.getText().equals("") && !cmbSelectSubject.getSelectionModel().isEmpty()){

            Teacher teacher = new Teacher(lblTeacherID.getText(),txtFullName.getText(),txtEmail.getText(),txtContact.getText(),txtAddress.getText(),txtNIC.getText(),txtDescription.getText(),cmbSelectSubject.getSelectionModel().getSelectedItem(),txtFee.getText());
            if(new TeacherController().setInfo(teacher)){
                new Alert(Alert.AlertType.CONFIRMATION,"Teacher details added successfully..!").show();
                txtNIC.clear();
                txtEmail.clear();
                txtDescription.clear();
                txtContact.clear();
                txtAddress.clear();
                txtFullName.clear();
                txtFee.clear();
                cmbSelectSubject.getItems().clear();
                teacherDetails.clear();
                initialize();
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Fill all fields and try again..!").show();
        }
    }


    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!txtTempFullName.getText().equals("") && !txtTempContact.getText().equals("") && !txtTempAddress.getText().equals("") && !txtTempNIC.getText().equals("") && !txtTempEmail.getText().equals("") && !txtTempDescription.getText().equals("") && !cmgTempSubject.getSelectionModel().isEmpty()) {
            if (selectedRow == -1) {
                new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
            } else {
                Teacher c = teacherDetails.get(selectedRow);
                String tID = c.getTeacherId();

                Teacher teacher = new Teacher(tID,txtTempFullName.getText(),txtTempEmail.getText(),txtTempContact.getText(),txtTempAddress.getText(),txtTempNIC.getText(),txtTempDescription.getText(),cmgTempSubject.getSelectionModel().getSelectedItem(),txtTempFee.getText());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Update Message");
                alert.setHeaderText("Message of update details of Teachers..");
                alert.setContentText("Do you want to Update..?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get() == null || option.get() == ButtonType.CANCEL) {


                } else if (option.get() == ButtonType.OK) {
                    if (new TeacherController().updateTeacher(teacher)) {
                        txtTempFullName.clear();
                        txtTempAddress.clear();
                        txtTempEmail.clear();
                        txtTempNIC.clear();
                        txtTempContact.clear();
                        txtTempDescription.clear();
                        txtTempFee.clear();
                        cmgTempSubject.getItems().clear();
                        teacherDetails.clear();
                        initialize();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try again..!").show();
                    }
                }
            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Fill all fields and Try again..!").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (selectedRow == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row..").show();
        } else {
            Teacher t = teacherDetails.get(selectedRow);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Message");
            alert.setHeaderText("Message of delete details of Teachers..");
            alert.setContentText("Do you want to delete this Teacher ?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null || option.get() == ButtonType.CANCEL) {


            } else if (option.get() == ButtonType.OK) {
                if(new TeacherController().deleteTeacher(t)){
                    txtTempFullName.clear();
                    txtTempAddress.clear();
                    txtTempEmail.clear();
                    txtTempNIC.clear();
                    txtTempContact.clear();
                    txtTempDescription.clear();
                    txtTempFee.clear();
                    cmgTempSubject.getItems().clear();
                    teacherDetails.clear();
                    initialize();
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try again..!").show();
                }
            }
        }
    }


}
