module com.unam.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires eclipselink;
    requires jakarta.persistence;
    requires javafx.graphics;


    opens com.unam.biblioteca to javafx.fxml;
    opens com.unam.biblioteca.modelo to eclipselink, javafx.base;
    exports com.unam.biblioteca;
}