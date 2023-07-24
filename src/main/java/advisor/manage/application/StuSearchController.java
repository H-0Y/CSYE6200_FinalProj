package advisor.manage.application;

import advisor.manage.Main;
import advisor.manage.entity.Advisor;
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
public class StuSearchController {

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
    @FXML
    private Button confirmSearchButton;

    private ControllerUtils controllerUtils = new ControllerUtils();
    @FXML
    public void initialize(){
        sex.getItems().clear();
        sex.getItems().addAll("Male", "Female");
        SqlUtil.doSqlWork(mapper ->{
            List<Advisor> advisorList = mapper.getAdvisorList();
            advisor.getItems().addAll(advisorList);
        });
    }

    @FXML
    public void confirmSearchStuButton(){
        StudentPageController studentPageController = (StudentPageController) Main.changeView("/student_page.fxml");
        studentPageController.showSearchStudents(controllerUtils.getFieldAsInteger(sid), name.getText(), sex.getValue(),controllerUtils.getFieldAsInteger(grade), advisor.getValue());
        Stage currentStage = (Stage)confirmSearchButton.getScene().getWindow();
        currentStage.close();
    }
}