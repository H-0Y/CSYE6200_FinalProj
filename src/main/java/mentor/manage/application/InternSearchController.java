package mentor.manage.application;

import mentor.manage.Main;
import mentor.manage.entity.Mentor;
import mentor.manage.sql.SqlUtil;
import mentor.manage.utils.ControllerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.java.Log;

import java.util.List;

@Log
public class InternSearchController {

    @FXML
    private TextField sid;
    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<String> sex;
    @FXML
    private TextField grade;
    @FXML
    private ChoiceBox<Mentor> mentor;
    @FXML
    private Button confirmSearchButton;

    private ControllerUtils controllerUtils = new ControllerUtils();
    @FXML
    public void initialize(){
        sex.getItems().clear();
        sex.getItems().addAll("Male", "Female");
        SqlUtil.doSqlWork(mapper ->{
            List<Mentor> mentorList = mapper.getMentorList();
            mentor.getItems().addAll(mentorList);
        });
    }

    @FXML
    public void confirmSearchInternButton(){
        InternPageController internPageController = (InternPageController) Main.changeView("/front-end/intern_page.fxml");
        internPageController.showSearchInterns(controllerUtils.getFieldAsInteger(sid), name.getText(), sex.getValue(),controllerUtils.getFieldAsInteger(grade), mentor.getValue());
        Stage currentStage = (Stage)confirmSearchButton.getScene().getWindow();
        currentStage.close();
    }
}