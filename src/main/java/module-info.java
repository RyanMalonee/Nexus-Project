module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens library to javafx.fxml;

    exports library;

    opens controllers to javafx.fxml;

    exports controllers to javafx.fxml;
}
