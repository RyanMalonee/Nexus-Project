package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    // @FXML
    // private TextField taskPriority;

    @FXML
    private TextField taskAssignee;

    @FXML
    private Label errorLabel;

    private ProjectManagerFacade facade;

    @FXML
    void onCreateTaskClicked(MouseEvent event) throws IOException {
        boolean invalidUser = false;

        String name = taskName.getText();
        String description = taskDescription.getText();
        // int priority = Integer.parseInt(taskPriority.getText());
        String assignee = this.taskAssignee.getText();

        ProjectManagerFacade facade = ProjectManagerFacade.getInstance();

        Project currentProject = facade.getProject();
        User assigneeUser = facade.getUserFromProject(assignee, currentProject);

        if (assigneeUser == null) {
            invalidUser = true;
            errorLabel = new Label("The user you entered is not a member of this project");
            return;
        }

        Task task = new Task(name, description, 1, assigneeUser);
        facade.addTask(currentProject, 0, task);

        App.setRoot("projectinfo");
    }

    @FXML
    void logOut(MouseEvent event) throws IOException {
        facade.logout();
        App.setRoot("home");
    }

    @FXML
    void initialize() {
        facade = ProjectManagerFacade.getInstance();
    }
}