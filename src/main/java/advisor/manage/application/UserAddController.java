package advisor.manage.application;

import advisor.manage.Main;
import advisor.manage.entity.Advisor;
import advisor.manage.entity.User;
import advisor.manage.sql.SqlUtil;
import javafx.fxml.FXML;
import lombok.extern.java.Log;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.List;

@Log
public class UserAddController {
    @FXML
    private TextField addUserName;
    @FXML
    private TextField addPassword;
    @FXML
    private TextField deleteUsername;

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait();
    }
    @FXML
    public void handleAddUserButton() {
        String userName = addUserName.getText();
        String password = addPassword.getText();

        try {
            SqlUtil.doSqlWork(mapper -> {
                User user = new User(userName, password);
                mapper.addUser(user);
            });
            log.info("User: " + userName + " logins successfully.");
        } catch (Exception e) {
            showErrorMessage("Please enter both username and password.");
        }
    }


    @FXML
    private void handleDeleteUserButton() {
        String userName = deleteUsername.getText();

        try {
            SqlUtil.doSqlWork(mapper -> {
                int deletedRows = mapper.deleteUser(userName);
                if (deletedRows > 0) {
                    log.info("User: " + userName + " deleted successfully.");

                } else {
                    log.info("Failed to delete user: " + userName);
                }
            });
        } catch (Exception e) {
            System.out.println("Failed to delete user. Please check and try again.");
        }
    }

    @FXML
    private void returnToLoginButton() {
        Main.changeView("/login.fxml");
    }
}
