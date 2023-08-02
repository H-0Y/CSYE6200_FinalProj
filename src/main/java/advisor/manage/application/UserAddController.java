package advisor.manage.application;

import advisor.manage.Main;
import advisor.manage.entity.User;
import advisor.manage.sql.SqlUtil;
import javafx.fxml.FXML;
import lombok.extern.java.Log;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

@Log
public class UserAddController {
    @FXML
    private TextField addUserName;
    @FXML
    private TextField addPassword;
    @FXML
    private TextField deleteUsername;

    private void showAlertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
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
            if (userName.isEmpty() || password.isEmpty()) {
                showAlertMessage("Please enter both username and password.");
            } else {
                SqlUtil.doSqlWork(mapper -> {
                    User user = new User(userName, password);
                    mapper.addUser(user);
                    showAlertMessage("User: " + userName + " is added successfully.");
                });
            }
        } catch (Exception e) {
            showAlertMessage("An error occurred while adding the user.");
        }
    }


    @FXML
    private void handleDeleteUserButton() {
        String userName = deleteUsername.getText();
        if (userName.isEmpty() ) {
            showAlertMessage("Please enter username to delete.");
        } else try {
            SqlUtil.doSqlWork(mapper -> {
                int deletedRows = mapper.deleteUser(userName);
                if (deletedRows > 0) {
                    showAlertMessage("User " + userName + " is deleted successfully.");
                } else {
                    showAlertMessage("User " + userName + " does not exist.");
                }
            });
        } catch (Exception e) {
            showAlertMessage("An error occurred while deleting the user.");
        }
    }

    @FXML
    private void returnToHomeButton() {
        Main.changeView("/home_page.fxml");
    }
}
