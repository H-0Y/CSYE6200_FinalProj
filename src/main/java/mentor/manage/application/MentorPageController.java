package mentor.manage.application;

        import mentor.manage.Main;
        import mentor.manage.entity.Mentor;
        import mentor.manage.sql.SqlUtil;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.cell.PropertyValueFactory;
        import lombok.extern.java.Log;

        import java.util.List;

@Log
public class MentorPageController {
    @FXML
    private TableView<Mentor> mentorTableView;
    @FXML
    private TableColumn<Mentor, Integer> aid;
    @FXML
    private TableColumn<Mentor, String> name;
    @FXML
    private TableColumn<Mentor, String> sex;



    @FXML
    private void returnToHomeButton() {
        Main.changeView("/front-end/home_page.fxml");
    }

    @FXML
    private void handleMentorsListButton() {
        showMentor();
    }

    @FXML
    public void handleAddMentorButton() {
        Main.addView("/front-end/add_mentor.fxml");
    }

    @FXML
    private void handleSearchMentorButton() {
        Main.addView("/front-end/search_mentor.fxml");
    }

    @FXML
    public void handleDeleteMentorButton() {
        Mentor mentor = mentorTableView.getSelectionModel().getSelectedItem();
        if(mentor != null){
            SqlUtil.doSqlWork(mapper -> {
                int i = mapper.deleteMentorByAid(mentor.getAid());
                showMentor();
                if(i>0) {
                    log.info("delete a intern info of sid: "+ mentor.getAid());
                }
                else log.info("Failure, please try again");
                i = mapper.deleteMentorshipByInternId(mentor.getAid());
                if(i>0) {
                    log.info("delete a mentorship info of sid: "+ mentor.getAid());
                }
                else log.info("Failure, please try again");
            });
        }
    }

    @FXML
    private void handleUpdateMentorButton() {
        //get current selected intern
        Mentor mentor = mentorTableView.getSelectionModel().getSelectedItem();
        if(mentor != null){
            MentorUpdateController.setCurMentor(mentor);
            Main.addView("/front-end/update_mentor.fxml");
        }
    }

    public void showMentor(){
        SqlUtil.doSqlWork(mapper -> {
            List<Mentor> mentorList = mapper.getMentorList();
            for(Mentor mentor : mentorList){
                log.info(mentor.toString());
            }
            showTableList(mentorList);
        });
    }

    public void showTableList(List<Mentor> mentorList) {
        ObservableList<Mentor> observableMentorList = FXCollections.observableArrayList(mentorList);

        aid.setCellValueFactory(new PropertyValueFactory<>("aid"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));

        mentorTableView.setItems(observableMentorList);
    }

    public void showSearchMentors(Integer searchId, String searchName, String searchSex) {
        SqlUtil.doSqlWork(mapper -> {
            List<Mentor> mentorList = mapper.searchMentorList(searchId, searchName, searchSex);
            for (Mentor mentor : mentorList) {
                log.info(mentor.toString());
            }
            showTableList(mentorList);
        });
    }
}

