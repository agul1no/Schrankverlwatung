module com.example.acschrankverwaltung {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mariadb.jdbc;
    requires java.sql;


    opens com.acera.acschrankverwaltung to javafx.fxml;
    exports com.acera.acschrankverwaltung;
    exports com.acera.acschrankverwaltung.gui;
    opens com.acera.acschrankverwaltung.gui to javafx.fxml;
}