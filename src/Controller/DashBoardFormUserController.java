package Controller;

import BusinessLogics.StudentController;
import BusinessLogics.SubjectController;
import BusinessLogics.TeacherController;
import BusinessLogics.UserController;
import Model.OwnerDetail;
import Model.Teacher;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.ArrayList;

public class DashBoardFormUserController {
    public AnchorPane dashBoardFormContext;
    public Label lblNumberOfStudents;
    public Label lblNumberOfTeachers;
    public Label lblNumberOfSubjects;
    public TableView tblDetails;
    public TableColumn colSubjects;
    public TableColumn colTeachers;
    public Label lblOwnersName;
    public Label lblOwnersContact;

    ArrayList<Teacher> details = new ArrayList<>();

    public void initialize(){
        try {
            OwnerDetail o = new UserController().getOwnerDetails();
            lblOwnersName.setText(o.getName());
            lblOwnersContact.setText(o.getContact());
            details = new TeacherController().getDetails();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        setDataToTable();

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
}
