package ui;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bl.Controller;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import entity.TimeRequirement;
import enums.CheckResult;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Logging extends Application{
    static CheckResult result ;
    static boolean finish = false;
    Task copyWorker;

    public void execute() {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        // 使用guava提供的MoreExecutors工具类包装原始的线程池
        ListeningExecutorService listeningExecutor = MoreExecutors.listeningDecorator(executor);
        //向线程池中提交一个任务后，将会返回一个可监听的Future，该Future由Guava框架提供
        ListenableFuture<String> lf = listeningExecutor.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                System.out.println("task started!");
//                Controller controller = new Controller();
//                Map<CheckResult, List<String>> map = controller.testDatabase();
//                if (map.containsKey(CheckResult.DRIVER_ERROR) ){
//                    result = CheckResult.DRIVER_ERROR;
//                }
//                else if(map.containsKey(CheckResult.CONNECTION_ERROR)){
//                    result= CheckResult.CONNECTION_ERROR;
//                }
//                else if(map.containsKey(CheckResult.DATABASE_ERROR)){
//                    result = CheckResult.DATABASE_ERROR;
//                }else {
//                    result = CheckResult.SUCCESS;
//                }
                result = CheckResult.SUCCESS;
                Thread.sleep(3000);
                System.out.println("task finished!");
                return "hello";
            }
        });
        //添加回调，回调由executor中的线程触发，但也可以指定一个新的线程
        Futures.addCallback(lf, new FutureCallback<String>() {

            //耗时任务执行失败后回调该方法
            @Override
            public void onFailure(Throwable t) {
                System.out.println("failure");
            }

            //耗时任务执行成功后回调该方法
            @Override
            public void onSuccess(String s) {
                System.out.println("success " + s);
                finish =true;
            }
        });

        //主线程可以继续做其他的工作
        System.out.println("main thread is running");

        start(new Stage());
    }
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 280, 100, Color.WHITE);

        BorderPane mainPane = new BorderPane();
        root.getChildren().add(mainPane);

        final Label label = new Label("   系统检测中:");
        final ProgressBar progressBar = new ProgressBar(0);

        final HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(label, progressBar);
        mainPane.setTop(new Label(""));
        mainPane.setCenter(hb);

        progressBar.setProgress(0);
        copyWorker = createWorker();
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(copyWorker.progressProperty());
        copyWorker.messageProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                System.out.println(newValue);
                if(newValue.equals("是der")){
                    //NO_SUCH_FILE, WRONG_FORMAT, SHEET_NAME_ERROR,
                    //    WRONG_PASSWORD, CONNECTION_ERROR, DRIVER_ERROR, DATABASE_ERROR,
                    //    FILE_WRITING_ERROR,
                    //    SUCCESS
                    if(result.equals(CheckResult.SUCCESS)) {
                        Operator operator =new Operator();
                        operator.start(new Stage());
                        primaryStage.close();
                    }
                    else if(result.equals(CheckResult.WRONG_FORMAT)){
                        Warn.display("失败", "输入格式错误");
                        primaryStage.close();
                    }
                    else if(result.equals(CheckResult.CONNECTION_ERROR)){
                        Warn.display("失败", "连接错误");
                        primaryStage.close();
                    }
                    else if(result.equals(CheckResult.SHEET_NAME_ERROR)){
                        Warn.display("失败", "文件名错误");
                        primaryStage.close();
                    }
                    else if(result.equals(CheckResult.DATABASE_ERROR)){
                        Warn.display("失败", "数据库错误");
                        primaryStage.close();
                    }
                    else if(result.equals(CheckResult.DRIVER_ERROR)){
                        Warn.display("失败", "驱动错误");
                        primaryStage.close();
                    } else if(result.equals(CheckResult.NO_SUCH_FILE)){
                        Warn.display("失败", "文件路径错误");
                        primaryStage.close();
                    }
                    else if(result.equals(CheckResult.FILE_WRITING_ERROR)){
                        Warn.display("失败","写生成文件失败");
                        primaryStage.close();
                    }
                }
//                if(newValue.equals("是der")){
//                    Operator operator =new Operator();
//                    operator.start(new Stage());
//                    primaryStage.close();
//                }
            }
        });
        new Thread(copyWorker).start();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(2000);
                    updateMessage(finish?"是der":"Nope");
                }
                return true;
            }
        };
    }
}
