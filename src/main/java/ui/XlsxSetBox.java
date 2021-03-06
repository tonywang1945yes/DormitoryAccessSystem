package ui;

import bl.strategy.LongInInspector;
import bl.strategy.LongOutInspector;
import entity.TimePair;
import entity.TimeRequirement;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.log.AppLog;
import util.log.LogImpl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

import static java.time.temporal.ChronoUnit.MINUTES;

public class XlsxSetBox {
    Stage window;

    public void start(Stage primaryStage, boolean isOutStrategy, Integer day,
                      Integer hour, Integer minute, LocalDate begin, LocalDate end,
                      Integer thresheldDay,
                      Integer thresheldHour, Integer thresheldMinute, LocalDateTime yesterday,LocalDateTime today) {
        AppLog ope = LogImpl.getInstance();
        window = primaryStage;
        AnchorPane panel = new AnchorPane();
        VBox vBox = new VBox();
        panel.getChildren().addAll(vBox);

        HBox hBox = new HBox();

        Label path = new Label("   生成名单:");
        TextField text = new TextField();
        text.setPromptText("在此选择路径");
        text.setEditable(false);
        text.setOpacity(0.7);
        text.setText(ope.readPath(4));
        Button relate = new Button("选择路径");
        hBox.getChildren().add(path);
        hBox.getChildren().add(text);
        hBox.getChildren().add(relate);
        relate.setOnAction(event -> {
            DirectoryChoose choose =new DirectoryChoose();
            choose.getFilePath(new Stage());
            text.setText(choose.path);
            System.out.println(choose.path);
        });

        Button yesButton = new Button("确认生成");
        vBox.getChildren().addAll(new Label(""), new Label(""), hBox, new Label(""), yesButton);

        vBox.setAlignment(Pos.CENTER);

        yesButton.setOnAction(event -> {

            try {
                ope.createInsertionRecord(Operator.whitesheet.getText(), Operator.relatesheet.getText(), Operator.concernsheet.getText(), Operator.holidaySheet.getText(), text.getText());


                Operator.controller.setWhiteListPath(Operator.whitesheet.getText());
                Operator.controller.setTutorMapListPath(Operator.relatesheet.getText());
                Operator.controller.setBlackListPath(Operator.concernsheet.getText());
                Operator.controller.setHolidayExcelPath(Operator.holidaySheet.getText());
                Operator.controller.setOutputExcelPath(text.getText());
//                设置节假日配置路径
                Operator.controller.setHolidayExcelPath(Operator.holidaySheet.getText());
                Operator.controller.setLongInspector(isOutStrategy ? new LongOutInspector() : new LongInInspector());
                Timestamp time1 = string2Time(begin.toString());
                Timestamp time2 = string2Time(end.toString());
                TimePair pair = new TimePair(time1, time2);
//                System.out.println(pair.getDuration());
                Duration result = Duration.of(24 * 60 * day + 60 * hour + minute, MINUTES);
                //默认使用分钟
                Duration specialReq = Duration.of(24 * 60 * thresheldDay + 60 * thresheldHour + thresheldMinute, MINUTES);

                TimePair pair1 = new TimePair(Timestamp.valueOf(yesterday),Timestamp.valueOf(today));

                TimeRequirement requirement = new TimeRequirement(pair, result, specialReq, isOutStrategy);
                GuavaWaiting waiting = new GuavaWaiting();
                waiting.execute(requirement,pair1);
                window.close();
            } catch (Exception e) {
                ope.createExceptionRecord("ParseException");
                e.printStackTrace();
//                System.out.println(e);
            }
        });
        Scene scene = new Scene(panel, 380, 180);
        window.setTitle("Dormitory Access System");
        window.setScene(scene);
        window.show();
        window.setResizable(false);
    }

    public final Timestamp string2Time(String dateString)
            throws java.text.ParseException {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);//设定格式
        //dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);
        dateFormat.setLenient(false);
        java.util.Date timeDate = dateFormat.parse(dateString);//util类型
        java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());//Timestamp类型,timeDate.getTime()返回一个long型
//        System.out.println(dateTime);
        return dateTime;
    }
}
