package mentor.manage.application;

import mentor.manage.Main;
import mentor.manage.utils.ControllerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.java.Log;

@Log
public class MentorSearchController {
    @FXML
    private TextField aid;
    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<String> sex;
    @FXML
    private Button confirmSearchButton;

    private ControllerUtils controllerUtils = new ControllerUtils();

    @FXML
    public void initialize() {
        sex.getItems().clear();
        sex.getItems().addAll("Male", "Female");
    }

    @FXML
    public void confirmSearchMentorButton() {
        MentorPageController mentorPageController = (MentorPageController) Main.changeView("/front-end/mentor_page.fxml");
        mentorPageController.showSearchMentors(controllerUtils.getFieldAsInteger(aid), name.getText(), sex.getValue());
        Stage currentStage = (Stage) confirmSearchButton.getScene().getWindow();
        currentStage.close();
    }
}
