package model;

import java.util.Date;

public class Message {

	private int msg_num;
	private int send_m_num;
	private int recv_m_num;
	private String msg_title;
	private String msg_content;
	private String chkYN;
	private String send_delYN;
	private String recv_delYN;
	private Date msg_writeDate;
	private String msg_status;
	public int getMsg_num() {
		return msg_num;
	}
	public void setMsg_num(int msg_num) {
		this.msg_num = msg_num;
	}
	public int getSend_m_num() {
		return send_m_num;
	}
	public void setSend_m_num(int send_m_num) {
		this.send_m_num = send_m_num;
	}
	public int getRecv_m_num() {
		return recv_m_num;
	}
	public void setRecv_m_num(int recv_m_num) {
		this.recv_m_num = recv_m_num;
	}
	public String getMsg_title() {
		return msg_title;
	}
	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}
	public String getMsg_content() {
		return msg_content;
	}
	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}
	public String getChkYN() {
		return chkYN;
	}
	public void setChkYN(String chkYN) {
		this.chkYN = chkYN;
	}
	public String getSend_delYN() {
		return send_delYN;
	}
	public void setSend_delYN(String send_delYN) {
		this.send_delYN = send_delYN;
	}
	public String getRecv_delYN() {
		return recv_delYN;
	}
	public void setRecv_delYN(String recv_delYN) {
		this.recv_delYN = recv_delYN;
	}
	public Date getMsg_writeDate() {
		return msg_writeDate;
	}
	public void setMsg_writeDate(Date msg_writeDate) {
		this.msg_writeDate = msg_writeDate;
	}
	public String getMsg_status() {
		return msg_status;
	}
	public void setMsg_status(String msg_status) {
		this.msg_status = msg_status;
	}
	@Override
	public String toString() {
		return "Message [msg_num=" + msg_num + ", send_m_num=" + send_m_num + ", recv_m_num=" + recv_m_num
				+ ", msg_title=" + msg_title + ", msg_content=" + msg_content + ", chkYN=" + chkYN + ", send_delYN="
				+ send_delYN + ", recv_delYN=" + recv_delYN + ", msg_writeDate=" + msg_writeDate + ", msg_status="
				+ msg_status + ", getMsg_num()=" + getMsg_num() + ", getSend_m_num()=" + getSend_m_num()
				+ ", getRecv_m_num()=" + getRecv_m_num() + ", getMsg_title()=" + getMsg_title() + ", getMsg_content()="
				+ getMsg_content() + ", getChkYN()=" + getChkYN() + ", getSend_delYN()=" + getSend_delYN()
				+ ", getRecv_delYN()=" + getRecv_delYN() + ", getMsg_writeDate()=" + getMsg_writeDate()
				+ ", getMsg_status()=" + getMsg_status() + "]";
	}
	
	
}
