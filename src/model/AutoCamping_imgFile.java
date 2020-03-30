package model;

import java.util.Date;

public class AutoCamping_imgFile {	
	private int aci_num;
	private int ac_num;
	private int aci_subNume;
	private String aci_imgFileName;
	private Date writeDate;
	public int getAci_num() {
		return aci_num;
	}
	public void setAci_num(int aci_num) {
		this.aci_num = aci_num;
	}
	public int getAc_num() {
		return ac_num;
	}
	public void setAc_num(int ac_num) {
		this.ac_num = ac_num;
	}
	public int getAci_subNume() {
		return aci_subNume;
	}
	public void setAci_subNume(int aci_subNume) {
		this.aci_subNume = aci_subNume;
	}
	public String getAci_imgFileName() {
		return aci_imgFileName;
	}
	public void setAci_imgFileName(String aci_imgFileName) {
		this.aci_imgFileName = aci_imgFileName;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	@Override
	public String toString() {
		return "AutoCamping_imgFile [aci_num=" + aci_num + ", ac_num=" + ac_num + ", aci_subNum=" + aci_subNume
				+ ", aci_imgFileName=" + aci_imgFileName + ", writeDate=" + writeDate + ", getAci_num()=" + getAci_num()
				+ ", getAc_num()=" + getAc_num() + ", getAci_subNum()=" + getAci_subNume() + ", getAci_imgFileName()="
				+ getAci_imgFileName() + ", getWriteDate()=" + getWriteDate() + "]";
	}
	
	
	
}
