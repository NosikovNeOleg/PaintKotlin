module com.example.paintkotlin {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires com.google.gson;
    requires kotlinx.serialization.core;

    opens com.example.paintkotlin to javafx.fxml,javafx.graphics;
    exports com.example.paintkotlin;
}