package model;

public class Glamping_ImgFile {

	private int gli_num;
	private int gl_num;
	private int gli_subnume;
	private String gli_imgFileName;
	public int getGli_num() {
		return gli_num;
	}
	public void setGli_num(int gli_num) {
		this.gli_num = gli_num;
	}
	public int getGl_num() {
		return gl_num;
	}
	public void setGl_num(int gl_num) {
		this.gl_num = gl_num;
	}
	public int getGli_subnume() {
		return gli_subnume;
	}
	public void setGli_subnume(int gli_subnume) {
		this.gli_subnume = gli_subnume;
	}
	public String getGli_imgFileName() {
		return gli_imgFileName;
	}
	public void setGli_imgFileName(String gli_imgFileName) {
		this.gli_imgFileName = gli_imgFileName;
	}
	@Override
	public String toString() {
		return "Glamping_ImgFile [gli_num=" + gli_num + ", gl_num=" + gl_num + ", gli_subnume=" + gli_subnume
				+ ", gli_imgFileName=" + gli_imgFileName + ", getGli_num()=" + getGli_num() + ", getGl_num()="
				+ getGl_num() + ", getGli_subnume()=" + getGli_subnume() + ", getGli_imgFileName()="
				+ getGli_imgFileName() + "]";
	}
	
	
}
