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

@Log
public class MentorAddController {
    @FXML
    private TextField aid;
    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<String> sex;
    @FXML
    private Button confirmAddButton;


    private ControllerUtils controllerUtils = new ControllerUtils();

    @FXML
    public void initialize() {
        sex.getItems().clear();
        sex.getItems().addAll("Male", "Female");
    }

    @FXML
    public void confirmAddMentorButton() {
        Mentor mentor = new Mentor(controllerUtils.getFieldAsInteger(aid), name.getText(), sex.getValue());

        try {
            SqlUtil.doSqlWork(mapper -> {
                int result = mapper.addMentor(mentor);
                if (result > 0) {
                    log.info("Added a new mentor: " + mentor);
                } else {
                    log.info("Failure to add a new mentor: " + mentor);
                }
            });
        } catch (Exception e) {
            System.out.println("Failure, please check and try again.");
        }

        MentorPageController mentorPageController = (MentorPageController) Main.changeView("/front-end/mentor_page.fxml");
        mentorPageController.showMentor();
        Stage currentStage = (Stage) confirmAddButton.getScene().getWindow();
        currentStage.close();
    }
}
