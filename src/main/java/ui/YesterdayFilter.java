package ui;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author 233loser
 * @Date 2019/5/30 18:12
 * @Version 1.0
 **/
public class YesterdayFilter {
    Stage window;

    public void start(Stage primaryStage) {
        window = primaryStage;
//
        AnchorPane panel = new AnchorPane();

        panel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        VBox vbox = new VBox();
        panel.getChildren().add(vbox);

        HBox hbox0 = new HBox();
        HBox hbox00 = new HBox();
//        vbox.setPadding(new Insets(10, 12, 20, 40));
        HBox hbox1 = new HBox(); // 选择起始时间
        HBox hbox2 = new HBox(); // 选择终止时间
        HBox hbox3 = new HBox(); // 按钮
        HBox hBox6 = new HBox(); // 空行
        HBox hBox7 = new HBox(); // 输入阈值
        HBox hbox8 = new HBox();
        HBox hbox9 = new HBox();

        Label label1 = new Label("         从昨日：");
        ChoiceBox<Integer> choiceBoxHour = new ChoiceBox();
        choiceBoxHour.setItems(FXCollections.observableArrayList(getTimeList(11,11)));
        choiceBoxHour.setValue(11);
        Label label2 = new Label("小时");
        ChoiceBox<Integer> choiceBoxMinute = new ChoiceBox();
        choiceBoxMinute.setItems(FXCollections.observableArrayList(getTimeList(0, 59)));
        choiceBoxMinute.setValue(0);
        Label label3 = new Label("分钟");
        ChoiceBox<Integer> choiceBoxSecond = new ChoiceBox();
        choiceBoxSecond.setValue(0);
        choiceBoxSecond.setItems(FXCollections.observableArrayList(getTimeList(0, 59)));
        Label label4 = new Label("秒");
        hbox00.getChildren().addAll(label1, choiceBoxHour, label2, choiceBoxMinute, label3, choiceBoxSecond, label4);

//        System.out.println(button2.selectedProperty().getValue());
//        System.out.println(button1.selectedProperty().getValue());

        Label labelx = new Label("         至今日： ");
        ChoiceBox<Integer> choiceBox4Hour = new ChoiceBox();
        choiceBox4Hour.setValue(07);
        choiceBox4Hour.setItems(FXCollections.observableArrayList(getTimeList(0, 7)));
        Label label4thresholdDate = new Label("小时");
        ChoiceBox<Integer> choiceBox4Minute = new ChoiceBox();
        choiceBox4Minute.setValue(0);
        choiceBox4Minute.setItems(FXCollections.observableArrayList(getTimeList(0, 23)));
        Label label4thresholdHour = new Label("分钟");
        ChoiceBox<Integer> choiceBox4Second = new ChoiceBox();
        choiceBox4Second.setValue(0);
        choiceBox4Second.setItems(FXCollections.observableArrayList(getTimeList(0, 59)));
        Label label4thresholdMin = new Label("秒");
        hBox7.getChildren().addAll(labelx, choiceBox4Hour, label4thresholdDate, choiceBox4Minute, label4thresholdHour, choiceBox4Second, label4thresholdMin);


//        按钮

        Button setxlsx = new Button();
        setxlsx.setText("点此确认生成筛选条件");
        setxlsx.setOpacity(1);
        hbox3.getChildren().add(setxlsx);
        hbox3.setAlignment(Pos.CENTER);

        setxlsx.setOnAction(event -> {
            XlsxSetBox setBox = new XlsxSetBox();

            LocalDate today = LocalDate.now();
            LocalTime todayTime = LocalTime.of(choiceBox4Hour.getValue(),choiceBox4Minute.getValue(),choiceBox4Second.getValue());
            LocalDateTime todayActual = LocalDateTime.of(today,todayTime);

            LocalDate yesterday = today.minusDays(1);
            LocalTime yesterdayTime = LocalTime.of(choiceBoxHour.getValue(), choiceBoxMinute.getValue(),choiceBoxSecond.getValue());
            LocalDateTime yesterdayActual = LocalDateTime.of(yesterday,yesterdayTime);



//            System.out.println(today);
            // TODO

        });

//        组合

        hBox6.getChildren().add(new Label(""));
        hbox8.getChildren().add(new Label(""));
        hbox9.getChildren().add(new Label(""));

        vbox.getChildren().add(hbox0);
        vbox.getChildren().add(hbox8);
        vbox.getChildren().add(hbox00);
        vbox.getChildren().add(hbox9);
        vbox.getChildren().add(hbox1);
        vbox.getChildren().add(hbox2);
        vbox.getChildren().add(hBox7);
        vbox.getChildren().add(hBox6);
        vbox.getChildren().add(hbox3);
//        设置边距
        vbox.setAlignment(Pos.CENTER);

//
//        setpath.setOnMouseClicked(new Setpath());
//        setxlsx.setOnMouseClicked(new SetExcel());
//        deliver.setOnMouseClicked(new Deliver());

//        窗口
        Scene scene = new Scene(panel, 400, 200);

        window.setTitle("Dormitory Access System");
        window.setOnCloseRequest(event -> {
            window.close();
            System.exit(0);

        });
        window.setScene(scene);
        window.show();
        window.setResizable(false);
    }

    private List<Integer> getTimeList(int start, int end) {
        List<Integer> timeList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            timeList.add(i);
        }
        return timeList;
    }
}
