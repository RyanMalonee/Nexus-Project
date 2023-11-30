package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import library.App;

public class HomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void onLoginClicked(MouseEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    void onSignupClicked(MouseEvent event) throws IOException {
        App.setRoot("signup");
    }

    @FXML
    void initialize() {
    }

}
