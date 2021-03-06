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
import java.time.LocalDateTime;

public class ReCheck {
    Stage window;

    //    表格是否生成

    public void start(Stage primaryStage, boolean isOutStrategy, Integer day,
                      Integer hour, Integer minute, LocalDate begin, LocalDate end,
                      Integer theshreldDay,
                      Integer theshreldHour, Integer theshreldMin,LocalDateTime yesterday,LocalDateTime today) {
        window = new Stage();

//        背景板
        AnchorPane panel = new AnchorPane();
//        图片

//        panel.setMaxSize(BoundarySize.WIDTH + 1000,BoundarySize.HEIGHT + 1000);
//        panel.setMinSize(BoundarySize.WIDTH,BoundarySize.HEIGHT);

        VBox vBox = new VBox();

        Label label = new Label("\n    请核对筛选策略，确认无误后点击确定，"  + "有误请点击重置");
        Label label1 = new Label("筛选策略:");
        String ranges = begin.toString() + "至" + end.toString() + "期间";
        String scale = isOutStrategy ? "至多离开寝室" : "至多留在寝室";
        String timeLength = day.toString() + "天" + hour.toString() + "小时" + minute.toString() + "分钟";
        Label label2 = new Label("筛选从" + ranges);
        Label label3 = new Label(scale + timeLength + "的学生");
        String thresheld = theshreldDay.toString() + "天" + theshreldHour.toString() + "小时" + theshreldMin.toString() + "分钟";
        Label label4 = new Label((isOutStrategy ? "到当前最长时间为" : "最小出进间隔为") + thresheld);
        Label label5 = new Label("以及"+yesterday.toString()+"至"+today.toString()+"\n彻夜未归同学");

        HBox hBox = new HBox(10);
        Button yesButton = new Button("确认");
        Button noButton = new Button("重置");
        yesButton.setOnAction(event -> {
            XlsxSetBox setBox = new XlsxSetBox();
            setBox.start(new Stage(), isOutStrategy, day, hour, minute, begin, end, theshreldDay, theshreldHour, theshreldMin,yesterday,today);
            window.close();
        });
        noButton.setOnAction(event -> {
            Filterate filterate = new Filterate();
            filterate.start(new Stage());
            window.close();
        });
        hBox.getChildren().addAll(yesButton, noButton);
        hBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll( label1, label2, label3, label4, label5,new Label(""), hBox);

        vBox.setLayoutX(50);
        vBox.setLayoutY(70);
        panel.getChildren().addAll(label,vBox);
        Scene scene = new Scene(panel, 400, 260);
        window.setTitle("Dormitory Access System");
        window.setScene(scene);
        window.show();
        window.setResizable(false);
    }

}
