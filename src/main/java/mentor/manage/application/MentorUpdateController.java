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
public class MentorUpdateController {
    @FXML
    private TextField aid;
    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<String> sex;
    @FXML
    private Button confirmUpdateButton;

    private static Mentor curMentor;
    private ControllerUtils controllerUtils = new ControllerUtils();

    public static void setCurMentor(Mentor curMentor) {
        MentorUpdateController.curMentor = curMentor;
    }

    @FXML
    public void initialize() {
        sex.getItems().clear();
        sex.getItems().addAll("Male", "Female");

        aid.setText(String.valueOf(curMentor.getAid()));
        name.setText(curMentor.getName());
        sex.setValue(curMentor.getSex());
    }

    @FXML
    public void confirmUpdateMentorButton() {
        // Create a new mentor
        Mentor mentor = new Mentor(controllerUtils.getFieldAsInteger(aid), name.getText(), sex.getValue());
        log.info("Update an mentor info: " + mentor.getAid());

        // Update SQL
        try {
            SqlUtil.doSqlWork(mapper -> {
                mapper.updateMentor(mentor);
                log.info("Update an mentor info: " + mentor);
            });
        } catch (Exception e) {
            System.out.println("Failure, please check and try again.");
        }

        MentorPageController mentorPageController = (MentorPageController) Main.changeView("/front-end/mentor_page.fxml");
        mentorPageController.showMentor();
        Stage currentStage = (Stage) confirmUpdateButton.getScene().getWindow();
        currentStage.close();
    }
}
