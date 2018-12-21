package ui;

import entity.ListGenerateResult;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SetExcel implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
        if (Main.filepath == null) {
            Dialog.popDialog("请选择路径", "warning");
        }
        else if (!Main.filepath.endsWith(".xls") && !Main.filepath.endsWith(".xlsx")){
            Dialog.popDialog("请选择正确路径", "warning");
        }
        else if (getpassword().length() == 0){
            Dialog.popDialog("请输入密码", "warning");
        }
        else if (getthreshold().length() == 0) {
            Dialog.popDialog("请输入阈值", "warning");
        }
        else if (Integer.valueOf(getthreshold()) <= 0){
                Dialog.popDialog("请输入正确阈值", "warning");
        }
        else {
            Main.password.setEditable(false);
            Main.threshold.setEditable(false);
            String password = getpassword();
            ListGenerateResult result = Main.controller.StudentListGenerate(Main.filepath, password);

            switch (result) {
                case Success:
                    Dialog.popDialog("生成表格完成", "information");
                    Main.isvisited = true;
                    break;
                case Driver_Error:
                    Dialog.popDialog("驱动错误", "error");
                    break;
                case No_Such_File:
                    Dialog.popDialog("未找到对应文件", "error");
                    break;
                case Wrong_Password:
                    Dialog.popDialog("密码错误", "error");
                    break;
                case Connection_Error:
                    Dialog.popDialog("数据库链接错误", "error");
                    break;

            }

        }
    }
    public String getpassword(){
        String s=Main.password.getText();
        return s;
    }
    public String getthreshold(){
        String s=Main.threshold.getText();
        return s;
    }

}
