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
public class AdvAddController {
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
    public void confirmAddAdvButton() {
        Advisor advisor = new Advisor(controllerUtils.getFieldAsInteger(aid), name.getText(), sex.getValue());

        try {
            SqlUtil.doSqlWork(mapper -> {
                int result = mapper.addAdvisor(advisor);
                if (result > 0) {
                    log.info("Added a new advisor: " + advisor);
                } else {
                    log.info("Failure to add a new advisor: " + advisor);
                }
            });
        } catch (Exception e) {
            System.out.println("Failure, please check and try again.");
        }

        AdvisorPageController advisorPageController = (AdvisorPageController) Main.changeView("/advisor_page.fxml");
        advisorPageController.showAdvisor();
        Stage currentStage = (Stage) confirmAddButton.getScene().getWindow();
        currentStage.close();
    }
}
