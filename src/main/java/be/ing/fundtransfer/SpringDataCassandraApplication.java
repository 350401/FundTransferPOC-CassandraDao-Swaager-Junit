package be.ing.fundtransfer;

import be.ing.fundtransfer.dao.UserDAO;
import be.ing.fundtransfer.data.User;


import be.ing.fundtransfer.data.UserDetails;
import be.ing.fundtransfer.repository.TransactionRepository;
import be.ing.fundtransfer.repository.UserDetailsRepository;
import be.ing.fundtransfer.repository.UserRepository;
import be.ing.fundtransfer.util.AESencrp;

import com.datastax.driver.core.utils.UUIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Properties;
import java.util.UUID;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"be.ing.fundtransfer.config","be.ing.fundtransfer.controller","be.ing.fundtransfer.service","be.ing.fundtransfer.repository","be.ing.fundtransfer.dao"})
@EnableCassandraRepositories("be.ing.fundtransfer.repository")
public class SpringDataCassandraApplication implements CommandLineRunner{
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    UserDAO userDAO;

    @Autowired
    TransactionRepository transactionRepository;

    public static void main(String[] args) {
        SpringApplication app =
                new SpringApplication(SpringDataCassandraApplication.class);
        Properties properties = new Properties();
        properties.setProperty("spring.resources.staticLocations",
                "classpath:/static/, classpath:/static/test/");
        app.setDefaultProperties(properties);
        SpringApplication.run(SpringDataCassandraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        clearData();
        saveData();
        lookup();
    }

    public void clearData(){
        userDetailsRepository.deleteAll();
        userRepository.deleteAll();
        transactionRepository.deleteAll();

    }

    public void saveData(){

        System.out.println("===================Save Users to Cassandra===================");
        User user = new User();
        UUID uid= UUIDs.timeBased();
        System.out.println(uid);
        user.setId(uid);
        user.setPassword("Madhava");
        user.setUserName("SathyaPavan");
        user.setFirstName("Madhava");
        user.setLastName("Kalli");
        user.setMiddleName("Reddy");
        user.setMobileNumber("8056201250");
        user.setEmail("kalli.madhavareddy@gmail.com");
        userRepository.save(user);

        UserDetails userDetails = new UserDetails();
        userDetails.setId(uid);
        userDetails.setUsername("SathyaPavan");
        userDetails.setIbanNum("madhava123");
        userDetails.setCurAmount(1000);
        userDetails.setAvalAmount(1000);
        System.out.println(":::::::::::::::::::::::Testing::::");
        System.out.println(userDetails);
        System.out.println(":::::::::::::::::::::::Testing::::end");

        userDetailsRepository.save(userDetails);
    }

    public void lookup(){

        System.out.println("===================Lookup All Users from Cassandra ===================");
        Iterable<User> userList = userRepository.findAll();
        userList.forEach(System.out::println);

        System.out.println("===================Lookup All UserDetails from Cassandra ===================");
        Iterable<UserDetails> UserDetailsList = userDetailsRepository.findAll();
        UserDetailsList.forEach(System.out::println);

        System.out.println(" =================== DAO ACcess==================== ");
        try{
            User user = userDAO.getUserByUserName("SathyaPavan");
            System.out.println(" getUserByUserName " + user);


        user = userDAO.getUserByEmail("kalli.madhavareddy@gmail.com");
        System.out.println(" getUserByEmail " + user);
        
        String password = "mypassword";
        String passwordEnc = AESencrp.encrypt(password);
        String passwordDec = AESencrp.decrypt(passwordEnc);

        System.out.println("Plain Text : " + password);
        System.out.println("Encrypted Text : " + passwordEnc);
        System.out.println("Decrypted Text : " + passwordDec);
        
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
