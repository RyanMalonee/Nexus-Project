package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import library.App;
import model.*;

public class LoginController {

    private ProjectManagerFacade facade;

    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField usernameTxt;

    @FXML
    private TextField passwordTxt;

    @FXML
    void onLoginClicked(ActionEvent event) throws IOException {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        if (!facade.login(username, password)) {
            System.out.println("Login failed");
            return;
        }

        App.setRoot("projects");
    }

    @FXML
    void onSignupClicked(ActionEvent event) throws IOException {
        App.setRoot("signup");
    }

    @FXML
    void initialize() {
    }

}
