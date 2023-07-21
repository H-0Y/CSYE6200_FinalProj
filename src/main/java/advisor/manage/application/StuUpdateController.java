package advisor.manage.application;


import advisor.manage.entity.Student;
import advisor.manage.sql.SqlUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Log
public class StuUpdateController {
    @FXML
    private TextField sid;
    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<String> sex;
    @FXML
    private TextField grade;
    private static Student curStudent;

    public static void setCurStudent(Student curStudent) {
        StuUpdateController.curStudent = curStudent;
    }



    @FXML
    public void initialize(){
        sex.getItems().clear();
        sex.getItems().addAll("Male", "Female");

        sid.setText(String.valueOf(curStudent.getSid()));
        name.setText(curStudent.getName());
        sex.setValue(curStudent.getSex());
        grade.setText(String.valueOf(curStudent.getGrade()));
    }

    @FXML
    public void confirmUpdateStuButton(){
        //create a new student
        Student student = new Student(getSidAsInteger(), name.getText(), sex.getValue(),getGradeAsInteger());
        //update sql
        try {
            SqlUtil.doSqlWork(mapper -> {
                mapper.updateStudent(student);
                log.info("update a student info: "+ student);
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

