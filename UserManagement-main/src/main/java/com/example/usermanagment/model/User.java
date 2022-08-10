package com.example.usermanagment.model;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="user")
public class User {
	
	@Id

	private String username;
	private String password;
	private String email;
	private String role;
	private String name;
	
	@Column(name="mobile_no")
	private String mobileNo;
	//private Date dob;
	private String gender;
	@OneToMany(cascade=CascadeType.ALL,mappedBy = "user",fetch = FetchType.LAZY)
	private List<Address> addresses =new ArrayList<>();


	@Transient
	private String confirmPassword;


	public User(String username, String password, String email, String role, String name, String mobileNo, Date dob,
				String gender,List<Address> addresses) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.name = name;
		this.mobileNo = mobileNo;
		//this.dob = dob;
		this.gender = gender;
		this.addresses = addresses;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
//	public Date getDob() {
//		return dob;
//	}
//	public void setDob(Date dob) {
//		this.dob = dob;
//	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public User() {
		super();
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public List<User> setAddresses(List<Address> addresses) {
		this.addresses = addresses;
		return null;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}


	@Override
	public String toString() {
		return "User{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", role='" + role + '\'' +
				", name='" + name + '\'' +
				", mobileNo='" + mobileNo + '\'' +
				", gender='" + gender + '\'' +
				", addresses=" + addresses +
				", confirmPassword='" + confirmPassword + '\'' +
				'}';
	}
}
