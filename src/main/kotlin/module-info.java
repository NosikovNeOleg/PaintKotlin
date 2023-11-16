module com.example.paintkotlin {

    requires kotlin.stdlib;

    requires kotlinx.serialization.core;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.google.gson;

    opens com.example.paintkotlin to javafx.fxml,javafx.graphics;
    exports com.example.paintkotlin;
}