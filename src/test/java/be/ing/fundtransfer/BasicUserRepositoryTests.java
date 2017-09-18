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
import be.ing.fundtransfer.data.User;
import be.ing.fundtransfer.data.UserDetails;
import be.ing.fundtransfer.repository.UserDetailsRepository;
import be.ing.fundtransfer.repository.UserRepository;
import be.ing.fundtransfer.util.AESencrp;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class BasicUserRepositoryTests {

    @Autowired
    private CassandraAdminOperations adminTemplate;	
    
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;  
    
	@Before
	public void setUp() {
		
        adminTemplate.dropTable(CqlIdentifier.cqlId("users"));
        adminTemplate.createTable(true, CqlIdentifier.cqlId("users"), User.class, new HashMap<String, Object>());
	    
        adminTemplate.dropTable(CqlIdentifier.cqlId("user_details"));
        adminTemplate.createTable(true, CqlIdentifier.cqlId("user_details"), UserDetails.class, new HashMap<String, Object>());
	}
	
	private User buildUser(String userName,String mobileNum,String emailId){
        User user = new User();
        user.setId(UUIDs.timeBased());
        user.setUserName(userName);
        user.setPassword("pavan");
        user.setFirstName("pavan");
        user.setLastName("rankireddy");
        user.setMobileNumber(mobileNum);
        user.setEmail(emailId);
        return user;
	}
	
	private UserDetails buildUserDetails(String userName,String IbanNum){
		UserDetails userDetails = new UserDetails();
		userDetails.setId(UUIDs.timeBased());
        userDetails.setUsername(userName);
        userDetails.setIbanNum(IbanNum);
        userDetails.setCurAmount(1000);
        userDetails.setAvalAmount(1000);
        return userDetails;
	}
	
	/**
	 * Saving an object using the Cassandra Repository will create a persistent representation of the object in Cassandra.
	 */
	
	@Test
	public void findSavedUserByUserName() {
		User user = buildUser("pavankumar", "9701001050", "pavan.rankireddy@gmail.com");
		user = userRepository.save(user);
		assertThat(userRepository.findByUserName(user.getUserName()), is(user));
	}
	

	@Test
	public void findUserByEmail() {
		User user = buildUser("rankireddy", "8897611195", "rankireddy@gmail.com");
		user = userRepository.save(user);
		assertThat(userRepository.findByEmail( "rankireddy@gmail.com"), is(user));
	}
	
	@Test
	public void findUserByInvalidEmail() {
		assertNull(userRepository.findByEmail( "test@gmail.com"));
	}
	
	@Test
	public void findSavedUserDetailsByUserName() {
		UserDetails userDetails = buildUserDetails("pavankumar", "pavankumar");
		userDetails =  userDetailsRepository.save(userDetails);
		assertThat(userDetailsRepository.findByUserName(userDetails.getUsername()), is(userDetails));
	}	
	
}
