package ui;

import bl.EmailSender;
import bl.StudentListGenerator;
import entity.Student;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.BoundarySize;

public class Main extends Application {
    public static Button setexcel;
    public static Button deliver;
    public static Label resultLabel=new Label();
    public static StudentListGenerator generator;
    public static EmailSender sender;

    public void start(Stage primaryStage) throws Exception {

        AnchorPane panel=new AnchorPane();

        panel.setMaxSize(BoundarySize.WIDTH,BoundarySize.HEIGHT);
        panel.setMaxSize(BoundarySize.WIDTH,BoundarySize.HEIGHT);

        VBox vbox1=new VBox();
        panel.getChildren().add(vbox1);

        HBox hbox1=new HBox();
        HBox hbox2=new HBox();
        vbox1.getChildren().add(hbox1);
        vbox1.getChildren().add(hbox2);
        //应该是 一列两行的意思
        setexcel=new Button("生成失踪人口统计表");
        setexcel.setVisible(true);//

        hbox1.getChildren().add(setexcel);

        deliver=new Button("发送给各院系辅导员");
        deliver.setVisible(false);//

        hbox1.getChildren().add(deliver);
        hbox1.setPrefSize(400,30);
        hbox1.setMinSize(400,30);
        vbox1.setLayoutX(20);
        vbox1.setLayoutY(100);

        resultLabel.setMinWidth(200);
        resultLabel.setMinHeight(30);
        resultLabel.setText("请稍后");

        hbox2.getChildren().add(resultLabel);


        setexcel.setOnMouseClicked(new SetExcel());
        deliver.setOnMouseClicked(new Deliver());

        Scene scene=new Scene(panel, BoundarySize.WIDTH, BoundarySize.HEIGHT);

        primaryStage.setTitle("javafx 实例");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }

}



