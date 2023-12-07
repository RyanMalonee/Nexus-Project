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
    void onCreateColumnClicked(MouseEvent event) throws IOException {
        String name = columnName.getText();
        if (name.isEmpty()) {
            return;
        }
        ProjectManagerFacade facade = ProjectManagerFacade.getInstance();
        facade.addColumn(name);
        App.setRoot("projectInfo");
    }
}
