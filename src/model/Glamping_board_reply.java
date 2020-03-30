package model;

import java.util.Date;

public class Glamping_board_reply {

	private int gbr_num;
	private int gl_num;
	private String content;
	private int m_num;
	private Date writeDate;
	private String delyn;
	private String nickName;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getGbr_num() {
		return gbr_num;
	}
	public void setGbr_num(int gbr_num) {
		this.gbr_num = gbr_num;
	}
	public int getGl_num() {
		return gl_num;
	}
	public void setGl_num(int gl_num) {
		this.gl_num = gl_num;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getM_num() {
		return m_num;
	}
	public void setM_num(int m_num) {
		this.m_num = m_num;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public String getDelyn() {
		return delyn;
	}
	public void setDelyn(String delyn) {
		this.delyn = delyn;
	}
	@Override
	public String toString() {
		return "Glamping_board_reply [gbr_num=" + gbr_num + ", gl_num=" + gl_num + ", content=" + content + ", m_num="
				+ m_num + ", writeDate=" + writeDate + ", delyn=" + delyn + ", nickName=" + nickName
				+ ", getNickName()=" + getNickName() + ", getGbr_num()=" + getGbr_num() + ", getGl_num()=" + getGl_num()
				+ ", getContent()=" + getContent() + ", getM_num()=" + getM_num() + ", getWriteDate()=" + getWriteDate()
				+ ", getDelyn()=" + getDelyn() + "]";
	}
	
	
	
	
	
	
}
