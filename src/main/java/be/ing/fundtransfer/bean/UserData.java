package be.ing.fundtransfer.bean;

import org.springframework.data.cassandra.mapping.Column;
import org.apache.commons.lang.builder.ToStringBuilder;

public class UserData {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String middleName;
	private String mobileNumber;
	private String email;
	private String address;
	private String ibanNum;
	private double curAmount;
	private double avalAmount;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
}
