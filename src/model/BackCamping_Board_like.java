package model;

public class BackCamping_Board_like {
	private int bbc_num;
	private int bc_num;
	private int m_num;
	private int liketo;
	
	public int getBbc_num() {
	
		return bbc_num;
	}
	public void setBbc_num(int bbc_num) {
		this.bbc_num = bbc_num;
	}
	public int getBc_num() {
		return bc_num;
	}
	public void setBc_num(int bc_num) {
		this.bc_num = bc_num;
	}
	public int getM_num() {
		return m_num;
	}
	public void setM_num(int m_num) {
		this.m_num = m_num;
	}
	public int getLiketo() {
		return liketo;
	}
	public void setLiketo(int liketo) {
		this.liketo = liketo;
	}
	@Override
	public String toString() {
		return "BackCamping_Board_like [bbc_num=" + bbc_num + ", bc_num=" + bc_num + ", m_num=" + m_num + ", liketo="
				+ liketo + ", getBbc_num()=" + getBbc_num() + ", getBc_num()=" + getBc_num() + ", getM_num()="
				+ getM_num() + ", getLiketo()=" + getLiketo() + "]";
	}
	
	
}
