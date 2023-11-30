package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import library.App;
import model.*;

public class SignupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailTxt;

    @FXML
    private TextField firstNameTxt;

    @FXML
    private TextField lastNameTxt;

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordTxt;

    @FXML
    private TextField userNameTxt;

    @FXML
    void onLoginClicked(MouseEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    void signUpUser(MouseEvent event) throws IOException {
        ProjectManagerFacade facade = new ProjectManagerFacade();
        UserList users = UserList.getInstance();
        facade.signup(firstNameTxt.getText(), lastNameTxt.getText(), userNameTxt.getText(), emailTxt.getText(),
                passwordTxt.getText(), 0, 0);
        facade.login(userNameTxt.getText(), passwordTxt.getText());
        users.saveUsers();
        App.setRoot("projects");
    }

    @FXML
    void initialize() {
        assert emailTxt != null : "fx:id=\"emailTxt\" was not injected: check your FXML file 'signup.fxml'.";
        assert firstNameTxt != null : "fx:id=\"firstNameTxt\" was not injected: check your FXML file 'signup.fxml'.";
        assert lastNameTxt != null : "fx:id=\"lastNameTxt\" was not injected: check your FXML file 'signup.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'signup.fxml'.";
        assert passwordTxt != null : "fx:id=\"passwordTxt\" was not injected: check your FXML file 'signup.fxml'.";
        assert userNameTxt != null : "fx:id=\"userNameTxt\" was not injected: check your FXML file 'signup.fxml'.";
    }

}
