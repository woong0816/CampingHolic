package model;

import java.util.Date;

public class AutoCampingBoard {

	private int ac_num;
	private String ac_title;
	private String ac_content;
	private String ac_address;
	private int readCount;
	private int m_num;
	private Date writeDate;
	private String denly;
	public int getAc_num() {
		return ac_num;
	}
	public void setAc_num(int ac_num) {
		this.ac_num = ac_num;
	}
	public String getAc_title() {
		return ac_title;
	}
	public void setAc_title(String ac_title) {
		this.ac_title = ac_title;
	}
	public String getAc_content() {
		return ac_content;
	}
	public void setAc_content(String ac_content) {
		this.ac_content = ac_content;
	}
	public String getAc_address() {
		return ac_address;
	}
	public void setAc_address(String ac_address) {
		this.ac_address = ac_address;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
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
	public String getDenly() {
		return denly;
	}
	public void setDenly(String denly) {
		this.denly = denly;
	}
	@Override
	public String toString() {
		return "AutoCampingBoard [ac_num=" + ac_num + ", ac_title=" + ac_title + ", ac_content=" + ac_content
				+ ", ac_address=" + ac_address + ", readCount=" + readCount + ", m_num=" + m_num + ", writeDate="
				+ writeDate + ", denly=" + denly + ", getAc_num()=" + getAc_num() + ", getAc_title()=" + getAc_title()
				+ ", getAc_content()=" + getAc_content() + ", getAc_address()=" + getAc_address() + ", getReadCount()="
				+ getReadCount() + ", getM_num()=" + getM_num() + ", getWriteDate()=" + getWriteDate() + ", getDenly()="
				+ getDenly() + "]";
	}
	
	
}
