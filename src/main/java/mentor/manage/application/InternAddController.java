package mentor.manage.application;

import mentor.manage.Main;
import mentor.manage.entity.Mentor;
import mentor.manage.entity.Intern;
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
public class InternAddController {
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
    private Button confirmAddButton;

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
    public void confirmAddInternButton(){
        Intern intern = new Intern(controllerUtils.getFieldAsInteger(sid), name.getText(), sex.getValue(),controllerUtils.getFieldAsInteger(grade), mentor.getValue());

        try {
            SqlUtil.doSqlWork(mapper -> {
                int result = mapper.addIntern(intern);
                if(result > 0) {
                    log.info("add a new intern info: "+ intern);
                }
                else log.info("Failure to add a new intern info: "+ intern);

                if (intern.getMentor() == null) {
                    return;
                }
                result = mapper.addMentorship(intern.getSid(), intern.getMentor().getAid());
                if(result > 0) {
                    log.info("add a new mentorship info: "+ intern);
                }
                else log.info("Failure to add a new mentorship info: "+ intern);
            });
        }catch (Exception e) {
            System.out.println("Failure, please check and try again.");
        }
        InternPageController internPageController = (InternPageController) Main.changeView("/front-end/intern_page.fxml");
        internPageController.showIntern();
        Stage currentStage = (Stage)confirmAddButton.getScene().getWindow();
        currentStage.close();
    }
}

