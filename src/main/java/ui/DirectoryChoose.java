package ui;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class DirectoryChoose {
    String path;
    public void getFilePath(Stage primaryStage){
        DirectoryChooser chooser = new DirectoryChooser();
        File file = chooser.showDialog(primaryStage);
        if(file==null){
            path=null;
        }
        else {
            path=file.getPath();
        }
    }
}
