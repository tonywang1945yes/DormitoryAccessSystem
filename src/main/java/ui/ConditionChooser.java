package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @Description 选择筛选昨日或者详细筛选
 * @Author 233loser
 * @Date 2019/5/30 18:11
 * @Version 1.0
 **/
public class ConditionChooser {

    public static void display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(250);
        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("筛选昨日未归");
        Button noButton = new Button("筛选详细名单");

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(yesButton,new Label(""),noButton);
        hbox.setAlignment(Pos.CENTER);

        yesButton.setOnAction(event -> {
            YesterdayFilter filter = new YesterdayFilter();
            filter.start(new Stage());
            window.close();
        });

        noButton.setOnAction(event -> {
            Filterate filterate = new Filterate();
            filterate.start(new Stage());
            window.close();
        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,new Label(""),hbox);
        layout.setAlignment(Pos.CENTER);

        Scene scene  =  new Scene(layout);
        window.setScene(scene);
        window.show();

    }
}
