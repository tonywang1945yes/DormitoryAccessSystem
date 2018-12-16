package util.mailUtil;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

import com.sun.mail.util.MailSSLSocketFactory;
import util.mailUtil.mailException.AttachmentTooLargeException;
import util.mailUtil.mailException.UnknownHostException;

public class Mail {
    /**
     * 发送一封简单邮件
     *
     * @param sendAddress    发件人邮箱地址
     * @param password       发件人邮箱密码（授权码）
     * @param subject        邮件主题
     * @param receiveAddress 收件人邮箱地址
     * @param content        邮件内容
     * @throws GeneralSecurityException 安全异常
     * @throws UnknownHostException     找不到相应的邮件服务器
     * @throws MessagingException       发信异常
     */
    public static void sendSimpleMail(String sendAddress, String password, String subject, String receiveAddress, String content)
            throws GeneralSecurityException, UnknownHostException, MessagingException {
        MimeMessage message = new MimeMessage(init(sendAddress, password));
        message.setFrom(new InternetAddress(sendAddress));// 发件人
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiveAddress));// 收件人
        message.setSubject(subject);// 主题
        message.setText(content);// 内容
        Transport.send(message);
        System.out.println("The message has been sent.");
    }

    /**
     * 发送一封带附件的邮件
     *
     * @param sendAddress    发件人邮箱地址
     * @param password       发件人邮箱密码（授权码）
     * @param subject        邮件主题
     * @param receiveAddress 收件人邮箱地址
     * @param content        邮件内容
     * @param attachmentPath 附件路径（可以是文件夹，如果是文件夹则打包）
     * @throws GeneralSecurityException    安全异常
     * @throws MessagingException          发信异常
     * @throws UnknownHostException        找不到相应的邮件服务器
     * @throws AttachmentTooLargeException 附件过大
     * @throws IOException                 IO异常
     */

    public static void sendMailWithAttachment(String sendAddress, String password, String subject,
                                              String receiveAddress, String content, String attachmentPath)
            throws GeneralSecurityException, UnknownHostException, MessagingException, IOException, AttachmentTooLargeException {
        MimeMessage message = new MimeMessage(init(sendAddress, password));
        message.setFrom(new InternetAddress(sendAddress));// 发件人
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiveAddress));// 收件人
        message.setSubject(subject);// 主题

        Multipart fullText = new MimeMultipart();// 邮件内容

        BodyPart messagePart = new MimeBodyPart();// 文字部分
        messagePart.setText(content);
        fullText.addBodyPart(messagePart);

        BodyPart attachmentPart = null;// 附件部分
        File src = new File(attachmentPath);
        if (!src.exists()) {
            throw new FileNotFoundException("File not exists.");
        }
        DataSource ds = null;
        if (src.isDirectory()) {
            String zipPath = toZip(src, src.getName());// 将附件打包
            if (zipPath == null) {
                throw new FileNotFoundException("Zip path not exists.");
            }
            attachmentPart = new MimeBodyPart();
            ds = new FileDataSource(zipPath);
        } else {
            if (src.length() > 1024 * 1024 * 20) {// 附件过大
                throw new AttachmentTooLargeException("File too large.");
            }
            attachmentPart = new MimeBodyPart();
            ds = new FileDataSource(src.getPath());
        }
        attachmentPart.setDataHandler(new DataHandler(ds));
        attachmentPart.setFileName(ds.getName());
        fullText.addBodyPart(attachmentPart);
        message.setContent(fullText);
        Transport.send(message);
        System.out.println("The message has been sent.");
    }

    /**
     * 初始化
     *
     * @param sendAddress 发件人邮箱地址
     * @param password    发件人邮箱密码（授权码）
     * @return 初始化信息
     * @throws GeneralSecurityException 安全异常
     * @throws UnknownHostException     找不到相应的邮件服务器
     */
    private static Session init(final String sendAddress, final String password)
            throws GeneralSecurityException, UnknownHostException {
        String host = getHost(sendAddress);// 邮件服务器
        Properties properties = System.getProperties();// 系统设置
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();// SSL
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        Session session = Session.getDefaultInstance(properties, new Authenticator() {// 授权
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendAddress, password);
            }
        });
        return session;
    }

    /**
     * 根据发件人邮箱地址确定邮件服务器，应该还有很多，但目前只测试了前面四个（一直到网易为止，没有后面的邮箱）
     *
     * @param sendAddress 发件人邮箱地址
     * @return 邮件服务器
     * @throws UnknownHostException 找不到相应的邮件服务器
     */

    private static String getHost(String sendAddress) throws UnknownHostException {
        if (sendAddress.endsWith("@smail.nju.edu.cn")) {
            return "smtp.exmail.qq.com";
        } else if (sendAddress.endsWith("@qq.com")) {
            return "smtp.qq.com";
        } else if (sendAddress.endsWith("@126.com")) {
            return "smtp.126.com";
        } else if (sendAddress.endsWith("@163.com")) {
            return "smtp.163.com";
        } else if (sendAddress.endsWith("@sina.com")) {
            return "smtp.sina.com.cn";
        } else if (sendAddress.endsWith("@sohu.com")) {
            return "smtp.sohu.com.cn";
        } else if (sendAddress.endsWith("@139.com")) {
            return "smtp.139.com";
        } else {
            throw new UnknownHostException("Unknown Host.");
        }
    }

    /**
     * 将目录下的文件打包
     *
     * @param directory 需要打包的文件夹
     * @param fileName  压缩包名称
     * @return 压缩包，默认放在原文件夹中
     * @throws AttachmentTooLargeException 附件过大
     * @throws IOException                 IO异常
     */
    private static String toZip(File directory, String fileName) throws AttachmentTooLargeException, IOException {
        if (!directory.exists()) {
            throw new FileNotFoundException("File not found.");
        }
        String directoryPath = directory.getAbsolutePath();
        String zipPath = directoryPath + "/" + fileName + ".zip";// 压缩包生成路径，默认原文件夹
        BufferedInputStream bis = null;
        ZipOutputStream zos = null;
        File[] contents = directory.listFiles();// 先检查文件夹是否为空
        if (contents == null || contents.length < 1) {
            throw new FileNotFoundException("No file in it.");
        }
        zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipPath)));
        byte[] buffer = new byte[1024 * 10];// 缓冲流的缓冲区（大小暂定为10KB）
        long totalSize = 0;
        for (File file : contents) {
            totalSize += file.length();
            if (totalSize > 1024 * 1024 * 20) {
                throw new AttachmentTooLargeException("File too large.");
            }
            ZipEntry zipEntry = new ZipEntry(file.getName());// 创建压缩包
            zos.putNextEntry(zipEntry);
            bis = new BufferedInputStream(new FileInputStream(file), 1024 * 10);
            int hasRead = 0;// 读取内容
            while ((hasRead = bis.read(buffer, 0, 1024 * 10)) != -1) {
                zos.write(buffer, 0, hasRead);
            }
        }
        if (bis != null) {
            bis.close();
        }
        if (zos != null) {
            zos.close();
        }
        return zipPath;
    }
}