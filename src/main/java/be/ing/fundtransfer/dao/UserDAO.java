package be.ing.fundtransfer.dao;


import org.springframework.stereotype.Repository;

import be.ing.fundtransfer.data.User;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;

@Repository
public interface UserDAO {

	public User createUser(User user) throws DataInsertionException;
	public User getUserByEmail(String userEamil) throws DataRetrievalException;
	public User getUserByUserName(String userName)throws DataRetrievalException;
	public Iterable<User> findAllUsers()throws DataRetrievalException;
}
