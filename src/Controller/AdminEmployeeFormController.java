package Controller;

import BusinessLogics.EmployeeController;
import BusinessLogics.UserController;
import Model.Employee;
import Model.User;
import View.tm.EmployeeDetailsTM;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
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
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class AdminEmployeeFormController {
    public AnchorPane AdminEmployeeContext;
    public JFXTextField txtFirstName;
    public JFXTextField txtAddress;
    public Label lblEmployeeID;
    public JFXTextField txtLastName;
    public JFXTextField txtEmail;
    public JFXTextField txtNIC;
    public JFXTextField txtJobRoll;
    public JFXTextField txtContact;
    public TableView tblEmployeeDetails;
    public TableColumn colEmployeeID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colNIC;
    public TableColumn colContact;
    public TextField txtTempFName;
    public TextField txtTempAddress;
    public TextField txtTempContact;
    public TextField txtTempLName;
    public TextField txtTempEmail;
    public TextField txtTempNIC;
    public TextField txtTempJobRole;
    public TextField txtSearchEmployee;
    public TableColumn colFName;
    public TableColumn colLName;
    public TableColumn colJobRole;
    public Button btnAdd;
    public Button btnUpdate;

    int selectedRow =-1;
    ArrayList<Employee> employees = new ArrayList<>();

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> map1 = new LinkedHashMap();
    Pattern firstNamePattern = Pattern.compile("^[A-z.]{2,}$");
    Pattern lastNamePattern = Pattern.compile("^[A-z.]{2,}$");
    Pattern addressPattern = Pattern.compile("^[#./0-9a-zA-Z,-[ ]]{5,}$");
    Pattern contactPattern = Pattern.compile("^(07)[0-9][0-9]{7}$");
    Pattern emailPattern = Pattern.compile("^[a-z0-9]{3,}(@)[a-z]{3,}(.)[a-z]{2,3}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}[V,v]$");
    Pattern jobRolePattern = Pattern.compile("^[A-z.,-[ ]]{2,}$");

    Pattern tempFirstNamePattern = Pattern.compile("^[A-z.]{2,}$");
    Pattern tempLastNamePattern = Pattern.compile("^[A-z.]{2,}$");
    Pattern tempAddressPattern = Pattern.compile("^[#./0-9a-zA-Z,-[ ]]{5,}$");
    Pattern tempContactPattern = Pattern.compile("^(07)[0-9][0-9]{7}$");
    Pattern tempEmailPattern = Pattern.compile("^[a-z0-9]{3,}(@)[a-z]{3,}(.)[a-z]{2,3}$");
    Pattern tempNicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}[V,v]$");
    Pattern tempJobRolePattern = Pattern.compile("^[A-z.,-[ ]]{2,}$");


    public void initialize() throws SQLException, ClassNotFoundException {
        btnAdd.setDisable(true);
        btnUpdate.setDisable(true);
        lblEmployeeID.setText(new EmployeeController().setEmployeeIDS());

        employees = new EmployeeController().getAllEmployeeDetails();


        setDataToTable();

        tblEmployeeDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRow = (int) newValue;
        });

        txtSearchEmployee.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });

        storeValidations();
        storeValidationsNormal();

    }

    private void storeValidations() {
        map.put(txtFirstName, firstNamePattern);
        map.put(txtLastName, lastNamePattern);
        map.put(txtEmail, emailPattern);
        map.put(txtAddress, addressPattern);
        map.put(txtNIC, nicPattern);
        map.put(txtJobRoll, jobRolePattern);
        map.put(txtContact, contactPattern);
    }

    private void storeValidationsNormal() {
        map1.put(txtTempFName, tempFirstNamePattern);
        map1.put(txtTempLName, tempLastNamePattern);
        map1.put(txtTempEmail, tempEmailPattern);
        map1.put(txtTempContact, tempContactPattern);
        map1.put(txtTempNIC, tempNicPattern);
        map1.put(txtTempAddress, tempAddressPattern);
        map1.put(txtTempJobRole, tempJobRolePattern);
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

    public void txtSearchEmployeeOnAction(ActionEvent actionEvent) {
        search(txtSearchEmployee.getText());
   }

    private void search(String value){
        try {
            employees = EmployeeController.searchEmployee(value);
            ObservableList<EmployeeDetailsTM> tableData = FXCollections.observableArrayList();
            tblEmployeeDetails.getItems().setAll(employees);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setDataToTable(){
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        colFName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));

        tblEmployeeDetails.setItems(FXCollections.observableArrayList(employees));
    }

    public void setDataOnAction(MouseEvent mouseEvent) {
        Employee e = employees.get(selectedRow);

        txtTempFName.setText(e.getFirstName());
        txtTempLName.setText(e.getLastName());
        txtTempEmail.setText(e.getEmail());
        txtTempContact.setText(e.getContact());
        txtTempNIC.setText(e.getNic());
        txtTempAddress.setText(e.getAddress());
        txtTempJobRole.setText(e.getJobRole());

    }


    public void EmployeeAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!txtFirstName.getText().equals("") && !txtLastName.getText().equals("") && !txtAddress.getText().equals("") && !txtEmail.getText().equals("") && !txtContact.getText().equals("") && !txtJobRoll.getText().equals("") && !txtNIC.getText().equals("")) {

            Employee employee = new Employee(lblEmployeeID.getText(), txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtNIC.getText(), txtEmail.getText(), txtContact.getText(), txtJobRoll.getText());

            if (new EmployeeController().setInfo(employee)) {
                new Alert(Alert.AlertType.CONFIRMATION, "Added Successfully").show();
                txtFirstName.clear();
                txtLastName.clear();
                txtAddress.clear();
                txtEmail.clear();
                txtNIC.clear();
                txtContact.clear();
                txtJobRoll.clear();
                employees.clear();
                initialize();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again..").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Fill all Fields and Try Again..").show();
        }
    }


    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!txtTempFName.getText().equals("") && !txtTempLName.getText().equals("") && !txtTempAddress.getText().equals("") && !txtTempEmail.getText().equals("") && !txtTempContact.getText().equals("") && !txtTempNIC.getText().equals("") && !txtTempJobRole.getText().equals("")) {
            if (selectedRow == -1) {
                new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
            } else {
                Employee c = employees.get(selectedRow);
                String eID = c.getEmployeeID();

                Employee e = new Employee(eID, txtTempFName.getText(), txtTempLName.getText(), txtTempAddress.getText(), txtTempNIC.getText(), txtTempEmail.getText(), txtTempContact.getText(), txtTempJobRole.getText());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Update Message");
                alert.setHeaderText("Message of update details of Employees..");
                alert.setContentText("Do you want to Update ?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get() == null || option.get() == ButtonType.CANCEL) {


                } else if (option.get() == ButtonType.OK) {
                    if (new EmployeeController().updateEmployee(e)) {
                        txtTempFName.clear();
                        txtTempLName.clear();
                        txtTempAddress.clear();
                        txtTempEmail.clear();
                        txtTempNIC.clear();
                        txtTempContact.clear();
                        txtTempJobRole.clear();
                        //=============
                        txtSearchEmployee.clear();
                        employees.clear();
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
            Employee e = employees.get(selectedRow);

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete Message");
            alert.setHeaderText("Message of delete details of Employees..");
            alert.setContentText("Do you want to delete this employee ?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null || option.get() == ButtonType.CANCEL) {


            } else if (option.get() == ButtonType.OK) {
                if(new EmployeeController().deleteEmployee(e)){
                    txtTempFName.clear();
                    txtTempLName.clear();
                    txtTempAddress.clear();
                    txtTempEmail.clear();
                    txtTempNIC.clear();
                    txtTempContact.clear();
                    txtTempJobRole.clear();
                    //============
                    employees.clear();
                    txtSearchEmployee.clear();
                    initialize();
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try again..!").show();
                }
            }
        }
    }



}
