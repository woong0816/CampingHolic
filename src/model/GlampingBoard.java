package model;

import java.util.Date;

public class GlampingBoard {

	private int gl_num;
	private String gl_title;
	private String gl_content;
	private String gl_address;
	private int readcount;
	private int m_num;
	private Date writedate;
	
	public int getGl_num() {
		return gl_num;
	}
	public void setGl_num(int gl_num) {
		this.gl_num = gl_num;
	}
	public String getGl_title() {
		return gl_title;
	}
	public void setGl_title(String gl_title) {
		this.gl_title = gl_title;
	}
	public String getGl_content() {
		return gl_content;
	}
	public void setGl_content(String gl_content) {
		this.gl_content = gl_content;
	}
	public String getGl_address() {
		return gl_address;
	}
	public void setGl_address(String gl_address) {
		this.gl_address = gl_address;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public int getM_num() {
		return m_num;
	}
	public void setM_num(int m_num) {
		this.m_num = m_num;
	}
	public Date getwritedate() {
		return writedate;
	}
	public void setwritedate(Date writedate) {
		this.writedate = writedate;
	}
	@Override
	public String toString() {
		return "GlampingBoard [gl_num=" + gl_num + ", gl_title=" + gl_title + ", gl_content=" + gl_content
				+ ", gl_address=" + gl_address + ", readcount=" + readcount + ", m_num=" + m_num + ", writedate="
				+ writedate + ", getGl_num()=" + getGl_num() + ", getGl_title()=" + getGl_title() + ", getGl_content()="
				+ getGl_content() + ", getGl_address()=" + getGl_address() + ", getReadcount()=" + getReadcount()
				+ ", getM_num()=" + getM_num() + ", getwritedate()=" + getwritedate() + "]";
	}
	
}
