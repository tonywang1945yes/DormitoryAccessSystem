package ui;

import bl.EmailSender;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.event.MouseListener;

public class Deliver implements EventHandler<MouseEvent> ,MouseListener {
    @Override
    public void handle(MouseEvent event) {
        Main.controller.MailSend();
        Main.resultLabel.setText("已发送给各院系辅导员。");
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        Main.controller.MailSend();
        Main.resultLabel.setText("已发送给各院系辅导员。");
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        Main.resultLabel.setText("shitting!");
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {

    }
}
