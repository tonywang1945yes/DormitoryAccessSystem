//package ui.old;
//
//import enums.MailResult;
//
//import javafx.event.EventHandler;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
//import javafx.scene.input.MouseEvent;
//
//import java.util.Optional;
//
//public class Deliver implements EventHandler<MouseEvent> {
//    @Override
//    public void handle(MouseEvent event) {
//        if (Main.isVisited) {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Confirmation");
//            alert.setHeaderText("CONFIRMATION!");
//            alert.setContentText("确认发送？");
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == ButtonType.OK) {
//                // ... user chose OK
//                Alert waiting = new Alert(Alert.AlertType.INFORMATION);
//                waiting.setTitle("Information");
//                waiting.setHeaderText("INFORMATION");
//                waiting.setContentText("发送中……");
//                waiting.show();
//                Main.controller.sendMail();
//                if (Main.controller.sendMail() == MailResult.OK) {
////                Main.resultLabel.setText("已发送给辅导员");
//                    waiting.close();
//                    Dialog.popDialog("发送成功！", "information");
//                } else {
////                Main.resultLabel.setText("发送失败");
//                    waiting.close();
//                    Dialog.popDialog("发送失败", "error");
//                }
//            }
//        } else {
////            Main.resultLabel.setText("请生成文件");
//            Dialog.popDialog("请生成文件", "warning");
//        }
//    }
//}
