package Controller;

import BusinessLogics.EmployeeController;
import BusinessLogics.PaymentController;
import BusinessLogics.StudentController;
import BusinessLogics.TeacherController;
import Model.*;
import View.tm.EmployeeDetailsTM;
import View.tm.EmployeeSalaryTM;
import View.tm.TeacherSalaryTM;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.regex.Pattern;

public class SalaryManagementController {
    public AnchorPane salaryManagementFormContext;
    public ToggleGroup rdSalaryType;
    public TableView tblSectionDetails;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colDescAndJobRole;
    public TableColumn colSubNameAndWorkingDays;
    public TableColumn colFeeAndSalaryPerDay;
    public TableColumn colTotalFeeAndAllowances;
    public TableColumn colDeductions;
    public TableColumn colNetSalary;
    public TableColumn colCondition;
    public JFXComboBox<String> cmbMonths;
    public Label lblTeacherID;
    public TextField txtTeacherDeductions;
    public TextField txtNetSalary;
    public TextField txtClassFee;
    public TextField txtPercentage;
    public Label lblPercentage;
    public Label lblEmployeeID;
    public TextField txtEDeductions;
    public TextField txtWorkingDays;
    public TextField txtSalaryPerDay;
    public TextField txtENetSalary;
    public TextField txtEName;
    public TextField txtTName;
    public TextField txtEAllowances;
    public TextField txtTAllowances;
    public Button btnPayRoll;

    ArrayList<Teacher> teachers = new ArrayList<>();
    ArrayList<TeacherSalaryTM> tempTeachersSalary = new ArrayList<>();
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<EmployeeSalaryTM> tempEmployeesSalary = new ArrayList<>();
    int selectedRowInSalaryTable = -1;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> map1 = new LinkedHashMap();

    Pattern teacherAllowancesPattern = Pattern.compile("^[0-9.]{1,10}$");
    Pattern teacherDeductionsPattern = Pattern.compile("^[0-9.]{1,10}$");

    Pattern employeeWorkingDaysPattern = Pattern.compile("^[0-9]{1,5}$");
    Pattern employeeSalaryPerDayPattern = Pattern.compile("^[0-9.]{1,10}$");
    Pattern employeeAllowancesPattern = Pattern.compile("^[0-9.]{1,10}$");
    Pattern employeeDeductionsPattern = Pattern.compile("^[0-9.]{1,10}$");

