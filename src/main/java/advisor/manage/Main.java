package advisor.manage;

import advisor.manage.application.LoginController;
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

   public static void changeView(String fxml){
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("/application.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {

        }
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



    /*public static void main(String[] args) {
        // create an instance of Operation Class
        Operation operation = new Operation();
        // capture exception
        try(Scanner scanner = new Scanner(System.in)) {
            // used for log system
            LogManager manager = LogManager.getLogManager();
            manager.readConfiguration(Resources.getResourceAsStream("logging.properties"));

            // User login: there are two users in the cloud database now.
            // one is {username: test, password: 123456}
            // another is {username: root, password: 123}

            boolean flag = true;
            while (flag) {
                try {
                    operation.login(scanner);
                    flag = false;
                } catch (Exception e) {
                    System.out.println("Failure, your username or password is incorrect.");
                }
            }

            // Main interface:
            // Level 1: First choose an operation
            while(true) {
                System.out.println("========================");
                System.out.println("1. Add info");
                System.out.println("2. Delete info by id");
                System.out.println("3. Query info by id");
                System.out.println("4. Show info");
                System.out.println("5. Modify user info");
                System.out.print("Enter a operation you want (enter any other number to quit) : ");

                int input;
                // check whether the input is legal
                try {
                    input = scanner.nextInt();
                }catch (Exception e){
                    System.out.println("Sorry, you entered an illegal character");
                    return;
                }
                scanner.nextLine();
                // Level 2: Decide the object you want to manipulate
                switch (input){
                    // ADD
                    case 1:
                        System.out.println("========================");
                        System.out.println("1. Add student info");
                        System.out.println("2. Add advisor info");
                        System.out.println("3. Add advised info");
                        System.out.println("4. Back to last level");
                        System.out.print("Enter a operation you want (enter any other number to quit) : ");

                        // check whether the input is legal
                        try {
                            input = scanner.nextInt();
                        }catch (Exception e){
                            System.out.println("Sorry, you entered an illegal character");
                            return;
                        }
                        scanner.nextLine();
                        switch (input){
                            case 1:
                                operation.addStudent(scanner);
                                break;
                            case 2:
                                operation.addAdvisor(scanner);
                                break;
                            case 3:
                                operation.addAdvised(scanner);
                                break;
                            case 4:
                                break;
                            default:
                                return;
                        }
                        break;
                    // DELETE
                    case 2:
                        System.out.println("========================");
                        System.out.println("1. Delete student info");
                        System.out.println("2. Delete advisor info");
                        System.out.println("3. Delete advised info");
                        System.out.println("4. Back to last level");
                        System.out.print("Enter a operation you want (enter any other number to quit) : ");
                        try {
                            input = scanner.nextInt();
                        }catch (Exception e){
                            System.out.println("Sorry, you entered an illegal character");
                            return;
                        }
                        scanner.nextLine();
                        switch (input){
                            case 1:
                                operation.deleteStudent(scanner);
                                break;
                            case 2:
                                operation.deleteAdvisor(scanner);
                                break;
                            case 3:
                                operation.deleteAdvised(scanner);
                                break;
                            case 4:
                                break;
                            default:
                                return;
                        }
                        break;
                    // Query
                    case 3:
                        System.out.println("========================");
                        System.out.println("1. Query student info");
                        System.out.println("2. Query advisor info");
                        System.out.println("3. Query advised info");
                        System.out.println("4. Back to last level");
                        System.out.print("Enter a operation you want (enter any other number to quit) : ");
                        try {
                            input = scanner.nextInt();
                        }catch (Exception e){
                            System.out.println("Sorry, you entered an illegal character");
                            return;
                        }
                        scanner.nextLine();
                        switch (input){
                            case 1:
                                operation.getStudentBySid(scanner);
                                break;
                            case 2:
                                operation.getAdvisorByAid(scanner);
                                break;
                            case 3:
                                operation.getAdvisedById(scanner);
                                break;
                            case 4:
                                break;
                            default:
                                return;
                        }
                        break;
                    // Show
                    case 4:
                        System.out.println("========================");
                        System.out.println("1. Show all student info");
                        System.out.println("2. Show all advisor info");
                        System.out.println("3. Show all advised info");
                        System.out.println("4. Back to last level");
                        System.out.print("Enter a operation you want (enter any other number to quit) : ");
                        try {
                            input = scanner.nextInt();
                        }catch (Exception e){
                            System.out.println("Sorry, you entered an illegal character");
                            return;
                        }
                        scanner.nextLine();
                        switch (input){
                            case 1:
                                operation.showStudent();
                                break;
                            case 2:
                                operation.showAdvisor();
                                break;
                            case 3:
                                operation.showAdvised();
                                break;
                            case 4:
                                break;
                            default:
                                return;
                        }
                        break;
                    // Modify user info
                    case 5:
                        System.out.println("========================");
                        System.out.println("1. Add a new user");
                        System.out.println("2. Delete a user");
                        System.out.println("3. Back to last level");
                        try {
                            input = scanner.nextInt();
                        }catch (Exception e){
                            System.out.println("Sorry, you entered an illegal character");
                            return;
                        }
                        scanner.nextLine();
                        switch (input) {
                            case 1:
                                operation.addUser(scanner);
                                break;
                            case 2:
                                operation.deleteUser(scanner);
                                break;
                            case 3:
                                break;
                            default:
                                return;
                        }
                        break;
                    default:
                        return;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }*/


