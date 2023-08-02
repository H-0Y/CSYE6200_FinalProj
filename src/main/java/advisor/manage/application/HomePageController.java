package advisor.manage.application;

import advisor.manage.Main;
import javafx.fxml.FXML;

public class HomePageController {
    @FXML
    public void ShowStudentListButton() {
        StudentPageController studentPageController = (StudentPageController)Main.changeView("/student_page.fxml");
        studentPageController.showStudent();
    }

    public void ShowAdvisorListButton() {
        AdvisorPageController advisorPageController = (AdvisorPageController)Main.changeView("/advisor_page.fxml");
        advisorPageController.showAdvisor();
    }

    public void ShowAddUserButton() {
        Main.changeView("/add_user.fxml");
    }

    @FXML
    private void returnToLoginButton() {
        Main.changeView("/login.fxml");
    }
}
