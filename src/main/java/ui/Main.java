package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;
    Button login;

    static TextField url;
    static TextField hostname;
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

        url = new TextField();
        HBox urlBox = new HBox();
        urlBox.getChildren().addAll(new Label("url：  "),url);


        hostname = new TextField();
        HBox hostBox = new HBox();
        hostBox.getChildren().addAll(new Label("用户："),hostname);



        secret = new PasswordField();
        HBox hBox = new HBox();
        hBox.getChildren().addAll(new Label("密钥："), secret);

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(urlBox,hostBox,hBox);

        vBox.setLayoutX(35);
        vBox.setLayoutY(50);

        login.setLayoutX(130);
        login.setLayoutY(190);
        panel.getChildren().add(vBox);
        panel.getChildren().add(login);
        Scene scene = new Scene(panel, 300, 230);
        window.setScene(scene);
        window.show();
        window.setResizable(false);
    }

}

