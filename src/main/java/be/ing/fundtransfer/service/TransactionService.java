package be.ing.fundtransfer.service;

import be.ing.fundtransfer.bean.TransactionData;
import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.data.Transaction;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    public String createTransaction(TransactionData transactionData) throws DataInsertionException;
    public TransactionData getTransaction(TransactionData transactionData) throws DataRetrievalException;
    public String updateTransaction(TransactionData transactionData) throws DataInsertionException;
    public String deleteTransaction(TransactionData transactionData)throws DataRetrievalException;
}




