package ui;

import bl.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static Label resultLabel=new Label();
    public static boolean isVisited = false;
    public static TextField path;
    public static PasswordField password;
    public static String filepath;
    public static Controller controller=new Controller();

    public void start(Stage primaryStage) throws Exception {
        ImageView mv=new ImageView();
        AnchorPane panel=new AnchorPane();
        panel.getChildren().add(mv);
        Image image=new Image(this.getClass().getResource("/ui/singal.jpg").toExternalForm(),true);
        mv.setImage(image);
        mv.setOpacity(0.2);
        mv.setLayoutX(65);
        panel.setMaxSize(BoundarySize.WIDTH,BoundarySize.HEIGHT);
        panel.setMaxSize(BoundarySize.WIDTH,BoundarySize.HEIGHT);
        panel.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));

        VBox vbox1=new VBox();
        panel.getChildren().add(vbox1);

        HBox hbox1=new HBox();
        HBox hbox2=new HBox();
        HBox hbox3=new HBox();
        HBox hbox4=new HBox();
        HBox hbox5=new HBox();

        vbox1.getChildren().add(hbox1);
        vbox1.getChildren().add(hbox4);
        vbox1.getChildren().add(hbox2);
        vbox1.getChildren().add(hbox5);
        vbox1.getChildren().add(hbox3);
        //应该是 一列两行的意思

        Label num=new Label("");
        hbox4.getChildren().add(num);
        Label numm=new Label("");
        hbox5.getChildren().add(numm);
        //手动换行

        Label pathlabel=new Label("文件路径：");
         path=new TextField();
        path.setEditable(false);
        path.setPromptText("");
        path.setOpacity(0.7);
        Button setpath=new Button("选择路径");
        hbox1.getChildren().add(pathlabel);
        hbox1.getChildren().add(path);
        hbox1.getChildren().add(setpath);


        Label password=new Label("密码:         ");
        this.password=new PasswordField();

        this.password.setPromptText("在此输入密码");
        this.password.setOpacity(0.7);
        hbox2.getChildren().add(password);
        hbox2.getChildren().add(this.password);



        Label setfield=new Label();
        setfield.setText("欢迎使用：");
        Button setxlsx=new Button();
        setxlsx.setText("点此生成名单");
        setxlsx.setOpacity(1);
        Button deliver=new Button();
        deliver.setText("点此发送名单");
        deliver.setOpacity(1);

        hbox3.getChildren().add(setfield);
        hbox3.getChildren().add(setxlsx);
        hbox3.getChildren().add(deliver);


        hbox1.setPrefSize(400,30);
        hbox1.setMinSize(400,30);
        vbox1.setLayoutX(20);
        vbox1.setLayoutY(80);




        resultLabel.setMinWidth(200);
        resultLabel.setMinHeight(30);
        resultLabel.setText("在此查看状态");

        hbox3.getChildren().add(resultLabel);

        setpath.setOnMouseClicked(new SetPath());
        setxlsx.setOnMouseClicked(new SetExcel());
        deliver.setOnMouseClicked(new Deliver());

        Scene scene=new Scene(panel, BoundarySize.WIDTH, BoundarySize.HEIGHT);

        primaryStage.setTitle("Dormitory Access System");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }

}



