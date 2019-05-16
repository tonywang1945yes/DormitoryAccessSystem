package ui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;
    Button login;
    static PasswordField dbPassword;
    static PasswordField mailPassword;
    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
        AnchorPane panel=new AnchorPane();
        window = primaryStage;
        window.setTitle("Dormitary");

        Label password=new Label("数据库密码: ");
        Main.dbPassword =new PasswordField();
        Main.dbPassword.setPromptText("在此输入密码");
        Main.dbPassword.setOpacity(0.7);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(password,Main.dbPassword);
//        hbox.setPadding(new Insets(75,50,180,20));

        HBox hbox1 = new HBox();
        Label label = new Label("");
        hbox1.getChildren().add(label);


        password=new Label("邮箱的密码: ");
        Main.mailPassword =new PasswordField();
        Main.mailPassword.setPromptText("在此输入密码");
        Main.mailPassword.setOpacity(0.7);
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(password,Main.mailPassword);

        login = new Button("登录");

        login.setOnAction(event -> {
            if(true){
                try {
                    Operator open = new Operator();
                    open.start(new Stage());
                    window.hide();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                Warn.display("警告","密码不正确");
            }
        });

        VBox vBox = new VBox();
        panel.getChildren().add(vBox);
        vBox.getChildren().addAll(hbox,hbox1,hbox2);

        vBox.setLayoutX(20);
        vBox.setLayoutY(60);

//        vbox =new VBox();
//        layout.getChildren().add(vbox);
        login.setLayoutX(140);
        login.setLayoutY(180);
        panel.getChildren().add(login);
        Scene scene = new Scene(panel,350,250);
        window.setScene(scene);
        window.show();
        window.setResizable(false);
    }

}

