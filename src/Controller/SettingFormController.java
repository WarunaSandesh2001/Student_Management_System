package Controller;

import BusinessLogics.UserController;
import Model.OwnerDetail;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.ValidationUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SettingFormController {
    public AnchorPane settingFormContext;
    public TextField txtOwnersName;
    public TextField txtOwnersContact;
    public Button btnAdd;
    public Button btnUpdate;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePatter = Pattern.compile("^[A-z.[ ]]{2,}$");
    Pattern contactPattern = Pattern.compile("^(07)[0-9][0-9]{7}$");

    public void initialize(){
        try {
            OwnerDetail o = new UserController().getOwnerDetails();
            if(o==null){
                btnAdd.setDisable(false);
            }else {
                txtOwnersName.setText(o.getName());
                txtOwnersContact.setText(o.getContact());
                btnAdd.setDisable(true);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        btnUpdate.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtOwnersName, namePatter);
        map.put(txtOwnersContact, contactPattern);
    }

    public void TextFields_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateForNormalTextFields(map, btnUpdate);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                //new Alert(Alert.AlertType.INFORMATION, "Added").showAndWait();
            }
        }
    }

    public void openRegisterFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RegisterForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.initStyle(StageStyle.UNDECORATED);
        stage1.show();
    }

    public void addOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!txtOwnersName.getText().isEmpty() && !txtOwnersContact.getText().isEmpty()){
            OwnerDetail o = new OwnerDetail(txtOwnersName.getText(),txtOwnersContact.getText());
            if(new UserController().setInfoToOwnerTable(o)){
               initialize();
               new Alert(Alert.AlertType.CONFIRMATION,"Details Saved Successfully..!").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Something went wrong...!").show();
            }
        }else {
            new Alert(Alert.AlertType.WARNING,"Fill all fields and try again..!").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!txtOwnersName.getText().isEmpty() && !txtOwnersContact.getText().isEmpty()){
            OwnerDetail o = new UserController().getOwnerDetails();
            if(txtOwnersName.getText().equals(o.getName()) && txtOwnersContact.getText().equals(o.getContact())){
                new Alert(Alert.AlertType.CONFIRMATION,"Already Saved..!").show();
            }else{
                OwnerDetail o1 = new OwnerDetail(txtOwnersName.getText(),txtOwnersContact.getText());
                if(new UserController().updateOwnerDetails(o1)){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated Successfully...!").show();
                }else{
                    new Alert(Alert.AlertType.WARNING,"Something went wrong..!").show();
                }
            }
        }else {
            new Alert(Alert.AlertType.WARNING,"Fill all fields and try again..!").show();
        }
    }


}
