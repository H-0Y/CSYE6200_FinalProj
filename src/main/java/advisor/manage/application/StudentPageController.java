package advisor.manage.application;

import advisor.manage.Main;
import advisor.manage.entity.Advisor;
import advisor.manage.entity.Student;
import advisor.manage.sql.SqlUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lombok.extern.java.Log;

import java.util.List;

@Log
public class StudentPageController {
    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, Integer> sid;
    @FXML
    private TableColumn<Student, String> name;
    @FXML
    private TableColumn<Student, String> sex;
    @FXML
    private TableColumn<Student, Integer> grade;
    @FXML
    private TableColumn<Student, String> advisor;

    @FXML
    private void handleStudentListButton() {
        showStudent();
    }
    @FXML
    public void handleAddStuButton() {
        Main.addView("/add_student.fxml");
    }

    @FXML
    public void handleDeleteStuButton() {
        Student student = studentTableView.getSelectionModel().getSelectedItem();
        if(student != null){
            SqlUtil.doSqlWork(mapper -> {
                int i = mapper.deleteStudentBySid(student.getSid());
                showStudent();
                if(i>0) {
                    log.info("delete a student info of sid: "+ student.getSid());
                }
                else log.info("Failure, please try again");
                i = mapper.deleteAdvisedByStudentId(student.getSid());
                if(i>0) {
                    log.info("delete a advised info of sid: "+ student.getSid());
                }
                else log.info("Failure, please try again");
            });
        }
    }

    @FXML
    private void handleUpdateStuButton() {
        //get current selected student
        Student student = studentTableView.getSelectionModel().getSelectedItem();
        if(student != null){
            StuUpdateController.setCurStudent(student);
            Main.addView("/update_student.fxml");
        }
    }

    @FXML
    private void handleSearchStuButton() {
        Main.addView("/search_student.fxml");
    }

    @FXML
    private void returnToHomeButton() {
        Main.changeView("/home_page.fxml");
    }

    public void showStudent(){
        SqlUtil.doSqlWork(mapper -> {
            List<Student> studentList = mapper.getAllStudentsWithAdvisors();
            for(Student student : studentList){
                log.info(student.toString());
            }
            showTableList(studentList);
        });
    }

    public void showSearchStudents(Integer searchId, String sname, String ssex, Integer sgrade, Advisor sadvisor){
        SqlUtil.doSqlWork(mapper -> {
            List<Student> studentList = mapper.searchStudentList(searchId, sname, ssex, sgrade, sadvisor == null ? null : sadvisor.getName());
            for(Student student : studentList){
                log.info(student.toString());
            }
            showTableList(studentList);
        });
    }

    private void showTableList(List<Student> studentList) {
        ObservableList<Student> observableStudentList = FXCollections.observableArrayList(studentList);

        Callback<TableColumn.CellDataFeatures<Student, String>, ObservableValue<String>> advisorCellValueFactory = cellData -> {
            Advisor advisor = cellData.getValue().getAdvisor();
            if (advisor != null) {
                return new SimpleStringProperty(advisor.getName());
            } else {
                return new SimpleStringProperty("");
            }
        };

        sid.setCellValueFactory(new PropertyValueFactory<>("sid"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        advisor.setCellValueFactory(advisorCellValueFactory);

        studentTableView.setItems(observableStudentList);
    }
}

