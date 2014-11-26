package com.ysten.local.bss.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSender {

    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
    private static final String TEXT = "text";
    private static final String HTML = "html";
    
    private boolean sendMail(MailBean mailInfo, String mailText) {
        MailAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            authenticator = new MailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            Message mailMessage = new MimeMessage(sendMailSession);
            Address from = new InternetAddress(mailInfo.getFromAddress());
            mailMessage.setFrom(from);
            if (mailInfo.getToAddress() != null) {
                Address to = new InternetAddress(mailInfo.getToAddress());
                mailMessage.setRecipient(Message.RecipientType.TO, to);
            } else if (mailInfo.getToArrayAddress() != null) {
                Address[] address = new Address[mailInfo.getToArrayAddress().length];
                for (int i = 0; i < mailInfo.getToArrayAddress().length; i++) {
                    address[i] = new InternetAddress(mailInfo.getToArrayAddress()[i]);
                }
                mailMessage.setRecipients(Message.RecipientType.TO, address);
            } else {
                return false;
            }
            mailMessage.setSubject(mailInfo.getTitle());
            mailMessage.setSentDate(new Date());
            String mailContent = mailInfo.getContent();
            if(HTML.equals(mailText)){
                mailMessage.setContent(mailContent, "text/html;charset=utf-8");
            }else{
                mailMessage.setText(mailContent);
            }
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            if (logger.isErrorEnabled()) {
                logger.error("MailSender sendTextMail exception{}" + ex);
            }
        }
        return false;
    }
    
    public boolean sendTextMail(MailBean mailInfo){
        return sendMail(mailInfo, TEXT);
    }
    
    public boolean sendHtmlMail(MailBean mailInfo){
        return sendMail(mailInfo, HTML);
    }

}