package ui;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileChoose {
    String path;

    public void getFilePath(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file == null) {
            path = null;
        } else {
            path = file.getPath();
        }
        System.out.println(path);
    }
}