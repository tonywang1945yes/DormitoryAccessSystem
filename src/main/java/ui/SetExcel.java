package ui;

import enums.CheckResult;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SetExcel implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
        String password = Main.password.getText();
        CheckResult result = Main.controller.generateStudentList(Main.filepath, password);
        if (result == CheckResult.NO_SUCH_FILE) {
            Main.resultLabel.setText("请选择正确路径。");
        } else if (result == CheckResult.WRONG_PASSWORD) {
            Main.resultLabel.setText("请输入正确密码");
        } else {
            Main.resultLabel.setText("生成表格完成");
            Main.isVisited = true;
        }
//        Main.generator=new StudentListGenerator();
//        Main.generator.start();
    }


}
