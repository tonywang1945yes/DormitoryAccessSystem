package ui;

import enums.ListGenerateResult;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SetExcel implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
        String password=getpassword();
        ListGenerateResult result=Main.controller.StudentListGenerate(Main.filepath,password);
        if(result== ListGenerateResult.NO_SUCH_FILE){
            Main.resultLabel.setText("请选择正确路径。");
        }
        else if(result== ListGenerateResult.WRONG_PASSWORD){
            Main.resultLabel.setText("请输入正确密码");
        }
        else{
            Main.resultLabel.setText("生成表格完成");
            Main.isvisited=true;
        }
//        Main.generator=new StudentListGenerator();
//        Main.generator.start();
    }
    public String getpassword(){
        String s=Main.password.getText();
        return s;
    }

}
