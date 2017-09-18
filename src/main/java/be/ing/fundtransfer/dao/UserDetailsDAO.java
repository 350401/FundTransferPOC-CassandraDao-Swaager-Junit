package be.ing.fundtransfer.dao;

import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.data.UserDetails;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataModificationException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsDAO {
    public String createUserdDetails(UserDetails userDetails) throws DataInsertionException;
    public UserDetails getUserDetailsByUserName(String userName)throws DataRetrievalException;
	public boolean updateUser(UserData userData)throws DataModificationException;
}
