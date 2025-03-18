module com.ghee.personalexpensemanagement {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ghee.personalexpensemanagement to javafx.fxml;
    exports com.ghee.personalexpensemanagement;
}
