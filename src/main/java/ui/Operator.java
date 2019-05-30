package ui;

import bl.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.log.AppLog;
import util.log.LogImpl;

public class Operator {

    Stage window;

    AppLog log;
    //    表格是否生成
    static Controller controller = new Controller();
    //    文件选择框
    static TextField whitesheet;
    //    文本选择框
    static TextField concernsheet;
    //     文本选择框
    static TextField relatesheet;
    //节假日配置名单
    static TextField holidaySheet;
    //     阈值
    static String value;
    //    文件路径
    static String filepath;
    //    启动器


    public void start(Stage primaryStage) {

        log = LogImpl.getInstance();

        window = primaryStage;
//        图片加载器
        ImageView mv = new ImageView();
//        背景板
        AnchorPane panel = new AnchorPane();
//        图片
        Image image = new Image(this.getClass().getResource("/ui/singal.jpg").toExternalForm(), true);

        mv.setImage(image);
        mv.setOpacity(0.2);
        mv.setLayoutX(85);
        panel.getChildren().add(mv);
//        panel.setMaxSize(BoundarySize.WIDTH + 1000,BoundarySize.HEIGHT + 1000);
//        panel.setMinSize(BoundarySize.WIDTH,BoundarySize.HEIGHT);
        panel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        VBox vbox = new VBox();
        panel.getChildren().add(vbox);

//        vbox.setPadding(new Insets(10, 12, 20, 40));
        HBox hbox1 = new HBox(); // 选择白名单
        HBox hbox2 = new HBox(); // 选择关注对象
        HBox hbox3 = new HBox(); // 选择筛选条件或者生成文件
        HBox hbox4 = new HBox(); // 空行
        HBox hbox5 = new HBox(); // 空行
        HBox hBox6 = new HBox(); // 空行
        HBox hBox7 = new HBox(); // 选择师生对应关系
        HBox hbox8 = new HBox();
        HBox hBox9 = new HBox();
        HBox hBox10 = new HBox();


//        选择白名单
        Label pathlabel = new Label("选择白名单 :  ");
        whitesheet = new TextField();
        whitesheet.setMinWidth(20);
        whitesheet.setEditable(false);
        whitesheet.setPromptText("在此选择文件");
        whitesheet.setOpacity(0.7);
        whitesheet.setText(log.readPath(0));
        Button setpath = new Button("选择路径");
        hbox1.getChildren().add(pathlabel);
        hbox1.getChildren().add(whitesheet);
        hbox1.getChildren().add(setpath);
        setpath.setOnAction(event -> {
            FileChoose filechoose = new FileChoose();
            filechoose.getFilePath(new Stage());
            whitesheet.setText(filechoose.path);
        });

//        输入密码
        Label endlabel = new Label("选择关注名单:");
        concernsheet = new TextField();
        concernsheet.setEditable(false);
        concernsheet.setPromptText("在此选择文件");
        concernsheet.setOpacity(0.7);
        concernsheet.setText(log.readPath(2));
        Button concern = new Button("选择路径");
        hbox2.getChildren().add(endlabel);
        hbox2.getChildren().add(concernsheet);
        hbox2.getChildren().add(concern);
        concern.setOnAction(event -> {
            FileChoose filechoose = new FileChoose();
            filechoose.getFilePath(new Stage());
            concernsheet.setText(filechoose.path);
        });

//        输入阈值
        Label threshold = new Label("师生对应名单:");
        Operator.relatesheet = new TextField();
        Operator.relatesheet.setPromptText("在此选择文件");
        Operator.relatesheet.setEditable(false);
        Operator.relatesheet.setOpacity(0.7);
        Operator.relatesheet.setText(log.readPath(1));
        Button relate = new Button("选择路径");
        hBox7.getChildren().add(threshold);
        hBox7.getChildren().add(Operator.relatesheet);
        hBox7.getChildren().add(relate);
        relate.setOnAction(event -> {
            FileChoose filechoose = new FileChoose();
            filechoose.getFilePath(new Stage());
            Operator.relatesheet.setText(filechoose.path);
        });

        Label festivalSheet = new Label("节假日名单:   ");
        Operator.holidaySheet = new TextField();
        Operator.holidaySheet.setPromptText("在此选择文件");
        Operator.holidaySheet.setEditable(false);
        Operator.holidaySheet.setOpacity(0.7);
        Operator.holidaySheet.setText(log.readPath(3));
        Button fest = new Button("选择路径");
        hBox10.getChildren().add(festivalSheet);
        hBox10.getChildren().add(holidaySheet);
        hBox10.getChildren().add(fest);
        fest.setOnAction(event -> {
            FileChoose filechoose = new FileChoose();
            filechoose.getFilePath(new Stage());
            Operator.holidaySheet.setText(filechoose.path);
        });

//        按钮
        Label setfield = new Label();
        setfield.setText("欢迎使用：   ");
        Button filter = new Button();
        filter.setText("选择筛选条件");
        filter.setOpacity(1);


        hbox3.getChildren().add(setfield);
        hbox3.getChildren().add(filter);


        Label text = new Label();
        text.setText("                    ");
        Button deliver = new Button();
        deliver.setText("点此发送名单至各辅导员邮箱");
        deliver.setOpacity(1);
        deliver.setDisable(true);
        hBox9.getChildren().addAll(text, deliver);

        deliver.setOnAction(event -> {
            MailSend sender = new MailSend();
            sender.display(new Stage());
            window.close();
        });

//        组合
        hbox4.getChildren().add(new Label(""));
        hbox5.getChildren().add(new Label(""));
        hBox6.getChildren().add(new Label(""));
        hbox8.getChildren().add(new Label(""));


        vbox.getChildren().add(hbox1);
        vbox.getChildren().add(hbox4); // 类似于调整行间距  =_=
        vbox.getChildren().add(hbox2);
        vbox.getChildren().add(hbox5);
        vbox.getChildren().add(hBox7);
        vbox.getChildren().add(hBox6);
        vbox.getChildren().add(hBox10);
        vbox.getChildren().add(new Label(""));
        vbox.getChildren().add(hbox3);
        vbox.getChildren().add(hbox8);
        vbox.getChildren().add(hBox9);
//        设置边距
        vbox.setLayoutX(20);
        vbox.setLayoutY(40);

//
//        setpath.setOnMouseClicked(new Setpath());
//        setxlsx.setOnMouseClicked(new SetExcel());
//        deliver.setOnMouseClicked(new Deliver());

        filter.setOnAction(event -> {
            ConditionChooser.display("模式选择","请选择筛选模式");
            filter.setDisable(true);
            deliver.setDisable(false);
        });


//        窗口
        Scene scene = new Scene(panel, 400, 350);

        window.setOnCloseRequest(event -> {
            event.consume(); //不然就算点No也会关闭，因为调用的是setOnCloseRequest
            closeProgram();
        });
        window.setTitle("Dormitory Access System");
        window.setScene(scene);
        window.show();
        window.setResizable(false);
    }

    private void closeProgram() {
        Boolean answer = ConfirmBox.display("提示", "Sure you want to exit?");
        if (answer) {
            window.close();
            System.exit(0);
        }
    }
}



