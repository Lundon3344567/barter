package com.example.barter_authority.common.util;

import com.example.barter_authority.common.lang.Const;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author walnut
 * @version 1.0
 * @className emailUtil
 * @description 用来发送验证码邮件
 * @date 2021/11/13 4:47 下午
 */

@Component
//@ConfigurationProperties(prefix = "email")
public class EmailUtil {
    public void sendMail(String sendTo, String code, String type) throws GeneralSecurityException, MessagingException {
        Properties prop = new Properties();
        //设置QQ邮件服务器
        prop.setProperty("mail.host", "smtp.qq.com");
        // 邮件发送协议
        prop.setProperty("mail.transport.protocol", "smtp");
        // 需要验证用户名密码
        prop.setProperty("mail.smtp.auth", "true");

        // QQ邮箱设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        //1、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //传入发件人的姓名和授权码
                return new PasswordAuthentication("2514322002@qq.com", "rnbrfqcmqlweeacb");
            }
        });

        //2、通过session获取transport对象
        Transport transport = session.getTransport();

        //3、通过transport对象邮箱用户名和授权码连接邮箱服务器
        transport.connect("smtp.qq.com", "2514322002@qq.com", "rnbrfqcmqlweeacb");

        //4、创建邮件,传入session对象
        MimeMessage mimeMessage = complexEmail(session, sendTo, code, type);

        //5、发送邮件
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

        //6、关闭连接
        transport.close();


    }

    @Async
    public MimeMessage complexEmail(Session session, String sendTo, String code, String type) throws MessagingException {
        //消息的固定信息
        MimeMessage mimeMessage = new MimeMessage(session);

        //发件人
        mimeMessage.setFrom(new InternetAddress("2514322002@qq.com"));
        //收件人
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));


        //邮件内容
        //准备文本
        MimeBodyPart text = new MimeBodyPart();
        if (Const.SEND_REGISTER_MAIL.equals(type)) {

            //邮件标题
            mimeMessage.setSubject("易物———注册");

            text.setContent("<div\n" +
                    "  class=\"email-body\"\n" +
                    "  style=\"display: flex; justify-content: center; text-align: center\"\n" +
                    ">\n" +
                    "  <div class=\"container\" style=\"width: 500px\">\n" +
                    "    <div class=\"logo\" style=\"margin-top: 10px\">\n" +
                    "      <img\n" +
                    "        height=\"30\"\n" +
                    "        src=\"https://pic2.zhimg.com/80/v2-91170ed7011518c6518b62a53e8f38b5_720w.jpg\"\n" +
                    "        style=\"width: 100px; height: 100px\"\n" +
                    "      />\n" +
                    "    </div>\n" +
                    "    <div class=\"panel\" style=\"background-color: antiquewhite\">\n" +
                    "      <div class=\"panel-header\" style=\"padding-top: 15px\">注册验证</div>\n" +
                    "      <div class=\"panel-body\">\n" +
                    "        <p style=\"font-size: medium; font-weight: bolder\">\n" +
                    "          您好\n" +
                    "          <a href=\"mailto:"+sendTo+"\" rel=\"noopener\" target=\"_blank\"\n" +
                    "            >"+sendTo+"</a\n" +
                    "          >！\n" +
                    "        </p>\n" +
                    "        <p>欢迎注册 易物，请将验证码填写到注册页面。</p>\n" +
                    "        <p style=\"font-weight: bolder\">验证码："+code+"</p>\n" +
                    "        <p>请勿将此验证码泄露给他人</p>\n" +
                    "        <p style=\"padding-bottom: 15px\">（此验证码五分钟内有效）</p>\n" +
                    "      </div>\n" +
                    "    </div>\n" +
                    "    <div class=\"footer\" style=\"font-style: italic; font-size: small\">\n" +
                    "      @易物团队\n" +
                    "      <div class=\"pull-right\"></div>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</div>\n", "text/html;charset=utf-8");

        } else if(Const.SEND_FORGET_MAIL.equals(type)) {

            //邮件标题
            mimeMessage.setSubject("易物———找回密码");

            text.setContent("<div\n" +
                    "  class=\"email-body\"\n" +
                    "  style=\"display: flex; justify-content: center; text-align: center\"\n" +
                    ">\n" +
                    "  <div class=\"container\" style=\"width: 500px\">\n" +
                    "    <div class=\"logo\" style=\"margin-top: 10px\">\n" +
                    "      <img\n" +
                    "        height=\"30\"\n" +
                    "        src=\"https://pic2.zhimg.com/80/v2-91170ed7011518c6518b62a53e8f38b5_720w.jpg\"\n" +
                    "        style=\"width: 100px; height: 100px\"\n" +
                    "      />\n" +
                    "    </div>\n" +
                    "    <div class=\"panel\" style=\"background-color: antiquewhite\">\n" +
                    "      <div class=\"panel-header\" style=\"padding-top: 15px\">忘记密码</div>\n" +
                    "      <div class=\"panel-body\">\n" +
                    "        <p style=\"font-size: medium; font-weight: bolder\">\n" +
                    "          您好\n" +
                    "          <a href=\"mailto:"+sendTo+"\" rel=\"noopener\" target=\"_blank\"\n" +
                    "            >"+sendTo+"</a\n" +
                    "          >！\n" +
                    "        </p>\n" +
                    "        <p>您正在进行密码找回操作。</p>\n" +
                    "        <p style=\"font-weight: bolder\">验证码："+code+"</p>\n" +
                    "        <p>请勿将此验证码泄露给他人</p>\n" +
                    "        <p style=\"padding-bottom: 15px\">（此验证码五分钟内有效）</p>\n" +
                    "      </div>\n" +
                    "    </div>\n" +
                    "    <div class=\"footer\" style=\"font-style: italic; font-size: small\">\n" +
                    "      @易物团队\n" +
                    "      <div class=\"pull-right\"></div>\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</div>\n", "text/html;charset=utf-8");
        }
        else {
            text.setContent(type,"text/html;charset=utf-8");
        }

        //拼装邮件正文
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(text);
        //文本和图片内嵌成功
        mimeMultipart.setSubType("related");

        //将拼装好的正文内容设置为主体
        MimeBodyPart contentText = new MimeBodyPart();
        contentText.setContent(mimeMultipart);

        //拼接附件
        MimeMultipart allFile = new MimeMultipart();
//        allFile.addBodyPart(appendix);//附件
        //正文
        allFile.addBodyPart(contentText);
        //正文和附件都存在邮件中，所有类型设置为mixed
        allFile.setSubType("mixed");


        //放到Message消息中
        mimeMessage.setContent(allFile);
        mimeMessage.saveChanges();//保存修改

        return mimeMessage;
    }
}

