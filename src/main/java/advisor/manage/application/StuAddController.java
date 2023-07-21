package advisor.manage.application;

import advisor.manage.entity.Student;
import advisor.manage.sql.SqlUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import lombok.extern.java.Log;

@Log
public class StuAddController {
    @FXML
    private TextField sid;
    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<String> sex;
    @FXML
    private TextField grade;

    @FXML
    public void initialize(){
        sex.getItems().clear();
        sex.getItems().addAll("Male", "Female");
    }

    @FXML
    public void confirmAddStuButton(){
        Student student = new Student(getSidAsInteger(), name.getText(), sex.getValue(),getGradeAsInteger());
        try {
            SqlUtil.doSqlWork(mapper -> {
                int i = mapper.addStudent(student);
                if(i>0) {
                    log.info("add a new student info: "+ student);
                }
                else log.info("Failure to add a new student info: "+ student);
            });
        }catch (Exception e) {
            System.out.println("Failure, please check and try again.");
        }

    }

    private Integer getSidAsInteger() {
        try {
            return Integer.parseInt(sid.getText());
        } catch (NumberFormatException e) {
            // 如果转换失败，可以根据需要处理异常，例如显示错误提示等
            e.printStackTrace();
            return null;
        }
    }

    // 将grade字段从TextField转换为Integer
    private Integer getGradeAsInteger() {
        try {
            return Integer.parseInt(grade.getText());
        } catch (NumberFormatException e) {
            // 如果转换失败，可以根据需要处理异常，例如显示错误提示等
            e.printStackTrace();
            return null;
        }
    }
}

