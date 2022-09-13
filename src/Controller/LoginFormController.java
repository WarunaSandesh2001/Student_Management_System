package Controller;

import BusinessLogics.UserController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginFormController {
    public AnchorPane mainFormContext;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public Label lblClickHere;
    public JFXButton btnSignUP;
    public Label lblMessage;
    Stage stage=null;

    public void initialize(){
        ArrayList<String> userTypes = null;
        try {

            userTypes = new UserController().getUserTypes();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(String user : userTypes){
            if(user.equals("Admin")){
                btnSignUP.setVisible(false);
                btnSignUP.setDisable(true);
                lblClickHere.setVisible(false);
            }
        }
        lblMessage.setText(" ");
    }

    public void closeTheProgram2(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void minimizeProgram2(MouseEvent mouseEvent) {
        stage = (Stage) mainFormContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void closeTheProgram1(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void minimizeProgram1(MouseEvent mouseEvent) {
        stage = (Stage) mainFormContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void openRegisterForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RegisterForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.initStyle(StageStyle.UNDECORATED);
        stage1.show();
    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
            String uType = new UserController().searchUser(txtUserName.getText(),txtPassword.getText());


            if("Admin".equals(uType)) {
                txtUserName.setStyle("-fx-text-fill: black");
                txtPassword.setStyle("-fx-text-fill: black");
                URL resource = getClass().getResource("../View/AdminDashBoardForm.fxml");
                Parent load = FXMLLoader.load(resource);
                mainFormContext.getChildren().clear();
                mainFormContext.getChildren().add(load);
                return;

            }else if("System User".equals(uType)) {
                txtUserName.setStyle("-fx-text-fill: black");
                txtPassword.setStyle("-fx-text-fill: black");
                URL resource = getClass().getResource("../View/UserDashBoardForm.fxml");
                Parent load = FXMLLoader.load(resource);
                mainFormContext.getChildren().clear();
                mainFormContext.getChildren().add(load);
                return;

            }else{
                lblMessage.setText("UserName Or Password is incorrect..!");
                txtUserName.setStyle("-fx-text-fill: red");
                txtPassword.setStyle("-fx-text-fill: red");
            }

    }


    public void goToTXTPasswordField(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void goToBTNLogIn(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        loginOnAction(actionEvent);
    }
}
