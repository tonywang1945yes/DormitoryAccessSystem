package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Main2
public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(250);
        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(yesButton,new Label(""),noButton);
        hbox.setAlignment(Pos.CENTER);

        yesButton.setOnAction(event -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(event -> {
            answer = false;
            window.close();
        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,new Label(""),hbox);
        layout.setAlignment(Pos.CENTER);

        Scene scene  =  new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
