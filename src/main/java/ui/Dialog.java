package ui;

import javafx.scene.control.Alert;

public class Dialog {

    public static void popDialog(String message, String type) {
        Alert alert;
        switch (type) {
            case "warning":
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Information");
                alert.setHeaderText("WARNING!");
                break;
            case "error":
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Information");
                alert.setHeaderText("ERROR!");
                break;
            case "information":
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Attention");
                alert.setHeaderText("ATTENTION!");
                break;
            default:
                alert = new Alert(Alert.AlertType.NONE);
        }

        alert.setContentText(message);

        alert.showAndWait();
    }
}
