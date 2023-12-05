package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import library.App;
import model.*;

public class ProjectInfoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TitledPane DetailPane;

    @FXML
    private HBox columnContainer;

    @FXML
    private AnchorPane commentContaner;

    @FXML
    private TitledPane commentPane;

    @FXML
    private AnchorPane detailContainer;

    @FXML
    private Text projectDescription;

    @FXML
    private MenuButton projectPanel;

    @FXML
    private Text projectTitle;

    @FXML
    private Text scrumMasterTxt;

    @FXML
    private Text teamMembersTxt;

    @FXML
    private Text detailTxt;

    @FXML
    private Text commentTxt;

    private ProjectList projectList;
    private UserList userList;
    private ProjectManagerFacade facade;

    @FXML
    void logOut(MouseEvent event) throws IOException {
        facade.logout();
        App.setRoot("home");
    }

    @FXML
    private void displayProjects() {
        ArrayList<Project> projects = projectList.getProjectsByUser(facade.getCurrentUser());
        projectPanel.getItems().clear();
        for (Project project : projects) {
            MenuItem item = new MenuItem();
            item.setText(project.getProjectName());
            projectPanel.getItems().add(item);
            item.setOnAction(event -> {
                try {
                    menuItemSelected(item.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @FXML
    void displayProjectInfo(Project project) {
        projectTitle.setText(project.getProjectName());
        projectDescription.setText(project.getProjectDescription());

        scrumMasterTxt.setText(project.getScrumMaster().getFirstName() + " " + project.getScrumMaster().getLastName());
        String teamMembers = "";
        for (User user : project.getTeamMembers()) {
            teamMembers += "- " + user.getFirstName() + " " + user.getLastName() + "\n";
        }
        teamMembersTxt.setText(teamMembers);
        detailTxt.setText(project.getSprintTime() + " " + project.getSprintUnits());

        String commentText = "";
        for (Comment comment : project.getComments()) {
            commentText += "- " + comment.toString() + "\n";
        }
        commentTxt.setText(commentText);

        columnContainer.getChildren().clear();
        for (Column column : project.getColumns()) {
            HBox hbox = new HBox();
            columnContainer.getChildren().add(hbox);

            VBox vbox = new VBox();
            hbox.getChildren().add(vbox);
            hbox.getStyleClass().add("columns");

            Label label = new Label(column.getName());
            label.getStyleClass().add("small-text");

            vbox.getChildren().add(label);

            for (Task task : column.getTasks()) {
                ScrollPane scrollPane = new ScrollPane();
                scrollPane.setPrefSize(300, 50);
                Label taskLabel = new Label(task.getTaskName() + "\n" + task.getTaskDescription());
                taskLabel.getStyleClass().add("small-text");
                scrollPane.setContent(taskLabel);
                vbox.getChildren().add(scrollPane);
            }
        }
    }

    @FXML
    void menuItemSelected(String name) throws IOException {
        Project newProject = null;
        for (Project project : projectList.getProjects()) {
            if (project.getProjectName().equalsIgnoreCase(name)) {
                newProject = project;
            }
        }
        if (newProject != null) {
            facade.setProject(newProject);
        }
        initialize();
    }

    @FXML
    void initialize() {
        assert DetailPane != null : "fx:id=\"DetailPane\" was not injected: check your FXML file 'projectInfo.fxml'.";
        assert columnContainer != null
                : "fx:id=\"columnContainer\" was not injected: check your FXML file 'projectInfo.fxml'.";
        assert commentContaner != null
                : "fx:id=\"commentContaner\" was not injected: check your FXML file 'projectInfo.fxml'.";
        assert commentPane != null : "fx:id=\"commentPane\" was not injected: check your FXML file 'projectInfo.fxml'.";
        assert detailContainer != null
                : "fx:id=\"detailContainer\" was not injected: check your FXML file 'projectInfo.fxml'.";
        assert projectDescription != null
                : "fx:id=\"projectDescription\" was not injected: check your FXML file 'projectInfo.fxml'.";
        assert projectPanel != null
                : "fx:id=\"projectPanel\" was not injected: check your FXML file 'projectInfo.fxml'.";
        assert projectTitle != null
                : "fx:id=\"projectTitle\" was not injected: check your FXML file 'projectInfo.fxml'.";
        projectList = ProjectList.getInstance();
        userList = UserList.getInstance();
        facade = ProjectManagerFacade.getInstance();
        displayProjects();
        displayProjectInfo(facade.getProject());
    }

}