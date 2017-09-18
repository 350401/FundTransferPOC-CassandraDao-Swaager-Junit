package be.ing.fundtransfer.repository;

import be.ing.fundtransfer.data.UserDetails;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface UserDetailsRepository  extends CrudRepository<UserDetails, UUID> {
	@Query(value="SELECT * FROM user_details WHERE user_name=?0 ALLOW FILTERING")
	public UserDetails findByUserName(String userName);
	/*@Query(value="delete FROM user_details WHERE user_name=?0 ALLOW FILTERING")
	public String deleteByUserName(String userName);*/

}
