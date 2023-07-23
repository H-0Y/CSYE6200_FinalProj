package advisor.manage.utils;

import javafx.scene.control.TextField;

public class ControllerUtils {
    public Integer getFieldAsInteger(TextField field) {
        if ("".equals(field.getText())) {
            return null;
        }
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
