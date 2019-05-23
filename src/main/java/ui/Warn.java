package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Warn {
    static boolean answer;


    public static boolean display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(170);
        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("确认");
//        Button noButton = new Button("No");

        yesButton.setOnAction(event -> {
            answer = true;
            window.close();
        });

//        noButton.setOnAction(event -> {
//            answer = false;
//            window.close();
//        });
        VBox layout = new VBox(10);
//        HBox box = new HBox();
//        layout.getChildren().add(box);
        layout.getChildren().addAll(label,yesButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene  =  new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
