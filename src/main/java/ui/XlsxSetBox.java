package ui;

import bl.Controller;
import java.sql.Timestamp;

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
import util.logUtil.LogOperation;
import util.logUtil.RecordOpe;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

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
            LogOperation ope = new RecordOpe();
            try {
                ope.createInsertionRecord(Operator.whitesheet.getText(), Operator.relatesheet.getText(), Operator.concernsheet.getText(), text.getText());
                Controller controller = new Controller();
                controller.setWhiteListPath(Operator.whitesheet.getText());
                controller.setTutorMapList(Operator.relatesheet.getText());
                controller.setBlackListPath(Operator.concernsheet.getText());
                controller.setOutputExcelPath(text.getText());
                Timestamp time1 = string2Time(begin.toString());
                Timestamp time2 = string2Time(end.toString());
                TimePair pair = new TimePair(time1,time2);
//                System.out.println(pair.getDuration());
                LocalDateTime start = LocalDateTime.of(2000, 1, 1, 0, 0);
                LocalDateTime done = LocalDateTime.of(2000, 1, day+1, hour, minute);
                Duration result = Duration.between(start, done);

                TimeRequirement requirement = new TimeRequirement(pair,pair.getDuration(),result,isOutStrategy);
                controller.generateStudentList(requirement);
            }
            catch (ParseException e){
                ope.createExceptionRecord("ParseException");
                System.out.println(e);
            }
        });
        Scene scene=new Scene(panel, 380, 180);
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
        System.out.println(dateTime);
        return dateTime;
    }
}
