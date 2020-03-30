package model;

public class BackCamping_imgFile {
	
	private int bci_num;
	private int bc_num;
	private int bci_subnum;
	private String bci_imgFileName;
	
	
	public int getBci_num() {
		return bci_num;
	}
	public void setBci_num(int bci_num) {
		this.bci_num = bci_num;
	}
	public int getBc_num() {
		return bc_num;
	}
	public void setBc_num(int bc_num) {
		this.bc_num = bc_num;
	}
	public int getBci_subnum() {
		return bci_subnum;
	}
	public void setBci_subnum(int bci_subnum) {
		this.bci_subnum = bci_subnum;
	}
	public String getBci_imgFileName() {
		return bci_imgFileName;
	}
	public void setBci_imgFileName(String bci_imgFileName) {
		this.bci_imgFileName = bci_imgFileName;
	}
	@Override
	public String toString() {
		return "BackCamping_imgFile [bci_num=" + bci_num + ", bc_num=" + bc_num + ", bci_subnum=" + bci_subnum
				+ ", bci_imgFileName=" + bci_imgFileName + ", getBci_num()=" + getBci_num() + ", getBc_num()="
				+ getBc_num() + ", getBci_subnum()=" + getBci_subnum() + ", getBci_imgFileName()="
				+ getBci_imgFileName() + "]";
	}
	
	
}
