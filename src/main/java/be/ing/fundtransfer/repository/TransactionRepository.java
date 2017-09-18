package be.ing.fundtransfer.repository;

import java.util.UUID;

import be.ing.fundtransfer.data.Transaction;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
    @Query(value="SELECT * FROM users WHERE user_transactions=?0 ALLOW FILTERING")
    public Transaction findByTransactionName(String userName);
}
