package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ReCheck {
    Stage window;

    //    表格是否生成

    public void start(Stage primaryStage, boolean isOutStrategy, Integer day,
                      Integer hour, Integer minute, LocalDate begin, LocalDate end,
                      Integer theshreldDay,
                      Integer theshreldHour, Integer theshreldMin){
        window = new Stage();

//        背景板
        AnchorPane panel=new AnchorPane();
//        图片

//        panel.setMaxSize(BoundarySize.WIDTH + 1000,BoundarySize.HEIGHT + 1000);
//        panel.setMinSize(BoundarySize.WIDTH,BoundarySize.HEIGHT);

        VBox vBox = new VBox();
        panel.getChildren().add(vBox);
        Label label = new Label("请核对筛选策略，确认无误后点击确定按钮，"+"\n"+"有误请点击重置按钮");
        Label label1 = new Label("筛选策略:");
        String ranges = begin.toString()+"至"+end.toString()+"期间";
        String scale = isOutStrategy ? "至多离开寝室" : "至多留在寝室";
        String timeLength = day.toString()+"天"+hour.toString()+"小时"+minute.toString()+"分钟";
        Label label2 = new Label("筛选从"+ranges);
        Label label3 = new Label(scale+timeLength+"的学生");
        String thresheld = theshreldDay.toString()+"天"+theshreldHour.toString()+"小时"+theshreldMin.toString()+"分钟";
        Label label4 = new Label("阈值为"+thresheld);

        HBox hBox = new HBox();
        Button yesButton = new Button("确认");
        Button noButton = new Button("重置");
        yesButton.setOnAction(event -> {
            XlsxSetBox setBox = new XlsxSetBox();
            setBox.start(new Stage(),isOutStrategy,day,hour,minute,begin,end,theshreldDay,theshreldHour,theshreldMin);
            window.close();
        });
        noButton.setOnAction(event -> {
            Filterate filterate = new Filterate();
            filterate.start(new Stage());
            window.close();
        });
        hBox.getChildren().addAll(yesButton,noButton);
        hBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label,label1,label2,label3,label4,new Label(""),hBox);

        vBox.setAlignment(Pos.CENTER);
        Scene scene=new Scene(panel, 320, 190);
        window.setTitle("Dormitory Access System");
        window.setScene(scene);
        window.show();
        window.setResizable(false);
    }

}
