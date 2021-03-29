module Hotel {
    requires javafx.fxml;
    requires javafx.controls;
    requires org.controlsfx.controls;

    opens sample;
    opens sample.datamodel to javafx.base;
}