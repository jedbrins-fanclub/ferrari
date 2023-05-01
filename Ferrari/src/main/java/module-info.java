module dk.eamv.ferrari {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens dk.eamv.ferrari to javafx.fxml;
    exports dk.eamv.ferrari;
}