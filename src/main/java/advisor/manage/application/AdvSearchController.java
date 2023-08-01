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
public class AdvSearchController {
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
    public void confirmSearchAdvButton() {
        AdvisorPageController advisorPageController = (AdvisorPageController) Main.changeView("/advisor_page.fxml");
        advisorPageController.showSearchAdvisors(controllerUtils.getFieldAsInteger(aid), name.getText(), sex.getValue());
        Stage currentStage = (Stage) confirmSearchButton.getScene().getWindow();
        currentStage.close();
    }
}
