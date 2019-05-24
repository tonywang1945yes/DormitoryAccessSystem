package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;
    Button login;
    static PasswordField secret;

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


        secret = new PasswordField();
        HBox hBox = new HBox();
        hBox.getChildren().addAll(new Label("密钥："), secret);
        hBox.setLayoutX(35);
        hBox.setLayoutY(50);
        panel.getChildren().add(hBox);
        login.setLayoutX(130);
        login.setLayoutY(120);
        panel.getChildren().add(login);
        Scene scene = new Scene(panel, 300, 200);
        window.setScene(scene);
        window.show();
        window.setResizable(false);
    }

}