    public void initialize(){

        txtWorkingDays.setDisable(true);
        txtSalaryPerDay.setDisable(true);
        txtEAllowances.setDisable(true);
        txtEDeductions.setDisable(true);
        btnPayRoll.setDisable(true);

        RadioButton selectedRadioButton = (RadioButton) rdSalaryType.getSelectedToggle();
        String date = new SimpleDateFormat("MM", Locale.ENGLISH).format(new Date());
        cmbMonths.setValue(checkMonth(date));
        cmbMonths.getItems().addAll("January","February","March","April","May","June","July","August","September","October","November","December");

        sortTeacherSalaryDetails();

        cmbMonths.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            tempTeachersSalary.clear();
            tempEmployeesSalary.clear();
            System.out.println(selectedRadioButton.getText());
            if(selectedRadioButton.isSelected()){
                if(selectedRadioButton.getText().equals("Teacher")){
                    sortTeacherSalaryDetails();
                }
                if(selectedRadioButton.getText().equals("Employee")){
                    sortEmployeeSalaryDetails();
                }
            }else{

            }
        });

        tblSectionDetails.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectedRowInSalaryTable = (int) newValue;
        });

        storeValidationsTeacher();
    }

    private void storeValidationsTeacher() {
        map.put(txtTAllowances, teacherAllowancesPattern);
        map.put(txtTeacherDeductions, teacherDeductionsPattern);
    }

    private void storeValidationsEmployee() {
        map1.put(txtWorkingDays, employeeWorkingDaysPattern);
        map1.put(txtSalaryPerDay, employeeSalaryPerDayPattern);
        map1.put(txtEAllowances, employeeAllowancesPattern);
        map1.put(txtEDeductions, employeeDeductionsPattern);

    }

    public void textFields_Employee_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateForNormalTextFields(map1, btnPayRoll);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    public void textFields_Teacher_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validateForNormalTextFields(map, btnPayRoll);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }


    public void sortBySectionOnAction(ActionEvent actionEvent) {
        RadioButton selectedRadioButton = (RadioButton) rdSalaryType.getSelectedToggle();
        if(selectedRadioButton.getText().equals("Teacher")){
            selectedRowInSalaryTable=-1;
            txtWorkingDays.setDisable(true);
            txtSalaryPerDay.setDisable(true);
            txtEAllowances.setDisable(true);
            txtEDeductions.setDisable(true);
            txtENetSalary.setDisable(true);

            txtTAllowances.setDisable(false);
            txtTeacherDeductions.setDisable(false);
            txtNetSalary.setDisable(false);

            lblTeacherID.setText(" ");
            txtTName.clear();
            txtClassFee.clear();
            txtTAllowances.clear();
            txtTeacherDeductions.clear();
            txtNetSalary.clear();
            lblEmployeeID.setText(" ");
            txtEName.clear();
            txtEAllowances.clear();
            txtEDeductions.clear();
            txtENetSalary.clear();
            txtWorkingDays.clear();
            txtSalaryPerDay.clear();
            colID.setText("Teacher ID");
            colName.setText("Teacher Name");
            colDescAndJobRole.setText("Teacher Description");
            colSubNameAndWorkingDays.setText("Subject Name");
            colFeeAndSalaryPerDay.setText("fee (Rs)");
            colTotalFeeAndAllowances.setText("Total Fee");
            colDeductions.setText("Deductions");
            colNetSalary.setText("Net Salary");
            colCondition.setText("Condition");
            sortTeacherSalaryDetails();
            storeValidationsTeacher();

        }else{
            selectedRowInSalaryTable=-1;
            txtWorkingDays.setDisable(false);
            txtSalaryPerDay.setDisable(false);
            txtEAllowances.setDisable(false);
            txtEDeductions.setDisable(false);
            txtENetSalary.setDisable(false);

            txtTAllowances.setDisable(true);
            txtTeacherDeductions.setDisable(true);
            txtNetSalary.setDisable(true);

            lblTeacherID.setText(" ");
            txtTName.clear();
            txtClassFee.clear();
            txtEAllowances.clear();
            txtEDeductions.clear();
            txtENetSalary.clear();
            txtWorkingDays.clear();
            txtSalaryPerDay.clear();
            lblEmployeeID.setText(" ");
            txtEName.clear();
            txtTAllowances.clear();
            txtTeacherDeductions.clear();
            txtNetSalary.clear();
            colID.setText("Employee ID");
            colName.setText("Employee Name");
            colDescAndJobRole.setText("Job Role");
            colSubNameAndWorkingDays.setText("Working Days");
            colFeeAndSalaryPerDay.setText("Salary per day");
            colTotalFeeAndAllowances.setText("Allowances");
            colDeductions.setText("Deductions");
            colNetSalary.setText("Net Salary");
            colCondition.setText("Condition");
            sortEmployeeSalaryDetails();
            storeValidationsEmployee();
        }
    }

    private void sortTeacherSalaryDetails(){
        tempTeachersSalary.clear();
        try {
            teachers = new TeacherController().getAllTeacherDetails();
            for (Teacher t : teachers) {
                String paidOrNot = new TeacherController().getTeacherSalaryDetails(t.getTeacherId(),cmbMonths.getSelectionModel().getSelectedItem());
                if(paidOrNot.equals("Paid")){
                    TeacherSalary salary = new TeacherController().getSalaryDetails(t.getTeacherId());
                    double totalFee = new PaymentController().getTotalFeeOfTeacher(t.getTeacherName(),t.getSubName(),cmbMonths.getSelectionModel().getSelectedItem());
                    tempTeachersSalary.add(new TeacherSalaryTM(t.getTeacherId(),t.getTeacherName(),t.getTeacherDescription(),t.getSubName(),t.getFee(),String.valueOf(totalFee),salary.getDeductions(),salary.getNetSalary(),"Paid"));
                    setDataToTeacherTable();
                }else{
                    double totalFee = new PaymentController().getTotalFeeOfTeacher(t.getTeacherName(),t.getSubName(),cmbMonths.getSelectionModel().getSelectedItem());
                    tempTeachersSalary.add(new TeacherSalaryTM(t.getTeacherId(),t.getTeacherName(),t.getTeacherDescription(),t.getSubName(),t.getFee(),String.valueOf(totalFee),"0.00","0.00","Not Paid"));
                    setDataToTeacherTable();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sortEmployeeSalaryDetails(){
        tempEmployeesSalary.clear();
        try {
            employees = new EmployeeController().getAllEmployeeDetails();
            for (Employee t : employees) {
                String paidOrNot = new EmployeeController().getEmployeeSalaryDetails(t.getEmployeeID(),cmbMonths.getSelectionModel().getSelectedItem());
                if(paidOrNot.equals("Paid")){
                    EmployeeSalary salary = new EmployeeController().getSalaryDetails(t.getEmployeeID());
                    tempEmployeesSalary.add(new EmployeeSalaryTM(t.getEmployeeID(),t.getFirstName()+" "+t.getLastName(),t.getNic(),String.valueOf(salary.getWorkingDays()),String.valueOf(salary.getSalaryPerDay()),String.valueOf(salary.getAllowances()),String.valueOf(salary.getDeductions()),String.valueOf(salary.getNetSalary()),"Paid"));
                    setDataToEmployeeTable();
                }else{
                    tempEmployeesSalary.add(new EmployeeSalaryTM(t.getEmployeeID(),t.getFirstName()+" "+t.getLastName(),t.getNic(),"0","0.00","0.00","0.00","0.00","Not Paid"));
                    setDataToEmployeeTable();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setDataToTeacherTable(){
        colID.setCellValueFactory(new PropertyValueFactory<>("teacherID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        colDescAndJobRole.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSubNameAndWorkingDays.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colFeeAndSalaryPerDay.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colTotalFeeAndAllowances.setCellValueFactory(new PropertyValueFactory<>("totalFee"));
        colDeductions.setCellValueFactory(new PropertyValueFactory<>("deductions"));
        colNetSalary.setCellValueFactory(new PropertyValueFactory<>("netSalary"));
        colCondition.setCellValueFactory(new PropertyValueFactory<>("condition"));

        tblSectionDetails.setItems(FXCollections.observableArrayList(tempTeachersSalary));
    }

    private void setDataToEmployeeTable(){
        colID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colDescAndJobRole.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
        colSubNameAndWorkingDays.setCellValueFactory(new PropertyValueFactory<>("workingDays"));
        colFeeAndSalaryPerDay.setCellValueFactory(new PropertyValueFactory<>("salaryPerDay"));
        colTotalFeeAndAllowances.setCellValueFactory(new PropertyValueFactory<>("allowances"));
        colDeductions.setCellValueFactory(new PropertyValueFactory<>("deductions"));
        colNetSalary.setCellValueFactory(new PropertyValueFactory<>("netSalary"));
        colCondition.setCellValueFactory(new PropertyValueFactory<>("condition"));

        tblSectionDetails.setItems(FXCollections.observableArrayList(tempEmployeesSalary));
    }

    private String checkMonth(String month){
        if(month.equals("01")){
            return "January";
        }else if(month.equals("02")){
            return "February";
        }else if(month.equals("03")){
            return "March";
        }else if(month.equals("04")){
            return "April";
        }else if(month.equals("05")){
            return "May";
        }else if(month.equals("06")){
            return "June";
        }else if(month.equals("07")){
            return "July";
        }else if(month.equals("08")){
            return "August";
        }else if(month.equals("09")){
            return "September";
        }else if(month.equals("10")){
            return "October";
        }else if(month.equals("11")){
            return "November";
        }else if(month.equals("12")){
            return "December";
        }
        return null;
    }



    public void createPayRollOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(selectedRowInSalaryTable!=-1) {
            SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
            Date time = new Date();
            String pTime = formatter1.format(time);
            String pDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());
            RadioButton selectedRadioButton = (RadioButton) rdSalaryType.getSelectedToggle();
            if (selectedRadioButton.getText().equals("Teacher")) {
                if (!txtTAllowances.getText().isEmpty() && !txtTeacherDeductions.getText().isEmpty()) {
                    TeacherSalaryTM s = tempTeachersSalary.get(selectedRowInSalaryTable);
                    TeacherSalary teacherSalary = new TeacherSalary(s.getTeacherID(), s.getTeacherName(), s.getTotalFee(), "0.80", txtTAllowances.getText(), txtTeacherDeductions.getText(), txtNetSalary.getText(), cmbMonths.getSelectionModel().getSelectedItem(), pDate, pTime);
                    if (new PaymentController().PaidOrNotTeacher(s.getTeacherID(), cmbMonths.getSelectionModel().getSelectedItem())) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Already Paid....!").show();
                    } else {
                        if (new PaymentController().saveTeacherSalary(teacherSalary)) {
                            txtTAllowances.clear();
                            txtTeacherDeductions.clear();
                            txtNetSalary.clear();
                            sortTeacherSalaryDetails();
                            new Alert(Alert.AlertType.CONFIRMATION, "Paid Successfully...!").show();
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Something went wrong...!").show();
                        }
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "Please fill all fields and try again...!").show();
                }

            } else {
                if (!txtEAllowances.getText().isEmpty() && !txtEDeductions.getText().isEmpty() && !txtSalaryPerDay.getText().isEmpty() && !txtWorkingDays.getText().isEmpty()) {
                    EmployeeSalaryTM e = tempEmployeesSalary.get(selectedRowInSalaryTable);
                    EmployeeSalary employeeSalary = new EmployeeSalary(e.getEmployeeID(), e.getEmployeeName(), e.getJobRole(), txtWorkingDays.getText(), txtSalaryPerDay.getText(), txtEAllowances.getText(), txtEDeductions.getText(), txtENetSalary.getText(), cmbMonths.getSelectionModel().getSelectedItem(), pDate, pTime);
                    if (new PaymentController().PaidOrNotEmployee(e.getEmployeeID(), cmbMonths.getSelectionModel().getSelectedItem())) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Already Paid....!").show();
                    } else {
                        if (new PaymentController().saveEmployeeSalary(employeeSalary)) {
                            txtEDeductions.clear();
                            txtEAllowances.clear();
                            txtENetSalary.clear();
                            txtWorkingDays.clear();
                            txtSalaryPerDay.clear();
                            sortEmployeeSalaryDetails();
                            new Alert(Alert.AlertType.CONFIRMATION, "Paid Successfully...!").show();
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Something went wrong...!").show();
                        }
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "Please fill all fields and try again...!").show();
                }

            }
        }else {
            new Alert(Alert.AlertType.WARNING,"Please select arow and try again..!").show();
        }
    }

    public void setDataToFieldsOnAction(MouseEvent mouseEvent) {
        RadioButton selectedRadioButton = (RadioButton) rdSalaryType.getSelectedToggle();
        if(selectedRadioButton.getText().equals("Teacher")){
            TeacherSalaryTM s = tempTeachersSalary.get(selectedRowInSalaryTable);
            lblTeacherID.setText(s.getTeacherID());
            txtTName.setText(s.getTeacherName());
            txtClassFee.setText(s.getTotalFee());
        }else{
            EmployeeSalaryTM e = tempEmployeesSalary.get(selectedRowInSalaryTable);
            lblEmployeeID.setText(e.getEmployeeID());
            txtEName.setText(e.getEmployeeName());

        }
    }

    public void countNetSalaryOfTeacher(ActionEvent actionEvent) {
        if(selectedRowInSalaryTable!=-1) {
                if (!txtTAllowances.getText().isEmpty() && !txtTeacherDeductions.getText().isEmpty()) {
                Double netSalary = (Double.parseDouble(txtClassFee.getText()) * 0.80) + Double.parseDouble(txtTAllowances.getText()) - Double.parseDouble(txtTeacherDeductions.getText());
                txtNetSalary.setText(String.valueOf(netSalary));
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Please select a row and Try again").show();
        }
    }

    public void countNetSalaryOfEmployee(ActionEvent actionEvent) {
        if(selectedRowInSalaryTable!=-1) {
            if (!txtEAllowances.getText().isEmpty() && !txtEDeductions.getText().isEmpty() && !txtSalaryPerDay.getText().isEmpty() && !txtWorkingDays.getText().isEmpty()) {
                Double netSalary = (Integer.parseInt(txtWorkingDays.getText()) * Double.parseDouble(txtSalaryPerDay.getText())) + Double.parseDouble(txtEAllowances.getText()) - Double.parseDouble(txtEDeductions.getText());
                txtENetSalary.setText(String.valueOf(netSalary));
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"Please select a row and Try again").show();
        }
    }


    public void generateTeachersSalaryReportOnAction(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/Reports/TeacherSalary.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateEmployeesSalaryReportOnAction(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/Reports/EmployeeSalary.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
