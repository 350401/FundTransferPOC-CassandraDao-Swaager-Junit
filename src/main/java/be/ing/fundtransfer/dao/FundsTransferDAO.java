package be.ing.fundtransfer.dao;


import java.util.UUID;

import org.springframework.stereotype.Repository;

import be.ing.fundtransfer.bean.TransactionData;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;
@Repository
public interface FundsTransferDAO{
	public boolean createFundsTransfer(TransactionData transactionData)throws DataInsertionException;
	public TransactionData getTransaction(UUID transId) throws DataRetrievalException;
	public boolean updateTransaction(TransactionData transactionData)throws DataInsertionException;
}
