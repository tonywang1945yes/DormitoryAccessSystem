package ui;

import javafx.event.EventHandler;

public class Setpath  implements EventHandler<javafx.scene.input.MouseEvent> {
    @Override
    public void handle(javafx.scene.input.MouseEvent event) {
        Filechoose filechoose=new Filechoose();
        filechoose.getFilePath();
        Main.path.setText(filechoose.path);
        Main.filepath=filechoose.path;
    }
}
