package be.ing.fundtransfer.service.impl;



import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.dao.UserDAO;

import be.ing.fundtransfer.dao.UserDetailsDAO;
import be.ing.fundtransfer.data.User;
import be.ing.fundtransfer.data.UserDetails;
import be.ing.fundtransfer.exception.DataInsertionException;
import be.ing.fundtransfer.exception.DataModificationException;
import be.ing.fundtransfer.exception.DataRetrievalException;
import be.ing.fundtransfer.util.Constants;
import be.ing.fundtransfer.service.UserService;
import be.ing.fundtransfer.util.UserDaoWraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserDetailsDAO userDetailsDAO;


    @Override
    public String createUser(UserData userData) throws DataInsertionException{
        try {
            userDAO.createUser(UserDaoWraper.convertUserDataToUser(userData));
            userDetailsDAO.createUserdDetails(UserDaoWraper.convertUserDataToUserDetails(userData));
            return Constants.SUCCESS;
        }catch(Exception ex) {
            throw new DataInsertionException(ex.getLocalizedMessage());
        }
    }
    @Override
    public UserData getByUserName(String userName)throws DataRetrievalException {
        try {
             User user = userDAO.getUserByUserName(userName);
             UserDetails userDetails =userDetailsDAO.getUserDetailsByUserName(userName);
              return UserDaoWraper.convertUserAndUserDetailsToUserData(user, userDetails);
        }catch(Exception ex) {
            throw new DataRetrievalException(ex.getLocalizedMessage());
        }
    }

    @Override
    public UserData getByUserEmail(String email) throws DataRetrievalException{
        try {
            User user = userDAO.getUserByEmail(email);
            UserDetails userDetails =userDetailsDAO.getUserDetailsByUserName(user.getUserName());
            return UserDaoWraper.convertUserAndUserDetailsToUserData(user, userDetails);
        }catch(Exception ex) {
            throw new DataRetrievalException(ex.getLocalizedMessage());
        }
    }

    @Override
    public boolean updateUser(UserData user) throws DataModificationException {
        try {
    	  userDetailsDAO.updateUser(user);
        }catch(Exception ex) {
            throw new DataModificationException(ex.getLocalizedMessage());
        }
        return true;
    }

    @Override
    public boolean deleteUser(UserData user) {
/*        try {
        	userDAO.deleteUser(user);
        }catch(Exception ex) {
            throw new DataModificationException(ex.getLocalizedMessage());
        }*/
        return true;
    }

    @Override
    public List<UserData> findAllUsers() throws DataRetrievalException{
    	List<UserData> userList = new ArrayList<>();
    	try{
    		Iterable<User> users = userDAO.findAllUsers();
    		for(User usr: users){
    			userList.add(
    					UserDaoWraper.convertUserAndUserDetailsToUserData(usr
    							,userDetailsDAO.getUserDetailsByUserName(usr.getUserName())));
    		}
    	}catch(Exception ex) {
    		throw new DataRetrievalException(ex.getLocalizedMessage());
    	}
    	return userList;
    }
}
