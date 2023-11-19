package mentor.manage.application;

import mentor.manage.Main;
import javafx.fxml.FXML;

public class HomePageController {
    @FXML
    public void ShowInternListButton() {
        InternPageController internPageController = (InternPageController) Main.changeView("/front-end/intern_page.fxml");
        internPageController.showIntern();
    }

    @FXML
    public void ShowMentorListButton() {
        MentorPageController mentorPageController = (MentorPageController)Main.changeView("/front-end/mentor_page.fxml");
        mentorPageController.showMentor();
    }

    @FXML
    public void ShowAddUserButton() {
        Main.changeView("/front-end/add_user.fxml");
    }

    @FXML
    private void returnToLoginButton() {
        Main.changeView("/front-end/login.fxml");
    }
}
