package ui;

import bl.Controller;
import enums.CheckResult;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class Main extends Application {

    Stage window;
    Button login;
    static TextField key;

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
        AnchorPane panel = new AnchorPane();
        window = primaryStage;
        window.setTitle("Dormitary");

        login = new Button("登录");

        login.setOnAction(event -> {
            Logging logging = new Logging();
            logging.execute();
            window.hide();
        });



        key = new TextField();
        HBox vbox =new HBox();
        vbox.getChildren().addAll(new Label("密钥："),key);
        vbox.setLayoutX(35);
        vbox.setLayoutY(50);
        panel.getChildren().add(vbox);
        login.setLayoutX(130);
        login.setLayoutY(120);
        panel.getChildren().add(login);
        Scene scene = new Scene(panel, 300, 200);
        window.setScene(scene);
        window.show();
        window.setResizable(false);
    }

}

