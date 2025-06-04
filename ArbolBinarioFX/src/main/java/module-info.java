module com.arboles.arbolbinariofx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.arboles.arbolbinariofx to javafx.fxml;
    exports com.arboles.arbolbinariofx;
}