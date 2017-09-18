package be.ing.fundtransfer.data;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.UUID;

/*import org.apache.commons.lang.builder.ToStringBuilder;*/

@Table(value="user_transactions")
public class Transaction {

	@PrimaryKey
	private UUID id;
	
	@Column(value = "from_user_name")	
	private String fromAccountUser;
	
	@Column(value = "to_user_name")	
	private String toAccountUser;
	
	@Column(value = "transfer_amount")		
	private double trnsfAmount;
	
	@Column(value = "from_otp")		
	private int fromOtp;

	@Column(value = "to_otp")		
	private int toOtp;
	
	@Column(value = "status")		
	private int status;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFromAccountUser() {
		return fromAccountUser;
	}

	public void setFromAccountUser(String fromAccountUser) {
		this.fromAccountUser = fromAccountUser;
	}

	public String getToAccountUser() {
		return toAccountUser;
	}

	public void setToAccountUser(String toAccountUser) {
		this.toAccountUser = toAccountUser;
	}

	public double getTrnsfAmount() {
		return trnsfAmount;
	}

	public void setTrnsfAmount(double trnsfAmount) {
		this.trnsfAmount = trnsfAmount;
	}

	public int getFromOtp() {
		return fromOtp;
	}

	public void setFromOtp(int fromOtp) {
		this.fromOtp = fromOtp;
	}

	public int getToOtp() {
		return toOtp;
	}

	public void setToOtp(int toOtp) {
		this.toOtp = toOtp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() 	{
	    return ToStringBuilder.reflectionToString(this);
	}
	
	public boolean equals( Object obj ) { 
		if( obj == this ){ 
			return true; 
		} 
		if( obj == null ){ 
			return false; 
		} 
		if( !Transaction.class.isAssignableFrom( obj.getClass() ) ){ 
			return false; 
		} 
		return EqualsBuilder.reflectionEquals( this, obj ); 
	} 

	public int hashCode() { 
		return HashCodeBuilder.reflectionHashCode( this ); 
	} 	
	
}
