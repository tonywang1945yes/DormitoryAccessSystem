package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MailSend {
    Stage window;
    static PasswordField mailPassword;

    public void display(Stage primaryStage){
        window = primaryStage;
        AnchorPane panel=new AnchorPane();
        VBox box = new VBox();
        panel.getChildren().addAll(box);

        Scene scene=new Scene(panel, 380, 200);

        Label password=new Label("邮箱的密码: ");
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
        box.getChildren().addAll(hbox2,new Label(""));
        panel.getChildren().add(button);
        button.setOnAction(event -> {

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
