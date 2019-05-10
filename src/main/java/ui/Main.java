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
    //    public static Label resultLabel=new Label();
//    public static boolean isVisited = false;
//    public static TextField path;
//    public static PasswordField password;
//    public static String filepath;
//    public static Controller controller=new Controller();
//
//    public void start(Stage primaryStage) throws Exception {
//    表格是否生成
    static boolean isVisited = false;
    //    文件选择框
    static TextField pathField;
    //    密码框
    static PasswordField password;
    //     阈值框
    static TextField threshold;
    //     阈值
    static String value;
    //    文件路径
    static String filepath;
    //    启动器
    static Controller controller = new Controller();

    public void start(Stage primaryStage) {
//        图片加载器
        ImageView mv = new ImageView();
//        背景板
        AnchorPane panel = new AnchorPane();
//        图片
        Image image = new Image(this.getClass().getResource("/ui/singal.jpg").toExternalForm(), true);

        mv.setImage(image);
        mv.setOpacity(0.2);
        mv.setLayoutX(65);
        panel.getChildren().add(mv);
//        panel.setMaxSize(BoundarySize.WIDTH + 1000,BoundarySize.HEIGHT + 1000);
//        panel.setMinSize(BoundarySize.WIDTH,BoundarySize.HEIGHT);
        panel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        VBox vbox = new VBox();
        panel.getChildren().add(vbox);

//        vbox.setPadding(new Insets(10, 12, 20, 40));
        HBox hbox1 = new HBox(); // 选择路径
        HBox hbox2 = new HBox(); // 输入密码
        HBox hbox3 = new HBox(); // 按钮
        HBox hbox4 = new HBox(); // 空行
        HBox hbox5 = new HBox(); // 空行
        HBox hBox6 = new HBox(); // 空行
        HBox hBox7 = new HBox(); // 输入阈值


//        选择路径
        Label pathlabel = new Label("文件路径：");
        pathField = new TextField();
        pathField.setEditable(false);
        pathField.setPromptText("");
        pathField.setOpacity(0.7);
        Button setpath = new Button("选择路径");
        hbox1.getChildren().add(pathlabel);
        hbox1.getChildren().add(pathField);
        hbox1.getChildren().add(setpath);

//        输入密码
        Label password = new Label("密码:         ");
        Main.password = new PasswordField();
        Main.password.setPromptText("在此输入密码");
        Main.password.setOpacity(0.7);
        hbox2.getChildren().add(password);
        hbox2.getChildren().add(Main.password);

//        输入阈值
        Label threshold = new Label("阈值:         ");
        Main.threshold = new TextField();
        Main.threshold.setPromptText("在此输入阈值");
        Main.threshold.setOpacity(0.7);
        hBox7.getChildren().add(threshold);
        hBox7.getChildren().add(Main.threshold);


//        按钮
        Label setfield = new Label();
        setfield.setText("欢迎使用：");
        Button setxlsx = new Button();
        setxlsx.setText("点此生成名单");
        setxlsx.setOpacity(1);
        Button deliver = new Button();
        deliver.setText("点此发送名单");
        deliver.setOpacity(1);
        hbox3.getChildren().add(setfield);
        hbox3.getChildren().add(setxlsx);
        hbox3.getChildren().add(deliver);


//        组合
        hbox4.getChildren().add(new Label(""));
        hbox5.getChildren().add(new Label(""));
        hBox6.getChildren().add(new Label(""));

        vbox.getChildren().add(hbox1);
        vbox.getChildren().add(hbox4); // 类似于调整行间距  =_=
        vbox.getChildren().add(hbox2);
        vbox.getChildren().add(hbox5);
        vbox.getChildren().add(hBox7);
        vbox.getChildren().add(hBox6);
        vbox.getChildren().add(hbox3);
//        设置边距
        vbox.setLayoutX(20);
        vbox.setLayoutY(80);


        setpath.setOnMouseClicked(new SetPath());
        setxlsx.setOnMouseClicked(new SetExcel());
        deliver.setOnMouseClicked(new Deliver());

//        窗口
        Scene scene = new Scene(panel, BoundarySize.WIDTH, BoundarySize.HEIGHT);

        primaryStage.setTitle("Dormitory Access System");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }


}



