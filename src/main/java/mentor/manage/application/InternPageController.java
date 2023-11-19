package mentor.manage.application;

import mentor.manage.Main;
import mentor.manage.entity.Mentor;
import mentor.manage.entity.Intern;
import mentor.manage.sql.SqlUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lombok.extern.java.Log;

import java.util.List;

@Log
public class InternPageController {
    @FXML
    private TableView<Intern> internTableView;
    @FXML
    private TableColumn<Intern, Integer> sid;
    @FXML
    private TableColumn<Intern, String> name;
    @FXML
    private TableColumn<Intern, String> sex;
    @FXML
    private TableColumn<Intern, Integer> grade;
    @FXML
    private TableColumn<Intern, String> mentor;

    @FXML
    private void handleInternListButton() {
        showIntern();
    }
    @FXML
    public void handleAddInternButton() {
        Main.addView("/front-end/add_intern.fxml");
    }

    @FXML
    public void handleDeleteInternButton() {
        Intern intern = internTableView.getSelectionModel().getSelectedItem();
        if(intern != null){
            SqlUtil.doSqlWork(mapper -> {
                int i = mapper.deleteInternBySid(intern.getSid());
                showIntern();
                if(i>0) {
                    log.info("delete a intern info of sid: "+ intern.getSid());
                }
                else log.info("Failure, please try again");
                i = mapper.deleteMentorshipByInternId(intern.getSid());
                if(i>0) {
                    log.info("delete a mentorship info of sid: "+ intern.getSid());
                }
                else log.info("Failure, please try again");
            });
        }
    }

    @FXML
    private void handleUpdateInternButton() {
        //get current selected intern
        Intern intern = internTableView.getSelectionModel().getSelectedItem();
        if(intern != null){
            InternUpdateController.setCurIntern(intern);
            Main.addView("/front-end/update_intern.fxml");
        }
    }

    @FXML
    private void handleSearchInternButton() {
        Main.addView("/front-end/search_intern.fxml");
    }

    @FXML
    private void returnToHomeButton() {
        Main.changeView("/front-end/home_page.fxml");
    }

    public void showIntern(){
        SqlUtil.doSqlWork(mapper -> {
            List<Intern> internList = mapper.getAllInternsWithMentors();
            for(Intern intern : internList){
                log.info(intern.toString());
            }
            showTableList(internList);
        });
    }

    public void showSearchInterns(Integer searchId, String sname, String ssex, Integer sgrade, Mentor smentor){
        SqlUtil.doSqlWork(mapper -> {
            List<Intern> internList = mapper.searchInternList(searchId, sname, ssex, sgrade, smentor == null ? null : smentor.getName());
            for(Intern intern : internList){
                log.info(intern.toString());
            }
            showTableList(internList);
        });
    }

    private void showTableList(List<Intern> internList) {
        ObservableList<Intern> observableInternList = FXCollections.observableArrayList(internList);

        Callback<TableColumn.CellDataFeatures<Intern, String>, ObservableValue<String>> mentorCellValueFactory = cellData -> {
            Mentor mentor = cellData.getValue().getMentor();
            if (mentor != null) {
                return new SimpleStringProperty(mentor.getName());
            } else {
                return new SimpleStringProperty("");
            }
        };

        sid.setCellValueFactory(new PropertyValueFactory<>("sid"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        mentor.setCellValueFactory(mentorCellValueFactory);

        internTableView.setItems(observableInternList);
    }
}

