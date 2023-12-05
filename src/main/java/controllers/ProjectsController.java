package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import library.App;
import model.*;

public class ProjectsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> projectContainer;

    @FXML
    private MenuButton projectPanel;

    private ProjectList projectList;
    private Project selectedProject;
    private UserList userList;
    private ProjectManagerFacade facade;

    @FXML
    void initialize() {
        projectList = ProjectList.getInstance();
        userList = UserList.getInstance();
        facade = ProjectManagerFacade.getInstance();
        displayProjects();
    }

    @FXML
    private void displayProjects() {
        ObservableList<String> project_List = FXCollections.observableArrayList();
        ArrayList<Project> projects = projectList.getProjectsByUser(facade.getCurrentUser());

        for (Project project : projects) {
            project_List.add(project.getProjectName() + "\n" + project.getProjectDescription());
        }
        projectContainer.setItems(project_List);

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
    void projectSelected(MouseEvent event) throws IOException {
        String selectedProjectString = projectContainer.getSelectionModel().getSelectedItem().toString();
        selectedProjectString = selectedProjectString.substring(0, selectedProjectString.indexOf("\n"));
        for (Project project : projectList.getProjects()) {
            if (project.getProjectName().equalsIgnoreCase(selectedProjectString)) {
                facade.setProject(project);
                App.setRoot("projectInfo");
            }
        }
    }

    @FXML
    void menuItemSelected(String name) throws IOException {
        for (Project project : projectList.getProjects()) {
            if (project.getProjectName().equalsIgnoreCase(name)) {
                facade.setProject(project);
                App.setRoot("projectInfo");
            }
        }
    }

    @FXML
    void logOut(MouseEvent event) throws IOException {
        facade.logout();
        App.setRoot("home");
    }
}
