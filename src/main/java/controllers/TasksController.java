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

public class TasksController {

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
    private Text taskDescription;

    @FXML
    private MenuButton projectPanel;

    @FXML
    private Text taskTitle;

    @FXML
    private Text priorityText;

    @FXML
    private Text statusText;

    @FXML
    private Text assigneesText;

    @FXML
    private Text commentTxt;

    @FXML
    private Text destinationText;

    private Project project;

    private ProjectList projectList;
    private ProjectManagerFacade facade;
    private static Task selectedTask;
    private static Project selectedProject;

    @FXML
    void logOut(MouseEvent event) throws IOException {
        facade.logout();
        App.setRoot("home");
    }

    public static void setTask(Task task, Project project) {
        selectedTask = task;
        selectedProject = project;
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
    void displayTaskInfo(Task task) {
        task = selectedTask;
        project = selectedProject;
        taskTitle.setText(task.getTaskName());
        taskDescription.setText(task.getTaskDescription());
        priorityText.setText(String.valueOf(task.getTaskPriority()));
        for (Column column : facade.getProject().getColumns()) {
            for(Task findTask : column.getTasks()) {
                if (task.getTaskName().equalsIgnoreCase(findTask.getTaskName())) {
                    statusText.setText(column.getName());
                    break;
                }
            }
        }
        if (task.getAssignee() != null) {

            String assigneeString = task.getAssigneeString();

            assigneesText.setText(assigneesText.getText() + assigneeString + "\n");

        }

        String commentText = "";
        for (Comment comment : task.getComments()) {
            commentText += "- " + comment.toString() + "\n";
        }
        commentTxt.setText(commentText);
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
        App.setRoot("projectInfo");
    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        App.setRoot("projectInfo");
    }

    @FXML
    void goHome(MouseEvent event) throws IOException {
        App.setRoot("projects");
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
        assert taskDescription != null
                : "fx:id=\"taskDescription\" was not injected: check your FXML file 'projectInfo.fxml'.";
        assert projectPanel != null
                : "fx:id=\"projectPanel\" was not injected: check your FXML file 'projectInfo.fxml'.";
        assert taskTitle != null : "fx:id=\"projectTitle\" was not injected: check your FXML file 'projectInfo.fxml'.";
        facade = ProjectManagerFacade.getInstance();
        projectList = ProjectList.getInstance();
        displayProjects();
        displayTaskInfo(selectedTask);

    }

    @FXML
    void onMoveTaskClicked(MouseEvent event) throws IOException {
        String destination = destinationText.getText();

        for(Column column : selectedProject.getColumns()){
            if(column.getName().equals(destination)){
                destination = column.getColumnType().toString();
            }
        }

        facade.moveTaskBetweenColumns(selectedProject, destination, selectedTask);
    }

}
