package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

class FileChoose {
    String path;

    void getFilePath() {

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        String path = null;
        File f = null;
        int flag = 0;
        try {
            flag = fc.showOpenDialog(null);
        } catch (HeadlessException head) {
            System.out.println("Open File Dialog ERROR!");
        }
        if (flag == JFileChooser.APPROVE_OPTION) {
            //获得该文件
            f = fc.getSelectedFile();
            this.path = f.getPath();
        }

//以上获得选择的文件夹

    }


}