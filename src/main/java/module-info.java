module dk.eamv.ferrari {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires transitive javafx.graphics;
    requires org.controlsfx.controls;
    requires org.simplejavamail;
    requires org.simplejavamail.core;

    opens dk.eamv.ferrari to javafx.fxml;
    exports dk.eamv.ferrari;
}