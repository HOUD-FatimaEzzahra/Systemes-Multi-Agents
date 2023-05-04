module ma.enset.tp_sm1_new {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jade;

    opens ma.enset to javafx.fxml;
    exports ma.enset;
}