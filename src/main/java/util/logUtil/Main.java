package util.logUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ho.yaml.Yaml;

public class Main {
//    public static void main(String[] args) throws IOException {
//        String filePath = "C://ProgramData/MySQL/MySQL Server 8.0/Data/LAPTOP-KOMGR1G5-bin.000178";
//        File binlogFile = new File(filePath);
//        EventDeserializer eventDeserializer = new EventDeserializer();
//        eventDeserializer.setChecksumType(ChecksumType.CRC32);
//        BinaryLogFileReader reader = new BinaryLogFileReader(binlogFile,
//                eventDeserializer);
//        try {
//            // 准备写入的文件名称
//            /*
//             * File f1 = new File("D:\\mysql-bin.000845.sql"); if
//             * (f1.exists()==false){ f1.getParentFile().mkdirs(); }
//             */
//            FileOutputStream fos = new FileOutputStream(
//                    "D:\\mysql-bin.000345.sql", true);
//            for (Event event; (event = reader.readEvent()) != null;) {
//                System.out.println(event.toString());
//
//                // 把数据写入到输出流
//                fos.write(event.toString().getBytes());
//            }
//            // 关闭输出流
//            fos.close();
//            System.out.println("输入完成");
//        } finally {
//            reader.close();
//        }
//
//    }
    public static void main(String[] args){
        Person michael = new Person();
        Person floveria = new Person();
        Person[] children = new Person[2];
        children[0] = new Person();
        children[1] = new Person();

        michael.setName("Michael Corleone");
        michael.setAge(24);
        floveria.setName("Floveria Edie");
        floveria.setAge(24);
        children[0].setName("boy");
        children[0].setAge(3);
        children[1].setName("girl");
        children[1].setAge(1);

        michael.setSpouse(floveria);
        floveria.setSpouse(michael);

        michael.setChildren(children);
        floveria.setChildren(children);

        /* Export data to a YAML file. */
        File dumpFile = new File("E://John.yaml");
        try {
            Yaml.dump(michael, dumpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}


