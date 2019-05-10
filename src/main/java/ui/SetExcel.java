package ui;


import enums.CheckResult;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SetExcel implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {

        if (Main.filepath == null) {
            Dialog.popDialog("请选择路径", "warning");
        } else if (!Main.filepath.endsWith(".xls") && !Main.filepath.endsWith(".xlsx")) {
            Dialog.popDialog("请选择正确路径", "warning");
        } else if (getPassword().length() == 0) {
            Dialog.popDialog("请输入密码", "warning");
        } else if (getThreshold().length() == 0) {
            Dialog.popDialog("请输入阈值", "warning");
        } else if (Integer.valueOf(getThreshold()) <= 0) {
            Dialog.popDialog("请输入正确阈值", "warning");
        } else {
            Main.password.setEditable(false);
            Main.threshold.setEditable(false);
            String password = getPassword();
            // TODO 该处需商讨接口后才能确定
            CheckResult result = Main.controller.generateStudentList(Main.filepath, password);

            switch (result) {
                case SUCCESS:
                    Dialog.popDialog("生成表格完成", "information");
                    Main.isVisited = true;
                    break;
                case DRIVER_ERROR:
                    Dialog.popDialog("驱动错误", "error");
                    break;
                case NO_SUCH_FILE:
                    Dialog.popDialog("未找到对应文件", "error");
                    break;
                case WRONG_PASSWORD:
                    Dialog.popDialog("密码错误", "error");
                    break;
                case CONNECTION_ERROR:
                    Dialog.popDialog("数据库链接错误", "error");
                    break;

            }

        }
    }

    private String getPassword() {
        return Main.password.getText();
    }

    private String getThreshold() {
        return Main.threshold.getText();
    }

}
