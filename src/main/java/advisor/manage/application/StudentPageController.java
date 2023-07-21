package advisor.manage.application;

import advisor.manage.Main;
import advisor.manage.entity.Student;
import advisor.manage.sql.SqlUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;
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

    // 创建存储学生数据的ObservableList
    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    @FXML
    private void handleStudentListButton() {showStudent();

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
                    log.info("delete a student info of sid: "+ sid);
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

    public void showStudent(){
        SqlUtil.doSqlWork(mapper -> {
            List<Student> studentList = mapper.getStudentList();
            // 打印日志
            for(Student student : studentList){
                log.info(student.toString());
            }
            // 将List<Student>转换为ObservableList<Student>
            ObservableList<Student> observableStudentList = FXCollections.observableArrayList(studentList);

            //绑定TableView的每个列与Student类的属性，并设置cellValueFactory
            sid.setCellValueFactory(new PropertyValueFactory<>("sid"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
            grade.setCellValueFactory(new PropertyValueFactory<>("grade"));

            // 将ObservableList<Student>设置为TableView的数据源
            studentTableView.setItems(observableStudentList);
        });
    }


}

