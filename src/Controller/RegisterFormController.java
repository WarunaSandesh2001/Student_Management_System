package Controller;

import BusinessLogics.UserController;
import Model.User;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.ValidationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class RegisterFormController {
    public AnchorPane RegisterFormContext;
    public TextField txtUserName;
    public TextField txtPassword;
    public PasswordField txtConfirmPassword;
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtContact;
    public JFXTextField txtNIC;
    public JFXTextField txtAddress;
    public JFXTextField txtEmail;
    public JFXComboBox<String> cmbUserType;
    public Button btnRegister;
    Stage stage = null;
    double x=0;
    double y=0;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> map1 = new LinkedHashMap();
    Pattern firstNamePattern = Pattern.compile("^[A-z.]{2,}$");
    Pattern lastNamePattern = Pattern.compile("^[A-z.]{2,}$");
    Pattern addressPattern = Pattern.compile("^[#./0-9a-zA-Z,-[ ]]{5,}$");
    Pattern contactPattern = Pattern.compile("^(07)[0-9][0-9]{7}$");
    Pattern emailPattern = Pattern.compile("^[a-z0-9]{3,}(@)[a-z]{3,}(.)[a-z]{2,3}$");
    Pattern nicPattern = Pattern.compile("^[0-9]{12}|[0-9]{9}[V,v]$");
    Pattern userNamePattern = Pattern.compile("^[a-zA-Z_]{4,15}$");
    Pattern passwordPattern = Pattern.compile("^[a-zA-Z0-9@#$%&_]{4,15}$");
    Pattern confirmPasswordPattern = Pattern.compile("^[a-zA-Z0-9@#$%&_]{4,15}$");

    public void initialize(){
        cmbUserType.getItems().setAll("System User","Admin");

        cmbUserType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            txtUserName.requestFocus();
        });

        btnRegister.setDisable(true);
        storeValidations();
        storeValidationsNormal();
    }

    private void storeValidations() {
        map.put(txtFirstName, firstNamePattern);
        map.put(txtLastName, lastNamePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtContact, contactPattern);
        map.put(txtEmail, emailPattern);
        map.put(txtNIC, nicPattern);
    }

    private void storeValidationsNormal() {
        map1.put(txtUserName, userNamePattern);
        map1.put(txtPassword, passwordPattern);
        map1.put(txtConfirmPassword, confirmPasswordPattern);
    }

    public void jfxTextFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateForJFXTextFields(map, btnRegister);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof JFXTextField) {
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateForNormalTextFields(map1, btnRegister);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
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

    public void minimizeProgram2(MouseEvent mouseEvent) {
        stage = (Stage) RegisterFormContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void closeTheProgram2(MouseEvent mouseEvent) {
        Stage stage = (Stage) RegisterFormContext.getScene().getWindow();
        stage.close();
    }

    public void closeTheProgram1(MouseEvent mouseEvent) {
        Stage stage = (Stage) RegisterFormContext.getScene().getWindow();
        stage.close();
    }

    public void minimizeProgram1(MouseEvent mouseEvent) {
        stage = (Stage) RegisterFormContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void registerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                if (!txtFirstName.getText().equals("") && !txtLastName.getText().equals("") && !cmbUserType.getSelectionModel().getSelectedItem().isEmpty() && !txtEmail.getText().equals("") && !txtUserName.getText().equals("") && !txtPassword.getText().equals("") && !txtConfirmPassword.getText().equals("")) {

                    User user = new User(txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtContact.getText(), txtEmail.getText(), txtNIC.getText(), txtUserName.getText(), txtPassword.getText(), cmbUserType.getSelectionModel().getSelectedItem());

                    if (new UserController().setInfo(user)) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Registered Successfully").show();
                        txtFirstName.clear();
                        txtLastName.clear();
                        txtAddress.clear();
                        txtEmail.clear();
                        txtNIC.clear();
                        txtContact.clear();
                        cmbUserType.getSelectionModel().clearSelection();
                        txtUserName.clear();
                        txtPassword.clear();
                        txtConfirmPassword.clear();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again..").show();
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "Fill all Fields and Try Again..").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Password Confirmation is incorrect..!").show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.WARNING,"Please fill all fields and try again..!").show();
        }
    }



}
