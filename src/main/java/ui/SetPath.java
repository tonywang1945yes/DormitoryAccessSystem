package ui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SetPath implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
        FileChoose fileChoose = new FileChoose();
        fileChoose.getFilePath();
        Main.path.setText(fileChoose.path);
        Main.filepath = fileChoose.path;
    }
}
