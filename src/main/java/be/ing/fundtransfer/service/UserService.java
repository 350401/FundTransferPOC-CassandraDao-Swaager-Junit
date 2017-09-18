package be.ing.fundtransfer.service;
import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataModificationException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import org.springframework.stereotype.Service;
import org.w3c.dom.UserDataHandler;

import java.util.List;

@Service
public interface UserService {
    public String createUser(UserData userData) throws DataInsertionException;
    public UserData getByUserName(String userName)throws DataRetrievalException;
    public UserData getByUserEmail(String email)throws DataRetrievalException;
    public boolean updateUser(UserData userData)throws DataModificationException;
    public boolean deleteUser(UserData userData)throws DataRetrievalException;
    public List<UserData> findAllUsers()throws DataRetrievalException;
}
