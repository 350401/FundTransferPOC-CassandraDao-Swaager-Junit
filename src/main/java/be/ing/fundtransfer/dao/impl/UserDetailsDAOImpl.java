package be.ing.fundtransfer.dao.impl;

import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.dao.UserDetailsDAO;
import be.ing.fundtransfer.data.UserDetails;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataModificationException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import be.ing.fundtransfer.repository.UserDetailsRepository;
import be.ing.fundtransfer.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailsDAOImpl implements UserDetailsDAO {
    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Override
    public String createUserdDetails(UserDetails userDetails) 
    		throws DataInsertionException{
    	try{	
    		userDetailsRepository.save(userDetails);
    	}catch (Exception exp){
    		throw new DataInsertionException("Failed to Create User Details : " + exp.getLocalizedMessage());
    	}
    	return Constants.SUCCESS;
    }

    @Override
    public UserDetails getUserDetailsByUserName(String userName) 
    		throws DataRetrievalException{
    	UserDetails fetchedUserDetails = null;
    	try{
    		fetchedUserDetails = userDetailsRepository.findByUserName(userName);
    		if (fetchedUserDetails == null) {
    			throw new DataRetrievalException("Unable to find UserDetails by UserName : " );
    		}
    	}catch (Exception exp){
    		throw new DataRetrievalException("Failed to Get UserDetails : " + exp.getLocalizedMessage());
    	}
    	return fetchedUserDetails;
    }
    
	@Override
	public boolean updateUser(UserData userData) throws DataModificationException  {
		UserDetails userDetails  = null;
		try {
			userDetails = userDetailsRepository.findByUserName(userData.getUsername());
			userDetails.setCurAmount(userData.getCurAmount());
			userDetails.setAvalAmount(userData.getAvalAmount());
			userDetailsRepository.save(userDetails);
		}catch (Exception exp){
			throw new DataModificationException("Failed to Get User : " + exp.getLocalizedMessage());
		}
		return true;
	}
}
