package ui;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Filterate {
    Stage window;

    //    起始时间
    static DatePicker beginfield;
    //    终止日期
    static DatePicker endfield;
    //     阈值框
//    static TextField threshold;
    //     阈值
    static String value;
    //    文件路径
    static String filepath;
    //    启动器
//    static Controller controller=new Controller();

    public void start(Stage primaryStage) {
        window = primaryStage;
//        图片加载器
        ImageView mv = new ImageView();
//        背景板
        AnchorPane panel = new AnchorPane();
//        图片
        Image image = new Image(this.getClass().getResource("/ui/singal.jpg").toExternalForm(), true);

        mv.setImage(image);
        mv.setOpacity(0.2);
        mv.setLayoutY(80);
        mv.setLayoutX(75);
        panel.getChildren().add(mv);
//        panel.setMaxSize(BoundarySize.WIDTH + 1000,BoundarySize.HEIGHT + 1000);
//        panel.setMinSize(BoundarySize.WIDTH,BoundarySize.HEIGHT);
        panel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        VBox vbox = new VBox();
        panel.getChildren().add(vbox);

        HBox hbox0 = new HBox();
        HBox hbox00 = new HBox();
//        vbox.setPadding(new Insets(10, 12, 20, 40));
        HBox hbox1 = new HBox(); // 选择起始时间
        HBox hbox2 = new HBox(); // 选择终止时间
        HBox hbox3 = new HBox(); // 按钮
        HBox hbox4 = new HBox(); // 空行
        HBox hbox5 = new HBox(); // 空行
        HBox hBox6 = new HBox(); // 空行
        HBox hBox7 = new HBox(); // 输入阈值
        HBox hbox8 = new HBox();
        HBox hbox9 = new HBox();

        Label label = new Label("筛选策略：      ");
        ToggleGroup group = new ToggleGroup();
        RadioButton button1 = new RadioButton("查找长时间离寝");
        button1.setToggleGroup(group);
        button1.setSelected(true);
        RadioButton button2 = new RadioButton("查找长时间在寝");
        button2.setToggleGroup(group);
        hbox0.getChildren().addAll(label, button1, button2);


        Label label1 = new Label("请选择时长: ");
        ChoiceBox<Integer> choiceBoxDate = new ChoiceBox();
        choiceBoxDate.setItems(FXCollections.observableArrayList(getTimeList(0, 15)));
        choiceBoxDate.setValue(1);
        Label label2 = new Label("天 ");
        ChoiceBox<Integer> choiceBoxHour = new ChoiceBox();
        choiceBoxHour.setItems(FXCollections.observableArrayList(getTimeList(0, 23)));
        choiceBoxHour.setValue(0);
        Label label3 = new Label("小时 ");
        ChoiceBox<Integer> choiceBoxMinute = new ChoiceBox();
        choiceBoxMinute.setValue(0);
        choiceBoxMinute.setItems(FXCollections.observableArrayList(getTimeList(0, 59)));
        Label label4 = new Label("分钟");
        hbox00.getChildren().addAll(label1, choiceBoxDate, label2, choiceBoxHour, label3, choiceBoxMinute, label4);

//        System.out.println(button2.selectedProperty().getValue());
//        System.out.println(button1.selectedProperty().getValue());
//        选择路径
        Label pathlabel = new Label("起始日期：");
        beginfield = new DatePicker();
        beginfield.setPromptText("起始于该日的00:00分");
        beginfield.setOpacity(0.7);
        hbox1.getChildren().add(pathlabel);
        hbox1.getChildren().add(beginfield);

//        输入密码
        Label endlabel = new Label("结束日期：");
        endfield = new DatePicker();
        endfield.setPromptText("截止至该日的23:59分");
        endfield.setOpacity(0.7);
        hbox2.getChildren().add(endlabel);
        hbox2.getChildren().add(endfield);

//        输入阈值
        Label labelx = new Label("阈值:         ");
        ChoiceBox<Integer> choiceBox4thresholdDay = new ChoiceBox();
        choiceBox4thresholdDay.setValue(1);
        choiceBox4thresholdDay.setItems(FXCollections.observableArrayList(getTimeList(0, 15)));
        Label label4thresholdDate = new Label("天 ");
        ChoiceBox<Integer> choiceBox4thresholdHour = new ChoiceBox();
        choiceBox4thresholdHour.setValue(0);
        choiceBox4thresholdHour.setItems(FXCollections.observableArrayList(getTimeList(0, 23)));
        Label label4thresholdHour = new Label("小时 ");
        ChoiceBox<Integer> choiceBox4thresholdMin = new ChoiceBox();
        choiceBox4thresholdMin.setValue(0);
        choiceBox4thresholdMin.setItems(FXCollections.observableArrayList(getTimeList(0, 59)));
        Label label4thresholdMin = new Label("分钟");
        hBox7.getChildren().addAll(labelx, choiceBox4thresholdDay, label4thresholdDate, choiceBox4thresholdHour, label4thresholdHour, choiceBox4thresholdMin, label4thresholdMin);


//        按钮
        Label setfield = new Label();
        setfield.setText("欢迎使用：");
        Button setxlsx = new Button();
        setxlsx.setText("点此确认生成筛选条件");
        setxlsx.setOpacity(1);
        hbox3.getChildren().add(setfield);
        hbox3.getChildren().add(setxlsx);



        HBox hbox10 = new HBox();
        Label label4yesterday = new Label("自从:     ");
        ChoiceBox<Integer> hour4Yesterday = new ChoiceBox();
        hour4Yesterday.setItems(FXCollections.observableArrayList(getTimeList(23, 6)));
        hour4Yesterday.setValue(23);
        Label labeli = new Label("时");
        ChoiceBox<Integer> minute4Yesterday = new ChoiceBox();
        minute4Yesterday.setItems(FXCollections.observableArrayList(getTimeList(0, 23)));
        minute4Yesterday.setValue(0);
        Label labelii = new Label("分");
        ChoiceBox<Integer> second4Yesterday = new ChoiceBox();
        second4Yesterday.setValue(0);
        second4Yesterday.setItems(FXCollections.observableArrayList(getTimeList(0, 59)));
        Label labeliii = new Label("秒");
        hbox10.getChildren().addAll(label4yesterday, hour4Yesterday, labeli, minute4Yesterday, labelii, second4Yesterday, labeliii);

        HBox hbox11 = new HBox();
        Label label4today = new Label("直至:       ");
        ChoiceBox<Integer> hour4Today = new ChoiceBox();
        hour4Today.setItems(FXCollections.observableArrayList(getTimeList(0, 7)));
        hour4Today.setValue(7);
        Label labelu = new Label("时");
        ChoiceBox<Integer> minute4Today = new ChoiceBox();
        minute4Today.setItems(FXCollections.observableArrayList(getTimeList(0, 23)));
        minute4Today.setValue(0);
        Label labeluu = new Label("分");
        ChoiceBox<Integer> second4Today = new ChoiceBox();
        second4Today.setValue(0);
        second4Today.setItems(FXCollections.observableArrayList(getTimeList(0, 59)));
        Label labeluuu = new Label("秒");
        hbox11.getChildren().addAll(label4today, hour4Today, labelu, minute4Today, labeluu, second4Today, labeluuu);

        HBox hBoxxx = new HBox();
        hBoxxx.getChildren().addAll(new Label("筛选未归同学："));



        setxlsx.setOnAction(event -> {
            LocalDate todayDate = LocalDate.now();
            LocalTime todayTime = LocalTime.of(hour4Today.getValue(),minute4Today.getValue(),second4Today.getValue());
            LocalDateTime today = LocalDateTime.of(todayDate,todayTime);

            LocalDate yesterdayDate;
            if(hour4Yesterday.getValue()==23) {
                yesterdayDate = todayDate.minusDays(1);
            }else{
                yesterdayDate = todayDate;
            }
            LocalTime yesterdayTime = LocalTime.of(hour4Yesterday.getValue(),minute4Yesterday.getValue(),second4Yesterday.getValue());
            LocalDateTime yesterday = LocalDateTime.of(yesterdayDate,yesterdayTime);
            if (choiceBoxDate.getValue() == null || choiceBoxHour.getValue() == null || choiceBoxMinute.getValue() == null) {
                LackRemindBox.display("时长选择不完全", "请选择合适的时长");
            } else if (beginfield.getValue() == null) {
                LackRemindBox.display("记录筛选起始日期不完全", "请选择合适的起始日期");
            } else if (endfield.getValue() == null) {
                LackRemindBox.display("记录筛选结束日期不完全", "请选择合适的结束日期");
            } else if (beginfield.getValue().isAfter(endfield.getValue())) {
                LackRemindBox.display("日期逻辑错误", "起始日期不能在结束日期之后");
            }else if(yesterday.isAfter(today)){
                LackRemindBox.display("昨日未归条件错误","筛选未归起始时间不能后于终止时间");
            }
            else if (choiceBox4thresholdDay.getValue() == null || choiceBox4thresholdHour.getValue() == null || choiceBox4thresholdMin.getValue() == null) {
                LackRemindBox.display("阈值选择不完全", "请选择合适的阈值");
            } else {
                ReCheck reCheck = new ReCheck();
                reCheck.start(new Stage(), button1.isSelected(), choiceBoxDate.getValue(),
                        choiceBoxHour.getValue(), choiceBoxMinute.getValue(), beginfield.getValue(),
                        endfield.getValue(), choiceBox4thresholdDay.getValue(), choiceBox4thresholdHour.getValue(), choiceBox4thresholdMin.getValue(),yesterday,today);
                window.close();
            }


        });
//        组合
        hbox4.getChildren().add(new Label(""));
        hbox5.getChildren().add(new Label(""));
        hBox6.getChildren().add(new Label(""));
        hbox8.getChildren().add(new Label(""));
        hbox9.getChildren().add(new Label(""));



        vbox.getChildren().add(hbox0);
        vbox.getChildren().add(hbox8);
        vbox.getChildren().add(hbox00);
        vbox.getChildren().add(hbox9);
        vbox.getChildren().add(hbox1);
        vbox.getChildren().add(hbox4); // 类似于调整行间距  =_=
        vbox.getChildren().add(hbox2);
        vbox.getChildren().add(hbox5);
        vbox.getChildren().add(hBox7);
        vbox.getChildren().add(new Label(""));
        vbox.getChildren().add(hBoxxx);
        vbox.getChildren().add(new Label(""));
        vbox.getChildren().add(hbox10);
        vbox.getChildren().add(new Label(""));
        vbox.getChildren().add(hbox11);
        vbox.getChildren().add(hBox6);
        vbox.getChildren().add(hbox3);
//        设置边距
        vbox.setLayoutX(20);
        vbox.setLayoutY(30);

//
//        setpath.setOnMouseClicked(new Setpath());
//        setxlsx.setOnMouseClicked(new SetExcel());
//        deliver.setOnMouseClicked(new Deliver());

//        窗口
        Scene scene = new Scene(panel, 400, 470);

        window.setTitle("Dormitory Access System");
        window.setOnCloseRequest(event -> {
            event.consume(); //不然就算点No也会关闭，因为调用的是setOnCloseRequest
            closeProgram();
        });
        window.setScene(scene);
        window.show();
        window.setResizable(false);
    }

    private void closeProgram() {
        Boolean answer = ConfirmBox.display("Title", "Sure you want to exit?");
        if (answer) {
            window.close();
            System.exit(0);
        }
    }

    private List<Integer> getTimeList(int start, int end) {

        List<Integer> timeList = new ArrayList<>();
        if(end<start){
            for (int i = start; i < 24; i++) {
                timeList.add(i);
            }
            for(int j = 0;j<=end;j++){
                timeList.add(j);
            }
            return timeList;
        }
        for (int i = start; i <= end; i++) {
            timeList.add(i);
        }
        return timeList;
    }
}



