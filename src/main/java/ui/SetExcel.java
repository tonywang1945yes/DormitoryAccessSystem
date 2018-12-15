package ui;

import bl.EmailSender;
import bl.StudentListGenerator;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ui.Main;

public class SetExcel implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
        Main.generator=new StudentListGenerator();
        Main.generator.start();
        Main.resultLabel.setText("生成表格完成");
        Main.deliver.setVisible(true);
        Main.setexcel.setVisible(false);
    }
}
