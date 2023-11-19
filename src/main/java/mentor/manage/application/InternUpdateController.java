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
public class InternUpdateController {
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
    private static Intern curIntern;
    @FXML
    private Button confirmUpdateButton;

    private ControllerUtils controllerUtils = new ControllerUtils();

    public static void setCurIntern(Intern curIntern) {
        InternUpdateController.curIntern = curIntern;
    }


    @FXML
    public void initialize(){
        sex.getItems().clear();
        sex.getItems().addAll("Male", "Female");
        SqlUtil.doSqlWork(mapper ->{
            List<Mentor> mentorList = mapper.getMentorList();
            mentor.getItems().addAll(mentorList);
        });

        sid.setText(String.valueOf(curIntern.getSid()));
        name.setText(curIntern.getName());
        sex.setValue(curIntern.getSex());
        grade.setText(String.valueOf(curIntern.getGrade()));
        mentor.setValue(curIntern.getMentor());
    }

    @FXML
    public void confirmUpdateInternButton() {
        //create a new intern
        Intern intern = new Intern(controllerUtils.getFieldAsInteger(sid), name.getText(), sex.getValue(), controllerUtils.getFieldAsInteger(grade),mentor.getValue());
        log.info("update a intern info: " + intern.getSid());
        //update sql
        try {
            SqlUtil.doSqlWork(mapper -> {
                mapper.updateIntern(intern);
                log.info("update a intern info: " + intern);
                Mentor mentorByInternId = mapper.getMentorByInternId(intern.getSid());
                if (mentorByInternId != null && mentorByInternId.getAid() == intern.getMentor().getAid()) {
                    return;
                }
                if(mentorByInternId == null && intern.getMentor() != null){
                    int i = mapper.addMentorship(intern.getSid(), intern.getMentor().getAid());
                    if(i>0) log.info("add a mentor info: " + intern.getSid());
                    else log.info("Failure to add a mentor info: " + intern.getSid());
                    return;
                }
                if (intern.getMentor() == null) {
                    int i =  mapper.deleteMentorshipByInternId(intern.getSid());
                    if(i>0) log.info("delete a mentor info: " + intern.getSid());
                    else log.info("Failure to delete a mentor info: " + intern.getSid());
                    return;
                }
                int  i = mapper.updateMentorship(intern.getSid(), intern.getMentor().getAid());
                if(i>0) log.info("update a mentorship info: " + intern.getSid());
                else log.info("Failure to update a mentorship info: " + intern.getSid());
            });

        } catch (Exception e) {
            System.out.println("Failure, please check and try again.");
        }
        InternPageController internPageController = (InternPageController) Main.changeView("/front-end/intern_page.fxml");
        internPageController.showIntern();
        Stage currentStage = (Stage) confirmUpdateButton.getScene().getWindow();
        currentStage.close();
    }
}

