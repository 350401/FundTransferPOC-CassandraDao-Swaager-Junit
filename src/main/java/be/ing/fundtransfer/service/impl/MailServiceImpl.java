package be.ing.fundtransfer.service.impl;

import be.ing.fundtransfer.bean.EmailMessage;
import be.ing.fundtransfer.bean.TransactionData;
import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.data.Transaction;
import be.ing.fundtransfer.data.User;
import be.ing.fundtransfer.service.MailService;
import be.ing.fundtransfer.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender sender;

    @Override
    public String triggerEmail(UserData userData, TransactionData transactiondata)  {
        String mailStatus = null;
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(userData.getEmail());
            String confirmationUrl
                    = "/transferConfirmation.html?uid=" + transactiondata.getId();
            helper.setText("Please Enter OTP in Below Link and Confirm\n"+ "http://localhost:8080" + confirmationUrl);
            helper.setSubject(userData.getUsername()+ Constants.BLANK+"Initiated Fund Transfer");

            try {
                sender.send(message);
                mailStatus = "{\"message\": \"OK\"}";
            } catch (Exception e) {
                e.printStackTrace();
                mailStatus = "{\"message\": \"Error\"}";
            }
        }catch(Exception ex){
            mailStatus = "{\"message\": \"Error\"}";
        }
        return mailStatus;
    }

    @Override
    public String sendEmail(EmailMessage emailMessage)  {
        String mailStatus = null;
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(emailMessage.getEmail());
            String confirmationUrl
                    = "/transferConfirmation.html?uid=" + emailMessage.getTransactionId();
            helper.setText("Please Enter OTP in Below Link and Confirm\n"+ "http://localhost:8080" + confirmationUrl);
            helper.setSubject(emailMessage.getUserName()+ Constants.BLANK+"Initiated Fund Transfer");
            try {
                sender.send(message);
                mailStatus = "{\"message\": \"OK\"}";
            } catch (Exception e) {
                e.printStackTrace();
                mailStatus = "{\"message\": \"Error\"}";
            }
        }catch(Exception ex){
            mailStatus = "{\"message\": \"Error\"}";
        }
        return mailStatus;
    }

    @Override
    public Date calculateExpiryDate(int expiryTimeInMinutes) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());




            /* smtp server
            Send Email though Java using smtp
            CASANDRA DB:::UserID |OTP
            Send OTP to MOBILE
            generate a unique key, encrypt it and add it to registration link (http://localhost:8080?sId=XXXXXXXXXXX) and save non-encrypted key in database (you need some kind of cross verification process)
            when user clicks link, grab the key and decrypt it and validate the key with key in database
            Validte User
            Current Transefer Status
            Validate OTP*/

    }
}
