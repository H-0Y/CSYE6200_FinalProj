package advisor.manage.application;

        import advisor.manage.Main;
        import advisor.manage.entity.Advisor;
        import advisor.manage.entity.Student;
        import advisor.manage.sql.SqlUtil;
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
public class AdvisorPageController {
    @FXML
    private TableView<Advisor> advisorTableView;
    @FXML
    private TableColumn<Advisor, Integer> aid;
    @FXML
    private TableColumn<Advisor, String> name;
    @FXML
    private TableColumn<Advisor, String> sex;



    @FXML
    private void returnToHomeButton() {
        Main.changeView("/home_page.fxml");
    }

    @FXML
    private void handleAdvisorsListButton() {
        showAdvisor();
    }

    @FXML
    public void handleAddAdvButton() {
        Main.addView("/add_advisor.fxml");
    }

    @FXML
    private void handleSearchAdvButton() {
        Main.addView("/search_advisor.fxml");
    }

    @FXML
    public void handleDeleteAdvButton() {
        Advisor advisor = advisorTableView.getSelectionModel().getSelectedItem();
        if(advisor != null){
            SqlUtil.doSqlWork(mapper -> {
                int i = mapper.deleteAdvisorByAid(advisor.getAid());
                showAdvisor();
                if(i>0) {
                    log.info("delete a student info of sid: "+ advisor.getAid());
                }
                else log.info("Failure, please try again");
                i = mapper.deleteAdvisedByStudentId(advisor.getAid());
                if(i>0) {
                    log.info("delete a advised info of sid: "+ advisor.getAid());
                }
                else log.info("Failure, please try again");
            });
        }
    }

    @FXML
    private void handleUpdateAdvButton() {
        //get current selected student
        Advisor advisor = advisorTableView.getSelectionModel().getSelectedItem();
        if(advisor != null){
            AdvUpdateController.setCurAdvisor(advisor);
            Main.addView("/update_advisor.fxml");
        }
    }

    public void showAdvisor(){
        SqlUtil.doSqlWork(mapper -> {
            List<Advisor> advisorList = mapper.getAdvisorList();
            for(Advisor advisor : advisorList){
                log.info(advisor.toString());
            }
            showTableList(advisorList);
        });
    }

    public void showTableList(List<Advisor> advisorList) {
        ObservableList<Advisor> observableAdvisorList = FXCollections.observableArrayList(advisorList);

        aid.setCellValueFactory(new PropertyValueFactory<>("aid"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));

        advisorTableView.setItems(observableAdvisorList);
    }

    public void showSearchAdvisors(Integer searchId, String searchName, String searchSex) {
        SqlUtil.doSqlWork(mapper -> {
            List<Advisor> advisorList = mapper.searchAdvisorList(searchId, searchName, searchSex);
            for (Advisor advisor : advisorList) {
                log.info(advisor.toString());
            }
            showTableList(advisorList);
        });
    }
}

