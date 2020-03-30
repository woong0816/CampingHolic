package model;

import java.util.Date;

public class BackCamping_board_reply {
	private int bbr_num;
	private int bc_num;
	private String content;
	private int m_num;
	private Date writeDate;
	private String delyn;
	public int getBbr_num() {
		return bbr_num;
	}
	public void setBbr_num(int bbr_num) {
		this.bbr_num = bbr_num;
	}
	public int getBc_num() {
		return bc_num;
	}
	public void setBc_num(int bc_num) {
		this.bc_num = bc_num;
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
		return "BackCamping_board_reply [bbr_num=" + bbr_num + ", bc_num=" + bc_num + ", content=" + content
				+ ", m_num=" + m_num + ", writeDate=" + writeDate + ", delyn=" + delyn + ", getBbr_num()="
				+ getBbr_num() + ", getBc_num()=" + getBc_num() + ", getContent()=" + getContent() + ", getM_num()="
				+ getM_num() + ", getWriteDate()=" + getWriteDate() + ", getDelyn()=" + getDelyn() + "]";
	}
	
	
}
