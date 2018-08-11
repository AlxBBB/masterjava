package ru.javaops.masterjava.service.mail;

import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import ru.javaops.masterjava.config.Configs;

import java.util.List;

@Slf4j
public class MailSender {

    private static Config mailConfig = Configs.getConfig("mail.conf", "mail");

    static void sendMail(List<Addressee> to, List<Addressee> cc, String subject, String body) {
        try {
            final Email email = new SimpleEmail();
            email.setHostName(mailConfig.getString("host"));
            email.setSmtpPort(mailConfig.getInt("port"));
            email.setSSLOnConnect(mailConfig.getBoolean("useSSL"));
            email.setStartTLSEnabled(mailConfig.getBoolean("useTLS"));
            email.setAuthenticator(new DefaultAuthenticator(mailConfig.getString("username"), mailConfig.getString("password")));
            email.setDebug(mailConfig.getBoolean("debug"));
            email.setFrom(mailConfig.getString("username"));
            to.forEach(t -> {
                try {
                    email.addTo(t.getEmail());
                } catch (EmailException ex) {
                    ex.printStackTrace();
                }
            });
            cc.forEach(c -> {
                try {
                    email.addCc(c.getEmail());
                } catch (EmailException ex) {
                    ex.printStackTrace();
                }
            });
            email.setSubject(subject);
            email.setMsg(body);
            email.send();
            log.info("Send mail to \'" + to + "\' cc \'" + cc + "\' subject \'" + subject + (log.isDebugEnabled() ? "\nbody=" + body : ""));
        } catch (
                EmailException e)

        {
            e.printStackTrace();
        }

    }
}
