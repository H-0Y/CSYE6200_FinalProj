package advisor.manage.application;


import advisor.manage.Main;
import advisor.manage.entity.Advisor;
import advisor.manage.entity.Student;
import advisor.manage.sql.SqlUtil;
import advisor.manage.utils.ControllerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.java.Log;

import java.util.List;

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
    @FXML
    private ChoiceBox<Advisor> advisor;
    private static Student curStudent;
    @FXML
    private Button confirmUpdateButton;

    private ControllerUtils controllerUtils = new ControllerUtils();

    public static void setCurStudent(Student curStudent) {
        StuUpdateController.curStudent = curStudent;
    }


    @FXML
    public void initialize(){
        sex.getItems().clear();
        sex.getItems().addAll("Male", "Female");
        SqlUtil.doSqlWork(mapper ->{
            List<Advisor> advisorList = mapper.getAdvisorList();
            advisor.getItems().addAll(advisorList);
        });

        sid.setText(String.valueOf(curStudent.getSid()));
        name.setText(curStudent.getName());
        sex.setValue(curStudent.getSex());
        grade.setText(String.valueOf(curStudent.getGrade()));
        advisor.setValue(curStudent.getAdvisor());
    }

    @FXML
    public void confirmUpdateStuButton() {
        //create a new student
        Student student = new Student(controllerUtils.getFieldAsInteger(sid), name.getText(), sex.getValue(), controllerUtils.getFieldAsInteger(grade),advisor.getValue());
        log.info("update a student info: " + student.getSid());
        //update sql
        try {
            SqlUtil.doSqlWork(mapper -> {
                mapper.updateStudent(student);
                log.info("update a student info: " + student);
                Advisor advisorByStudentId = mapper.getAdvisorByStudentId(student.getSid());
                if (advisorByStudentId != null && advisorByStudentId.getAid() == student.getAdvisor().getAid()) {
                    return;
                }
                if(advisorByStudentId == null && student.getAdvisor() != null){
                    int i = mapper.addAdvised(student.getSid(), student.getAdvisor().getAid());
                    if(i>0) log.info("add a advisor info: " + student.getSid());
                    else log.info("Failure to add a advisor info: " + student.getSid());
                    return;
                }
                if (student.getAdvisor() == null) {
                    int i =  mapper.deleteAdvisedByStudentId(student.getSid());
                    if(i>0) log.info("delete a advisor info: " + student.getSid());
                    else log.info("Failure to delete a advisor info: " + student.getSid());
                    return;
                }
                int  i = mapper.updateAdvised(student.getSid(), student.getAdvisor().getAid());
                if(i>0) log.info("update a advised info: " + student.getSid());
                else log.info("Failure to update a advised info: " + student.getSid());
            });

        } catch (Exception e) {
            System.out.println("Failure, please check and try again.");
        }
        StudentPageController studentPageController = (StudentPageController) Main.changeView("/student_page.fxml");
        studentPageController.showStudent();
        Stage currentStage = (Stage) confirmUpdateButton.getScene().getWindow();
        currentStage.close();
    }
}

