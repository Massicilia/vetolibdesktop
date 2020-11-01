module ControlsFXTest {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens org.vetolib to javafx.fxml;

    exports org.vetolib;
}