package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LackRemindBox {

    public static void display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //
        window.setTitle(title);
        window.setMinWidth(270);
        window.setMinHeight(170);
        Label label = new Label();
        label.setText(message);
        Button yesButton = new Button("确认");
        yesButton.setOnAction(event -> {
            window.close();
        });
        VBox layout = new VBox(10);
//        HBox box = new HBox();
//        layout.getChildren().add(box);
        layout.getChildren().addAll(label, yesButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);

        window.show();
    }
}
