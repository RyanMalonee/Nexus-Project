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

public class NewColumnController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField columnName;

    @FXML
    private Label errorLabel;

    private ProjectManagerFacade facade;

    @FXML
    void onCreateColumnClicked(MouseEvent event) throws IOException {
        String name = columnName.getText();
        if (name.isEmpty()) {
            errorLabel = new Label("Please fill in all field with valid values.");
            return;
        }
        facade.addColumn(name);
        ProjectList.getInstance().saveProjects();
        App.setRoot("projectInfo");
    }

    @FXML
    void logOut(MouseEvent event) throws IOException {
        facade.logout();
        App.setRoot("home");
    }

    @FXML
    void goHome(MouseEvent event) throws IOException {
        App.setRoot("projects");
    }

    @FXML
    void initialize() {
        facade = ProjectManagerFacade.getInstance();
    }
}
