module com.cgp.ui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.cgp.ui to javafx.fxml;
    exports com.cgp.ui;
}