package ui;

import bl.Controller;
import enums.MailResult;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MailSend {
    Stage window;
    static TextField hostName;
    static PasswordField mailPassword;

    public void display(Stage primaryStage){
        window = primaryStage;
        AnchorPane panel=new AnchorPane();
        VBox box = new VBox();
        panel.getChildren().addAll(box);

        Scene scene=new Scene(panel, 380, 200);

        Label host=new Label("邮箱账户: ");
        hostName =new TextField();
        hostName.setPromptText("在此输入邮箱");
        hostName.setOpacity(0.7);
        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(host,hostName);

        Label password=new Label("邮箱密码: ");
        mailPassword =new PasswordField();
        mailPassword.setPromptText("在此输入密码");
        mailPassword.setOpacity(0.7);
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(password,mailPassword);

        box.setLayoutX(20);
        box.setLayoutY(60);
        Button button = new Button("确认发送");
        button.setLayoutX(140);
        button.setLayoutY(140);
        box.getChildren().addAll(hBox1,new Label(""),hbox2,new Label(""));
        panel.getChildren().add(button);
        button.setOnAction(event -> {
            MailResult  res =Operator.controller.sendMail(hostName.getText(),mailPassword.getText());
            if(res.equals(MailResult.NO_SUCH_FILE)){
                Warn.display("文件路径错误","请检查输入的路径");
                window.close();
            }
            else if(res.equals(MailResult.NOT_OK)){
                Warn.display("失败","发送失败，请稍后再试");
                window.close();
            }
            else {
                Warn.display("成功","发送成功");
                window.close();
            }
        });

        window.setOnCloseRequest(event -> {
            event.consume(); //不然就算点No也会关闭，因为调用的是setOnCloseRequest
            closeProgram();
        });
        window.setTitle("Dormitory Access System");
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
}
