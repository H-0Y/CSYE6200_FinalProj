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
public class AdvUpdateController {
    @FXML
    private TextField aid;
    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<String> sex;
    @FXML
    private Button confirmUpdateButton;

    private static Advisor curAdvisor;
    private ControllerUtils controllerUtils = new ControllerUtils();

    public static void setCurAdvisor(Advisor curAdvisor) {
        AdvUpdateController.curAdvisor = curAdvisor;
    }

    @FXML
    public void initialize() {
        sex.getItems().clear();
        sex.getItems().addAll("Male", "Female");

        aid.setText(String.valueOf(curAdvisor.getAid()));
        name.setText(curAdvisor.getName());
        sex.setValue(curAdvisor.getSex());
    }

    @FXML
    public void confirmUpdateAdvButton() {
        // Create a new advisor
        Advisor advisor = new Advisor(controllerUtils.getFieldAsInteger(aid), name.getText(), sex.getValue());
        log.info("Update an advisor info: " + advisor.getAid());

        // Update SQL
        try {
            SqlUtil.doSqlWork(mapper -> {
                mapper.updateAdvisor(advisor);
                log.info("Update an advisor info: " + advisor);
            });
        } catch (Exception e) {
            System.out.println("Failure, please check and try again.");
        }

        AdvisorPageController advisorPageController = (AdvisorPageController) Main.changeView("/advisor_page.fxml");
        advisorPageController.showAdvisor();
        Stage currentStage = (Stage) confirmUpdateButton.getScene().getWindow();
        currentStage.close();
    }
}
