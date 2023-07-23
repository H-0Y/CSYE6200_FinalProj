package advisor.manage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    private static Stage stage;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        Main.stage = primaryStage;
        primaryStage.setTitle("Student-Advisor Management System");
        changeView("/login.fxml");
        primaryStage.show();
    }

    public static Object changeView(String fxml){
        Parent root = null;
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("/application.css").toExternalForm());
            stage.setScene(scene);
            controller = loader.getController();
        } catch (IOException e) {

        }
        return controller;
    }

    public static void addView(String fxml){
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            root = loader.load();
            //stage.setScene(new Scene(root));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("/application.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
        }
        stage.show();
    }
}


