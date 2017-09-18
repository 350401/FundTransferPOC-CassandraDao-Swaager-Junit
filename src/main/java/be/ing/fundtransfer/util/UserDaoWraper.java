package be.ing.fundtransfer.util;

import com.datastax.driver.core.utils.UUIDs;

import be.ing.fundtransfer.bean.UserData;
import be.ing.fundtransfer.data.User;
import be.ing.fundtransfer.data.UserDetails;

public class UserDaoWraper {
    public static User convertUserDataToUser(UserData userData){
        User user = new User();

        if(userData!=null){

        user.setId(UUIDs.timeBased());
        user.setUserName(userData.getUsername());
        user.setFirstName(userData.getFirstName());
        user.setMiddleName(userData.getMiddleName());
        user.setLastName(userData.getLastName());
        user.setEmail(userData.getEmail());
        user.setAddress(userData.getAddress());
        user.setMobileNumber(userData.getMobileNumber());
        user.setPassword(userData.getPassword());
    }
       return user;
    }
    public static UserDetails convertUserDataToUserDetails(UserData userData){
        UserDetails userDetails = new UserDetails();
        if(userData!=null) {

            userDetails.setId(UUIDs.timeBased());
            userDetails.setUsername(userData.getUsername());
            userDetails.setIbanNum(userData.getIbanNum());
            userDetails.setCurAmount(userData.getCurAmount());
            userDetails.setAvalAmount(userData.getAvalAmount());
        }
        return userDetails;
    }
    public static UserData convertUserAndUserDetailsToUserData(User user,UserDetails userDetails){
        UserData userData= new UserData();
        try {
            userData.setUsername(user.getUserName());
            userData.setPassword(user.getPassword());
            userData.setFirstName(user.getFirstName());
            userData.setLastName(user.getLastName());
            userData.setMiddleName(user.getMiddleName());
            userData.setEmail(user.getEmail());
            userData.setAddress(user.getAddress());
            userData.setMobileNumber(user.getMobileNumber());
            userData.setIbanNum(userDetails.getIbanNum());
            userData.setCurAmount(userDetails.getCurAmount());
            userData.setAvalAmount(userDetails.getAvalAmount());
        }
        catch(Exception ex){
            userData= null;
            }
        return userData;
    }
}
