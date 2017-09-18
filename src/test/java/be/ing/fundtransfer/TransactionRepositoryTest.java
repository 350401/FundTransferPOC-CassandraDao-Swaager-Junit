package be.ing.fundtransfer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.datastax.driver.core.utils.UUIDs;

import be.ing.fundtransfer.config.CassandraConfig;
import be.ing.fundtransfer.data.Transaction;
import be.ing.fundtransfer.repository.TransactionRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class TransactionRepositoryTest {

    @Autowired
    private CassandraAdminOperations adminTemplate;	
    
    @Autowired
    TransactionRepository transactionRepository;
    
	@Before
	public void setUp() {
        adminTemplate.dropTable(CqlIdentifier.cqlId("user_transactions"));
        adminTemplate.createTable(true, CqlIdentifier.cqlId("user_transactions"), Transaction.class, new HashMap<String, Object>());
	} 
	
	@Test
	public void findSavedTransactionById() {
		Transaction transaction = buildTransaction("pavankumar", "rankireddy", 500);
		transaction = transactionRepository.save(transaction);
		assertThat(transactionRepository.findOne(transaction.getId()), is(transaction));
	}
	
	@Test
	public void findTransactionByInvalidId() {
		assertNull(transactionRepository.findOne(UUIDs.timeBased()));
	}
	
	private Transaction buildTransaction(String fromAccountUser, String toAccountUser, int amount){
        Transaction transaction = new Transaction();
        transaction.setId(UUIDs.timeBased());
        transaction.setFromAccountUser(fromAccountUser);
        transaction.setToAccountUser(toAccountUser);
        transaction.setFromOtp(1234);
        transaction.setToOtp(4567);
        transaction.setTrnsfAmount(amount);
        transaction.setStatus(1);
        return transaction;
	}
	
	
}
