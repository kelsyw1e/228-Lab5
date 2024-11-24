module com.example.riveratumarava_comp228lab5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.riveratumarava_comp228lab5 to javafx.fxml;
    exports com.example.riveratumarava_comp228lab5;
}