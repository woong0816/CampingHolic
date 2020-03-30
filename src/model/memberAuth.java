package model;

public class memberAuth {
	private int m_num;
	private String authKey;
	private String authYN;
	public int getm_num() {
		return m_num;
	}
	public void setm_num(int m_num) {
		this.m_num = m_num;
	}
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public String getAuthYN() {
		return authYN;
	}
	public void setAuthYN(String authYN) {
		this.authYN = authYN;
	}
	@Override
	public String toString() {
		return "memberAuth [m_num=" + m_num + ", authKey=" + authKey + ", authYN=" + authYN + ", getm_num()="
				+ getm_num() + ", getAuthKey()=" + getAuthKey() + ", getAuthYN()=" + getAuthYN() + "]";
	}
	
	
	
	
	
}
