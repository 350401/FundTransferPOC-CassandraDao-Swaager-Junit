package be.ing.fundtransfer.service.impl;

import be.ing.fundtransfer.bean.TransactionData;
import be.ing.fundtransfer.dao.FundsTransferDAO;
import be.ing.fundtransfer.data.Transaction;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import be.ing.fundtransfer.service.TransactionService;
import be.ing.fundtransfer.util.Constants;
import be.ing.fundtransfer.util.UserDaoWraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    FundsTransferDAO fundsTransferDAO;

    @Override
    public String createTransaction(TransactionData transactionData) throws DataInsertionException {
        try {
            boolean status= fundsTransferDAO.createFundsTransfer(transactionData);

            if(status==true){
                return Constants.SUCCESS;
            }else{
                return Constants.FAILED;
            }
        }catch(Exception ex) {
            throw new DataInsertionException(ex.getLocalizedMessage());
        }
    }

    @Override
    public TransactionData getTransaction(TransactionData transactionData) throws DataRetrievalException {
        try {
           return fundsTransferDAO.getTransaction(UUID.fromString(transactionData.getId()));
        }catch(Exception ex) {
            throw new DataRetrievalException(ex.getLocalizedMessage());
        }
    }

    @Override
    public String updateTransaction(TransactionData transactionData)throws DataInsertionException {
        try {
            boolean status= fundsTransferDAO.createFundsTransfer(transactionData);
            if(status==true){
                return Constants.SUCCESS;
            }else{
                return Constants.FAILED;
            }
        }catch(Exception ex) {
            throw new DataInsertionException(ex.getLocalizedMessage());
        }
    }
    @Override
    public String deleteTransaction(TransactionData transactionData) {
        return null;
    }
}
