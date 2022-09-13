package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainLoginFormController {
    public AnchorPane mainFormContext;
    public AnchorPane mainForm1Context;
    public Button btnAdmin;
    Stage stage = null;

    public void initialize(){

    }

    public void closeTheProgram2(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void closeTheProgram1(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void minimizeProgram2(MouseEvent mouseEvent) {
        stage = (Stage) mainFormContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void minimizeProgram1(MouseEvent mouseEvent) {
        stage = (Stage) mainFormContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void goToLoginForm(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/LoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        mainFormContext.getChildren().clear();
        mainFormContext.getChildren().add(load);
    }
}
