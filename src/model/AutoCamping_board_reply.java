package model;

import java.util.Date;

public class AutoCamping_board_reply {

	private int abr_num;
	private int ac_num;
	private String content;
	private int m_num;
	private Date writeDate;
	private String delyn;
	public int getAbr_num() {
		return abr_num;
	}
	public void setAbr_num(int abr_num) {
		this.abr_num = abr_num;
	}
	public int getAc_num() {
		return ac_num;
	}
	public void setAc_num(int ac_num) {
		this.ac_num = ac_num;
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
		return "AutoCamping_board_reply [abr_num=" + abr_num + ", ac_num=" + ac_num + ", content=" + content
				+ ", m_num=" + m_num + ", writeDate=" + writeDate + ", delyn=" + delyn + ", getAbr_num()="
				+ getAbr_num() + ", getAc_num()=" + getAc_num() + ", getContent()=" + getContent() + ", getM_num()="
				+ getM_num() + ", getWriteDate()=" + getWriteDate() + ", getDelyn()=" + getDelyn() + "]";
	}
	
	
}
