package Controller;

import BusinessLogics.StudentController;
import BusinessLogics.SubjectController;
import BusinessLogics.TeacherController;
import BusinessLogics.UserController;
import Model.OwnerDetail;
import Model.Teacher;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class UserDashBoardFormController {
    public AnchorPane UserDashBoardContext;
    public AnchorPane dashBoardFormContext;
    public Label lblTime;
    public Label lblDate;
    public Button btnDashBoard;
    public Button btnStudent;
    public Button btnSubject;
    public Button btnPayment;
    public Button btnAttendance;
    public TableView tblDetails;
    public TableColumn colSubjects;
    public TableColumn colTeachers;
    public Label lblNumberOfStudents;
    public Label lblNumberOfTeachers;
    public Label lblNumberOfSubjects;
    public Button btnResult;
    public AnchorPane closeBTN;
    public AnchorPane btnMinimize;
    public Button btnSchedule;
    public Label lblOwnersName;
    public Label lblOwnersContact;
    Stage stage = null;

    ArrayList<Teacher> details = new ArrayList<>();
    public void initialize(){
        try {
            OwnerDetail o = new UserController().getOwnerDetails();
            lblOwnersName.setText(o.getName());
            lblOwnersContact.setText(o.getContact());
            details = new TeacherController().getDetails();
            setDataToTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        btnDashBoard.setMouseTransparent(true);
        loadDateAndTime();
        btnDashBoard.setStyle("-fx-background-color : red;");
        btnDashBoard.setStyle("-fx-text-fill: red;");
        btnDashBoard.setStyle("-fx-base: #c41010;");
        btnDashBoard.applyCss();

        try {

            lblNumberOfStudents.setText(String.valueOf(new StudentController().getAmountOfStudent()));
            lblNumberOfSubjects.setText(String.valueOf(new SubjectController().getAmountOfSubjects()));
            lblNumberOfTeachers.setText(String.valueOf(new TeacherController().getAmountOfTeachers()));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setDataToTable(){
        colSubjects.setCellValueFactory(new PropertyValueFactory<>("TeacherName"));
        colTeachers.setCellValueFactory(new PropertyValueFactory<>("subName"));

        tblDetails.setItems(FXCollections.observableArrayList(details));
    }

    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void closeTheProgram2(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void minimizeProgram2(MouseEvent mouseEvent) {
        stage = (Stage) UserDashBoardContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void closeTheProgram1(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void minimizeProgram1(MouseEvent mouseEvent) {
        stage = (Stage) UserDashBoardContext.getScene().getWindow();
        stage.setIconified(true);
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/MainLoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        UserDashBoardContext.getChildren().clear();
        UserDashBoardContext.getChildren().add(load);
    }

    private void setStyles(){
        btnDashBoard.setStyle("-fx-background-color : white;");
        btnDashBoard.setStyle("-fx-text-fill: black;");
        btnDashBoard.setStyle("-fx-base: white;");
        btnDashBoard.setStyle("-fx-background-radius: 15;");

        btnStudent.setStyle("-fx-background-color : white;");
        btnStudent.setStyle("-fx-text-fill: black;");
        btnStudent.setStyle("-fx-base: white;");
        btnStudent.setStyle("-fx-background-radius: 15;");

        btnSubject.setStyle("-fx-background-color : white;");
        btnSubject.setStyle("-fx-text-fill: black;");
        btnSubject.setStyle("-fx-base: white;");
        btnSubject.setStyle("-fx-background-radius: 15;");

        btnAttendance.setStyle("-fx-background-color : white;");
        btnAttendance.setStyle("-fx-text-fill: black;");
        btnAttendance.setStyle("-fx-base: white;");
        btnAttendance.setStyle("-fx-background-radius: 15;");

        btnPayment.setStyle("-fx-background-color : white;");
        btnPayment.setStyle("-fx-text-fill: black;");
        btnPayment.setStyle("-fx-base: white;");
        btnPayment.setStyle("-fx-background-radius: 15;");

        btnResult.setStyle("-fx-background-color : white;");
        btnResult.setStyle("-fx-text-fill: black;");
        btnResult.setStyle("-fx-base: white;");
        btnResult.setStyle("-fx-background-radius: 15;");

        btnSchedule.setStyle("-fx-background-color : white;");
        btnSchedule.setStyle("-fx-text-fill: black;");
        btnSchedule.setStyle("-fx-base: white;");
        btnSchedule.setStyle("-fx-background-radius: 15;");

        btnDashBoard.setMouseTransparent(false);
        btnAttendance.setMouseTransparent(false);
        btnPayment.setMouseTransparent(false);
        btnSubject.setMouseTransparent(false);
        btnStudent.setMouseTransparent(false);
        btnResult.setMouseTransparent(false);
        btnSchedule.setMouseTransparent(false);
    }

    public void openStudentForm(ActionEvent actionEvent) throws IOException {
        setStyles();
        btnStudent.setMouseTransparent(true);
        btnStudent.setStyle("-fx-background-color : blue;");
        btnStudent.setStyle("-fx-text-fill: red;");
        btnStudent.setStyle("-fx-base: #c41010;");
        URL resource = getClass().getResource("../View/StudentForm.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardFormContext.getChildren().clear();
        dashBoardFormContext.getChildren().add(load);
    }

    public void openDashBoardForm(ActionEvent actionEvent) throws IOException {
        setStyles();
        btnDashBoard.setMouseTransparent(true);
        btnDashBoard.setStyle("-fx-background-color : blue;");
        btnDashBoard.setStyle("-fx-text-fill: red;");
        btnDashBoard.setStyle("-fx-base: #c41010;");
        URL resource = getClass().getResource("../View/DashBoardFormUser.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardFormContext.getChildren().clear();
        dashBoardFormContext.getChildren().add(load);
    }

    public void openSubjectForm(ActionEvent actionEvent) throws IOException {
        setStyles();
        btnSubject.setMouseTransparent(true);
        btnSubject.setStyle("-fx-background-color : blue;");
        btnSubject.setStyle("-fx-text-fill: red;");
        btnSubject.setStyle("-fx-base: #c41010;");
        URL resource = getClass().getResource("../View/UserSubjectForm.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardFormContext.getChildren().clear();
        dashBoardFormContext.getChildren().add(load);
    }

    public void openPaymentForm(ActionEvent actionEvent) throws IOException {
        setStyles();
        btnPayment.setMouseTransparent(true);
        btnPayment.setStyle("-fx-background-color : blue;");
        btnPayment.setStyle("-fx-text-fill: red;");
        btnPayment.setStyle("-fx-base: #c41010;");
        URL resource = getClass().getResource("../View/StudentPaymentsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardFormContext.getChildren().clear();
        dashBoardFormContext.getChildren().add(load);
    }

    public void openAttendanceFormOnAction(ActionEvent actionEvent) throws IOException {
        setStyles();
        btnAttendance.setMouseTransparent(true);
        btnAttendance.setStyle("-fx-background-color : blue;");
        btnAttendance.setStyle("-fx-text-fill: red;");
        btnAttendance.setStyle("-fx-base: #c41010;");
        URL resource = getClass().getResource("../View/StudentAttendanceForm.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardFormContext.getChildren().clear();
        dashBoardFormContext.getChildren().add(load);
    }

    public void openResultFormOnAction(ActionEvent actionEvent) throws IOException {
        setStyles();
        btnResult.setMouseTransparent(true);
        btnResult.setStyle("-fx-background-color : blue;");
        btnResult.setStyle("-fx-text-fill: red;");
        btnResult.setStyle("-fx-base: #c41010;");
        URL resource = getClass().getResource("../View/ResultForm.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardFormContext.getChildren().clear();
        dashBoardFormContext.getChildren().add(load);
    }

    public void openScheduleForm(ActionEvent actionEvent) throws IOException {
        setStyles();
        btnSchedule.setMouseTransparent(true);
        btnSchedule.setStyle("-fx-background-color : blue;");
        btnSchedule.setStyle("-fx-text-fill: red;");
        btnSchedule.setStyle("-fx-base: #c41010;");
        URL resource = getClass().getResource("../View/ScheduleForm.fxml");
        Parent load = FXMLLoader.load(resource);
        dashBoardFormContext.getChildren().clear();
        dashBoardFormContext.getChildren().add(load);
    }

    public void closeBTNMouseEntered(MouseEvent mouseEvent) {
        closeBTN.setStyle("-fx-background-color: #e74c3c");
    }

    public void closeBTNMouseExited(MouseEvent mouseEvent) {
        closeBTN.setStyle("-fx-background-color: white");
    }

    public void closeBTNMouseEntered1(MouseEvent mouseEvent) {
        closeBTN.setStyle("-fx-background-color: #e74c3c");
    }

    public void closeBTNMouseExited1(MouseEvent mouseEvent) {
        closeBTN.setStyle("-fx-background-color: white");
    }

    public void minimizeBTNMouseEntered(MouseEvent mouseEvent) {
        btnMinimize.setStyle("-fx-background-color: #bdc3c7");
    }

    public void minimizeBTNMouseExited(MouseEvent mouseEvent) {
        btnMinimize.setStyle("-fx-background-color: white");
    }

    public void minimizeBTNMouseEntered1(MouseEvent mouseEvent) {
        btnMinimize.setStyle("-fx-background-color: #bdc3c7");
    }

    public void minimizeBTNMouseExited1(MouseEvent mouseEvent) {
        btnMinimize.setStyle("-fx-background-color: white");
    }


}
