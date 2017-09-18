package be.ing.fundtransfer.service;

import be.ing.fundtransfer.bean.EmailMessage;
import be.ing.fundtransfer.bean.TransactionData;
import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.data.Transaction;
import be.ing.fundtransfer.data.User;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public interface MailService {
    public String triggerEmail(UserData userData, TransactionData transactionData) ;
    public Date calculateExpiryDate(int expiryTimeInMinutes);
    public String sendEmail(EmailMessage emailMessage)  ;
}
