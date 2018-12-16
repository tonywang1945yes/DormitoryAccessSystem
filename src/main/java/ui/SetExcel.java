package ui;

import bl.Controller;
import bl.EmailSender;
import bl.StudentListGenerator;
import entity.ListGeneratResult;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ui.Main;

public class SetExcel implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
        String password=getpassword();
        ListGeneratResult result=Main.controller.StudentListGenerate(Main.filepath,password);
        if(result==ListGeneratResult.No_Such_File){
            Main.resultLabel.setText("请选择正确路径。");
        }
        else if(result==ListGeneratResult.Wrong_Password){
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
