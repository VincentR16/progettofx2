module proggettofx2
{

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;
    requires org.postgresql.jdbc;

    opens com.example.proggettofx2;
    opens com.example.proggettofx2.entita;
}