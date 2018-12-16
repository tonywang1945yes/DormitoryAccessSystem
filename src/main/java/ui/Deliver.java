package ui;

import bl.EmailSender;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.event.MouseListener;

public class Deliver implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
        if(Main.isvisited){
            Main.controller.MailSend();
            Main.resultLabel.setText("已发送给辅导员");
        }
        else{
            Main.resultLabel.setText("请生成文件");
        }
    }
}
