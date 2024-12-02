module com.example.riveratumarava_comp228lab5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;
    requires java.desktop;

    opens com.example.riveratumarava_comp228lab5 to javafx.fxml;
    exports com.example.riveratumarava_comp228lab5;
}