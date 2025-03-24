module com.nhom4.fxpersonalexpensemanagement {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.nhom4.fxpersonalexpensemanagement to javafx.fxml;
    exports com.nhom4.fxpersonalexpensemanagement;
}
