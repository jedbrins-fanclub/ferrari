module dk.eamv.ferrari {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;

    opens dk.eamv.ferrari to javafx.fxml;
    exports dk.eamv.ferrari;
}