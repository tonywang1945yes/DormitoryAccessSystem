package ui;

import bl.EmailSender;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Deliver implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
        Main.sender=new EmailSender();
        Main.sender.start();
        Main.resultLabel.setText("已发送给各院系辅导员。");
        Main.deliver.setVisible(false);
    }
}
