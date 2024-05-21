module com.example.proyecto_javafx_edd {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.logging;
    requires ConexionDB;
    requires jdk.jdi;

    opens com.example.proyecto_javafx_edd to javafx.fxml;
    exports com.example.proyecto_javafx_edd;
    exports lib;
    opens lib to javafx.fxml;
}