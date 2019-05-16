package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class XlsxSetBox {
    Stage window;
    public void start(Stage primaryStage, boolean isOutStrategy, Integer day,
                      Integer hour, Integer minute, LocalDate begin, LocalDate end,
                      String threshold){
        window = primaryStage;
        AnchorPane panel=new AnchorPane();
        VBox vBox = new VBox();
        panel.getChildren().addAll(vBox);

        HBox hBox = new HBox();

        Label path = new Label("   生成名单:");
        TextField text = new TextField();
        text.setPromptText("在此选择路径");
        text.setEditable(false);
        text.setOpacity(0.7);
        Button relate = new Button("选择路径");
        hBox.getChildren().add(path);
        hBox.getChildren().add(text);
        hBox.getChildren().add(relate);
        relate.setOnAction(event -> {
            FileChoose filechoose =new FileChoose();
            filechoose.getFilePath();
            text.setText(filechoose.path);
        });

        Button yesButton = new Button("确认生成");
        vBox.getChildren().addAll(new Label(""),new Label(""),hBox,new Label(""),yesButton);

        vBox.setAlignment(Pos.CENTER);

        yesButton.setOnAction(event -> {

        });
        Scene scene=new Scene(panel, 380, 180);
        window.setTitle("Dormitory Access System");
        window.setScene(scene);
        window.show();
        window.setResizable(false);
    }

}
