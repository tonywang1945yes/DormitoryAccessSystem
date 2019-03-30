package ui;

import enums.MailResult;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Deliver implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
        if(Main.isvisited){
            Main.controller.MailSend();
            if(Main.controller.MailSend()==MailResult.OK) {
                Main.resultLabel.setText("已发送给辅导员");
            }
            else {
                Main.resultLabel.setText("发送失败");
            }
        }
        else{
            Main.resultLabel.setText("请生成文件");
        }
    }
}
