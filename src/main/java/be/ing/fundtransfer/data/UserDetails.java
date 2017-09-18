package be.ing.fundtransfer.data;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.UUID;

@Table(value="user_details")
public class UserDetails {
	@PrimaryKey
	private UUID id;

	@Column(value = "user_name")	
	private String username;
	
	@Column(value = "iban_number")	
	private String ibanNum;
	
	@Column(value = "current_Amount")		
	private double curAmount;
	
	@Column(value = "available_amount")		
	private double avalAmount;

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIbanNum() {
		return ibanNum;
	}
	public void setIbanNum(String ibanNum) {
		this.ibanNum = ibanNum;
	}
	public double getCurAmount() {
		return curAmount;
	}
	public void setCurAmount(double curAmount) {
		this.curAmount = curAmount;
	}
	public double getAvalAmount() {
		return avalAmount;
	}
	public void setAvalAmount(double avalAmount) {
		this.avalAmount = avalAmount;
	}

	@Override
	public String toString() {
		return "UserDetails{" +
				"id=" + id +
				", username='" + username + '\'' +
				", ibanNum='" + ibanNum + '\'' +
				", curAmount=" + curAmount +
				", avalAmount=" + avalAmount +
				'}';
	}
	
	public boolean equals( Object obj ) { 
		if( obj == this ){ 
			return true; 
		} 
		if( obj == null ){ 
			return false; 
		} 
		if( !UserDetails.class.isAssignableFrom( obj.getClass() ) ){ 
			return false; 
		} 
		return EqualsBuilder.reflectionEquals( this, obj ); 
	} 

	public int hashCode() { 
		return HashCodeBuilder.reflectionHashCode( this ); 
	} 	
}
