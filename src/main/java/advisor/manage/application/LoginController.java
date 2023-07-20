package advisor.manage.application;

import advisor.manage.entity.User;
import advisor.manage.sql.SqlUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.extern.java.Log;

@Log
public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    private boolean isValidUser;

    // 模拟校验逻辑，实际应用中应该调用真正的校验方法
    public boolean validateUser(String username, String password) {
        SqlUtil.doSqlWork(mapper -> {
            User user = mapper.loginCheck(username, password);
            if (user != null) {
                // 登录成功，记录日志，并设置 isValidUser 为 true
                log.info("User: " + user.getUserName() + " logins successfully.");
                isValidUser = true;
            } else {
                // 登录失败，记录日志，并设置 isValidUser 为 false
                log.info("Invalid username or password.");
                isValidUser = false;
            }
        });
        return isValidUser; // 返回校验结果
    }

    @FXML
    private void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // 在此处执行校验逻辑
        boolean isValidUser = validateUser(username, password);
        log.info("user name: " + username);
        log.info("password " + password);
        if (isValidUser) {
            // 登录成功，跳转到主界面或执行相应操作
            showMainScene(); // 示例：显示主界面
            log.info("User: " + username + " logins successfully.");
        } else {
            // 登录失败，显示错误提示
            showErrorMessage("Invalid username or password!");
        }
    }

    private void showMainScene() {
        // 实现跳转到主界面的逻辑
        // 例如：创建新的 Stage 或 Scene，显示主界面，关闭登录界面等
        // ...
    }

    private void showErrorMessage(String message) {
        // 创建一个错误提示框
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}




    // 编写校验逻辑
//    public boolean validateUser(String username, String password) {
//        SqlUtil.doSqlWork(mapper -> {
//            User user = mapper.loginCheck(username, password);
//            if (user != null) {
//                // 登录成功，记录日志，并设置 isValidUser 为 true
//                log.info("User: " + user.getUserName() + " logins successfully.");
//                isValidUser = true;
//            } else {
//                // 登录失败，记录日志，并设置 isValidUser 为 false
//                log.info("Invalid username or password.");
//                isValidUser = false;
//            }
//        });
//        return isValidUser; // 返回校验结果
//    }

