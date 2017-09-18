package be.ing.fundtransfer.data;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import be.ing.fundtransfer.util.AESencrp;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;

import java.util.UUID;

@Table(value="users")
public class User {

	@PrimaryKeyColumn(ordinal=1)
    private UUID id;

    @PrimaryKeyColumn(value = "user_name",type=PrimaryKeyType.PARTITIONED,ordinal=2)
    private String userName;

    @Column(value = "password")
    private String password;

    @Column(value = "first_name")
    private String firstName;

    @Column(value = "last_name")
    private String lastName;

    @Column(value = "middle_name")
    private String middleName;

    @PrimaryKeyColumn(value = "mobile_number",ordinal=3)
    private String mobileNumber;

     // @PrimaryKeyColumn(value = "email_id",ordinal=4)
    @Column(value = "email_id")
    private String email;

    @Column(value = "address")
    private String address;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return AESencrp.decrypt(password);
    }

    public void setPassword(String password) {
        this.password = AESencrp.encrypt(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
/* @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }*/

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
    
	public boolean equals( Object obj ) { 
		if( obj == this ){ 
			return true; 
		} 
		if( obj == null ){ 
			return false; 
		} 
		if( !User.class.isAssignableFrom( obj.getClass() ) ){ 
			return false; 
		} 
		return EqualsBuilder.reflectionEquals( this, obj ); 
	} 

	public int hashCode() { 
		return HashCodeBuilder.reflectionHashCode( this ); 
	}    
}
