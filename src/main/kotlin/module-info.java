module com.example.paintkotlin {

    requires kotlin.stdlib;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires kotlinx.serialization.json;

    opens com.example.paintkotlin.applications to javafx.fxml,javafx.graphics;
    opens com.example.paintkotlin.controllers to javafx.fxml,javafx.graphics;
    opens com.example.paintkotlin to javafx.fxml,javafx.graphics;
    exports com.example.paintkotlin;
}