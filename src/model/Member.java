package model;

import java.util.Date;

public class Member {
	private int m_num;
	private String email;
	private String name;
	private String nickName;
	private String password;
	private String bith;
	private String gender;
	private String type;
	private String pin;
	private Date regDate;
	private String authYN;
	
	public String getAuthYN() {
		return authYN;
	}
	public void setAuthYN(String authYN) {
		this.authYN = authYN;
	}
	public int getM_num() {
		return m_num;
	}
	public void setM_num(int m_num) {
		this.m_num = m_num;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBith() {
		return bith;
	}
	public void setBith(String bith) {
		this.bith = bith;
	}
	public String getGender() {
		return gender;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "Member [m_num=" + m_num + ", email=" + email + ", name=" + name + ", nickName=" + nickName
				+ ", password=" + password + ", bith=" + bith + ", gender=" + gender + ", type=" + type + ", pin=" + pin
				+ ", regDate=" + regDate + ", authYN=" + authYN + ", getAuthYN()=" + getAuthYN() + ", getM_num()="
				+ getM_num() + ", getEmail()=" + getEmail() + ", getName()=" + getName() + ", getNickName()="
				+ getNickName() + ", getPassword()=" + getPassword() + ", getBith()=" + getBith() + ", getGender()="
				+ getGender() + ", getType()=" + getType() + ", getPin()=" + getPin() + ", getRegDate()=" + getRegDate()
				+ "]";
	}
	
	

	
}
