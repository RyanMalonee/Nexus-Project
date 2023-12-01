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

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField usernameTxt;

    @FXML
    private TextField passwordTxt;

    @FXML
    private Label errorLabel;

    @FXML
    void onLoginClicked(MouseEvent event) throws IOException {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        ProjectManagerFacade facade = ProjectManagerFacade.getInstance();

        if (!facade.login(username, password)) {
            errorLabel.setText("Invalid username or password");
            return;
        }

        App.setRoot("projects");
    }

    @FXML
    void onSignupClicked(MouseEvent event) throws IOException {
        App.setRoot("signup");
    }

    @FXML
    void initialize() {
    }

}
