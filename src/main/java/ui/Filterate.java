package ui;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Filterate {
    Stage window;
    Button button;


    //    表格是否生成
    static boolean isvisited=false;
    //    起始时间
    static DatePicker beginfield;
    //    终止日期
    static DatePicker endfield;
    //     阈值框
    static TextField threshold;
    //     阈值
    static String value;
    //    文件路径
    static String filepath;
    //    启动器
//    static Controller controller=new Controller();

    public void start(Stage primaryStage) {
        window = primaryStage;
//        图片加载器
        ImageView mv=new ImageView();
//        背景板
        AnchorPane panel=new AnchorPane();
//        图片
        Image image=new Image(this.getClass().getResource("/ui/singal.jpg").toExternalForm(),true);

        mv.setImage(image);
        mv.setOpacity(0.2);
        mv.setLayoutX(65);
        panel.getChildren().add(mv);
//        panel.setMaxSize(BoundarySize.WIDTH + 1000,BoundarySize.HEIGHT + 1000);
//        panel.setMinSize(BoundarySize.WIDTH,BoundarySize.HEIGHT);
        panel.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));

        VBox vbox=new VBox();
        panel.getChildren().add(vbox);

        HBox hbox0 = new HBox();
        HBox hbox00 = new HBox();
//        vbox.setPadding(new Insets(10, 12, 20, 40));
        HBox hbox1=new HBox(); // 选择起始时间
        HBox hbox2=new HBox(); // 选择终止时间
        HBox hbox3=new HBox(); // 按钮
        HBox hbox4=new HBox(); // 空行
        HBox hbox5=new HBox(); // 空行
        HBox hBox6=new HBox(); // 空行
        HBox hBox7=new HBox(); // 输入阈值
        HBox hbox8 = new HBox();
        HBox hbox9 = new HBox();

        Label label = new Label("筛选策略：      ");
        ToggleGroup group = new ToggleGroup();
        RadioButton button1 = new RadioButton("至多外出时间");
        button1.setToggleGroup(group);
        button1.setSelected(true);
        RadioButton button2 = new RadioButton("至多在寝时间");
        button2.setToggleGroup(group);
        hbox0.getChildren().addAll(label,button1,button2);

        Label label1 = new Label("请选择时长: ");
        ChoiceBox<Integer> choiceBoxDate = new ChoiceBox();
        choiceBoxDate.setItems(FXCollections.observableArrayList(getTimeList(0,15)));
        Label label2 = new Label("天 ");
        ChoiceBox<Integer> choiceBoxHour = new ChoiceBox();
        choiceBoxHour.setItems(FXCollections.observableArrayList(getTimeList(0,23)));
        Label label3 = new Label("小时 ");
        ChoiceBox<Integer> choiceBoxMinute = new ChoiceBox();
        choiceBoxMinute.setItems(FXCollections.observableArrayList(getTimeList(0,59)));
        Label label4 = new Label("分钟");
        hbox00.getChildren().addAll(label1,choiceBoxDate,label2,choiceBoxHour,label3,choiceBoxMinute,label4);

//        System.out.println(button2.selectedProperty().getValue());
//        System.out.println(button1.selectedProperty().getValue());
//        选择路径
        Label pathlabel=new Label("起始日期：");
        beginfield = new DatePicker();
        beginfield.setPromptText("起始于该日的00:00分");
        beginfield.setOpacity(0.7);
        hbox1.getChildren().add(pathlabel);
        hbox1.getChildren().add(beginfield);

//        输入密码
        Label endlabel=new Label("结束日期：");
        endfield = new DatePicker();
        endfield.setPromptText("截止至该日的23:59分");
        endfield.setOpacity(0.7);
        hbox2.getChildren().add(endlabel);
        hbox2.getChildren().add(endfield);

//        输入阈值
        Label threshold = new Label("阈值:         ");
        Filterate.threshold = new TextField();
        Filterate.threshold.setPromptText("在此输入阈值");
        Filterate.threshold.setOpacity(0.7);
        hBox7.getChildren().add(threshold);
        hBox7.getChildren().add(Filterate.threshold);


//        按钮
        Label setfield=new Label();
        setfield.setText("欢迎使用：");
        Button setxlsx=new Button();
        setxlsx.setText("点此确认生成筛选条件");
        setxlsx.setOpacity(1);
        hbox3.getChildren().add(setfield);
        hbox3.getChildren().add(setxlsx);

        setxlsx.setOnAction(event -> {
            if(choiceBoxDate.getValue()==null||choiceBoxHour.getValue()==null||choiceBoxMinute.getValue()==null){
                LackRemindBox.display("时长选择不完全","请选择合适的时长");
            }
            else if (beginfield.getValue()==null){
                LackRemindBox.display("记录筛选起始日期不完全","请选择合适的起始日期");
            }
            else if (endfield.getValue()==null){
                LackRemindBox.display("记录筛选终止日期不完全","请选择合适的终止日期");
            }else if (this.threshold.getText().equals("")){
                LackRemindBox.display("阈值输入不能为空","请输入阈值");
            }
            else{
                ReCheck reCheck = new ReCheck();
                reCheck.start(new Stage(),button1.isSelected(),choiceBoxDate.getValue(),
                        choiceBoxHour.getValue(),choiceBoxMinute.getValue(),beginfield.getValue(),
                        endfield.getValue(),this.threshold.getText());
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
        Scene scene=new Scene(panel, 400, 350);

        window.setTitle("Dormitory Access System");
        window.setOnCloseRequest(event -> {
            event.consume(); //不然就算点No也会关闭，因为调用的是setOnCloseRequest
            closeProgram();
        });
        window.setScene(scene);
        window.show();
        window.setResizable(false);
    }
    private void closeProgram(){
        Boolean answer = ConfirmBox.display("Title","Sure you want to exit?");
        if(answer){
            window.close();
        }
    }

    private List<Integer> getTimeList(int start,int end){
        List<Integer> timeList = new ArrayList<>();
        for(int i = start;i<=end;i++){
            timeList.add(i);
        }
        return timeList;
    }
}



