package model;

import java.util.Date;

public class BackCampingBoard {

	private int bc_num;
	private String bc_title;
	private String bc_content;
	private String bc_address;
	private int readCount;
	private int m_num;
	private Date writedate;
	private String denYN;
	
	public int getBc_num() {
		return bc_num;
	}
	public void setBc_num(int bc_num) {
		this.bc_num = bc_num;
	}
	public String getBc_title() {
		return bc_title;
	}
	public void setBc_title(String bc_title) {
		this.bc_title = bc_title;
	}
	public String getBc_content() {
		return bc_content;
	}
	public void setBc_content(String bc_content) {
		this.bc_content = bc_content;
	}
	public String getBc_address() {
		return bc_address;
	}
	public void setBc_address(String bc_address) {
		this.bc_address = bc_address;
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
	public Date getWritedate() {
		return writedate;
	}
	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}
	public String getDenYN() {
		return denYN;
	}
	public void setDenYN(String denYN) {
		this.denYN = denYN;
	}
	@Override
	public String toString() {
		return "BackCampingBoard [bc_num=" + bc_num + ", bc_title=" + bc_title + ", bc_content=" + bc_content
				+ ", bc_address=" + bc_address + ", readCount=" + readCount + ", m_num=" + m_num + ", writedate="
				+ writedate + ", denYN=" + denYN + ", getBc_num()=" + getBc_num() + ", getBc_title()=" + getBc_title()
				+ ", getBc_content()=" + getBc_content() + ", getBc_address()=" + getBc_address() + ", getReadCount()="
				+ getReadCount() + ", getM_num()=" + getM_num() + ", getWritedate()=" + getWritedate() + ", getDenYN()="
				+ getDenYN() + "]";
	}
	
	
	
}
