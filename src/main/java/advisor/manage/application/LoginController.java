package advisor.manage.application;

import advisor.manage.Main;
import advisor.manage.entity.User;
import advisor.manage.sql.SqlUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.extern.java.Log;

@Log
public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField addUserName;
    @FXML
    private TextField addPassword;
    @FXML
    private TextField deleteUsername;
    private boolean isValidUser;


    @FXML
    private void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isValidUser = validateUser(username, password);
        if (isValidUser == true) {
            Main.changeView("/home_page.fxml");
            log.info("Show main page successfully.");
        } else {
            showErrorMessage("Invalid username or password!");
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        ButtonType okButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait();
    }

    public boolean validateUser(String username, String password) {
        SqlUtil.doSqlWork(mapper -> {
            User user = mapper.loginCheck(username, password);
            if (user != null) {
                log.info("User: " + user.getUserName() + " logins successfully.");
                isValidUser = true;
            } else {
                log.info("Invalid username or password.");
                isValidUser = false;
            }
        });
        return isValidUser;
    }

    @FXML
    private void handleAddUserButton() {
        String username = addUserName.getText();
        String password = addPassword.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            SqlUtil.doSqlWork(mapper -> {
                User newUser = new User(username, password);
                int result = mapper.addUser(newUser);
                if (result > 0) {
                    log.info("User: " + username + " added successfully.");
                    // You can add any additional UI updates or success messages here.
                } else {
                    log.info("Failed to add user: " + username);
                    // You can add any error handling or UI updates here.
                }
            });
        } else {
            showErrorMessage("Please enter both username and password.");
        }
    }

    @FXML
    private void handleDeleteUserButton() {
        String username = deleteUsername.getText();

        if (!username.isEmpty()) {
            SqlUtil.doSqlWork(mapper -> {
                int result = mapper.deleteUser(username);
                if (result > 0) {
                    log.info("User: " + username + " deleted successfully.");
                    // You can add any additional UI updates or success messages here.
                } else {
                    log.info("Failed to delete user: " + username);
                    // You can add any error handling or UI updates here.
                }
            });
        } else {
            showErrorMessage("Please enter the username to delete.");
        }
    }
}






