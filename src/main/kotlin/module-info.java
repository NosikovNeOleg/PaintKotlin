module com.example.paintkotlin {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens com.example.paintkotlin to javafx.fxml;
    exports com.example.paintkotlin;
}