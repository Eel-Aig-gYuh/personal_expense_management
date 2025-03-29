module com.ghee.personalexpensemanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires jakarta.persistence;
    requires cloudinary.core;
    requires io.github.cdimascio.dotenv.java;
    requires jbcrypt;

    opens com.ghee.personalexpensemanagement to javafx.fxml;
    opens com.ghee.services;
    exports com.ghee.personalexpensemanagement;
    exports com.ghee.services;
    exports com.ghee.pojo;
}
