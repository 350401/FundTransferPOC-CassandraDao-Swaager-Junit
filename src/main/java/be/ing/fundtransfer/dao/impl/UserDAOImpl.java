package be.ing.fundtransfer.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import be.ing.fundtransfer.dao.UserDAO;
import be.ing.fundtransfer.data.User;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import be.ing.fundtransfer.repository.UserRepository;


@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	UserRepository userRepository;

	@Override
	public User createUser(User user) throws DataInsertionException {

		try {
			return userRepository.save(user);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new DataInsertionException("Failed to Create User : " + ex.getLocalizedMessage());
		}
		//return null;
	}



	@Override
	public User getUserByEmail(String email) throws DataRetrievalException {
		User user = null;
		try {
			user = userRepository.findByEmail(email);
			if (user == null) {
				throw new DataRetrievalException("User Not found BY Email : " );
			}
		}catch (Exception exp){
			throw new DataRetrievalException("Failed to Get User : " + exp.getLocalizedMessage());
		}
		return user;
	}

	@Override
	public User getUserByUserName(String userName) throws DataRetrievalException {

		User user = null;
		try {
			user = userRepository.findByUserName(userName);
			if (user == null) {
				throw new DataRetrievalException("User Not found BY  " + userName);
			}
		}catch (Exception exp){
			throw new DataRetrievalException("Failed to Get User : " + exp.getLocalizedMessage());
		}
		return user;
	}	

	@Override
	public Iterable<User> findAllUsers() throws DataRetrievalException {
		try {
			return userRepository.findAll();
		}catch (Exception exp){
			throw new DataRetrievalException("Failed to Get User : " 
					+ exp.getLocalizedMessage());
		}
	}
}
