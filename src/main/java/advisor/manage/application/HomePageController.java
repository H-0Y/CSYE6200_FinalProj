package advisor.manage.application;

import advisor.manage.Main;
import javafx.fxml.FXML;

public class HomePageController {
    @FXML
    public void ShowStudentListButton() {
        StudentPageController studentPageController = (StudentPageController)Main.changeView("/student_page.fxml");
        studentPageController.showStudent();
    }
}
