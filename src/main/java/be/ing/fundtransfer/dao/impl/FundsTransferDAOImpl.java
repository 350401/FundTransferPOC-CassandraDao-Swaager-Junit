package be.ing.fundtransfer.dao.impl;


import be.ing.fundtransfer.bean.TransactionData;
import be.ing.fundtransfer.dao.FundsTransferDAO;
import be.ing.fundtransfer.data.Transaction;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.repository.TransactionRepository;
import com.datastax.driver.core.utils.UUIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public class FundsTransferDAOImpl implements FundsTransferDAO {

	@Autowired
	TransactionRepository transactionRepository;
	
	@Override
	public boolean createFundsTransfer(TransactionData transactionData) {

		Transaction transactionObj = null;
		
		transactionObj = new Transaction();
		transactionObj.setId(UUIDs.timeBased());
		transactionObj.setFromAccountUser(transactionData.getFromAccount());
		transactionObj.setToAccountUser(transactionData.getToAccount());
		transactionObj.setFromOtp(transactionData.getFromOtp());
		transactionObj.setToOtp(transactionData.getToOtp());
		transactionObj.setTrnsfAmount(transactionData.getTrnsfAmount());
		transactionObj.setStatus(transactionData.getStatus());
		transactionRepository.save(transactionObj);
		return true;
	}

	@Override
	public TransactionData getTransaction(UUID transId) {
		Transaction transactionObj = null;
		
		TransactionData	  transactionData = new TransactionData();
		transactionObj = transactionRepository.findOne(transId);
		transactionData.setId(transactionObj.getId().toString());
		transactionData.setToAccount(transactionObj.getToAccountUser());
		transactionData.setFromAccount(transactionObj.getFromAccountUser());
		transactionData.setFromOtp(transactionObj.getFromOtp());
		transactionData.setToOtp(transactionObj.getToOtp());
		transactionData.setTrnsfAmount(transactionObj.getTrnsfAmount());
	
		return transactionData;
	}

	@Override
	public boolean updateTransaction(TransactionData transactionData) throws DataInsertionException {
		Transaction transactionObj = null;

		transactionObj = new Transaction();

		transactionObj.setFromAccountUser(transactionData.getFromAccount());
		transactionObj.setToAccountUser(transactionData.getToAccount());
		transactionObj.setFromOtp(transactionData.getFromOtp());
		transactionObj.setToOtp(transactionData.getToOtp());
		transactionObj.setTrnsfAmount(transactionData.getTrnsfAmount());
		transactionObj.setStatus(transactionData.getStatus());

		transactionRepository.save(transactionObj);
		return true;
	}
}
