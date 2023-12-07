package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import library.App;
import model.*;

public class NewTaskController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField taskName;

    @FXML
    private TextField taskDescription;

    @FXML
    private MenuButton projectPanel;

    // @FXML
    // private TextField taskPriority;

    @FXML
    private TextField taskAssignee;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField taskPriority;

    private ProjectManagerFacade facade;

    private ProjectList projectList;

    @FXML
    void onCreateTaskClicked(MouseEvent event) throws IOException {

        String name = taskName.getText();
        String description = taskDescription.getText();
        int priority = Integer.parseInt(taskPriority.getText());

        if (name.isEmpty() || description.isEmpty() || priority < 1 || priority > 3) {
            errorLabel = new Label("Please fill in all field with valid values.");
            return;
        }

        String assignee = this.taskAssignee.getText();

        ProjectManagerFacade facade = ProjectManagerFacade.getInstance();

        Project currentProject = facade.getProject();
        User assigneeUser = facade.getUserFromProject(assignee, currentProject);

        if (assigneeUser == null) {
            errorLabel = new Label("The user you entered is not a member of this project");
            return;
        }

        Task task = new Task(name, description, 1, assigneeUser);
        facade.addTask(currentProject, 1, task);

        projectList.saveProjects();
        App.setRoot("projectInfo");
    }

    @FXML
    void logOut(MouseEvent event) throws IOException {
        facade.logout();
        App.setRoot("home");
    }

    @FXML
    void onCancelClicked(MouseEvent event) throws IOException {
        App.setRoot("projectInfo");
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
    void goHome(MouseEvent event) throws IOException {
        App.setRoot("projects");
    }

    @FXML
    void initialize() {
        facade = ProjectManagerFacade.getInstance();
        projectList = ProjectList.getInstance();
        displayProjects();
    }
}